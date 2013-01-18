package border.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import border.model.AdminUnitSubordination;

public interface AdminUnitSubordinationRepository extends
JpaRepository<AdminUnitSubordination, Long>, AdminUnitSubordinationRepositoryCustom{

}
