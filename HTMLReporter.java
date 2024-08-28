package lib;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class HTMLReporter {

	public static ExtentHtmlReporter html;
	public static ExtentReports extent;
	public static ExtentTest svcTest;
	public static ExtentTest testSuite, test;
	public String testCaseName, testDescription, nodes, authors,category;
	

	//@BeforeSuite

	public void startReport() {
		html = new ExtentHtmlReporter("./reports/result_"+".html");
		html.setAppendExisting(true);
		html.loadXMLConfig("./src/main/resources/extent-config.xml");
		extent = new ExtentReports();
		extent.attachReporter(html);
	}

	//@BeforeClass
	public ExtentTest startTestCase(String testCaseName, String testDescription) {
		testSuite = extent.createTest(testCaseName, testDescription);		
		return testSuite;
	}

	//@BeforeMethod
	public ExtentTest startTestModule(String nodes) {
		test = testSuite.createNode(nodes);
		return test;
	}

	//@AfterSuite
	public void endResult() {
		extent.flush();
	}

	public static void reportRequest(String desc, String status) {
		try {
			if (status.equalsIgnoreCase("PASS")) {
				svcTest.pass(desc);
			}
			else if (status.equalsIgnoreCase("FAIL")) {
				svcTest.fail(desc);
				//svcTest.log(Status.FAIL, desc+" "+ Thread.currentThread().getStackTrace().toString());
			}
			else if (status.equalsIgnoreCase("WARNING")) {
					svcTest.warning(desc);
				} else {
					svcTest.info(desc);
				}
			}catch (Exception e) {
			e.printStackTrace();
		}

	}
}