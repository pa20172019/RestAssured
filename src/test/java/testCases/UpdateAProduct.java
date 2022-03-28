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

public class UpdateAProduct {
	
	SoftAssert softAssertObj;
	HashMap<String,String> updatePayload;

	
	public UpdateAProduct() {
		softAssertObj = new SoftAssert();
		
	}	
		 
		
		public Map<String,String> updatePayloadMap(){
			updatePayload =  new HashMap<String,String>();
			updatePayload.put("id","3911");
			updatePayload.put("name","MDs Amazing Pillow 2.0");
			updatePayload.put("price","299");
			updatePayload.put("description","The best pillow for amazing programmers.");
			updatePayload.put("category_id","2");
			System.out.println("updatePayloadMap" + updatePayload.toString() );
			return updatePayload;
		}
		
		
	@Test
	public void updateAProduct() {

		        Response response =

				given() 
				        .baseUri("https://techfios.com/api-prod/api/product").headers("Content-Type", "application/json")
						.auth().preemptive().basic("demo@techfios.com", "abc123").queryParam("id", "3911")
//						.body(new File("src\\main\\java\\createPayload.json")).
						.body(updatePayloadMap()).
				when()
						.put("/update.php").
				then()
				        .extract().response();
		       

		int actualStatusCode = response.getStatusCode();
		System.out.println("actualstatusCode: " + actualStatusCode);
	    softAssertObj.assertEquals(actualStatusCode, 200,"Status codes are not matching");

		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader: " + actualHeader);
		softAssertObj.assertEquals(actualHeader, "application/json; charset=UTF-8","Headers are not  matching");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jasonPathObject = new JsonPath(actualResponseBody);

		String actualProductMessage = jasonPathObject.get("message");
		System.out.println("actualProductMessage: " + actualProductMessage);
		softAssertObj.assertEquals(actualProductMessage, "Product was updated.","Product messages are not matching");

        softAssertObj.assertAll();
	}

}
