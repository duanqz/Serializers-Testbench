package org.xo.serializers.pojo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

public class AddressBook implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Person> persons;

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public JSONObject toJSON() {
		return JSONObject.fromObject(this);
	}
	
	public String toXML() {
		Document document = DocumentHelper.createDocument();
		Element addressbookElem = document.addElement("AddressBook");
		for(Person person : this.getPersons()) {
			Element personElem = addressbookElem.addElement("Person");
			personElem.addElement("id").setText(String.valueOf(person.getId()));
			personElem.addElement("name").setText(person.getName());
			personElem.addElement("email").setText(person.getEmail());
			
			Element phoneNumbersElem = personElem.addElement("PhoneNumbers");
			for(PhoneNumber phoneNumber : person.getPhoneNumbers()) {
				Element phoneNumberElem = phoneNumbersElem.addElement("phonenumber");
				phoneNumberElem.addElement("number").setText(phoneNumber.getPhoneNumber());
				phoneNumberElem.addElement("type").setText(phoneNumber.getPhoneType().toString());
			}
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLWriter writer;
		try {
			writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			writer.close();
			
			return out.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
