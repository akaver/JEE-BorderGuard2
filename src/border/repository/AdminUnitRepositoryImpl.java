package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AdminUnitRepositoryImpl implements AdminUnitRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
}
