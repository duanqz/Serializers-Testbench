package org.xo.serializers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.xo.serializers.datasource.DataSourceFactory;
import org.xo.serializers.testcase.Dom4jCase;
import org.xo.serializers.testcase.JacksonCase;
import org.xo.serializers.testcase.JsonCase;
import org.xo.serializers.testcase.PojoCase;
import org.xo.serializers.testcase.ProtobufCase;
import org.xo.serializers.testcase.WbxmlCase;
import org.xo.serializers.testcase.XStreamCase;

/**
 * TestBench.
 * 
 * @author duanqizhi
 */
public class TestBench extends TestBenchBase {

	private static final int TEST_COUNT = 10 * 50;          

	@Override
	protected void setUp() {
		testCases.add(new PojoCase());
		testCases.add(new ProtobufCase());
		testCases.add(new WbxmlCase());
		testCases.add(new JsonCase());
		testCases.add(new JacksonCase());
		testCases.add(new Dom4jCase());
		testCases.add(new XStreamCase());
	}
	
	@Override
	protected void tearDown() {
		testCases.clear();
		testCases = null;
	}
	
	public static void main(String[] args) { 
		
		int testCount = TEST_COUNT;
		TestBench testBench = new TestBench();
		
		if(args.length == 0) {
			DataSourceFactory.init(TestBench.class.getResourceAsStream("addressbook.json"));
		} else if(args.length > 0) {
			try {
				DataSourceFactory.init( new FileInputStream(args[0]));
				
				if(args.length > 1) {
					testCount = Integer.parseInt(args[1]);
				}
				
				redirectOut();
			} catch(FileNotFoundException e) {
				System.out.println("Usage: TestBench path_to_datasource [test count].");
				System.out.println("Eg. TestBench C:/data.json 1000");
				System.exit(1);
			} catch(NumberFormatException e) {
				// Not a number
				System.out.println("Test count format error. Use default test count: " + TEST_COUNT);
				redirectOut(args[1]);
			}
			
		}
		
		testBench.runTest(testCount);
	}
	
	//TODO refactor
	private static void redirectOut(String outFilePath) {
		
		try {
			File outFile = new File(outFilePath);
			File parent = new File(outFile.getParent());
			if(parent.exists() == false) {
				parent.mkdir();
			}
			if(outFile.exists() == false) {
				outFile.createNewFile();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(outFile));
			System.setOut(ps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO refactor
	private static void redirectOut() {
		try {
			String outFileDir = System.getProperty("user.dir") + File.separator + "out";
			File outFile = new File(outFileDir);
			if(outFile.exists() == false) {
				outFile.mkdir();
			}
			
			File result = new File(outFile, "result.html");
			if(result.exists() == false) {
				result.createNewFile();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(result, false));
			System.setOut(ps);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to redirect System.out");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
