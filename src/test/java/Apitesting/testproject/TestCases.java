package Apitesting.testproject;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class TestCases {
ResponseSpecification res;
	
	@BeforeClass
	public void setSpecification() {
		res = RestAssured.expect();
		res.statusLine("HTTP/1.1 200 OK");
		res.contentType(ContentType.JSON);
		ExtentReportManager.createReport();
	}
	
//	GET
	@Test(description="Health Checkup (ping server)", testName="healthCheck")
	public void healthCheck() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://restful-booker.herokuapp.com/");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "ping");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "statusCode");
		ResponseSpecification resp;
		resp = RestAssured.expect();
		resp = RestAssured.expect();
		resp.statusLine("HTTP/1.1 201 Created");
		resp.contentType(ContentType.TEXT);
		RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
		given().when().get("ping").then().spec(resp).assertThat().statusCode(201);
	}
	
	
	@Test(description="Generating auth token",testName="testCreateToken")
	public void testCreateToken() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/auth");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","StatusCode");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		HashMap<String,String> params = new HashMap<>();
		params.put("username", "admin123");
		params.put("password", "password123");
		given().when().contentType("application/json").body(params).post("/auth").then().spec(res);
		
	}
	
	@Test(description="Getting the list of booking ids",testName="testgetbookingid")
	public void testgetbookingid() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/booking");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","bookingid");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		given().when().get("/booking").then().assertThat().body("[0].bookingid", IsEqual.equalTo(605));
		
	}
	
	
	
	@Test(description="Fetches the booking details using id", testName="getBookingId")
	public void getBookingId() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://restful-booker.herokuapp.com/");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "booking/1089");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "firstname");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
		given().when().get("booking/399").then().spec(res).assertThat().body("firstname", IsEqual.equalTo("Austin"));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(description="Creates booking details", testName="createBooking")
	public void createBooking() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://restful-booker.herokuapp.com/");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Payload", "{firstname: \"Shivam\", lastname: \"Pandoh\", totalprice: 111, depositpaid: true, bookingdates: {checkin: \"2022-01-01\", checkout: \"2022-01-10\"}, additionalNeeds: \"Lunch\"}");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "booking");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "booking.firstname");
		JSONObject data = new JSONObject();
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2022-01-01");
		bookingdates.put("checkout", "2022-01-10");
		data.put("firstname", "Shivam");
		data.put("lastname", "Pandoh");
		data.put("totalprice", 111);
		data.put("depositpaid", true);
		data.put("bookingdates", bookingdates);
		data.put("additionalneeds", "Lunch");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
		given().when().contentType("application/json").body(data).post("booking").then().assertThat().body("booking.firstname", IsEqual.equalTo("Shivam"));
	}
	
	
	
}
