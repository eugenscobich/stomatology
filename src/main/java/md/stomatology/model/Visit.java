package md.stomatology.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "visits")
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "visit_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitDate;

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name = "additional_info")
	private String additionalInfo;

	@Column(name = "price")
	private Float price;

	@Column(name = "paid")
	private Float paid;

	@ManyToOne
	@JoinColumn(name= "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "dentist_id")
	private User dentist;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "visit_id")
	private List<ToothInfo> toothInfos;
	
	private transient List<ToothInfo> topToothInfos;
	private transient List<ToothInfo> bottomToothInfos;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "visit_id")
	private List<File> files;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getPaid() {
		return paid;
	}

	public void setPaid(Float paid) {
		this.paid = paid;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public User getDentist() {
		return dentist;
	}

	public void setDentist(User dentist) {
		this.dentist = dentist;
	}

	public List<ToothInfo> getToothInfos() {
		return toothInfos;
	}

	public void setToothInfos(List<ToothInfo> toothInfos) {
		this.toothInfos = toothInfos;
	}

	public List<ToothInfo> getTopToothInfos() {
		return topToothInfos;
	}

	public void setTopToothInfos(List<ToothInfo> topToothInfos) {
		this.topToothInfos = topToothInfos;
	}

	public List<ToothInfo> getBottomToothInfos() {
		return bottomToothInfos;
	}

	public void setBottomToothInfos(List<ToothInfo> bottomToothInfos) {
		this.bottomToothInfos = bottomToothInfos;
	}

	public List<ToothInfo> getAllToothInfos() {
		List<ToothInfo> result = new ArrayList<>();
		result.addAll(topToothInfos);
		result.addAll(bottomToothInfos);
		return result;
	}
	
}
