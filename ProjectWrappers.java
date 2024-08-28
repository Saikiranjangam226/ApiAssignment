package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.util.JSON;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.parser.ParseException;

public class ProjectWrappers extends ReadProperties{
		

	
 public static RequestSpecification setLogsForOurAPIs(String contenttype) {
   
	 RequestLogSpecification log = null ;	 
     if(contenttype.equalsIgnoreCase("JSON"))
     {	
	  log= RestAssured
	      .given()
	      .port(443)
	      .contentType(ContentType.JSON)
	      .log();	
      }		
		return log.all();
	}

	public static Response getResponse(String verb, String URL,String requestbody,String contenttype)
	{
		Response response = null;
		verb = verb.trim().toUpperCase();
		
		try {
			switch (verb) {
				case "GET":
					response = getWithURL(URL,contenttype);
					break;

				case "PATCH":
					response = patchWithBody(requestbody,URL,contenttype);
					break;

				case "POST":
					response = postWithBody(requestbody,URL,contenttype);
					break;

				case "DELETE":
					response = deleteWithURL(URL,contenttype);
					break;
					
				case "PUT" :
					response = putWithBody(requestbody,URL,contenttype);
					break;			
			}
		}
		catch (Exception e)
		{
			test.error(e.getMessage());
			System.out.println(e.getMessage());
			response = null;
		}
		
		return response;
	}



	public static Response getWithURL(String URL,String contenttype) {
		return setLogsForOurAPIs(contenttype)
				.when()
				.get(URL);
	}

	public static Response deleteWithURL(String URL,String contentype) {	
		return setLogsForOurAPIs(contentype)
				.when()		
				.delete(URL);
	}

	public static Response patchWithBody(String requestbody,String URL,String contenttype) {	
	    	return setLogsForOurAPIs(contenttype)
					.body(requestbody)
					.patch(URL);  	  
	}
	
	public static Response putWithBody(String requestbody,String URL,String contenttype) {
	        return setLogsForOurAPIs(contenttype)
    				.body(requestbody)
    				.put(URL);   
	}



	public static Response postWithBody(String requestbody, String url,String contenttype) throws IOException {
		Response response=null;
	        response= setLogsForOurAPIs(contenttype)
    				.body(requestbody)
    				.post(url);  	
		return response;
	}
	   


	public static Response patchWithBodyAsString(String body, String url,String contenttype ) {
		return setLogsForOurAPIs(contenttype)
				.body(body)
				.patch(url);
	}

	public static void verifyContentType(Response response, String type) {
		if(response.getContentType().toLowerCase().contains(type.toLowerCase())) {
			reportRequest("The Content type "+type+" matches the expected content type", "PASS");
		}else {
			reportRequest("The Content type "+response.getContentType()+" does not match the expected content type "+type, "FAIL");	
		}
	}

	
	public static void verifyResponseCode(Response response, int code) {
		
		if(response.statusCode() == code) {
			reportRequest("The status code "+code+" matches the expected code ", "PASS");
		}else {
			reportRequest("The status code "+response.statusCode()+" does not match the expected code "+code, "FAIL");
		}
	}
	
	public static void verifyResponseTime(Response response, long time) {
		if(response.time() <= time) {
			reportRequest("The time taken "+response.time() +" with in the expected time", "PASS");
		}else {
			reportRequest("The time taken "+response.time() +" is greater than expected SLA time "+time,"FAIL");		
		}
	}
	

	
}