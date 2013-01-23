package border.repository;


public interface AdminUnitSubordinationRepositoryCustom {

	void removeSubordination(Long adminUnitSubordinateID,
			String dateTimeString);

	void updateOrAddSubordination(Long adminUnitSubordinateID,
			Long adminUnitMasterID, String dateTimeString);

}
