package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agenda implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String phone;
	private String cellphone;
	private String email;
	private Date birthdate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
	
	public Agenda() {
	}

	public Agenda(Integer id, String name, String phone, String cellphone, String email, Date birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.cellphone = cellphone;
		this.email = email;
		this.birthdate = birthdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getId() + ", " + getName() + ", " + getPhone() + ", " + getCellphone() + ", " + getEmail() + ", " + sdf.format(birthdate);  //getName().substring(0,1).toUpperCase() + getName().substring(1)
	}
}
