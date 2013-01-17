package border.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import border.service.AdminUnitTypeService;

@Controller
public class HomeController {
	@Autowired
	AdminUnitTypeService adminUnitTypeService;
	
	@Resource
	private MessageSource resources;

	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}

}
