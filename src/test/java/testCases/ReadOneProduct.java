package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadOneProduct {
	
	SoftAssert softAssertObj;
	
	public ReadOneProduct() {
		softAssertObj = new SoftAssert();
	}
	@Test
	public void readOneProduct() {

		 
		/*
		 * give: all input, details(base
		 * URI,Headers,Authorization,Payload/Body,QueryParameters) when: submit api
		 * requests(Http method,Endpoint/Resource) then: validate response(status code,
		 * Headers, responseTime, Payload/Body)
		 */

		// http method: GET
		// headers :
		// Content-Type= application/json
		// Authorization:
		// basicAuth: username,password
		// EndPointUrl:https://techfios.com/api-prod/api/product/read_one.php
		// queryParams:
		// id=65
		// statusCode= 200 OK

		Response response =

				given() 
				        .baseUri("https://techfios.com/api-prod/api/product").headers("Content-Type", "application/json")
						.auth().preemptive().basic("demo@techfios.com", "abc123").queryParam("id", "3898").
				when()
						.get("/read_one.php").
				then()
				        .extract().response();

		int actualStatusCode = response.getStatusCode();
		System.out.println("actualstatusCode: " + actualStatusCode);
      //Assert.assertEquals(actualStatusCode, 200);
	    softAssertObj.assertEquals(actualStatusCode, 200,"Status codes are not same");

		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader: " + actualHeader);
		softAssertObj.assertEquals(actualHeader, "application/json","Headers are not same");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualRresponseBody: " + actualResponseBody);

		JsonPath jasonPathObject = new JsonPath(actualResponseBody);

		String actualProductId = jasonPathObject.get("id");
		System.out.println("actualProductId: " + actualProductId);
		softAssertObj.assertEquals(actualProductId, "3898","Product Id's are not same");

		String actualProductName = jasonPathObject.get("name");
		System.out.println("actualProductName: " + actualProductName);
		softAssertObj.assertEquals(actualProductName, "MD's Amazing Pillow 3.0","Product Names are not same");

		String actualProductPrice = jasonPathObject.get("price");
		System.out.println("actualProductPrice: " + actualProductPrice);
		softAssertObj.assertEquals(actualProductPrice, "199","Product Prices are not same");
		
        softAssertObj.assertAll();
	}

}
