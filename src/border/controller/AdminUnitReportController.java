package border.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import border.model.AdminUnit;
import border.service.AdminUnitService;
import border.service.AdminUnitTypeService;
import border.viewmodel.AdminUnitReportVM;

@Controller
@RequestMapping("/AdminUnitReport")
@SessionAttributes("formData")
public class AdminUnitReportController {

	@Autowired
	AdminUnitService adminUnitService;
	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String AdminUnitReportHome(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID) {
		
		//Make sure we have acceptable ID
		//If problems appear, just move to state view
		Long adminUnitID;
		try {
			adminUnitID = Long.decode(_AdminUnitID);
			if (adminUnitID < 1L) {
				adminUnitID = 1L;
			}
		} catch (Exception e) {
			adminUnitID = 1L;
		}
		
		//Find out which unit type corresponds to the unit shown at main screen dropdown
		AdminUnit au = adminUnitService.getByID(adminUnitID);
		Long adminUnitTypeID = au.getAdminUnitTypeID();
		
		AdminUnitReportVM adminUnitReportVM = populateViewModelWithData(adminUnitTypeID);
		model.addAttribute("formData", adminUnitReportVM);

		return "AdminUnitReport";
	}

	private AdminUnitReportVM populateViewModelWithData(Long adminUnitTypeID) {
		AdminUnitReportVM formData = new AdminUnitReportVM();
		formData.setSearchDate(initializeDate());
		formData.setAdminUnitType(adminUnitTypeService.getByID(
				adminUnitTypeID));
		formData.setAdminUnitTypeList(adminUnitTypeService.findAll());
		formData = setUnitTypeSpecifics(formData);
		
		return formData;
	}

	private String initializeDate() {

		Calendar today = Calendar.getInstance();
		String dayPart = String.valueOf(today.get(Calendar.DATE));
		dayPart = guaranteeTwoNumbers(dayPart);
		String monthPart = String.valueOf(today.get(Calendar.MONTH) + 1);
		monthPart = guaranteeTwoNumbers(monthPart);

		String dateString = dayPart + "." + monthPart + "."
				+ today.get(Calendar.YEAR);
		return dateString;
	}

	// make sure date is two digits long
	private String guaranteeTwoNumbers(String datePart) {
		if (datePart.length() == 1) {
			datePart = "0" + datePart;
		}
		return datePart;
	}
	
	private AdminUnitReportVM setUnitTypeSpecifics(AdminUnitReportVM formData) {

		Long adminUnitTypeID = formData.getAdminUnitType()
				.getAdminUnitTypeID();
		//String dateString = reFormat(formData.getSearchDate());
		String dateString = "NOW()";

		// get the master units, the ones we have chosen from dropdown
		formData.setAdminUnitMasterList(adminUnitService
				.getByAdminUnitTypeID(adminUnitTypeID, dateString));
		
		// for each unit the subordinates list will be filled automatically
		// by JPA one-to-many mapping. time limits are not considered 
		// - just like spec says this time
		
		return formData;
	}

}
