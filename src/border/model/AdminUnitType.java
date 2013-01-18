package border.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/*
 * JavaBean is this, says the Yoda!
 */
@Entity
@Table(name = "AdminUnitType")
public class AdminUnitType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; // this has to be named id, otherwise spring jpa cant work its magic
	private String code;
	private String name;
	private String comment;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date fromDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date toDate;
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
	
	public AdminUnitType(){
	}

	public AdminUnitType(String code, String name, String comment, String fromDate, String toDate, String openedBy, String openedDate, String changedBy, String changedDate,  String closedBy, String closedDate) throws ParseException{
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		this.code = code;
		this.name = name;
		this.comment = comment;   
		this.fromDate = (Date) formatter.parse(fromDate); 
		this.toDate = (Date) formatter.parse(toDate);   
		this.openedBy = openedBy;
		this.openedDate = (Date) formatter.parse(openedDate);   
		this.changedBy = changedBy; 
		this.changedDate = (Date) formatter.parse(changedDate);    
		this.closedBy = closedBy;   
		this.closedDate = (Date) formatter.parse(closedDate); 
	}

	public AdminUnitType(String code, String name, String comment){
		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		this.code = code;
		this.name = name;
		this.comment = comment;   
		
		this.fromDate = new Date(); 
		try {
			this.toDate = (Date) formatter.parse("9999-12-31 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		this.openedBy = "admin";
		this.openedDate = new Date();  
		this.changedBy = "admin";
		this.changedDate = new Date();    
		this.closedBy = "admin";
		try {
			this.closedDate = (Date) formatter.parse("9999-12-31 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long adminUnitTypeID) {
		this.id = adminUnitTypeID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
        return "Person [id=" + id + ", name=" + name + ", code="+code+"]";
    }

}
