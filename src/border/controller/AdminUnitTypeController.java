package border.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;
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
	public String home(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID) {
		LOGGER.info("/");
		
		Long adminUnitID;
		try{
			adminUnitID = Long.decode(_AdminUnitID);
		} catch( Exception e) {
			adminUnitID = 0L;
		}
		
		model.addAttribute("formData", populateViewModelWithData(adminUnitID));
		return "AdminUnitType";
	}

	private AdminUnitTypeVM populateViewModelWithData(Long adminUnitTypeID) {
		// create the view model object and populate it with some data, get
		// it through service
		AdminUnitTypeVM formData = new AdminUnitTypeVM();

		if (adminUnitTypeID == 0) {
			// this is new entity
			formData.setAdminUnitType(new AdminUnitType());
		} else {
			// get the entity from dao
			formData.setAdminUnitType(adminUnitTypeService
					.getByID(adminUnitTypeID));
			// get the adminUnitTypeSubordinationMasters
			// so this list contains items, where this unit acts as Master - so
			// the list is filled with slaves
			// master(this unit)-slave1
			// master(this unit)-slave2

			for (AdminUnitTypeSubordination foo : formData.getAdminUnitType()
					.getAdminUnitTypeSubordinationMasters()) {
				System.out
						.println("getAdminUnitTypeSubordinationMasters comment "
								+ foo.getComment());
				System.out
						.println("getAdminUnitTypeSubordinationMasters master "
								+ foo.getAdminUnitTypeMaster().getName());
				System.out
						.println("getAdminUnitTypeSubordinationMasters subordinate "
								+ foo.getAdminUnitTypeSubordinate().getName());
			}
			// get the adminUnitTypeSubordinationSubordinates
			// so this list contains items, where this unit acts as Slave - so
			// the list is filled with masters (there can be only one in any
			// specific timeperiod)
			// master-slave(this unit)
			for (AdminUnitTypeSubordination foo : formData.getAdminUnitType()
					.getAdminUnitTypeSubordinationSubordinates()) {
				System.out
						.println("getAdminUnitTypeSubordinationSubordinates comment "
								+ foo.getComment());
				System.out
						.println("getAdminUnitTypeSubordinationSubordinates master "
								+ foo.getAdminUnitTypeMaster().getName());
				System.out
						.println("getAdminUnitTypeSubordinationSubordinates subordinate "
								+ foo.getAdminUnitTypeSubordinate().getName());
				// so we hope, that this was the one!
				formData.setAdminUnitTypeMaster(foo.getAdminUnitTypeMaster());
			}
		}

		// load the full list of AdminUnitType
		// TODO - remove all the subordinates of itself, otherwise user can
		// cause circular reference (its taken care in jsp render for now)
		formData.setAdminUnitTypeMasterListWithZero(adminUnitTypeService
				.findAll());

		// load the list of subordinates
		formData.setAdminUnitTypesSubordinateList(adminUnitTypeService
				.getSubordinates(formData.getAdminUnitType(), "NOW"));
		// load the list of possible new subordinates
		formData.setAdminUnitTypesSubordinateListPossible(adminUnitTypeService
				.getPossibleSubordinates(formData.getAdminUnitType(), "NOW"));

		return formData;
	}
}
