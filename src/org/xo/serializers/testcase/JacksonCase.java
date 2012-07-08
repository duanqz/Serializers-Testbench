package org.xo.serializers.testcase;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.xo.serializers.pojo.AddressBook;

public class JacksonCase implements TestCase<AddressBook> {
	private final ObjectMapper objectMapper = new ObjectMapper();  

	@Override
	public String getProtocol() {
		return "jackson";
	}

	@Override
	public byte[] serialize(Object data) {
		 try {  
	            return objectMapper.writeValueAsBytes(data);  
	        } catch (JsonGenerationException e) {  
	            e.printStackTrace();  
	        } catch (JsonMappingException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	  
	        return null;  
	}

	@Override
	public Object deserialize(byte[] bytes) {
		 try {  
	            return objectMapper.readValue(bytes, 0, bytes.length, AddressBook.class);  
	        } catch (JsonParseException e) {  
	            e.printStackTrace();  
	        } catch (JsonMappingException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	}

}
