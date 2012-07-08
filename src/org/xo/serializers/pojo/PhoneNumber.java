package org.xo.serializers.pojo;

import java.io.Serializable;

public class PhoneNumber implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum PhoneType {
		MOBILE, HOME, WORK;
		
		@Override
		public String toString() {
			switch(this) {
			case MOBILE:
				return "MOBILE";
			case HOME:
				return "HOME";
			case WORK:
				return "WORK";
			}
			return "HOME";
		}
	}
	
	private String phoneNumber;
	
	private PhoneType phoneType = PhoneType.HOME;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PhoneType getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}
	
}
