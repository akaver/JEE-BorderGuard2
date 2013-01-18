package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import border.model.AdminUnitTypeSubordination;

public class AdminUnitTypeSubordinationRepositoryImpl implements AdminUnitTypeSubordinationRepositoryCustom{
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminUnitTypeRepositoryImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private QueryDslJpaRepository<AdminUnitTypeSubordination, Long> repository;

	@Override
	public AdminUnitTypeSubordination findById(Long id) {
		LOGGER.debug("findById");
		return repository.findOne(id);
	}

}
