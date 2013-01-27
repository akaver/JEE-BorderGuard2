package border.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import border.model.AdminUnitTypeSubordination;

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

public interface AdminUnitTypeSubordinationRepository extends
		JpaRepository<AdminUnitTypeSubordination, Long>,
		AdminUnitTypeSubordinationRepositoryCustom {

	@Query(value = "select * from AdminUnitTypeSubordination  "
			+ "where MasterAdminUnitTypeID=(:adminUnitTypeID) "
			+ "and OpenedDate < NOW() and ClosedDate > NOW()", nativeQuery = true)
	List<AdminUnitTypeSubordination> findSubordinatesActiveNow(
			@Param("adminUnitTypeID") Long adminUnitTypeID);

	@Query(value = "select * from AdminUnitTypeSubordination  "
			+ "where SubordinateAdminUnitTypeID=(:adminUnitTypeID) "
			+ "and OpenedDate < NOW() and ClosedDate > NOW() limit 1", nativeQuery = true)
	List<AdminUnitTypeSubordination> getMasterActiveNow(
			@Param("adminUnitTypeID") Long adminUnitTypeID);

}
