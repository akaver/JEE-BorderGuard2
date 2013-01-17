package border.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.repository.*;

@Repository
public class AdminUnitTypeService {
	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;
	
    @Transactional
	public void create(AdminUnitType adminUnitType){
		adminUnitTypeRepository.save(adminUnitType);
	}
}
