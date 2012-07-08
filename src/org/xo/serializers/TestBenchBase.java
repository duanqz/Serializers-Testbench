package org.xo.serializers;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import org.xo.serializers.datasource.DataSourceFactory;
import org.xo.serializers.testcase.TestCase;

/**
 * TestBenchBase.
 * <p>
 * Call {@link #process(TestCase, int)} to process the data source.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public abstract class TestBenchBase {

	protected List<TestCase<?>> testCases = new ArrayList<TestCase<?>>();
	
	public void runTest(int testCount) {
		setUp();
		
		System.out.print("<html><body><table border=\"1\">");
		System.out.print("<tr><td>Protocol</td><td>ser_cost(ns)</td><td>deser_cost(ns)</td><td>sizes(bytes)</td></tr>");
		
		TestCase<?> testCase;
		for(int i = 0; i < testCases.size(); i++) {
			testCase = testCases.get(i);
			process(testCase, testCount);
		}
		
		System.out.print("</body></table></html>");
		tearDown();
	}

	/**
	 * Set up test bench.
	 */
	protected void setUp() { }
	
	/**
	 * Tear test bench down.
	 */
	protected void tearDown() { }
	
	
	/**
	 * Serialize and deserialize the data source. Compute the cost time and size.
	 * @param dataSource
	 * @param count Counts of the same process of serialize and deserialize.
	 */
	protected void process(TestCase<?> testCase, int count) {
		assert(testCase != null);
		assert(count > 0);
		
        int warmup = 10;  
        // Warm up, load some class, to avoid affect of JIT
        Object dataSource = DataSourceFactory.getDataSource(testCase);
        for (int i = 0; i < warmup; i++) {  
            byte[] bytes = testCase.serialize(dataSource);  
            testCase.deserialize(bytes);  
        }  
        restoreJvm();
        
        // Begin to test
        long size = 0l;
        long totalSerializeCost = 0l, totalDeserializeCost = 0l;
        long serializeStart, serializeCost, deserailizeStart, deserializeCost;
        for (int i = 0; i < count; i++) {
        	// Serialize time and size
        	serializeStart = System.nanoTime();
            byte[] bytes = testCase.serialize(dataSource);
            size += bytes.length;
            serializeCost = System.nanoTime() - serializeStart;
            totalSerializeCost += serializeCost;
           
            // Deserialize time
            deserailizeStart = System.nanoTime();
            testCase.deserialize(bytes);
            deserializeCost =  System.nanoTime() - deserailizeStart;
            totalDeserializeCost += deserializeCost;
            
            bytes = null;  
        } 
        
		System.out.print("<tr><td>" + testCase.getProtocol() + "</td><td>" + (totalSerializeCost/count) + "</td><td>" + (totalDeserializeCost/count) +"</td><td>" + (size/count) + "</td></tr>");
        restoreJvm();  
  
    } 
	
	private void restoreJvm() {
		int maxRestoreJvmLoops = 10;
		long memUsedPrev = memoryUsed();
		for (int i = 0; i < maxRestoreJvmLoops; i++) {
			System.runFinalization();
			System.gc();

			long memUsedNow = memoryUsed();
			// break early if have no more finalization and get constant memory used
			if ((ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount() == 0)
					&& (memUsedNow >= memUsedPrev)) {
				break;
			} else {
				memUsedPrev = memUsedNow;
			}
		}
	}
  
	private long memoryUsed() {
		Runtime rt = Runtime.getRuntime();
		return rt.totalMemory() - rt.freeMemory();
	} 
}
