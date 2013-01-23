package border.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import border.model.AdminUnit;
import border.repository.AdminUnitRepositoryImpl;
import border.service.*;
import border.viewmodel.AdminUnitVM;

@Controller
@RequestMapping("/AdminUnit")
@SessionAttributes("formData")
public class AdminUnitController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitRepositoryImpl.class);

	@Autowired
	AdminUnitService adminUnitService;
	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	
	//GET part	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminUnitHome(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID) {
		LOGGER.info("adminUnit home for " + _AdminUnitID);

		// set up the amdminUnitID
		Long adminUnitID;
		try {
			adminUnitID = Long.decode(_AdminUnitID);
		} catch (Exception e) {
			adminUnitID = 0L;
		}

		AdminUnitVM adminUnitVM = populateViewModelWithData(adminUnitID);
		model.addAttribute("formData", adminUnitVM);

		return "AdminUnit";
	}

	private AdminUnitVM populateViewModelWithData(Long adminUnitID) {

		AdminUnitVM formData = new AdminUnitVM();
		formData.setAdminUnitTypeList(adminUnitTypeService.findAll());

		if (adminUnitID == 0) {
			formData.setAdminUnit(new AdminUnit());
		} else {
			// unit itself
			formData.setAdminUnit(adminUnitService.getByID(adminUnitID));

			// current unit type
			formData.setAdminUnitType(adminUnitTypeService.getByID(formData
					.getAdminUnit().getAdminUnitTypeID()));

			// its current master
			formData.setAdminUnitMaster(adminUnitService
					.getAdminUnitMaster(adminUnitID));
			LOGGER.info("Found a master: "
					+ formData.getAdminUnitMaster().getName());

			// possible masters
			formData.setAdminUnitMasterListWithZero(adminUnitService
					.getAllowedMastersByID(formData.getAdminUnitType()
							.getAdminUnitTypeID()), formData
					.getAdminUnitMaster());

			// its current slaves
			formData.setAdminUnitsSubordinateList(adminUnitService
					.getAdminUnitSubordinates(adminUnitID));

			for (AdminUnit sub : formData.getAdminUnitsSubordinateList()) {
				LOGGER.info("Found a slave: " + sub.getName());
			}

			// those that might still be enslaved
			formData.setAdminUnitsSubordinateListPossible(adminUnitService
					.getAdminUnitSubordinatesPossible(formData.getAdminUnit()
							.getAdminUnitID(), formData.getAdminUnit()
							.getAdminUnitTypeID()));

			for (AdminUnit sub : formData
					.getAdminUnitsSubordinateListPossible()) {
				LOGGER.info("A possible slave: " + sub.getName());
			}

			// initate list of slaves that might be freed
			formData.setAdminUnitsSubordinateListRemoved(new ArrayList<AdminUnit>());
		}

		return formData;
	}
	
	
	// POST part
	
	
	@RequestMapping(value = "/AdminUnitForm", method = RequestMethod.POST, params = "CancelButton")
	public String cancelChanges(ModelMap model) {
		LOGGER.info("/cancelChanges - no save, return to root view ");
		// jump back to root view
		return "redirect:/";
	}
	
//	@RequestMapping(value = "/AdminUnitForm", method = RequestMethod.POST, params = )
}
