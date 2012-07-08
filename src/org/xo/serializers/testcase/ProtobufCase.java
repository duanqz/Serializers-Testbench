package org.xo.serializers.testcase;

import org.xo.serializers.protobuf.AddressBookProtos;
import org.xo.serializers.protobuf.AddressBookProtos.AddressBook;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtobufCase implements TestCase<AddressBook> {

	@Override
	public String getProtocol() {
		return "protobuf";  
	}

	@Override
	public byte[] serialize(Object source) {
		if (source instanceof AddressBookProtos.AddressBook) {
			AddressBookProtos.AddressBook message = (AddressBookProtos.AddressBook) source;
			return message.toByteArray();
		}

		return null;
	}

	@Override
	public Object deserialize(byte[] bytes) {
		try {
			return AddressBookProtos.AddressBook.parseFrom(bytes);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return null;
	}

}
