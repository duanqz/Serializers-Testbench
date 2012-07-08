package org.xo.serializers.testcase;

/**
 * 
 * Test Case.
 * 
 * <p>Used to serialize and de-serialize the data source. </p>
 * 
 * @author duanqizhi
 *
 * @param <T> Type of the data source
 */
public interface TestCase<T> {

	/**
	 * Get 
	 * @return
	 */
	String getProtocol();  
	
	/**
	 * Serialize.
	 * @param data
	 * @return byte array after serialization.
	 */
    byte[] serialize(Object dataSource);  
  
    /**
     * Deserialize.
     * @param bytes
     * @return The original object after deserialization.
     */
    Object deserialize(byte[] bytes);  
}
