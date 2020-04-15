package com.qa.restclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class RestClient {
	public CloseableHttpResponse GetMethod(String url) throws ClientProtocolException, IOException {
		//1.GET method w/o header

		//create client connection
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//http get request
		HttpGet httpget= new HttpGet(url);
		// 	hit the get url
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);

		return closeableHttpResponse;

	}
	//2.GET method with headers
	public CloseableHttpResponse GetMethod(String url,HashMap <String,String> headermap) throws ClientProtocolException, IOException {


		//create client connection
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//http get request
		HttpGet httpget= new HttpGet(url);
		// 	hit the get url
		for(Map.Entry<String,String> entry : headermap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);

		return closeableHttpResponse;

	}
	//3.POST method
	public CloseableHttpResponse POSTMethod(String url,String entityString , HashMap<String,String> headerMAp) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new StringEntity(entityString));//for payload
		//for headers
		for(Map.Entry<String,String> entry : headerMAp.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;
	}
}
