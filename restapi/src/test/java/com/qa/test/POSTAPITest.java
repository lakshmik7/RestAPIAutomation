package com.qa.test;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.data.Users;
import com.qa.restclient.RestClient;

public class POSTAPITest extends TestBase{
	TestBase testbase;
	String serviceurl;
	String apiurl;
	RestClient restclient;
	String URL ;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		//accessing prop using constructor iniatilization
		testbase = new TestBase();
		serviceurl = prop.getProperty("ServiceUrl");
		apiurl = prop.getProperty("APIURL");
		URL = serviceurl+apiurl;
	}
	@Test
	public void postAPIMethod() throws JsonGenerationException, JsonMappingException, IOException {
		restclient = new RestClient();
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson API - for mashelling and unmarshelling --add dependency 
		
		ObjectMapper mapper = new ObjectMapper();
		Users users=new Users("morpheus","leader"); //expected user object
		
		//java object to json file conversion
		mapper.writeValue(new File("C:\\abcd\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"), users); //users data is getting stored in json file at that location
		
		//covert jsonobject to json string
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		closeableHttpResponse = restclient.POSTMethod(URL, usersJsonString, headerMap); // hit post call
		
		//validate responsefrom api
		//1.status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_201,"status code is not 201");
		
		//2.json string 
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responsejson = new JSONObject(responseString);
		System.out.println("response from API"+ responsejson);
		
		//json to java object
		Users userResObj= mapper.readValue(responseString, Users.class); //actual user objct
		System.out.println(userResObj);
		Assert.assertTrue(users.getName().equals(userResObj.getName()));
		Assert.assertTrue(users.getJob().equals(userResObj.getJob()));
		
		System.out.println(userResObj.getId());
		System.out.println(userResObj.getCreatedAt());
	}
}
