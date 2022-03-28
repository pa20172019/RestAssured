package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CreateAProduct {
	
	SoftAssert softAssertObj;
	HashMap<String,String> createPayload;

	
	public CreateAProduct() {
		softAssertObj = new SoftAssert();
		
	}	
		public Map<String,String> createPayloadMap(){
			createPayload =  new HashMap<String,String>();
			createPayload.put("name","MDs Amazing Pillow 3.0");
			createPayload.put("price","199");
			createPayload.put("description","The best pillow for amazing programmers.");
			createPayload.put("category_id","2");
			System.out.println("createPayloadMap" + createPayload.toString() );
			return createPayload;
		}
		
		
		
	@Test
	public void createAProduct() {

		        Response response =

				given() 
				        .baseUri("https://techfios.com/api-prod/api/product").headers("Content-Type", "application/json")
						.auth().preemptive().basic("demo@techfios.com", "abc123").queryParam("id", "3698")
//						.body(new File("src\\main\\java\\createPayload.json")).
						.body(createPayloadMap()).
				when()
						.post("/create.php").
				then()
				        .extract().response();
		       

		int actualStatusCode = response.getStatusCode();
		System.out.println("actualstatusCode: " + actualStatusCode);
	    softAssertObj.assertEquals(actualStatusCode, 201,"Status codes are not matching");

		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader: " + actualHeader);
		softAssertObj.assertEquals(actualHeader, "application/json; charset=UTF-8","Headers are not  matching");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jasonPathObject = new JsonPath(actualResponseBody);

		String actualProductMessage = jasonPathObject.get("message");
		System.out.println("actualProductMessage: " + actualProductMessage);
		softAssertObj.assertEquals(actualProductMessage, "Product was created.","Product messages are not matching");

        softAssertObj.assertAll();
	}

}
