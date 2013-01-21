package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import border.model.AdminUnitSubordination;
import border.model.AdminUnitType;

public class AdminUnitSubordinationRepositoryImpl implements AdminUnitSubordinationRepositoryCustom {
private static final Logger LOGGER = LoggerFactory.getLogger(AdminUnitSubordinationRepositoryImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private QueryDslJpaRepository<AdminUnitSubordination, Long> repository;



}
