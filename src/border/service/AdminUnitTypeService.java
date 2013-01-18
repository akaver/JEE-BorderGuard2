package border.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.repository.*;

@Service
public class AdminUnitTypeService {

	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;

	
    @Transactional
	public void create(AdminUnitType adminUnitType){
		adminUnitTypeRepository.save(adminUnitType);
	}
}
