package border.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import border.repository.AdminUnitTypeRepositoryImpl;
import border.service.AdminUnitTypeService;
import border.service.AdminUnitService;

@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminUnitTypeRepositoryImpl.class);

	@Autowired
	AdminUnitTypeService adminUnitTypeService;
	@Autowired
	AdminUnitService adminUnitService;
	
	@Resource
	private MessageSource resources;

	@RequestMapping(value = "/")
	public String home(Model model) {
		LOGGER.debug("homepage");
		model.addAttribute("adminUnitTypeList", adminUnitTypeService.findAll());

		return "home";
	}
	
	@RequestMapping(value = "/populate")
	public String populate(Model model) {
		LOGGER.debug("Populating data");
		adminUnitTypeService.populateData();
		//adminUnitService.deleteAll();
		adminUnitService.populateData();
		
		return "home";
	}
	

}
