package border.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import border.model.AdminUnit;

public interface AdminUnitRepository extends
JpaRepository<AdminUnit, Long>, AdminUnitRepositoryCustom {
	
	@Query(value="select * from AdminUnit where AdminUnitTypeID in "
			+ "(select SubordinateAdminUnitTypeID from AdminUnitTypeSubordination "
			+ "where AdminUnitTypeID=(:adminUnitID) and "
			+ "OpenedDate < NOW() and ClosedDate > NOW() and FromDate < NOW() and ToDate > NOW())",
			nativeQuery = true)
	List<AdminUnit> getAdminUnitSubordinatesPossible(@Param("adminUnitID") Long adminUnitID);
}