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

import border.helper.AccessHelper;
import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;
import border.service.AdminUnitTypeService;
import border.viewmodel.AdminUnitTypeVM;
import java.util.*;

@Controller
@RequestMapping("/AdminUnitType")
// create session
@SessionAttributes("formData")
public class AdminUnitTypeController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeController.class);

	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			Model model,
			@RequestParam(required = false, value = "AdminUnitID") String _AdminUnitID) {

		// only admins can access
		if (!AccessHelper.userAuthorized("ROLE_ADMIN")) {
			return "redirect:/";
		}

		LOGGER.info("/");

		// set up the amdminUnitID
		Long adminUnitID;
		try {
			adminUnitID = Long.decode(_AdminUnitID);
			// if there is no unit with such id
			if (adminUnitTypeService.getByID(adminUnitID) == null) {
				adminUnitID = 0L;
			}
		} catch (Exception e) {
			adminUnitID = 0L;
		}
		// set up viewmodel for rendering, lets name it formData (its based on
		// HomeVM). Save it into session
		AdminUnitTypeVM adminUnitTypeVM = populateViewModelWithData(adminUnitID);
		model.addAttribute("formData", adminUnitTypeVM);

		return "AdminUnitType";
	}

	// so that language change GET wouldn't break the pot
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.GET)
	public String handleLanguageChanges() {

		// only admins can access
		if (!AccessHelper.userAuthorized("ROLE_ADMIN")) {
			return "redirect:/";
		}

		return "AdminUnitType";
	}

	// only when save button is pressed on the jsp
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.POST, params = "SubmitButton")
	public String saveChanges(ModelMap model,
			@Valid @ModelAttribute("formData") AdminUnitTypeVM formData,
			BindingResult bindingResult) {
		LOGGER.info("/AdminUnitTypeForm (bindingresult: " + bindingResult + ")");
		LOGGER.info("admin id: " + formData.getAdminUnitTypeMasterID());

		// RestoreViewModelData(formData, modelLists);
		model.addAttribute("formData", formData);

		if (bindingResult.hasErrors()) {
			return "AdminUnitType";
		}

		// there was no errors, so save everything
		SaveEntityChanges(formData);

		return "redirect:/";
	}

	private void SaveEntityChanges(AdminUnitTypeVM formData) {
		// save the changes
		LOGGER.info("SaveEntityChanges");
		// we have to update
		// 1 - basic data
		// 2 - master unit (add or remove)
		// 3 - subordinates (add or remove)

		// save basic changes, if this was new entry then id has now value
		formData.setAdminUnitType(adminUnitTypeService.save(formData
				.getAdminUnitType()));

		// update this units master
		// if master id is 0, then master is removed/nothing
		// if master id != 0, then master is added/updated
		adminUnitTypeService.saveMaster(formData.getAdminUnitType(),
				formData.getAdminUnitTypeMasterID(), "NOW()");

		// update this units subordinates
		// user can remove and add items back and forth
		// find out, what shall we really do - what is changed compared to
		// original list

		// so, load back original list of subordinates from service
		List<AdminUnitType> originalSubordinates = adminUnitTypeService
				.getSubordinates(formData.getAdminUnitType(), "NOW");
		// go through the list of current (on the form) subordinates
		if (formData.getAdminUnitTypesSubordinateList() != null) {
			for (AdminUnitType curSubordinate : formData
					.getAdminUnitTypesSubordinateList()) {
				int status = 0;
				int i = 0;

				// this item is in the original list - do nothing (it wasnt
				// added or removed)
				for (i = 0; i < originalSubordinates.size(); i++) {
					if (originalSubordinates.get(i).getAdminUnitTypeID()
							.equals(curSubordinate.getAdminUnitTypeID())) {
						// so this item from session was in original list
						status = 1;
						break;
					}
				}

				// so this item from session was in original list
				if (status == 1) {
					// nothing to save
					// remove it from originals list
					LOGGER.info("Nothing to do on subordinate:"
							+ originalSubordinates.get(i));
					originalSubordinates.remove(i);
				}

				// so this item wasnt int the originals list, so its new

				if (status == 0) {
					// save it to db, as new subordinate
					System.out.println("Adding new subordinate:"
							+ curSubordinate);

					adminUnitTypeService.addSubordinate(
							formData.getAdminUnitType(), curSubordinate);
				}

			}
		}

		// go over the rest of list, these are removed subordinates
		for (AdminUnitType removeSubordinate : originalSubordinates) {
			System.out.println("Deleting old subordinate:" + removeSubordinate);
			adminUnitTypeService.removeSubordinate(formData.getAdminUnitType(),
					removeSubordinate);
		}

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
			@Valid @ModelAttribute("formData") AdminUnitTypeVM formData,
			BindingResult bindingResult,
			@RequestParam(value = "AdminUnitType_NewSubordinateNo") Integer adminUnitType_NewSubordinateNo) {
		LOGGER.info("/addSubordinates (AdminUnitType_NewSubordinateNo: "
				+ adminUnitType_NewSubordinateNo + ")");
		LOGGER.info("bindingresult: " + bindingResult);

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

		return "AdminUnitType";
	}

	// all other posts will end up here - this can only be to remove subordinate
	@RequestMapping(value = "/AdminUnitTypeForm", method = RequestMethod.POST)
	public String removeSubordinates(ModelMap model,
			@Valid @ModelAttribute("formData") AdminUnitTypeVM formData,
			BindingResult bindingResult, HttpServletRequest request) {
		LOGGER.info("/removeSubordinates");

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith("RemoveButton_")) {
				// found the button from the list, get the id
				Integer removeSubLineNo = Integer.parseInt(paramName
						.substring(13));
				LOGGER.info("Item no to remove: " + removeSubLineNo);

				// get the list
				List<AdminUnitType> adminUnitTypesSubordinateList = formData
						.getAdminUnitTypesSubordinateList();

				// get the item about to be removed, and insert it into
				// possible sublist
				formData.getAdminUnitTypesSubordinateListPossible().add(
						adminUnitTypesSubordinateList.get((int) removeSubLineNo
								.intValue()));

				// remove the item
				adminUnitTypesSubordinateList.remove((int) removeSubLineNo
						.intValue());
				// put the list back
				formData.setAdminUnitTypesSubordinateList(adminUnitTypesSubordinateList);
			}
		}

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

			// you could get the list through jpa, but since we have time
			// constraints - no good
			// for (AdminUnitTypeSubordination foo :
			// formData.getAdminUnitType().getAdminUnitTypeSubordinationSubordinates())
			// {
			// so, you have to query it through service
			formData.setAdminUnitTypeMasterID(adminUnitTypeService
					.getAdminUnitTypeMasterID(formData.getAdminUnitType(),
							"NOW()"));

		}

		// load the possible masters, excluding itself and all childrens under
		// itself
		// so you cant attach this unit into circular reference
		formData.setAdminUnitTypeMasterListWithZero(adminUnitTypeService
				.findAllPossibleMasters(formData.getAdminUnitType()));

		// is it new entity?
		if (adminUnitTypeID != 0) {
			// load the list of subordinates
			formData.setAdminUnitTypesSubordinateList(adminUnitTypeService
					.getSubordinates(formData.getAdminUnitType(), "NOW"));
		}

		// load the list of possible new subordinates
		formData.setAdminUnitTypesSubordinateListPossible(adminUnitTypeService
				.getPossibleSubordinates(formData.getAdminUnitType(), "NOW"));

		return formData;
	}
}
