package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AdminUnitTypeRepositoryImpl implements AdminUnitTypeRepositoryCustom{
	//private static final Logger LOGGER = LoggerFactory.getLogger(AdminUnitTypeRepositoryImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager;
	
	//private QueryDslJpaRepository<AdminUnitType, Long> repository;


}
