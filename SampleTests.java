package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ProjectWrappers;
import lib.ReadProperties;
import net.minidev.json.parser.ParseException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SampleTests extends ProjectWrappers {

	@BeforeTest 
	public void setValues() {
		RestAssured.useRelaxedHTTPSValidation();
		testCaseName = "Sample API Tests";
		testDescription = "Sample API Tests";
		authors = "Sai Kiran";
		category = "Functional Tests";
		dataFileName = "Data_MasterSheet";
		dataFileType = "Excel";
	}

	@Test (dataProvider = "fetchData")
	public void Tests(String method,String description, String execute,String endPoint, String contentType, String requestBody, String expectedResponseCode, String expectedContentType) throws ParseException {
			
		if(execute.trim().equalsIgnoreCase("no"))
		{
     		return;			
		}
		
		nodes=method+":"+description;
		reportMethods();	
		String BaseUrl = ReadProperties.getbaseurl();
        String URL = BaseUrl+endPoint;
        System.out.println(URL);
		test.info("URL: " + URL);
		
		Response response = getResponse(method, URL, requestBody,contentType);

		if(response == null)
		{
			test.error("Bad Response, probably due to wrong VERB : " + method);
			return;
		}

 //Verify StatusCode		

		int code = Integer.parseInt(expectedResponseCode);
		verifyResponseCode(response, code);

 //Verify Response	
		
		// Verify Expected Response
		verifyContentType(response,expectedContentType);

 // Verify ResponseTime
		
		verifyResponseTime(response, 30000);
	}
			
}
