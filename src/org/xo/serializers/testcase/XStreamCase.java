package org.xo.serializers.testcase;

import org.xo.serializers.pojo.AddressBook;

import com.thoughtworks.xstream.XStream;

public class XStreamCase implements TestCase<AddressBook> {
	private XStream xStream = new XStream();
	
	@Override
	public String getProtocol() {
		return "xstream";
	}

	@Override
	public byte[] serialize(Object data) {
		return xStream.toXML(data).getBytes();
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return xStream.fromXML(new String(bytes));
	}

}
