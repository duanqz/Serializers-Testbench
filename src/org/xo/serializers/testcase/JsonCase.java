package org.xo.serializers.testcase;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.xo.serializers.pojo.AddressBook;

public class JsonCase implements TestCase<AddressBook> {

	@Override
	public String getProtocol() {
		return "json";
	}

	@Override
	public byte[] serialize(Object data) {
		return JSONSerializer.toJSON(data).toString().getBytes();
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return JSONObject.fromObject(new String(bytes));
	}

}
