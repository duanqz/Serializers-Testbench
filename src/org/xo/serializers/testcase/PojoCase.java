package org.xo.serializers.testcase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.xo.serializers.pojo.AddressBook;

public class PojoCase implements TestCase<AddressBook> {

	@Override
	public String getProtocol() {
		return "build-in";
	}

	@Override
	public byte[] serialize(Object source) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream output = new ObjectOutputStream(bout);
			output.writeObject(source);

			return bout.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object deserialize(byte[] bytes) {
		try {
			ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			ObjectInputStream input = new ObjectInputStream(bin);
			return input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
