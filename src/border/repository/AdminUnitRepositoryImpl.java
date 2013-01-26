package border.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnit;

public class AdminUnitRepositoryImpl implements AdminUnitRepositoryCustom {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	//private JpaRepository<AdminUnit, Long> repository;

	@Override
	@Transactional
	public List<AdminUnit> getAdminUnitsOfCertainType(Long adminUnitTypeID,
			String dateString) {
		LOGGER.info("Getting adminunits of type: " + adminUnitTypeID);

		List<AdminUnit> res = new ArrayList<AdminUnit>();
		TypedQuery<AdminUnit> query = entityManager
				.createQuery(
						"select au from AdminUnit au where AdminUnitTypeID = :adminUnitTypeID "
								+ "and OpenedDate < " + dateString + " and ClosedDate > " + dateString
								+ " and FromDate < " + dateString + " and ToDate > " + dateString,
						AdminUnit.class);
		query.setParameter("adminUnitTypeID", adminUnitTypeID);

		res = query.getResultList();

		return res;
	}

}
