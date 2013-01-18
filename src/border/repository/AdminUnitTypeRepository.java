package border.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import border.model.AdminUnitType;

/*
 * Implementing a data access layer of an application has been cumbersome for quite a while. 
 * Too much boilerplate code has to be written to execute simple queries as well as perform pagination, and auditing. 
 * Spring JPA aims to significantly improve the implementation of data access layers by 
 * reducing the effort to the amount that's actually needed. As a developer you write your repository interfaces, 
 * including custom finder methods, and Spring will provide the implementation automatically. 
 * Source: http://www.springsource.org/spring-data/jpa
 * 
 * so the logic goes like this
 * 1 - declare custom interface, where you define your custom methods for jpa
 * 2 - define a class where you implement them cusom interface
 * 3 - declare interface for repository which extends JpaRepository and your custom interface
 * 4 - spring will magicaly create a bean (class) out of it, including methods from both interfaces
 * 5 - use this magic class in your service implementation
 *  
 */

public interface AdminUnitTypeRepository extends
		JpaRepository<AdminUnitType, Long>, AdminUnitTypeRepositoryCustom {


}
