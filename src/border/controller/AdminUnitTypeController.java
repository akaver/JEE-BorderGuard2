package border.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import border.model.AdminUnitType;
import border.repository.AdminUnitTypeRepositoryImpl;
import border.service.AdminUnitTypeService;
import border.viewmodel.AdminUnitTypeVM;

@Controller
@RequestMapping("/AdminUnitType")
public class AdminUnitTypeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeRepositoryImpl.class);

	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model,
			@ModelAttribute AdminUnitTypeVM adminUnitTypeVM) {
		LOGGER.debug("/");
		adminUnitTypeVM = populateViewModelWithData(2L);
		return "AdminUnitType";
	}

	private AdminUnitTypeVM populateViewModelWithData(Long adminUnitTypeID) {
		// create the view model object and populate it with some data, get
		// it through dao
		AdminUnitTypeVM formData = new AdminUnitTypeVM();

		if (adminUnitTypeID == 0) {
			// this is new entity
			formData.setAdminUnitType(new AdminUnitType());
		} else {
			// get the entity from dao
			formData.setAdminUnitType(adminUnitTypeService
					.getByID(adminUnitTypeID));
		}

		/*
		 * // get the master for this AdminUnitType
		 * formData.setAdminUnitTypeMaster(new AdminUnitTypeDAO()
		 * .getMasterByIDWithZero(formData.getAdminUnitType()
		 * .getAdminUnitTypeID()));
		 * 
		 * // load the full list of AdminUnitType // TODO - remove all the
		 * subordinates of itself, otherwise user can // cause circular
		 * reference!!!!!! formData.setAdminUnitTypeMasterListWithZero(new
		 * AdminUnitTypeDAO() .getAll());
		 * 
		 * // load the list of subordinates
		 * formData.setAdminUnitTypesSubordinateList(new AdminUnitTypeDAO()
		 * .getSubordinates(formData.getAdminUnitType() .getAdminUnitTypeID(),
		 * "NOW()"));
		 * 
		 * // load the list of possible new subordinates
		 * formData.setAdminUnitTypesSubordinateListPossible(new
		 * AdminUnitTypeDAO()
		 * .getPossibleSubordinates(formData.getAdminUnitType()
		 * .getAdminUnitTypeID(), "NOW()"));
		 */

		return formData;
	}
}
