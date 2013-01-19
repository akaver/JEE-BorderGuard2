package border.viewmodel;

import java.util.List;

import border.model.AdminUnit;
import border.model.AdminUnitType;

public class HomeVM {
	private List<AdminUnitType> adminUnitTypeList;
	private List<AdminUnit> adminUnitList;

	public List<AdminUnitType> getAdminUnitTypeList() {
		return adminUnitTypeList;
	}

	public void setAdminUnitTypeList(List<AdminUnitType> adminUnitTypeList) {
		this.adminUnitTypeList = adminUnitTypeList;
	}

	public List<AdminUnit> getAdminUnitList() {
		return adminUnitList;
	}

	public void setAdminUnitList(List<AdminUnit> adminUnitList) {
		this.adminUnitList = adminUnitList;
	}
}
