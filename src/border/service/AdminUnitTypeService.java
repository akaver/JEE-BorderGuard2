package border.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import border.model.AdminUnitType;
import border.model.AdminUnitTypeSubordination;
import border.repository.*;

@Service
public class AdminUnitTypeService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeRepositoryImpl.class);

	// here are our database repos
	@Autowired
	private AdminUnitTypeRepository adminUnitTypeRepository;
	@Autowired
	private AdminUnitTypeSubordinationRepository adminUnitTypeSubordinationRepository;

    @Transactional
	public List<AdminUnitType> findAll() {
		LOGGER.debug("findAll");
		return adminUnitTypeRepository.findAll();
	}

    @Transactional
    public void deleteAll() {
    	LOGGER.debug("deleteAll");
    	adminUnitTypeSubordinationRepository.deleteAll();
    	adminUnitTypeRepository.deleteAll();
    }
    
    @Transactional
	public void populateData() {
		LOGGER.debug("populateData");
		

		
		AdminUnitType master = new AdminUnitType("0","Riik","0");
		AdminUnitType sub1 = new AdminUnitType("1","Maakond","1");
		AdminUnitType sub11 = new AdminUnitType("11","Maakonna linn","11");
		AdminUnitType sub12 = new AdminUnitType("12","Vald","12");
		adminUnitTypeRepository.save(master);
		adminUnitTypeRepository.save(sub1);
		adminUnitTypeRepository.save(sub11);
		adminUnitTypeRepository.save(sub12);

		AdminUnitTypeSubordination master_sub1 = new AdminUnitTypeSubordination(master,sub1,"riik->maakond");
		AdminUnitTypeSubordination sub1_sub11 = new AdminUnitTypeSubordination(sub1,sub11,"maakond->maakonna_linn");
		AdminUnitTypeSubordination sub1_sub12 = new AdminUnitTypeSubordination(sub1,sub12,"maakond->vald");
		
		master_sub1=adminUnitTypeSubordinationRepository.save(master_sub1);
		sub1_sub11=adminUnitTypeSubordinationRepository.save(sub1_sub11);
		sub1_sub12=adminUnitTypeSubordinationRepository.save(sub1_sub12);
				
	}

    @Transactional
	public AdminUnitType getByID(Long adminUnitTypeID) {
		LOGGER.debug("getByID:",adminUnitTypeID);
		AdminUnitType res = adminUnitTypeRepository.findOne(adminUnitTypeID);
    	return res; 
	}

    
/*    
    public List<AdminUnitType> getSubordinates(Integer adminUnitTypeID,
			String dateTimeString) {
		System.out.println("Finding subordinates for adminUnitType with ID:"
				+ adminUnitTypeID);

		if (adminUnitTypeID == null) {
			return null;
		}
		List<AdminUnitType> res = new ArrayList<AdminUnitType>();

		String dateLimits = "";

		// if we search for NOW, entry must opened and valid
		if (dateTimeString.equals("NOW()")) {
			dateLimits = " and AdminUnitTypeSubordination.OpenedDate < "
					+ dateTimeString
					+ " and AdminUnitTypeSubordination.ClosedDate > "
					+ dateTimeString;
		} else if (dateTimeString.equals("")) {
			dateLimits = "";
		}

		// get the list of subordinate ID's
		String sql = "select * from AdminUnitTypeSubordination where AdminUnitTypeID=?"
				+ dateLimits;
		try {
			PreparedStatement preparedStatement = super.getConnection()
					.prepareStatement(sql);
			preparedStatement.setInt(1, adminUnitTypeID);
			ResultSet resultSet = preparedStatement.executeQuery();
			// find the record from AdminUnitType and insert into result
			while (resultSet.next()) {
				Integer subid = resultSet.getInt("SubordinateAdminUnitTypeID");
				System.out.println("Fetching subordinate with ID:" + subid);
				AdminUnitType tempAdminUnitType = getByID(subid);
				res.add(tempAdminUnitType);
			}

			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(preparedStatement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}

		return res;
	}
*/    
	public List<AdminUnitType> getSubordinates(AdminUnitType adminUnitType,
			String dateTimeString) {
		LOGGER.debug("getSubordinates for: "+adminUnitType.getAdminUnitTypeID()+" Time: "+dateTimeString);		

		if (adminUnitType.getAdminUnitTypeID() == 0L) {
			return null;
		}
		
		// get the subordination structrure
		List<AdminUnitTypeSubordination> subordination = adminUnitTypeSubordinationRepository.findSubordinatesActiveNow(adminUnitType.getAdminUnitTypeID());
		// get the subordinate items
		List<AdminUnitType> res = new ArrayList<AdminUnitType>();
		for (AdminUnitTypeSubordination item : subordination){
			res.add(item.getAdminUnitTypeSubordinate());
		}
		
		return res;
	}

	public List<AdminUnitType> getPossibleSubordinates(
			AdminUnitType adminUnitType, String dateTimeString) {
		LOGGER.debug("getPossibleSubordinates for: "+adminUnitType.getAdminUnitTypeID()+" Time: "+dateTimeString);	
		if (adminUnitType.getAdminUnitTypeID() == 0L) {
			return null;
		}

	
		return adminUnitTypeRepository.findSubordinatesPossibleActiveNow(adminUnitType.getAdminUnitTypeID());
	}

    
}
