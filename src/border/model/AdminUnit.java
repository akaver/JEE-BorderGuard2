package border.model;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import border.helper.DateHelper;

@Entity
public class AdminUnit {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long AdminUnitID;
	
	@Column(nullable = false)
	@Size(min = 2, max = 16)
	private String code;
	@Column(nullable = false)
	@Size(min = 2, max = 64)
	private String name;
	@Column(nullable = false)
	private String comment;
	@Column(nullable = false)
	private Long adminUnitTypeID;
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
	
	// one-to-many definitions into AdminUnitSubordination
	// masters of this unit
	@OneToMany(mappedBy="adminUnitMaster", fetch = FetchType.EAGER)
	private Set<AdminUnitSubordination> adminUnitSubordinationMasters;
	// subordinates of this unit
	@OneToMany(mappedBy="adminUnitSubordinate", fetch = FetchType.EAGER)
	private Set<AdminUnitSubordination> adminUnitSubordinationSubordinates;
	
	public AdminUnit() {
	}

	public AdminUnit(String code, String name, String comment, Long adminUnitTypeID,
			String fromDate, String toDate, String openedBy, String openedDate,
			String changedBy, String changedDate, String closedBy,
			String closedDate) throws ParseException {

		this.code = code;
		this.name = name;
		this.comment = comment;
		this.adminUnitTypeID = adminUnitTypeID;
		this.fromDate = DateHelper.getParsedDate(fromDate);
		this.toDate = DateHelper.getParsedDate(toDate);
		this.openedBy = openedBy;
		this.openedDate = DateHelper.getParsedDate(openedDate);
		this.changedBy = changedBy;
		this.changedDate = DateHelper.getParsedDate(changedDate);
		this.closedBy = closedBy;
		this.closedDate = DateHelper.getParsedDate(closedDate);
	}

	public AdminUnit(String code, String name, String comment, Long adminUnitTypeID) {
		this.code = code;
		this.name = name;
		this.comment = comment;
		this.adminUnitTypeID = adminUnitTypeID;
		
		this.fromDate = DateHelper.getNow();
		this.toDate = DateHelper.getFutureDate();
		this.openedBy = "admin";
		this.openedDate = DateHelper.getNow();
		this.changedBy = "admin";
		this.changedDate = DateHelper.getNow();
		this.closedBy = "admin";
		this.closedDate = DateHelper.getFutureDate();
		
	}

	@PreUpdate
	public void preUpdate() {
		changedDate = new Date();
	}

	@PrePersist
	public void prePersist() {
		Date now = new Date();
		openedDate = now;
		changedDate = now;
	}
	
	public Long getAdminUnitID() {
		return AdminUnitID;
	}
	public void setAdminUnitID(Long adminUnitID) {
		AdminUnitID = adminUnitID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getAdminUnitTypeID() {
		return adminUnitTypeID;
	}
	public void setAdminUnitTypeID(Long adminUnitTypeID) {
		this.adminUnitTypeID = adminUnitTypeID;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
		return "AdminUnit [id=" + AdminUnitID + ", name=" + name + "]";
	}

}
