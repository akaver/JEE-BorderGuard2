package border.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;
import border.repository.*;

@Service
public class AdminUnitTypeService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeService.class);

	// here are our database repos
	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;
	@Autowired
	private AdminUnitTypeSubordinationRepository adminUnitTypeSubordinationRepository;

	@Transactional
	public List<AdminUnitType> findAll() {
		LOGGER.info("Find all adminunittypes");
		return adminUnitTypeRepository.findAll();
	}

	@Transactional
	public List<AdminUnitType> findAllPossibleMasters(
			AdminUnitType adminUnitType) {
		LOGGER.info("findAllExcludingOne (" + adminUnitType + ")");

		if (adminUnitType == null) {
			return adminUnitTypeRepository.findAll();
		}
		return adminUnitTypeRepository.findAllPossibleMasters(adminUnitType
				.getAdminUnitTypeID());
	}

	@Transactional
	public void deleteAll() {
		LOGGER.info("Delete all adminunittypes and subordinations");
		// this will not reset the autoincrement fields, use DBHelper truncate
		// functionality
		adminUnitTypeSubordinationRepository.deleteAll();
		adminUnitTypeRepository.deleteAll();
	}

	@Transactional
	public void populateData() {
		LOGGER.info("populateData for adminunittypes");

		AdminUnitType master = new AdminUnitType("0", "Riik", "0");
		AdminUnitType sub1 = new AdminUnitType("1", "Maakond", "1");
		AdminUnitType sub11 = new AdminUnitType("11", "Maakonna linn", "11");
		AdminUnitType sub12 = new AdminUnitType("12", "Vald", "12");
		AdminUnitType sub2 = new AdminUnitType("2", "Alev", "2");
		AdminUnitType sub3 = new AdminUnitType("2", "Talu", "2");
		AdminUnitType sub4 = new AdminUnitType("2", "Küla", "2");
		adminUnitTypeRepository.save(master);
		adminUnitTypeRepository.save(sub1);
		adminUnitTypeRepository.save(sub11);
		adminUnitTypeRepository.save(sub12);
		adminUnitTypeRepository.save(sub2);
		adminUnitTypeRepository.save(sub3);
		adminUnitTypeRepository.save(sub4);

		AdminUnitTypeSubordination master_sub1 = new AdminUnitTypeSubordination(
				master, sub1, "riik->maakond");
		AdminUnitTypeSubordination sub1_sub11 = new AdminUnitTypeSubordination(
				sub1, sub11, "maakond->maakonna_linn");
		AdminUnitTypeSubordination sub1_sub12 = new AdminUnitTypeSubordination(
				sub1, sub12, "maakond->vald");
		AdminUnitTypeSubordination sub12_sub4 = new AdminUnitTypeSubordination(
				sub12, sub4, "vald->küla");

		master_sub1 = adminUnitTypeSubordinationRepository.save(master_sub1);
		sub1_sub11 = adminUnitTypeSubordinationRepository.save(sub1_sub11);
		sub1_sub12 = adminUnitTypeSubordinationRepository.save(sub1_sub12);
		sub12_sub4 = adminUnitTypeSubordinationRepository.save(sub12_sub4);

	}

	@Transactional
	public AdminUnitType getByID(Long adminUnitTypeID) {
		LOGGER.info("getByID:", adminUnitTypeID);
		AdminUnitType res = new AdminUnitType();
		res = adminUnitTypeRepository.findOne(adminUnitTypeID);
		return res;
	}

	public List<AdminUnitType> getSubordinates(AdminUnitType adminUnitType) {
		LOGGER.info("getSubordinates for: "
				+ adminUnitType.getAdminUnitTypeID());

		// get the subordination structrure
		List<AdminUnitTypeSubordination> subordination = adminUnitTypeSubordinationRepository
				.findSubordinatesActiveNow(adminUnitType.getAdminUnitTypeID());
		// get the subordinate items
		List<AdminUnitType> res = new ArrayList<AdminUnitType>();
		for (AdminUnitTypeSubordination item : subordination) {
			res.add(item.getAdminUnitTypeSubordinate());
		}

		return res;
	}

	public List<AdminUnitType> getPossibleSubordinates(
			AdminUnitType adminUnitType) {
		LOGGER.info("getPossibleSubordinates for: "
				+ adminUnitType.getAdminUnitTypeID());

		if (adminUnitType.getAdminUnitTypeID() == null) {
			return adminUnitTypeRepository
					.findSubordinatesPossibleActiveNow(0L);

		}
		return adminUnitTypeRepository
				.findSubordinatesPossibleActiveNow(adminUnitType
						.getAdminUnitTypeID());
	}

	public AdminUnitType save(AdminUnitType adminUnitType) {
		LOGGER.info("save: " + adminUnitType);
		AdminUnitType res = adminUnitTypeRepository.save(adminUnitType);
		LOGGER.info("after save: " + adminUnitType);
		return res;
	}

	public void saveMaster(AdminUnitType adminUnitType,
			Long adminUnitTypeMasterID, String dateTimeString) {
		LOGGER.info("saveMaster: " + adminUnitType + " master ID:"
				+ adminUnitTypeMasterID);
		// update this units master

		// don't allow to set master on master unit (i.e the first should be
		// country/state, which has no master)
		if (adminUnitType.getAdminUnitTypeID() == 1)
			return;

		// if master id is 0, then master is removed/nothing
		// if master id != 0, then master is added/updated
		if ((adminUnitTypeMasterID == null) || (adminUnitTypeMasterID == 0L)) {
			// remove the master
			adminUnitTypeSubordinationRepository.removeMaster(adminUnitType,
					adminUnitTypeMasterID);
		} else {
			// add the master
			adminUnitTypeSubordinationRepository.addMaster(adminUnitType,
					adminUnitTypeMasterID);

		}
	}

	public Long getAdminUnitTypeMasterID(AdminUnitType adminUnitType) {
		LOGGER.info("getAdminUnitTypeMasterID: " + adminUnitType);

		List<AdminUnitTypeSubordination> resList = adminUnitTypeSubordinationRepository
				.getMasterActiveNow(adminUnitType.getAdminUnitTypeID());
		if (resList.size() == 0)
			return 0L;
		return resList.get(0).getAdminUnitTypeMaster().getAdminUnitTypeID();
	}

	public void addSubordinate(AdminUnitType masterAdminUnitType,
			AdminUnitType subordinateAdminUnitType) {
		AdminUnitTypeSubordination sub = new AdminUnitTypeSubordination(
				masterAdminUnitType, subordinateAdminUnitType, "");
		sub = adminUnitTypeSubordinationRepository.save(sub);

	}

	public void removeSubordinate(AdminUnitType masterAdminUnitType,
			AdminUnitType msubordinateAdminUnitType) {
		// we can't delete anything from DB, so you just have to update datetime
		// fields
		adminUnitTypeSubordinationRepository.removeSubordination(
				masterAdminUnitType, msubordinateAdminUnitType);
	}

	public int getSubordinateCount(AdminUnitType adminUnitType) {
		LOGGER.info("getSubordinateCount");
		
		// get the subordination structure
		List<AdminUnitTypeSubordination> subordination = adminUnitTypeSubordinationRepository
				.findSubordinatesActiveNow(adminUnitType.getAdminUnitTypeID());
		
		// get the subordinate items
		List<AdminUnitType> res = new ArrayList<AdminUnitType>();
		for (AdminUnitTypeSubordination item : subordination) {
			res.add(item.getAdminUnitTypeSubordinate());
		}

		return res.size();
	}

}
