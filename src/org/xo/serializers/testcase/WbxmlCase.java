package org.xo.serializers.testcase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.kxml2.io.KXmlParser;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.kxml2.wap.WbxmlParser;
import org.kxml2.wap.WbxmlSerializer;
import org.xo.serializers.pojo.AddressBook;

public class WbxmlCase implements TestCase<AddressBook> {

	private KXmlParser kXmlParser = new KXmlParser();
	private KXmlSerializer kXmlSer = new KXmlSerializer();
	private WbxmlParser wbXmlparser = new WbxmlParser();
	private WbxmlSerializer wbXmlSer = new WbxmlSerializer();
	
	@Override
	public String getProtocol() {
		return "wbxml";
	}

	@Override
	public byte[] serialize(Object data) {
		try {
			String xmlStr = ((AddressBook) data).toXML();
			InputStream in = new ByteArrayInputStream(xmlStr.getBytes());
			
			kXmlParser.setInput(in, "UTF-8");
			Document dom = new Document();
			dom.parse(kXmlParser);
	
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			wbXmlSer.setOutput(out, null);
			dom.write(wbXmlSer);
			wbXmlSer.flush();
	
			return out.toByteArray();
		} catch(Exception e) {
			
		}
		
		return null;
	}

	@Override
	public Object deserialize(byte[] bytes) {
		try {
			InputStream in = new ByteArrayInputStream(bytes);
			wbXmlparser.setInput(in, "UTF-8");
			Document dom = new Document();
			dom.parse(wbXmlparser);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			kXmlSer.setOutput(out, null);
			dom.write(kXmlSer);
			kXmlSer.flush();

		} catch(Exception e) {
			
		}
		return null;
	}

}
