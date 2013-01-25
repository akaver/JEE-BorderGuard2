package border.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import border.helper.DateHelper;

@Entity
@Table(name = "AdminUnitTypeSubordination")
public class AdminUnitTypeSubordination {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long AdminUnitTypeSubordinationID;

	
	@ManyToOne
    @JoinColumn(name="masterAdminUnitTypeID")
    private AdminUnitType adminUnitTypeMaster;
	
	@ManyToOne
    @JoinColumn(name="subordinateAdminUnitTypeID")
    private AdminUnitType adminUnitTypeSubordinate;
	
	
	// ID of master unit
	// private Long adminUnitTypeId;
	// ID of subordinate
	//private Long subordinateAdminUnitTypeId;

	private String comment;
	@NotNull
	private String openedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date openedDate;
	@NotNull
	private String changedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date changedDate;
	@NotNull
	private String closedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date closedDate;

	
	public AdminUnitTypeSubordination(){
		
	}

	public AdminUnitTypeSubordination(AdminUnitType adminUnitTypeMaster, AdminUnitType adminUnitTypeSubordinate, String comment){
		this.adminUnitTypeMaster = adminUnitTypeMaster;
		this.adminUnitTypeSubordinate = adminUnitTypeSubordinate;
    	this.comment = comment;
	}
	
	@PreUpdate
	public void preUpdate() {
		changedDate = DateHelper.getNow();
	}

	@PrePersist
	public void prePersist() {
		openedBy="admin";
		openedDate = DateHelper.getNow();
		changedBy="admin";
		changedDate = DateHelper.getNow();
		closedBy="admin";
		closedDate = DateHelper.getFutureDate();
	}



//	public Long getAdminUnitTypeID() {
//		return adminUnitTypeId;
//	}
//
//	public void setAdminUnitTypeID(Long adminUnitTypeId) {
//		this.adminUnitTypeId = adminUnitTypeId;
//	}

//	public Long getSubordinateAdminUnitTypeId() {
//		return subordinateAdminUnitTypeId;
//	}
//
//	public void setSubordinateAdminUnitTypeId(Long subordinateAdminUnitTypeId) {
//		this.subordinateAdminUnitTypeId = subordinateAdminUnitTypeId;
//	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(String openedBy) {
		this.openedBy = openedBy;
	}

	public Date getOpenedDate() {
		return openedDate;
	}

	public void setOpenedDate(Date openedDate) {
		this.openedDate = openedDate;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public AdminUnitType getAdminUnitTypeMaster() {
		return adminUnitTypeMaster;
	}

	public void setAdminUnitTypeMaster(AdminUnitType adminUnitTypeMaster) {
		this.adminUnitTypeMaster = adminUnitTypeMaster;
	}

	public AdminUnitType getAdminUnitTypeSubordinate() {
		return adminUnitTypeSubordinate;
	}

	public void setAdminUnitTypeSubordinate(AdminUnitType adminUnitTypeSubordinate) {
		this.adminUnitTypeSubordinate = adminUnitTypeSubordinate;
	}

	@Override
	public String toString() {
		// return "AdminUnitType [id=" + id + ", master adminUnitTypeID=" +
		// adminUnitTypeID +
		// ", slave subordinateAdminUnitTypeID="+subordinateAdminUnitTypeID+", comment="+comment+"]";
		return "AdminUnitType [id=" + AdminUnitTypeSubordinationID + ", comment=" + comment + "]";
	}

	public Long getAdminUnitTypeSubordinationID() {
		return AdminUnitTypeSubordinationID;
	}

	public void setAdminUnitTypeSubordinationID(Long adminUnitTypeSubordinationID) {
		AdminUnitTypeSubordinationID = adminUnitTypeSubordinationID;
	}
	
}
