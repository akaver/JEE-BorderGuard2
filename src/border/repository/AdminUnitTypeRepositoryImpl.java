package border.repository;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import border.model.AdminUnitType;

public class AdminUnitTypeRepositoryImpl implements AdminUnitTypeRepositoryCustom{

	private QueryDslJpaRepository<AdminUnitType, Long> repository;

	@Override
	public AdminUnitType findById(Long id) {
		
		return repository.findOne(id);
	}

}
