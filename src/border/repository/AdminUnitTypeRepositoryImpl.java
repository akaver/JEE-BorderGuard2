package border.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AdminUnitTypeRepositoryImpl implements AdminUnitTypeRepositoryCustom{
	
	@PersistenceContext
    private EntityManager entityManager;


}
