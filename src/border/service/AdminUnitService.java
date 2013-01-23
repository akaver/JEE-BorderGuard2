package border.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnit;
import border.model.AdminUnitSubordination;
import border.repository.AdminUnitRepository;
import border.repository.AdminUnitRepositoryImpl;
import border.repository.AdminUnitSubordinationRepository;

@Service
public class AdminUnitService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitRepositoryImpl.class);

	@Autowired
	private AdminUnitRepository adminUnitRepository;
	@Autowired
	private AdminUnitSubordinationRepository adminUnitSubordinationRepository;

	@Transactional
	public AdminUnit getByID(Long adminUnitID) {
		AdminUnit adminUnit = adminUnitRepository.findOne(adminUnitID);
		return adminUnit;
	}

	@Transactional
	public List<AdminUnit> findAll() {
		LOGGER.debug("findAll");

		return adminUnitRepository.findAll();
	}

	@Transactional
	public void deleteAll() {
		LOGGER.debug("deleteAll");
		adminUnitSubordinationRepository.deleteAll();
		adminUnitRepository.deleteAll();
	}

	@Transactional
	public AdminUnit getAdminUnitMaster(Long adminUnitID) {
		AdminUnit adminUnitMaster = null;
		List<AdminUnitSubordination> adminUnitMasterSubordinations = adminUnitSubordinationRepository
				.getMasterActiveNow(adminUnitID);

		for (AdminUnitSubordination sub : adminUnitMasterSubordinations) {
			adminUnitMaster = sub.getAdminUnitMaster();
			break;
		}

		if (adminUnitMaster == null) {
			adminUnitMaster = new AdminUnit();
			adminUnitMaster.setAdminUnitID(0L);
		}

		return adminUnitMaster;
	}

	@Transactional
	public List<AdminUnit> getAdminUnitSubordinates(Long adminUnitID) {
		List<AdminUnit> adminUnitSubordinates = new ArrayList<AdminUnit>();

		List<AdminUnitSubordination> adminUnitSubordinations = adminUnitSubordinationRepository
				.getSubordinatesActiveNow(adminUnitID);
		for (AdminUnitSubordination subordination : adminUnitSubordinations) {
			adminUnitSubordinates.add(subordination.getAdminUnitSubordinate());
		}
		return adminUnitSubordinates;
	}

	public List<AdminUnit> getAdminUnitSubordinatesPossible(Long adminUnitID,
			Long adminUnitTypeID) {

		List<AdminUnit> adminUnitSubordinatesPossible = new ArrayList<AdminUnit>();
		adminUnitSubordinatesPossible = adminUnitRepository
				.getAdminUnitSubordinatesPossible(adminUnitID, adminUnitTypeID);

		return adminUnitSubordinatesPossible;
	}
	
	public List<AdminUnit> getAllowedMastersByID(Long adminUnitTypeID) {
		List<AdminUnit> allowedMasters = new ArrayList<AdminUnit>();
		allowedMasters = adminUnitRepository.getAdminUnitMastersPossible(adminUnitTypeID);
		
		return allowedMasters;
	}

	@Transactional
	public void populateData() {
		LOGGER.debug("populateData");

		AdminUnit master = new AdminUnit("Eesti", "Eesti Vabariik",
				"K�rgeim haldus�ksus, riik", 1L);
		AdminUnit sub1 = new AdminUnit("Harjumaa", "Harjumaa maakond", "", 2L);
		AdminUnit sub11 = new AdminUnit("Tallinn", "Tallinn", "pealinn", 3L);
		AdminUnit sub12 = new AdminUnit("KiiliVald", "Kiili vald", "", 4L);
		AdminUnit sub121 = new AdminUnit("KiiliAlev", "Kiili alev", "", 5L);
		AdminUnit sub122 = new AdminUnit("Luige", "Luige alevik", "", 5L);
		AdminUnit sub2 = new AdminUnit("Kangru", "Kangru alevik", "", 5L);
		AdminUnit sub1211 = new AdminUnit("Arusta", "Arusta k�la", "", 7L);
		AdminUnit sub1212 = new AdminUnit("Kurevere", "Kurevere k�la", "", 7L);
		AdminUnit sub3 = new AdminUnit("Karuvere", "Karuvere k�la", "", 7L);
		AdminUnit sub13 = new AdminUnit("KureVald", "Kure vald", "", 4L);
		adminUnitRepository.save(master);
		adminUnitRepository.save(sub1);
		adminUnitRepository.save(sub11);
		adminUnitRepository.save(sub12);
		adminUnitRepository.save(sub121);
		adminUnitRepository.save(sub122);
		adminUnitRepository.save(sub2);
		adminUnitRepository.save(sub1211);
		adminUnitRepository.save(sub1212);
		adminUnitRepository.save(sub3);
		adminUnitRepository.save(sub13);

		AdminUnitSubordination master_sub1 = new AdminUnitSubordination(master,
				sub1, "Eesti Vabariik->Harjumaa");
		AdminUnitSubordination sub1_sub11 = new AdminUnitSubordination(sub1,
				sub11, "Harjumaa->Tallinn");
		AdminUnitSubordination sub1_sub12 = new AdminUnitSubordination(sub1,
				sub12, "Harjumaa->Kiili vald");
		AdminUnitSubordination sub12_sub121 = new AdminUnitSubordination(sub12,
				sub121, "Kiili vald->Kiili alev");
		AdminUnitSubordination sub1_sub122 = new AdminUnitSubordination(sub12,
				sub122, "Kiili vald->Luige alevik");
		AdminUnitSubordination sub121_sub1211 = new AdminUnitSubordination(
				sub121, sub1211, "Kiili alev->Arusta k�la");
		AdminUnitSubordination sub121_sub1212 = new AdminUnitSubordination(
				sub121, sub1212, "Kiili alev->Kurevere k�la");
		// AdminUnitSubordination sub1_sub13 = new AdminUnitSubordination(sub1,
		// sub13, "Harjumaa->Kure vald");

		master_sub1 = adminUnitSubordinationRepository.save(master_sub1);
		sub1_sub11 = adminUnitSubordinationRepository.save(sub1_sub11);
		sub1_sub12 = adminUnitSubordinationRepository.save(sub1_sub12);
		sub12_sub121 = adminUnitSubordinationRepository.save(sub12_sub121);
		sub1_sub122 = adminUnitSubordinationRepository.save(sub1_sub122);
		sub121_sub1211 = adminUnitSubordinationRepository.save(sub121_sub1211);
		sub121_sub1212 = adminUnitSubordinationRepository.save(sub121_sub1212);
		// sub1_sub13 = adminUnitSubordinationRepository.save(sub1_sub13);

	}

	
}
