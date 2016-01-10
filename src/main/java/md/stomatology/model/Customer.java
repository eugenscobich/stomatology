package md.stomatology.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "surname", length = 50)
	private String surname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
    public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("id : ").append(getId());
        strBuff.append(", name : ").append(getName());
        strBuff.append(", surname : ").append(getSurname());
        return strBuff.toString();
    }
}