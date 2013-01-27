package border.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import border.helper.AccessHelper;
import border.helper.DBHelper;
import border.model.AdminUnit;
import border.service.AdminUnitTypeService;
import border.service.AdminUnitService;
import border.viewmodel.HomeVM;

@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	AdminUnitTypeService adminUnitTypeService;
	@Autowired
	AdminUnitService adminUnitService;

	@Resource
	private MessageSource resources;

	@RequestMapping(value = "/")
	public String home(Model model) {
		LOGGER.info("homepage");

		HomeVM formData = new HomeVM();
		formData.setAdminUnitList(adminUnitService.findAll());
		formData.setAdminUnitTypeList(adminUnitTypeService.findAll());
		model.addAttribute("formData", formData);

		return "home";
	}

	@RequestMapping(value = "/populate")
	public String populate(Model model) {

		// only admins can access
		if (!AccessHelper.userAuthorized("ROLE_ADMIN")) {
			return "redirect:/";
		}
		
		LOGGER.info("Populating data");
		DBHelper.TruncateDB();
		adminUnitTypeService.populateData();
		adminUnitService.populateData();

		return "redirect:/";
	}

	@RequestMapping(value = "/homeActivityForm", method = RequestMethod.POST)
	public String redirectView(
			@RequestParam(required = false, value = "ViewAdminUnitType") String _ViewAdminUnitTypeFlag,
			@RequestParam(required = false, value = "AddAdminUnitType") String _AddAdminUnitTypeFlag,
			@RequestParam(required = false, value = "ReportAdminUnitType") String _ReportAdminUnitTypeFlag,
			@RequestParam(required = false, value = "ViewAdminUnit") String _ViewAdminUnitFlag,
			@RequestParam(required = false, value = "AddAdminUnit") String _AddAdminUnitFlag,
			@RequestParam(required = false, value = "ReportAdminUnit") String _ReportAdminUnitFlag,
			@RequestParam(required = false, value = "AdminUnitTypeID") String _AdminUnitTypeID,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID

	) {
		LOGGER.info("redirectView");
		String viewToLoad = "/";
		if (_ViewAdminUnitTypeFlag != null) {
			viewToLoad = "/AdminUnitType/?AdminUnitID=" + _AdminUnitTypeID;
		}
		if (_AddAdminUnitTypeFlag != null) {
			viewToLoad = "/AdminUnitType/?AdminUnitID=0";
		}
		if (_ReportAdminUnitTypeFlag != null) {
			viewToLoad = "/AdminUnitTypeReport/";
		}
		if (_ViewAdminUnitFlag != null) {
			viewToLoad = "/AdminUnit/?AdminUnitID=" + _AdminUnitID;
		}
		if (_AddAdminUnitFlag != null) {
			viewToLoad = "/AdminUnit/?AdminUnitID=0";
		}
		if (_ReportAdminUnitFlag != null) {

			// first find admin unit type
			Long adminUnitTypeID = makeTypeIdFromUnitId(_AdminUnitID);
			viewToLoad = "/AdminUnitReport/?AdminUnitTypeID=" + adminUnitTypeID;
		}

		return "redirect:" + viewToLoad;

	}

	private Long makeTypeIdFromUnitId(String _AdminUnitID) {

		// Make sure the unit ID is acceptable
		// If problems appear, set up unit type nr 1 (should be state, but no
		// difference)
		Long adminUnitID;
		try {
			adminUnitID = Long.decode(_AdminUnitID);
			// don't accept under 1, don't accept if not present at DB
			if (adminUnitID < 1L
					|| adminUnitService.getByID(adminUnitID) == null) {
				adminUnitID = 1L;
			}
		} catch (Exception e) {
			// if some non-numeric stuff is entered
			adminUnitID = 1L;
		}

		// Find out which unit type we are dealing with
		AdminUnit au = adminUnitService.getByID(adminUnitID);
		Long adminUnitTypeID = au.getAdminUnitTypeID();

		return adminUnitTypeID;
	}

}
