package border.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

import border.model.AdminUnitType;

/*
 * Implementing a data access layer of an application has been cumbersome for quite a while. 
 * Too much boilerplate code has to be written to execute simple queries as well as perform pagination, and auditing. 
 * Spring JPA aims to significantly improve the implementation of data access layers by 
 * reducing the effort to the amount that's actually needed. As a developer you write your repository interfaces, 
 * including custom finder methods, and Spring will provide the implementation automatically. 
 * Source: http://www.springsource.org/spring-data/jpa
 */
@Resource
@Transactional(readOnly = true)
public interface AdminUnitTypeRepository extends
		JpaRepository<AdminUnitType, Long> {
	
	public AdminUnitType findById(Long id);

}
