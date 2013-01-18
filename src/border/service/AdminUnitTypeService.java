package border.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.repository.*;

@Service
public class AdminUnitTypeService {

	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;

	
}
