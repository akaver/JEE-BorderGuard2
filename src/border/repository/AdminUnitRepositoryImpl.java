package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import border.model.AdminUnit;

public class AdminUnitRepositoryImpl implements AdminUnitRepositoryCustom {
private static final Logger LOGGER = LoggerFactory.getLogger(AdminUnitRepositoryImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private QueryDslJpaRepository<AdminUnit, Long> repository;

	@Override
	public AdminUnit findById(Long id) {
		LOGGER.debug("findById");
		return repository.findOne(id);
	}
}
