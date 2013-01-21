package border.repository;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;

public class AdminUnitTypeSubordinationRepositoryImpl implements
		AdminUnitTypeSubordinationRepositoryCustom {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeSubordinationRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	private JpaRepository<AdminUnitTypeSubordination, Long> repository;

	// CREATE MEMORY TABLE PUBLIC.ADMINUNITTYPE(ID BIGINT GENERATED BY DEFAULT
	// AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,CHANGEDBY
	// VARCHAR(255),CHANGEDDATE TIMESTAMP NOT NULL,
	// CLOSEDBY VARCHAR(255),CLOSEDDATE TIMESTAMP NOT NULL,CODE
	// VARCHAR(255),COMMENT VARCHAR(255),FROMDATE TIMESTAMP NOT NULL,NAME
	// VARCHAR(255),OPENEDBY VARCHAR(255),
	// OPENEDDATE TIMESTAMP NOT NULL,TODATE TIMESTAMP NOT NULL)

	// CREATE MEMORY TABLE PUBLIC.ADMINUNITTYPESUBORDINATION(ID BIGINT GENERATED
	// BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,CHANGEDBY
	// VARCHAR(255),CHANGEDDATE TIMESTAMP NOT NULL,
	// CLOSEDBY VARCHAR(255),CLOSEDDATE TIMESTAMP NOT NULL,COMMENT
	// VARCHAR(255),OPENEDBY VARCHAR(255),OPENEDDATE TIMESTAMP NOT
	// NULL,MASTERADMINUNITTYPEID BIGINT,
	// SUBORDINATEADMINUNITTYPEID BIGINT,CONSTRAINT FK1B31BB02B379C6AC FOREIGN
	// KEY(MASTERADMINUNITTYPEID) REFERENCES PUBLIC.ADMINUNITTYPE(ID),
	// CONSTRAINT FK1B31BB02F3E6AC82 FOREIGN KEY(SUBORDINATEADMINUNITTYPEID)
	// REFERENCES PUBLIC.ADMINUNITTYPE(ID))

	@Override
	@Transactional
	public void removeMaster(AdminUnitType adminUnitType,
			Long adminUnitTypeMasterID, String dateTimeString) {
		LOGGER.info("removeMaster AdminUnitType:" + adminUnitType
				+ " adminUnitTypeMasterID:" + adminUnitTypeMasterID
				+ " dateTimeString:" + dateTimeString);
		// select p from Person p where p.name = :name
		// TODO: time criteria is not used in where clause
		String sql = "update AdminUnitTypeSubordination set "
				+ "ChangedBy='Admin', ChangedDate=NOW(), ClosedBy='Admin', ClosedDate=NOW() "
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
			Long adminUnitTypeMasterID, String dateTimeString) {
		// try to update master
		LOGGER.info("addMaster AdminUnitType:" + adminUnitType
				+ " adminUnitTypeMasterID:" + adminUnitTypeMasterID
				+ " dateTimeString:" + dateTimeString);
		// select p from Person p where p.name = :name
		// TODO: time criteria is not used in where clause
		// TODO: user name is fixed
		String sql = "update AdminUnitTypeSubordination set "
				+ "MasterAdminUnitTypeID= :AdminUnitTypeMasterID, ChangedBy='Admin', ChangedDate=NOW() "
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

}
