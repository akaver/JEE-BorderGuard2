package border.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import border.model.AdminUnit;

public interface AdminUnitRepository extends
JpaRepository<AdminUnit, Long>, AdminUnitRepositoryCustom {

}