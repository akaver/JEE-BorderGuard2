package border.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import border.model.AdminUnit;
import border.repository.AdminUnitRepositoryImpl;
import border.service.AdminUnitService;
import border.viewmodel.AdminUnitVM;

@Controller
@RequestMapping("/AdminUnit")
@SessionAttributes("formData")
public class AdminUnitController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitRepositoryImpl.class);
	
	@Autowired
	AdminUnitService adminUnitService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminUnitHome(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID
			) {
		LOGGER.info("adminUnit home for " + _AdminUnitID);
		
		// set up the amdminUnitID
		Long adminUnitID;
		try{
			adminUnitID = Long.decode(_AdminUnitID);
		} catch( Exception e) {
			adminUnitID = 0L;
		}
		
		AdminUnitVM adminUnitVM = populateViewModelWithData(adminUnitID);
		model.addAttribute("formData", adminUnitVM);
		
		return "AdminUnit";	
	}

	private AdminUnitVM populateViewModelWithData(Long adminUnitID) {
		
		AdminUnitVM formData = new AdminUnitVM();
		
		if (adminUnitID == 0) {
			formData.setAdminUnit(new AdminUnit());
		}
		else {
			formData.setAdminUnit(adminUnitService.getByID(adminUnitID));
			formData.setAdminUnitMaster(adminUnitService.getAdminUnitMaster(adminUnitID));
			formData.setAdminUnitsSubordinateList(adminUnitService.getAdminUnitSubordinates(adminUnitID));
		}
		
		return formData;
	}
}
