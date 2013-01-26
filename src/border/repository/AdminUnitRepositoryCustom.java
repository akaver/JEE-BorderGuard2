package border.repository;

import java.util.List;

import border.model.AdminUnit;

public interface AdminUnitRepositoryCustom {

	List<AdminUnit> getAdminUnitsOfCertainType(Long adminUnitTypeID);
}
