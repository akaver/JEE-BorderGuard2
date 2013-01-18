package border.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.repository.*;

@Service
public class AdminUnitTypeService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeRepositoryImpl.class);

	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;

    @Transactional
	public List<AdminUnitType> findAll() {
		LOGGER.debug("findAll");

		return adminUnitTypeRepository.findAll();
	}

    @Transactional
	public void populateData() {
		LOGGER.debug("populateData");

		adminUnitTypeRepository.save(new AdminUnitType("1","Esimene","on jah nr 1"));
	}
}
