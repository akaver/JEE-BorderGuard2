package border.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;
import border.service.AdminUnitTypeService;
import border.viewmodel.AdminUnitTypeVM;
import java.util.*;

@Controller
@RequestMapping("/AdminUnitType")
// create session
@SessionAttributes("modelLists")
public class AdminUnitTypeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeController.class);

	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID) {
		LOGGER.info("/");

		// set up the amdminUnitID
		Long adminUnitID;
		try {
			adminUnitID = Long.decode(_AdminUnitID);
		} catch (Exception e) {
			adminUnitID = 0L;
		}
		// set up viewmodel for rendering, lets name it formData (its based on
		// HomeVM)
		AdminUnitTypeVM adminUnitTypeVM = populateViewModelWithData(adminUnitID);
		model.addAttribute("formData", adminUnitTypeVM);
		// save the viewmodel aslo into session, so we can get the lists from it
		// later
		model.addAttribute("modelLists", adminUnitTypeVM);

		return "AdminUnitType";
	}

	// only when save button is pressed on the jsp
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.POST, params = "SubmitButton")
	public String saveChanges(
			ModelMap model,
			// get the copy of viewmodel stored in the session
			@ModelAttribute("modelLists") AdminUnitTypeVM modelLists,
			@Valid @ModelAttribute("formData") AdminUnitTypeVM formData,
			BindingResult bindingResult) {
		LOGGER.info("/AdminUnitTypeForm (bindingresult: " + bindingResult + ")");
		LOGGER.info("admin id: {0}", formData.getAdminUnitTypeMasterID());

		if (bindingResult.hasErrors()) {
			RestoreViewModelData(formData, modelLists);
			model.addAttribute("formData", formData);
			return "AdminUnitType";
		}

		// there was no errors, so save everything
		// TODO: save changes
		model.addAttribute("message", "message.ok");
		return "redirect:/AdminUnitType/";
	}

	// only when cancel button is pressed on the jsp
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.POST, params = "CancelButton")
	public String cancelChanges(ModelMap model) {
		LOGGER.info("/cancelChanges - no save, return to root view ");
		// jump back to root view
		return "redirect:/";
	}

	// when AddSubordinate is pressed
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.POST, params = "AddSubordinateButton")
	public String addSubordinates(
			ModelMap model,
			// get the copy of viewmodel stored in the session
			@ModelAttribute("modelLists") AdminUnitTypeVM modelLists,
			@Valid @ModelAttribute("formData") AdminUnitTypeVM formData,
			BindingResult bindingResult,
			@RequestParam(value = "AdminUnitType_NewSubordinateNo") Integer adminUnitType_NewSubordinateNo) {
		LOGGER.info("/addSubordinates (AdminUnitType_NewSubordinateNo: "
				+ adminUnitType_NewSubordinateNo + ")");

		RestoreViewModelData(formData, modelLists);

		// add the select possible candidate to the subordinate list
		// get the list of exsisting subordinates
		List<AdminUnitType> adminUnitTypesSubordinateList = formData
				.getAdminUnitTypesSubordinateList();
		// if list is null, create it
		if (adminUnitTypesSubordinateList == null) {
			adminUnitTypesSubordinateList = new ArrayList<AdminUnitType>();
		}
		// put the new item into the list
		adminUnitTypesSubordinateList.add(formData
				.getAdminUnitTypesSubordinateListPossible().get(
						adminUnitType_NewSubordinateNo));
		// put the list back
		formData.setAdminUnitTypesSubordinateList(adminUnitTypesSubordinateList);
		// remove the item from list of possible candidates
		List<AdminUnitType> adminUnitTypesSubordinateListPossible = formData
				.getAdminUnitTypesSubordinateListPossible();
		adminUnitTypesSubordinateListPossible.remove(formData
				.getAdminUnitTypesSubordinateListPossible().get(
						adminUnitType_NewSubordinateNo));
		// and but it back
		formData.setAdminUnitTypesSubordinateListPossible(adminUnitTypesSubordinateListPossible);

		model.addAttribute("formData", formData);

		if (bindingResult.hasErrors()) {
			return "AdminUnitType";
		}

		// there was no errors, so return to form
		return "redirect:/AdminUnitType/";
	}

	private void RestoreViewModelData(AdminUnitTypeVM formData,
			AdminUnitTypeVM modelLists) {
		// restore data from session to viewmodel
		formData.setAdminUnitTypeMasterList(modelLists
				.getAdminUnitTypeMasterListWithZero());
		formData.setAdminUnitTypesSubordinateList(modelLists
				.getAdminUnitTypesSubordinateList());
		formData.setAdminUnitTypesSubordinateListPossible(modelLists
				.getAdminUnitTypesSubordinateListPossible());
	}

	// all other posts will end up here - this can only be remove subordinate
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.POST)
	public String removeSubordinates(
			ModelMap model, // get the copy of viewmodel stored in the session
			@ModelAttribute("modelLists") AdminUnitTypeVM modelLists,
			@Valid @ModelAttribute("formData") AdminUnitTypeVM formData,
			BindingResult bindingResult, 
			HttpServletRequest request)
	{
		LOGGER.info("/removeSubordinates");

		// jump back to root view
		return "redirect:/AdminUnitType/";
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
				// save the id
				formData.setAdminUnitTypeMasterID(foo.getAdminUnitTypeMaster()
						.getAdminUnitTypeID());
			}
		}

		// load the full list of AdminUnitType
		formData.setAdminUnitTypeMasterListWithZero(adminUnitTypeService
				.findAllExcludingOne(formData.getAdminUnitType()));

		// load the list of subordinates, if it isnt new entity
		if (adminUnitTypeID != 0) {
			formData.setAdminUnitTypesSubordinateList(adminUnitTypeService
					.getSubordinates(formData.getAdminUnitType(), "NOW"));
		}

		// load the list of possible new subordinates
		formData.setAdminUnitTypesSubordinateListPossible(adminUnitTypeService
				.getPossibleSubordinates(formData.getAdminUnitType(), "NOW"));

		return formData;
	}
}
