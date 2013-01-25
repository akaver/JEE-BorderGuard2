package border.model;

import java.util.Date;

import javax.persistence.*;

import border.helper.DateHelper;

@Entity
public class AdminUnitSubordination {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adminUnitSubordinationID;

	
	@ManyToOne
    @JoinColumn(name="masterAdminUnitID")
    private AdminUnit adminUnitMaster;
	
	@ManyToOne
    @JoinColumn(name="subordinateAdminUnitID")
    private AdminUnit adminUnitSubordinate;
	
	
	@Column(nullable = false)
	private String comment;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date fromDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date toDate;
	@Column(nullable = false)
	private String openedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date openedDate;
	@Column(nullable = false)
	private String changedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date changedDate;
	private String closedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date closedDate;

	
	public AdminUnitSubordination(){
		
	}

	public AdminUnitSubordination(AdminUnit adminUnitMaster, AdminUnit adminUnitSubordinate, String comment){
		this.adminUnitMaster = adminUnitMaster;
		this.adminUnitSubordinate = adminUnitSubordinate;
    	this.comment = comment;
    	this.fromDate = DateHelper.getNow();
    	this.toDate = DateHelper.getFutureDate();
    	this.openedDate = DateHelper.getNow();
    	this.openedBy = "admin";
    	this.closedDate = DateHelper.getFutureDate();    	
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
	
	public Long getAdminUnitSubordinationID() {
		return adminUnitSubordinationID;
	}

	public void setAdminUnitSubordinationID(Long adminUnitSubordinationID) {
		this.adminUnitSubordinationID = adminUnitSubordinationID;
	}

	public AdminUnit getAdminUnitMaster() {
		return adminUnitMaster;
	}

	public void setAdminUnitMaster(AdminUnit adminUnitMaster) {
		this.adminUnitMaster = adminUnitMaster;
	}

	public AdminUnit getAdminUnitSubordinate() {
		return adminUnitSubordinate;
	}

	public void setAdminUnitSubordinate(AdminUnit adminUnitSubordinate) {
		this.adminUnitSubordinate = adminUnitSubordinate;
	}
	
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

	@Override
	public String toString() {
		return "AdminUnit [id=" + adminUnitSubordinationID + ", comment=" + comment + "]";
	}	
	
}
