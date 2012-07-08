package org.xo.serializers.testcase;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xo.serializers.pojo.AddressBook;
import org.xo.serializers.pojo.Person;
import org.xo.serializers.pojo.PhoneNumber;
import org.xo.serializers.pojo.PhoneNumber.PhoneType;

public class Dom4jCase implements TestCase<AddressBook> {
	
	@Override
	public String getProtocol() {
		return "dom4j";
	}

	@Override
	public byte[] serialize(Object dataSource) {
		AddressBook addrBook = (AddressBook) dataSource;
		return addrBook.toXML().getBytes();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public Object deserialize(byte[] bytes) {
	
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new ByteArrayInputStream(bytes));
			List<Element> list = document.selectNodes("//Person");
			Iterator<Element> iter = list.iterator();
			Element person;
			Person pojoPerson = new Person();
			List<Person> persons = new ArrayList<Person>();
			List<Element> phoneNumbers;
			Iterator<Element> phoneNumbersIter;
			Element phoneNumber;
			List<PhoneNumber> pojoPhoneNumbers = new ArrayList<PhoneNumber>();
			PhoneNumber pojoPhoneNumber;
			while(iter.hasNext()) {
				person = iter.next();
				pojoPerson = new Person();
				pojoPerson.setId(Integer.parseInt(person.selectSingleNode("id").getText()));
				pojoPerson.setName(person.selectSingleNode("name").getText());
				pojoPerson.setEmail(person.selectSingleNode("email").getText());
				
				phoneNumbers = person.selectNodes("./PhoneNumbers");
				phoneNumbersIter = phoneNumbers.iterator();
				while(phoneNumbersIter.hasNext()) {
					phoneNumber = (Element) phoneNumbersIter.next().selectSingleNode("./phonenumber");
					pojoPhoneNumber = new PhoneNumber();
					pojoPhoneNumber.setPhoneNumber(phoneNumber.selectSingleNode("number").getText());
					pojoPhoneNumber.setPhoneType(toPojoPhoneType(phoneNumber.selectSingleNode("type").getText()));
					pojoPhoneNumbers.add(pojoPhoneNumber);
				}
				pojoPerson.setPhoneNumbers(pojoPhoneNumbers);
				
				persons.add(pojoPerson);
			}
			
			AddressBook addrBook = new AddressBook();
			addrBook.setPersons(persons);
			
			return addrBook;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static PhoneType toPojoPhoneType(String phoneTypeStr) {
		if("HOME".equals(phoneTypeStr)) {
			return PhoneType.HOME;
		} else if("MOBILE".equals(phoneTypeStr)) {
			return PhoneType.MOBILE;
		} else {
			return PhoneType.WORK;
		}
	}
}
