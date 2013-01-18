package border.repository;

import border.model.AdminUnitType;

/*
 * declare an interface for custom-add on methods for repository
 * then implement them on some class
 */
public interface AdminUnitTypeRepositoryCustom {
	public AdminUnitType findById(Long id);
}
