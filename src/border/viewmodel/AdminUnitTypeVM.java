package border.viewmodel;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import border.model.AdminUnitType;

public class AdminUnitTypeVM {
	// adminunittype we are editing
	// force validation on referenced object
	@Valid
	private AdminUnitType adminUnitType;
	
	// adminunittype which is master for adminUnitType
	private  AdminUnitType adminUnitTypeMaster;

	// ID of adminunittype which is master for current adminUnitType
	private Long adminUnitTypeMasterID;
	
	// list of adminUnitTypes, for dropdown
	private  List<AdminUnitType> adminUnitTypeMasterListWithZero;
	
	// list of adminunittypes which are subordinates to adminUnitType
	private List<AdminUnitType> adminUnitTypesSubordinateList;
	
	// list of adminunittypes which are possible new subordinates to adminUnitType
	private List<AdminUnitType> adminUnitTypesSubordinateListPossible;

	public AdminUnitType getAdminUnitType() {
		return adminUnitType;
	}

	public void setAdminUnitType(AdminUnitType adminUnitType) {
		this.adminUnitType = adminUnitType;
	}

	public AdminUnitType getAdminUnitTypeMaster() {
		return adminUnitTypeMaster;
	}

	public void setAdminUnitTypeMaster(AdminUnitType adminUnitTypeMaster) {
		this.adminUnitTypeMaster = adminUnitTypeMaster;
	}

	public List<AdminUnitType> getAdminUnitTypeMasterListWithZero() {
		return adminUnitTypeMasterListWithZero;
	}
	
	// do not add "---" in front
	public void setAdminUnitTypeMasterList(List<AdminUnitType> adminUnitTypeMasterListWithZero){
		this.adminUnitTypeMasterListWithZero = adminUnitTypeMasterListWithZero;
	}
	
	// do add "---" in front
	public void setAdminUnitTypeMasterListWithZero(
			List<AdminUnitType> adminUnitTypeMasterListWithZero) {
		// add default row into first position

		//resultant list
		List<AdminUnitType> res = new ArrayList<AdminUnitType>();
		
		// if there was no list, create it
		if (adminUnitTypeMasterListWithZero == null) {
			adminUnitTypeMasterListWithZero = new ArrayList<AdminUnitType>();
		}
		
		// create new AdminUnitType
		AdminUnitType withZero = new AdminUnitType();
		// set id to 0
		withZero.setAdminUnitTypeID(0L);
		// and name to "---"
		withZero.setName("---");
		// append it to resultant list
		res.add(withZero);
		
		for (AdminUnitType adminUnitTypeFromList : adminUnitTypeMasterListWithZero) {	
			res.add(adminUnitTypeFromList);
		}		
		
		this.adminUnitTypeMasterListWithZero = res;		
	}

	public List<AdminUnitType> getAdminUnitTypesSubordinateList() {
		return adminUnitTypesSubordinateList;
	}

	public void setAdminUnitTypesSubordinateList(
			List<AdminUnitType> adminUnitTypesSubordinateList) {
		this.adminUnitTypesSubordinateList = adminUnitTypesSubordinateList;
	}

	public List<AdminUnitType> getAdminUnitTypesSubordinateListPossible() {
		return adminUnitTypesSubordinateListPossible;
	}

	public void setAdminUnitTypesSubordinateListPossible(
			List<AdminUnitType> adminUnitTypesSubordinateListPossible) {
		this.adminUnitTypesSubordinateListPossible = adminUnitTypesSubordinateListPossible;
	}

	public Long getAdminUnitTypeMasterID() {
		return adminUnitTypeMasterID;
	}

	public void setAdminUnitTypeMasterID(Long adminUnitTypeMasterID) {
		this.adminUnitTypeMasterID = adminUnitTypeMasterID;
	}

	
}
