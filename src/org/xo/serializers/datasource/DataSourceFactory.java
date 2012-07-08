package org.xo.serializers.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.xo.serializers.pojo.AddressBook;
import org.xo.serializers.pojo.Person;
import org.xo.serializers.pojo.PhoneNumber;
import org.xo.serializers.pojo.PhoneNumber.PhoneType;
import org.xo.serializers.protobuf.AddressBookProtos;
import org.xo.serializers.testcase.Dom4jCase;
import org.xo.serializers.testcase.JacksonCase;
import org.xo.serializers.testcase.JsonCase;
import org.xo.serializers.testcase.PojoCase;
import org.xo.serializers.testcase.ProtobufCase;
import org.xo.serializers.testcase.TestCase;
import org.xo.serializers.testcase.WbxmlCase;
import org.xo.serializers.testcase.XStreamCase;

/**
 * Data Source Factory.
 * <p>Factory Pattern used to create different data source according to test case.
 * <br>{@link #init(InputStream)} MUST be called before {@link #getDataSource(TestCase)}.
 * </p>
 * @author duanqizhi
 *
 */
public class DataSourceFactory {

	private static final AddressBook addrBook = new AddressBook();
	
	/**
	 * Initiate data source from an input stream.
	 * <br>
	 * <font color="red">We use json text to describe the original data.</font>
	 * @param is
	 */
	public static void init(InputStream is) {
		assert(is != null);
		
		try {
			int size = is.available();
			byte[] bytes = new byte[size];
			is.read(bytes);
			parseToJavaByJson(bytes);
			is.close();
			
		} catch (IOException e) {
			System.err.println("Failed to initiate data source.");
		} 
	}
	
	/**
	 * Retrieve data source by test case.
	 * {@link #init(InputStream)} MUST be called before calling this method.
	 * @param testCase
	 * @return
	 */
	public static Object getDataSource(TestCase<?> testCase){
		if(testCase instanceof PojoCase) {
			return addrBook;
		} else if(testCase instanceof JsonCase) {
			return addrBook;
		} else if(testCase instanceof JacksonCase) {
			return addrBook;
		} else if(testCase instanceof XStreamCase) {
			return addrBook;
		} else if(testCase instanceof WbxmlCase) {
			return addrBook;
		} else if(testCase instanceof ProtobufCase) {
			return createProtobufDataSource();
		} else if(testCase instanceof Dom4jCase) {
			return addrBook;
		}
		return null;
	}
	
	private static void parseToJavaByJson(byte[] bytes) {
		List<Person> pojoPersons = new ArrayList<Person>();
		Person pojoPerson;
		
		JSONObject jsonAddrBook = JSONObject.fromObject(new String(bytes));
		JSONArray persons = JSONArray.fromObject(jsonAddrBook.get("persons"));
		for(Object obj : persons) {
			JSONObject person = JSONObject.fromObject(obj);
			pojoPerson = new Person();
			pojoPerson.setId((Integer.parseInt(person.getString("id"))));
			pojoPerson.setEmail(person.getString("email"));
			pojoPerson.setName(person.getString("name"));
			
			JSONArray phoneNumbers = JSONArray.fromObject(person.get("phoneNumbers"));
			List<PhoneNumber> pojoPhoneNumbers = new ArrayList<PhoneNumber>();
			for(Object phoneNumber : phoneNumbers) {
				PhoneNumber pojoPhoneNumber = new PhoneNumber();
				JSONObject jsonPhoneNumber = JSONObject.fromObject(phoneNumber);
				pojoPhoneNumber.setPhoneNumber(jsonPhoneNumber.getString("phoneNumber"));
				pojoPhoneNumber.setPhoneType(toPojoPhoneType(jsonPhoneNumber.getString("phoneType")));
				pojoPhoneNumbers.add(pojoPhoneNumber);
			}
			
			pojoPerson.setPhoneNumbers(pojoPhoneNumbers);
			pojoPersons.add(pojoPerson);
		}
		
		addrBook.setPersons(pojoPersons);
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
	
	private static AddressBookProtos.Person.PhoneType toProtobufPhoneType(PhoneType pojoPhoneType) {
		if(pojoPhoneType == PhoneType.HOME) {
			return AddressBookProtos.Person.PhoneType.HOME;
		} else if(pojoPhoneType == PhoneType.WORK) {
			return AddressBookProtos.Person.PhoneType.WORK;
		} else {
			return AddressBookProtos.Person.PhoneType.MOBILE;
		} 
			
	}
	
	private static AddressBookProtos.AddressBook createProtobufDataSource() {
		AddressBookProtos.AddressBook.Builder addrbookbuilder = AddressBookProtos.AddressBook.newBuilder();
		for(Person person : addrBook.getPersons()) {
			AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
			personBuilder.setId(person.getId()).setName(person.getName()).setEmail(person.getEmail());
			for(PhoneNumber phoneNumber : person.getPhoneNumbers()) {
					personBuilder.addPhone(
						AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber(phoneNumber.getPhoneNumber()).setType(toProtobufPhoneType(phoneNumber.getPhoneType())));
			}
					
			addrbookbuilder.addPerson(personBuilder);
		}
		
		return addrbookbuilder.build();
	}
}
