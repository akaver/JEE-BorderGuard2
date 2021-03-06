package border.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
 * 2 - define a class where you implement the cusom interface
 * 3 - declare interface for repository which extends JpaRepository and your custom interface
 * 4 - spring will magicaly create a bean (class) out of it, including methods from both interfaces
 * 5 - use this magic class in your service implementation
 *  
 */

public interface AdminUnitTypeRepository extends
		JpaRepository<AdminUnitType, Long>, AdminUnitTypeRepositoryCustom {

	// this is hideous, ugly and that's the way freaking hsqldb is working
	@Query(value = "SELECT * FROM   adminunittype "
			+ "WHERE  id IN (SELECT adminunittypeid "
			+ "              FROM   (SELECT adminunittype.id AS adminUnitTypeID, "
			+ "                             CASE "
			+ "                               WHEN masteradminunittypeid IS NULL THEN -1 "
			+ "                               ELSE 1  "
			+ "                             END              AS idNull "
			+ "                      FROM   adminunittype  "
			+ "                             LEFT JOIN (SELECT * "
			+ "                                        FROM   adminunittypesubordination "
			+ "                                        WHERE  closeddate > Now()) AS "
			+ "                                       AdminUnitTypeSubordinationTemp "
			+ "                                    ON adminunittype.id = "
			+ "             AdminUnitTypeSubordinationTemp.subordinateadminunittypeid "
			// this here should be id of first adminunittype id in db (ie id of
			// state)
			// state can not be subunit of anything
			+ "             WHERE  adminunittype.id not in (select id from adminunittype order by id asc limit 1) "
			+ "             AND adminunittype.id <> (:adminUnitTypeID)  "
			+ "             AND adminunittype.closeddate > Now() "
			+ "             AND adminunittype.todate > Now()) AS templist "
			+ "              WHERE  templist.idnull = -1) ", nativeQuery = true)
	List<AdminUnitType> findSubordinatesPossibleActiveNow(
			@Param("adminUnitTypeID") Long adminUnitTypeID);

	// return only list of these units, where current unit can be attached
	// as subunit
	// i.e exclude itself and all children of itself to avoid circular reference
	@Query(value = "select * from adminunittype where id not in ( "
			+ "with recursive link_tree as ( "
			+ "select MASTERADMINUNITTYPEID, SUBORDINATEADMINUNITTYPEID "
			+ "from adminunittypesubordination "
			+ "where MASTERADMINUNITTYPEID = (:adminUnitTypeID) "
			+ "union all  "
			+ "select c.MASTERADMINUNITTYPEID, c.SUBORDINATEADMINUNITTYPEID "
			+ "from adminunittypesubordination c "
			+ "join link_tree p on p.SUBORDINATEADMINUNITTYPEID = c.MASTERADMINUNITTYPEID) "
			+ "select SUBORDINATEADMINUNITTYPEID " + "from link_tree) "
			+ "and " + "id<>(:adminUnitTypeID)", nativeQuery = true)
	List<AdminUnitType> findAllPossibleMasters(
			@Param("adminUnitTypeID") Long adminUnitTypeID);
}
