package border.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import border.model.AdminUnitSubordination;

public interface AdminUnitSubordinationRepository extends
		JpaRepository<AdminUnitSubordination, Long>,
		AdminUnitSubordinationRepositoryCustom {

	@Query(value = "select * from AdminUnitSubordination where SubordinateAdminUnitID=(:adminUnitID) and OpenedDate < NOW() and ClosedDate > NOW() limit 1", nativeQuery = true)
	List<AdminUnitSubordination> getMasterActiveNow(
			@Param("adminUnitID") Long adminUnitID);

	@Query(value = "select * from AdminUnitSubordination where MasterAdminUnitID=(:adminUnitID) and OpenedDate < NOW() and ClosedDate > NOW() and FromDate < NOW() and ToDate > NOW()", nativeQuery = true)
	List<AdminUnitSubordination> getSubordinatesActiveNow(
			@Param("adminUnitID") Long adminUnitID);
}
