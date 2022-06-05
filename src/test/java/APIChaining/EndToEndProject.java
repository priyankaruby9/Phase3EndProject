package APIChaining;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndProject {
	
	Response response;
	String BaseURI = "http://54.167.66.255:8088/employees";
	@Test
	public void test1() {
		
		response = GetMethodAll();
		System.out.println("the get response: " + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		response = PostMethod("Lucia", "dev", "5005", "Lucia1234@abc.com");
		System.out.println("the Post response: " + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 201);
		
		JsonPath Jpath =response.jsonPath();
		int empid = Jpath.get("id");
		System.out.println("id " + empid);
		
		response = PutMethod(empid,"Raj", "dev", "2000", "rajdev@abc.com");
		System.out.println("the Put response: " + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Jpath =response.jsonPath();
		Assert.assertEquals(Jpath.get("firstName"), "Raj");
	
		response = DeleteMethod(empid);
		Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), ""); 
        
        response = GetMethod(empid);
        Jpath =response.jsonPath();
        Assert.assertEquals(Jpath.get("message"), "Entity Not Found");
		Assert.assertEquals(response.getStatusCode(), 400);        
	}
	
	
	public Response GetMethodAll() {
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		return response;		
	}
	
	public Response PostMethod(String FirstName,String Lastname,String Salary, String Email) {
		
		RestAssured.baseURI = BaseURI;
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", Lastname);
		jobj.put("salary", Salary);
		jobj.put("email", Email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.post();
	
		return response;
	}
	
	public Response PutMethod(int Empid, String FirstName, String Lastname,String Salary, String Email) {
		
		RestAssured.baseURI = BaseURI;
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", Lastname);
		jobj.put("salary", Salary);
		jobj.put("email", Email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.put("/" + Empid);
	
		return response;
	}
	
	public Response DeleteMethod(int Empid) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/" + Empid);
	
		return response;
	}
	
	public Response GetMethod(int Empid) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/" + Empid);
	
		return response;
	}

}
