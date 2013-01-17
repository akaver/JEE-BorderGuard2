package border.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/*
 * Service for manipulating AdminUnitType's 
 */

@Repository
public class AdminUnitTypeService {

    @PersistenceContext
    private EntityManager em;
	
	
}
