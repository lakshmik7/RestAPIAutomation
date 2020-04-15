package com.qa.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{
	TestBase testbase;
	String serviceurl;
	String apiurl;
	RestClient restclient;
	String URL ;
	CloseableHttpResponse closeableHttpResponse;
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		//accessing prop using constructoriniatilization
		testbase = new TestBase();
		 serviceurl = prop.getProperty("ServiceUrl");
		 apiurl = prop.getProperty("APIURL");
		
		 URL = serviceurl+apiurl;
		 
	}
	
	@Test(priority=1)
	public void getAPITestwithout() throws ClientProtocolException, IOException {
	restclient = new RestClient();
	 closeableHttpResponse = restclient.GetMethod(URL);
		//status code
		int statuscode= closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("status code : "+ statuscode);
		//asserting
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
		
		//response json
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJSON = new JSONObject(responsestring);
		System.out.println("response json "+ responseJSON);
		
		//single value assertion
		//perpage
		String perPageValue = TestUtil.getValueByJpath(responseJSON, "/per_page");
		System.out.println("per page value is  : " +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), perpageValue , "perpage value is not 6");
		//total
		String totalValue = TestUtil.getValueByJpath(responseJSON, "/total");
		System.out.println("total value is  : " +totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), totalvalue , "total value value is not 12");
		
		//getvalue from json array
		
		String lastname = TestUtil.getValueByJpath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJpath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJpath(responseJSON, "/data[0]/avatar");
		String firstname = TestUtil.getValueByJpath(responseJSON, "/data[0]/first_name");
		
		System.out.println(lastname);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstname);
		
		
		
		//response headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String,String> allheaders = new HashMap<String,String>();
		for(Header header: headerArray)
		{
			allheaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("headers array" + allheaders);
		
	}
	
	@Test(priority=2)
	public void getAPITestwithHeaders() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		 closeableHttpResponse = restclient.GetMethod(URL,headerMap);
		 
			//status code
			int statuscode= closeableHttpResponse.getStatusLine().getStatusCode();
			System.out.println("status code : "+ statuscode);
			//asserting
			Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
			
			//response json
			String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			
			JSONObject responseJSON = new JSONObject(responsestring);
			System.out.println("response json "+ responseJSON);
			
			//single value assertion
			//perpage
			String perPageValue = TestUtil.getValueByJpath(responseJSON, "/per_page");
			System.out.println("per page value is  : " +perPageValue);
			Assert.assertEquals(Integer.parseInt(perPageValue), perpageValue , "perpage value is not 6");
			//total
			String totalValue = TestUtil.getValueByJpath(responseJSON, "/total");
			System.out.println("total value is  : " +totalValue);
			Assert.assertEquals(Integer.parseInt(totalValue), totalvalue , "total value value is not 12");
			
			//getvalue from json array
			
			String lastname = TestUtil.getValueByJpath(responseJSON, "/data[0]/last_name");
			String id = TestUtil.getValueByJpath(responseJSON, "/data[0]/id");
			String avatar = TestUtil.getValueByJpath(responseJSON, "/data[0]/avatar");
			String firstname = TestUtil.getValueByJpath(responseJSON, "/data[0]/first_name");
			
			System.out.println(lastname);
			System.out.println(id);
			System.out.println(avatar);
			System.out.println(firstname);
			
			
			
			//response headers
			Header[] headerArray = closeableHttpResponse.getAllHeaders();
			
			HashMap<String,String> allheaders = new HashMap<String,String>();
			for(Header header: headerArray)
			{
				allheaders.put(header.getName(), header.getValue());
			}
			
			System.out.println("headers array" + allheaders);
			
		}
	
}
