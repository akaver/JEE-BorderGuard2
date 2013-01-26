package border.repository;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import border.helper.AccessHelper;
import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;

public class AdminUnitTypeSubordinationRepositoryImpl implements
		AdminUnitTypeSubordinationRepositoryCustom {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeSubordinationRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void removeMaster(AdminUnitType adminUnitType,
			Long adminUnitTypeMasterID) {
		LOGGER.info("removeMaster AdminUnitType:" + adminUnitType
				+ " adminUnitTypeMasterID:" + adminUnitTypeMasterID);
		
		String username = AccessHelper.getUserName();
		
		String sql = "update AdminUnitTypeSubordination set "
				+ "ChangedBy='" + username + "', ChangedDate=NOW(), ClosedBy='" + username + "', ClosedDate=NOW() "
				+ "where SubordinateAdminUnitTypeID=:SubordinateAdminUnitTypeID";
		Query query = entityManager.createQuery(sql);
		query.setParameter("SubordinateAdminUnitTypeID",
				adminUnitType.getAdminUnitTypeID());
		int rows = query.executeUpdate();
		LOGGER.info("removeMaster changed rowcount:" + rows);
		// if rows==0, then do nothing (there was no master on this unit)
	}

	@Override
	@Transactional
	public void addMaster(AdminUnitType adminUnitType,
			Long adminUnitTypeMasterID) {
		// try to update master
		LOGGER.info("addMaster AdminUnitType:" + adminUnitType
				+ " adminUnitTypeMasterID:" + adminUnitTypeMasterID);
		// select p from Person p where p.name = :name
		
		String username = AccessHelper.getUserName();
		
		String sql = "update AdminUnitTypeSubordination set "
				+ "MasterAdminUnitTypeID= :AdminUnitTypeMasterID, ChangedBy='" + username + "', ChangedDate=NOW() "
				+ "where SubordinateAdminUnitTypeID= :SubordinateAdminUnitTypeID and ClosedDate>NOW()";
		Query query = entityManager.createQuery(sql);
		query.setParameter("SubordinateAdminUnitTypeID",
				adminUnitType.getAdminUnitTypeID()).setParameter(
				"AdminUnitTypeMasterID", adminUnitTypeMasterID);

		int rows = query.executeUpdate();
		LOGGER.info("removeMaster changed rowcount:" + rows);

		// no master was updated, so we need to insert new one
		if (rows == 0) {
			TypedQuery<AdminUnitType> query2 = entityManager
					.createQuery(
							"select a from AdminUnitType a where a.adminUnitTypeID = :AdminUnitTypeMasterID",
							AdminUnitType.class);
			query2.setParameter("AdminUnitTypeMasterID", adminUnitTypeMasterID);
			AdminUnitType masterAdminUnitType = query2.getSingleResult();
			AdminUnitTypeSubordination master_sub = new AdminUnitTypeSubordination(
					masterAdminUnitType, adminUnitType, "");
			entityManager.persist(master_sub);
		}
	}

	@Override
	@Transactional
	public void removeSubordination(AdminUnitType masterAdminUnitType,
			AdminUnitType subordinateAdminUnitType) {
		LOGGER.info("removeSubordination masterAdminUnitType:" + masterAdminUnitType
				+ " subordinateAdminUnitType:" + subordinateAdminUnitType);
		
		String username = AccessHelper.getUserName();
		
		// find and update datetime fields
		String sql = "update AdminUnitTypeSubordination set "
				+ "ChangedBy='" + username + "', ChangedDate=NOW(), ClosedBy='" + username + "', ClosedDate=NOW() "
				+ "where SubordinateAdminUnitTypeID= :SubordinateAdminUnitTypeID and MasterAdminUnitTypeID=:MasterAdminUnitTypeID and ClosedDate>NOW()";
		Query query = entityManager.createQuery(sql);
		query.setParameter("SubordinateAdminUnitTypeID",
				subordinateAdminUnitType.getAdminUnitTypeID());
		query.setParameter(
				"MasterAdminUnitTypeID", masterAdminUnitType.getAdminUnitTypeID());

		int rows = query.executeUpdate();
		LOGGER.info("removeSubordination changed rowcount:" + rows);
	}

}
