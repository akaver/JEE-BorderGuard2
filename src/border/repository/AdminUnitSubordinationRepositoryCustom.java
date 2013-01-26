package border.repository;


public interface AdminUnitSubordinationRepositoryCustom {

	void removeSubordination(Long adminUnitSubordinateID);

	void updateOrAddSubordination(Long adminUnitSubordinateID,
			Long adminUnitMasterID);

}
