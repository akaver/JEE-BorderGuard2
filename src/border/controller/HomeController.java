package border.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import border.helper.DBHelper;
import border.repository.AdminUnitTypeRepositoryImpl;
import border.service.AdminUnitTypeService;
import border.service.AdminUnitService;
import border.viewmodel.HomeVM;

@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeRepositoryImpl.class);

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
		LOGGER.info("Populating data");

		//adminUnitService.deleteAll();
		//adminUnitTypeService.deleteAll();
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
			viewToLoad = "/AdminUnitType/?AdminUnitID="+_AdminUnitTypeID;
		}
		if (_AddAdminUnitTypeFlag != null) {
			viewToLoad = "/AdminUnitType/?AdminUnitID=0";
		}
		if (_ReportAdminUnitTypeFlag != null) {
			viewToLoad = "/AdminUnitTypeReport/";
		}
		// TODO: Make forwards
		if (_ViewAdminUnitFlag != null) {
			viewToLoad = "/";
		}
		if (_AddAdminUnitFlag != null) {
			viewToLoad = "/";
		}
		if (_ReportAdminUnitFlag != null) {
			viewToLoad = "/";
		}

		return "redirect:"+viewToLoad;

	}

}
