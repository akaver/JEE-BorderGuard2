package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import border.helper.AccessHelper;
import border.model.AdminUnit;
import border.model.AdminUnitSubordination;

public class AdminUnitSubordinationRepositoryImpl implements
		AdminUnitSubordinationRepositoryCustom {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitSubordinationRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void removeSubordination(Long adminUnitSubordinateID) {
		LOGGER.info("removeSubordination slaveID:" + adminUnitSubordinateID);
		
		String username = AccessHelper.getUserName();

		String sql = "update AdminUnitSubordination set " +
				"ChangedBy='" + username + "', ChangedDate=NOW(), ClosedBy='" + username + "', ClosedDate=NOW(), ToDate=NOW() " +
				"where SubordinateAdminUnitID=:subordinateAdminUnitID " +
				"and OpenedDate < NOW() and ClosedDate > NOW() and FromDate < NOW() and ToDate > NOW()";
		Query query = entityManager.createQuery(sql);

		query.setParameter("subordinateAdminUnitID", adminUnitSubordinateID);

		int rows = query.executeUpdate();
		LOGGER.info("removeMaster changed rowcount:" + rows);
		// if rows==0, then do nothing (there was no master on this unit)
	}

	@Override
	@Transactional
	public void updateOrAddSubordination(Long adminUnitSubordinateID,
			Long adminUnitMasterID) {
		
		// try to update master for this particular slave
		LOGGER.info("updateOrAddSubordination slaveID:" + adminUnitSubordinateID
				+ " masterID:" + adminUnitMasterID);
		
		String username = AccessHelper.getUserName();
		
		String sql = "update AdminUnitSubordination set " +
				"MasterAdminUnitID= :adminUnitMasterID, ChangedBy='" + username + "', ChangedDate=NOW() " +
				"where SubordinateAdminUnitID=:subordinateAdminUnitID and " +
				"OpenedDate < NOW() and ClosedDate > NOW() and FromDate < NOW() and ToDate > NOW()";
		
		Query query = entityManager.createQuery(sql);
		query.setParameter("subordinateAdminUnitID", adminUnitSubordinateID);
		query.setParameter("adminUnitMasterID", adminUnitMasterID);

		int rows = query.executeUpdate();
		LOGGER.info("updateOrAddSubordination changed rowcount:" + rows);

		// no master was updated, so we need to insert new one
		// since relation is based not only on id-s, but on objects, we shall fetch some real objects
		if (rows == 0) {
			TypedQuery<AdminUnit> query2 = entityManager
					.createQuery(
							"select a from AdminUnit a where a.id = :adminUnitSubordinateID and " +
							"OpenedDate < NOW() and ClosedDate > NOW() and FromDate < NOW() and ToDate > NOW()",
							AdminUnit.class);
			query2.setParameter("adminUnitSubordinateID", adminUnitSubordinateID);
			AdminUnit adminUnitSubordinate = query2.getSingleResult();			
			
			TypedQuery<AdminUnit> query3 = entityManager
					.createQuery(
							"select a from AdminUnit a where a.id = :adminUnitMasterID and " +
							"OpenedDate < NOW() and ClosedDate > NOW() and FromDate < NOW() and ToDate > NOW()",
							AdminUnit.class);
			query3.setParameter("adminUnitMasterID", adminUnitMasterID);
			AdminUnit adminUnitMaster = query3.getSingleResult();
			
			AdminUnitSubordination master_sub = new AdminUnitSubordination(
					adminUnitMaster, adminUnitSubordinate, "");
			entityManager.persist(master_sub);
		}
	}

}
