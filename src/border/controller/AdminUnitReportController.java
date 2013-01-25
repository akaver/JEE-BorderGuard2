package border.controller;

import java.util.Calendar;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import border.model.AdminUnit;
import border.repository.AdminUnitRepositoryImpl;
import border.service.AdminUnitService;
import border.service.AdminUnitTypeService;
import border.viewmodel.AdminUnitReportVM;
import border.viewmodel.AdminUnitTypeVM;

@Controller
@RequestMapping("/AdminUnitReport")
@SessionAttributes("formData")
public class AdminUnitReportController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitRepositoryImpl.class);

	@Autowired
	AdminUnitService adminUnitService;
	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	// GET part

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String AdminUnitReportHome(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID) {

		LOGGER.info("Entered admin unit report with ID: " + _AdminUnitID);

		Long adminUnitTypeID = processAndValidateID(_AdminUnitID);
		AdminUnitReportVM adminUnitReportVM = populateViewModelWithData(adminUnitTypeID);
		model.addAttribute("formData", adminUnitReportVM);

		return "AdminUnitReport";
	}

	private Long processAndValidateID(String _AdminUnitID) {

		// Make sure the unit ID is acceptable
		// If problems appear, set up unit nr 1 (state)
		Long adminUnitID;
		try {
			adminUnitID = Long.decode(_AdminUnitID);
			// don't accept under 1, don't accept if not present at DB
			if (adminUnitID < 1L
					|| adminUnitService.getByID(adminUnitID) == null) {
				adminUnitID = 1L;
			}
		} catch (Exception e) {
			adminUnitID = 1L;
		}

		// Find out which unit type we are dealing with
		AdminUnit au = adminUnitService.getByID(adminUnitID);
		Long adminUnitTypeID = au.getAdminUnitTypeID();

		return adminUnitTypeID;
	}

	private AdminUnitReportVM populateViewModelWithData(Long adminUnitTypeID) {
		AdminUnitReportVM formData = new AdminUnitReportVM();
		formData.setSearchDate(initializeDate());
		formData.setAdminUnitType(adminUnitTypeService.getByID(adminUnitTypeID));
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

		Long adminUnitTypeID = formData.getAdminUnitType().getAdminUnitTypeID();
		// String dateString = reFormat(formData.getSearchDate());
		String dateString = "NOW()";

		// get the master units, the ones we have chosen from dropdown
		formData.setAdminUnitMasterList(adminUnitService.getByAdminUnitTypeID(
				adminUnitTypeID, dateString));

		// for each unit the subordinates list will be filled automatically
		// by JPA one-to-many mapping. time limits are not considered
		// - just like spec says this time

		return formData;
	}

	// POST part

	@RequestMapping(value = "/AdminUnitReportForm", method = RequestMethod.POST, params = "BackButton")
	public String cancelChanges(ModelMap model) {
		LOGGER.info("Saw the report, now going back.");
		// jump back to root view
		return "redirect:/";
	}

	@RequestMapping(value = "/AdminUnitReportForm", method = RequestMethod.POST, params = "RefreshButton")
	public String refreshReport(
			ModelMap model,
			@ModelAttribute("formData") AdminUnitReportVM formData,
			BindingResult bindingResult,
			@RequestParam(value = "adminUnitType.adminUnitTypeID") Long adminUnitTypeID) {
		LOGGER.info("Will refresh view for: " + adminUnitTypeID);

		formData = populateViewModelWithData(adminUnitTypeID);
		model.addAttribute("formData", formData);

		// jump back to root view
		return "AdminUnitReport";
	}

}
