package md.stomatology.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import md.stomatology.model.type.GenderType;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "surname", length = 50)
	private String surname;
	
	@Column(name = "birth_year")
	private Integer birthYear;
	
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	
	@Column(name = "address", length = 256)
	private String address;
	
	@Column(name = "phone", length = 50)
	private String phone;
	
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name = "notes" , length = 1000)
	@Type(type="text")
	private String notes;
	
	@ManyToMany
	@JoinTable(
		      name="customers_to_allergies",
		      joinColumns={@JoinColumn(name="customer_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="allergy_id", referencedColumnName="id")})
	private List<Allergy> allergies;

	@ManyToMany
	@JoinTable(
		      name="customers_to_past_illnesses",
		      joinColumns={@JoinColumn(name="customer_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="past_illnesse_id", referencedColumnName="id")})
	private List<PastIllnesse> pastIllnesses;
	
	@ManyToOne
	@JoinColumn(name = "dentist_id")
	private User dentist;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "customer_id")
	private List<Visit> visits;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public List<PastIllnesse> getPastIllnesses() {
		return pastIllnesses;
	}

	public void setPastIllnesses(List<PastIllnesse> pastIllnesses) {
		this.pastIllnesses = pastIllnesses;
	}

	@Override
    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("id : ").append(getId());
        strBuff.append(", name : ").append(getName());
        strBuff.append(", surname : ").append(getSurname());
        strBuff.append(", birthYear : ").append(getBirthYear());
        return strBuff.toString();
    }

	public User getDentist() {
		return dentist;
	}

	public void setDentist(User dentist) {
		this.dentist = dentist;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
}