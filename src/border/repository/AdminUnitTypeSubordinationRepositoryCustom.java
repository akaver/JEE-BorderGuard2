package border.repository;


import border.model.*;

/*
 * declare an interface for custom-add on methods for repository
 * then implement them on some class
 */
public interface AdminUnitTypeSubordinationRepositoryCustom {
	public void removeMaster(AdminUnitType adminUnitType, Long adminUnitTypeMasterID);

	public void addMaster(AdminUnitType adminUnitType, Long adminUnitTypeMasterID);
	
	public void removeSubordination(AdminUnitType masterAdminUnitType,
			AdminUnitType subordinateAdminUnitType);
	
}
