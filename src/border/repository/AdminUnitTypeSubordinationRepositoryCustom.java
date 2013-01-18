package border.repository;

import border.model.*;

/*
 * declare an interface for custom-add on methods for repository
 * then implement them on some class
 */
public interface AdminUnitTypeSubordinationRepositoryCustom {
	public AdminUnitTypeSubordination findById(Long id);
}
