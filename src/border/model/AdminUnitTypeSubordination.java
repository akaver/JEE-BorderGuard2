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

import border.helper.AccessHelper;
import border.helper.DateHelper;

@Entity
@Table(name = "AdminUnitTypeSubordination")
public class AdminUnitTypeSubordination {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long AdminUnitTypeSubordinationID;

	@ManyToOne
	@JoinColumn(name = "masterAdminUnitTypeID")
	private AdminUnitType adminUnitTypeMaster;

	@ManyToOne
	@JoinColumn(name = "subordinateAdminUnitTypeID")
	private AdminUnitType adminUnitTypeSubordinate;

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
	private String closedBy; // only value from DB schema with NULL allowed
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date closedDate;

	public AdminUnitTypeSubordination() {

	}

	public AdminUnitTypeSubordination(AdminUnitType adminUnitTypeMaster,
			AdminUnitType adminUnitTypeSubordinate, String comment,
			String fromDate, String toDate, String openedBy, String openedDate,
			String changedBy, String changedDate, String closedBy,
			String closedDate) {

		this.adminUnitTypeMaster = adminUnitTypeMaster;
		this.adminUnitTypeSubordinate = adminUnitTypeSubordinate;

		if (comment == null || comment.trim().isEmpty()) {
			this.comment = "";
		} else {
			this.comment = comment;
		}

		this.fromDate = DateHelper.getParsedDate(fromDate);
		this.toDate = DateHelper.getParsedDate(toDate);
		this.openedBy = openedBy;
		this.openedDate = DateHelper.getParsedDate(openedDate);
		this.changedBy = changedBy;
		this.changedDate = DateHelper.getParsedDate(changedDate);
		this.closedBy = closedBy;
		this.closedDate = DateHelper.getParsedDate(closedDate);

	}

	public AdminUnitTypeSubordination(AdminUnitType adminUnitTypeMaster,
			AdminUnitType adminUnitTypeSubordinate, String comment) {
		this.adminUnitTypeMaster = adminUnitTypeMaster;
		this.adminUnitTypeSubordinate = adminUnitTypeSubordinate;

		if (comment == null || comment.trim().isEmpty()) {
			this.comment = "";
		} else {
			this.comment = comment;
		}
	}

	@PreUpdate
	public void preUpdate() {
		String username = AccessHelper.getUserName();
		this.changedDate = DateHelper.getNow();
		this.changedBy = username;
	}

	@PrePersist
	public void prePersist() {
		
		String username = AccessHelper.getUserName();

		// if comment is null, add empty string to satisfy DB
		if (this.comment == null) {
			this.comment = "";
		}

		if (this.fromDate == null)
			this.fromDate = DateHelper.getNow();
		if (this.toDate == null)
			this.toDate = DateHelper.getFutureDate();
		if (this.openedDate == null)
			this.openedDate = DateHelper.getNow();
		if (this.changedDate == null)
			this.changedDate = DateHelper.getNow();
		if (this.closedDate == null)
			this.closedDate = DateHelper.getFutureDate();

		this.openedBy = username;
		this.changedBy = username;
		this.closedBy = username;
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

	public AdminUnitType getAdminUnitTypeMaster() {
		return adminUnitTypeMaster;
	}

	public void setAdminUnitTypeMaster(AdminUnitType adminUnitTypeMaster) {
		this.adminUnitTypeMaster = adminUnitTypeMaster;
	}

	public AdminUnitType getAdminUnitTypeSubordinate() {
		return adminUnitTypeSubordinate;
	}

	public void setAdminUnitTypeSubordinate(
			AdminUnitType adminUnitTypeSubordinate) {
		this.adminUnitTypeSubordinate = adminUnitTypeSubordinate;
	}

	@Override
	public String toString() {
		return "AdminUnitType [id=" + AdminUnitTypeSubordinationID
				+ ", comment=" + comment + "]";
	}

	public Long getAdminUnitTypeSubordinationID() {
		return AdminUnitTypeSubordinationID;
	}

	public void setAdminUnitTypeSubordinationID(
			Long adminUnitTypeSubordinationID) {
		AdminUnitTypeSubordinationID = adminUnitTypeSubordinationID;
	}

}
