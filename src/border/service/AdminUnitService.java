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

	@Transactional
	public AdminUnit getByID(Long adminUnitID) {
		AdminUnit adminUnit = adminUnitRepository.findOne(adminUnitID);
		return adminUnit;
	}

	@Transactional
	public void populateData() {
		LOGGER.debug("populateData");

		AdminUnit master = new AdminUnit("Eesti", "Eesti Vabariik",
				"Kõrgeim haldusüksus, riik", 1L);
		AdminUnit sub1 = new AdminUnit("Harjumaa", "Harjumaa maakond", "", 2L);
		AdminUnit sub11 = new AdminUnit("Tallinn", "Tallinn", "pealinn", 3L);
		AdminUnit sub12 = new AdminUnit("KiiliVald", "Kiili vald", "", 4L);
		// AdminUnit sub1 = new AdminUnit("KiiliAlev", "Kiili alev", "", 6L);
		// AdminUnit sub11 = new AdminUnit("Luige", "Luige alevik", "", 7L);
		// AdminUnit sub12 = new AdminUnit("Kangru", "Kangru alevik", "", 7L);
		// AdminUnit sub1 = new AdminUnit("Arusta", "Arusta küla", "", 8L);
		// AdminUnit sub11 = new AdminUnit("Kurevere", "Kurevere küla", "", 8L);
		// AdminUnit sub12 = new AdminUnit("Karuvere", "Karuvere küla", "", 8L);
		AdminUnit sub13 = new AdminUnit("KureVald", "Kure vald", "", 4L);
		adminUnitRepository.save(master);
		adminUnitRepository.save(sub1);
		adminUnitRepository.save(sub11);
		adminUnitRepository.save(sub12);
		adminUnitRepository.save(sub13);

		AdminUnitSubordination master_sub1 = new AdminUnitSubordination(master,
				sub1, "Eesti Vabariik->Harjumaa");
		AdminUnitSubordination sub1_sub11 = new AdminUnitSubordination(sub1,
				sub11, "Harjumaa->Tallinn");
		AdminUnitSubordination sub1_sub12 = new AdminUnitSubordination(sub1,
				sub12, "Harjumaa->Kiili vald");
		AdminUnitSubordination sub1_sub13 = new AdminUnitSubordination(sub1,
				sub13, "Harjumaa->Kure vald");

		master_sub1 = adminUnitSubordinationRepository.save(master_sub1);
		sub1_sub11 = adminUnitSubordinationRepository.save(sub1_sub11);
		sub1_sub12 = adminUnitSubordinationRepository.save(sub1_sub12);
		sub1_sub13 = adminUnitSubordinationRepository.save(sub1_sub13);

	}
}
