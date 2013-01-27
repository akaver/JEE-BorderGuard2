package border.repository;

/*
 * declare an interface for custom-add on methods for repository
 * then implement them on some class
 */
public interface AdminUnitSubordinationRepositoryCustom {

	void removeSubordination(Long adminUnitSubordinateID);

	void updateOrAddSubordination(Long adminUnitSubordinateID,
			Long adminUnitMasterID);

}
