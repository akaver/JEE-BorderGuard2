package border.service;

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
			.getLogger(AdminUnitTypeRepositoryImpl.class);

	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;
	@Autowired
	private AdminUnitTypeSubordinationRepository adminUnitTypeSubordinationRepository;

    @Transactional
	public List<AdminUnitType> findAll() {
		LOGGER.debug("findAll");

		return adminUnitTypeRepository.findAll();
	}

    @Transactional
	public void populateData() {
		LOGGER.debug("populateData");
		
		AdminUnitType master = new AdminUnitType("0","Riik","0");
		AdminUnitType sub1 = new AdminUnitType("1","Maakond","1");
		AdminUnitType sub11 = new AdminUnitType("11","Maakonna linn","11");
		AdminUnitType sub12 = new AdminUnitType("12","Vald","12");
		adminUnitTypeRepository.save(master);
		adminUnitTypeRepository.save(sub1);
		adminUnitTypeRepository.save(sub11);
		adminUnitTypeRepository.save(sub12);

		AdminUnitTypeSubordination master_sub1 = new AdminUnitTypeSubordination(master,sub1,"riik->maakond");
		AdminUnitTypeSubordination sub1_sub11 = new AdminUnitTypeSubordination(sub1,sub11,"maakond->maakonna_linn");
		AdminUnitTypeSubordination sub1_sub12 = new AdminUnitTypeSubordination(sub1,sub12,"maakond->vald");
		
		master_sub1=adminUnitTypeSubordinationRepository.save(master_sub1);
		sub1_sub11=adminUnitTypeSubordinationRepository.save(sub1_sub11);
		sub1_sub12=adminUnitTypeSubordinationRepository.save(sub1_sub12);
				
	}
}
