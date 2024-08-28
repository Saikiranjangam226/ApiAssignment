package lib;

import java.io.File;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.restassured.RestAssured;

public class Wrappers extends HTMLReporter{
	
	public String dataFileName, dataFileType;	
	
	@BeforeSuite
	public void beforeSuite() {
		startReport();
	}
	
	@BeforeClass
	public void beforeClass() {
		startTestCase(testCaseName, testDescription);		
	}
	
	@BeforeMethod
	public void beforeMethod() {

	
	}

	@AfterMethod
	public void afterMethod() {

	
	}

	@AfterSuite
	public void afterSuite() {
		endResult();
	}

	@DataProvider(name="fetchData")
	public  Object[][] getData(){
		if(dataFileType.equalsIgnoreCase("Excel"))
			return DataInputProvider.getSheet(dataFileName);	
		else {
			Object[][] data = new Object[1][1];
			data[0][0] = new File("./data/"+dataFileName+"."+dataFileType);
			System.out.println(data[0][0]);
			return data;
		}
			
	}	
	
	public void reportMethods() {
		svcTest = startTestModule(nodes);
		svcTest.assignAuthor(authors);
		svcTest.assignCategory(category);			
	}
	
}
