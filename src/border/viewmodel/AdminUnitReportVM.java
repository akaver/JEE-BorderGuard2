package border.viewmodel;

import java.util.List;

import border.model.AdminUnit;
import border.model.AdminUnitType;

public class AdminUnitReportVM {
	// the date on which subordinates had to be present
	private String searchDate;

	// the current unit type
	private AdminUnitType adminUnitType;

	// list of adminUnitTypes, for choosing new type
	private List<AdminUnitType> adminUnitTypeList;

	// list of adminunits that we have chosen
	private List<AdminUnit> adminUnitMasterList;

	// the subordinate we need to display info about,
	// after pressing "Look"/"Vaata"
	private AdminUnit chosenSubordinate;

	// some fields for dialog - not to create mess to entity file
	private String adminUnitTypeName;
	private String adminUnitMasterName;
	
	private String username;

	public AdminUnitType getAdminUnitType() {
		return adminUnitType;
	}

	public void setAdminUnitType(AdminUnitType adminUnitType) {
		this.adminUnitType = adminUnitType;
	}

	public List<AdminUnitType> getAdminUnitTypeList() {
		return adminUnitTypeList;
	}

	public void setAdminUnitTypeList(List<AdminUnitType> adminUnitTypeList) {
		this.adminUnitTypeList = adminUnitTypeList;
	}

	public List<AdminUnit> getAdminUnitMasterList() {
		return adminUnitMasterList;
	}

	public void setAdminUnitMasterList(List<AdminUnit> adminUnitMasterList) {
		this.adminUnitMasterList = adminUnitMasterList;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public AdminUnit getChosenSubordinate() {
		return chosenSubordinate;
	}

	public void setChosenSubordinate(AdminUnit chosenSubOrdinate) {
		this.chosenSubordinate = chosenSubOrdinate;
	}

	public String getAdminUnitTypeName() {
		return adminUnitTypeName;
	}

	public void setAdminUnitTypeName(String adminUnitTypeName) {
		this.adminUnitTypeName = adminUnitTypeName;
	}

	public String getAdminUnitMasterName() {
		return adminUnitMasterName;
	}

	public void setAdminUnitMasterName(String adminUnitMasterName) {
		this.adminUnitMasterName = adminUnitMasterName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
