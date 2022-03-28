package testCases;

import org.testng.Assert;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {
	
	SoftAssert softAssert = new SoftAssert();
	
	@Test
	public void readAllproducts() {
		
		
		Response response =
		
		given()
		       .log().all()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .headers("Content-Type","application/json; charset=UTF-8")
		       .auth().preemptive().basic("demo@techfios.com","abc123").
		       
		when()
		       .log().all()
		       .get("/read.php").
		then()
		       .log().all()
		       .extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actualstatusCode: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		       
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader: " + actualHeader);
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		String actualRresponseBody = response.getBody().asString();
		System.out.println("actualRresponseBody: " + actualRresponseBody);
		
		JsonPath jp = new JsonPath(actualRresponseBody);
		String firstProductId = jp.get("records[0].id");
		System.out.println("firstProductId: "+  firstProductId);
		
		if(firstProductId != null) {
			System.out.println("Records are not null");
		}else {
			System.out.println("Records are null");
		}
			
		 
		
	}

}
