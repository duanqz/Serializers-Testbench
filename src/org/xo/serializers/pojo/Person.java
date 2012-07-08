package org.xo.serializers.pojo;

import java.io.Serializable;
import java.util.List;


public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String email;
	
	private List<PhoneNumber> phoneNumbers;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	
	
}
