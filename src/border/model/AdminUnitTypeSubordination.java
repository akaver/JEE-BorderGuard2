package border.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import border.helper.DateHelper;

@Entity
@Table(name = "AdminUnitTypeSubordination")
public class AdminUnitTypeSubordination {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	// TODO: this is still mess 
	
	kala
	
	// this is master unit
	// private Long adminUnitTypeID;
	@OneToMany(mappedBy = "manager")
	@JoinColumn(name = "adminUnitTypeID")
	private Set<AdminUnitTypeSubordination> subordinates = new HashSet<AdminUnitTypeSubordination>();

	// this is slave to master
	// private Long subordinateAdminUnitTypeID;
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "subordinateAdminUnitTypeId")
	private AdminUnitTypeSubordination subordinateAdminUnitType;

	
	ja kala
	
	
	private String comment;
	private String openedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date openedDate;
	private String changedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date changedDate;
	private String closedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date closedDate;

	
	@PreUpdate
	public void preUpdate() {
		changedDate = DateHelper.getNow();
	}

	@PrePersist
	public void prePersist() {
		openedDate = DateHelper.getNow();
		changedDate = DateHelper.getNow();
		closedDate = DateHelper.getFutureDate();
	}	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public Long getAdminUnitTypeID() { return adminUnitTypeID; } public void
	 * setAdminUnitTypeID(Long adminUnitTypeID) { this.adminUnitTypeID =
	 * adminUnitTypeID; } public Long getSubordinateAdminUnitTypeID() { return
	 * subordinateAdminUnitTypeID; } public void
	 * setSubordinateAdminUnitTypeID(Long subordinateAdminUnitTypeID) {
	 * this.subordinateAdminUnitTypeID = subordinateAdminUnitTypeID; }
	 */
	

	
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
		// return "Person [id=" + id + ", master adminUnitTypeID=" +
		// adminUnitTypeID +
		// ", slave subordinateAdminUnitTypeID="+subordinateAdminUnitTypeID+", comment="+comment+"]";
		return "Person [id=" + id + ", comment=" + comment + "]";
	}

}
