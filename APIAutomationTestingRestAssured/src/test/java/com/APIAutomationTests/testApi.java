package com.APIAutomationTests;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.APIAutomation.GenericHelpers;
import com.APIAutomation.GenericVariables;
import com.APIAutomation.RestAPI;
import com.APIAutomation.postReqPayloadUsers;

import io.restassured.response.Response;

public class testApi {
	RestAPI restapi = new RestAPI();

	@Test
	public void getResponse() {

		HashMap<String, String> formParams = new HashMap<>();
		formParams.put("page", "2");
		String endpointHost = GenericVariables.endpoint;
		String arrGetParam[] = { endpointHost + GenericVariables.userApi_Get, "" };

		HashMap<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		JSONObject objGetJSON = new JSONObject();

		Response GetResponse = restapi.call("get", arrGetParam, objGetJSON, header, formParams, "");
		System.out.println(GetResponse.asString());
		System.out.println(GetResponse.asString());
	}

	@Test
	public void postResponsePojo() {

		HashMap<String, String> formParams = new HashMap<>();
		String endpointHost = GenericVariables.endpoint;
		String arrGetParam[] = { endpointHost + GenericVariables.userApi_Post, "" };

		HashMap<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		postReqPayloadUsers obj = new postReqPayloadUsers("222", "2020-10-28T13:56:59:868Z");
		JSONObject objGetJSON = new JSONObject(obj);

		Response GetResponse = restapi.call("post", arrGetParam, objGetJSON, header, formParams, "");
		System.out.println(GetResponse.asString());
		System.out.println(GetResponse.asString());
		System.out.println(GetResponse.getStatusCode() + " " + GetResponse.getStatusLine());
	}

	@Test
	public void postResponseTextFile() throws Exception {

		HashMap<String, String> formParams = new HashMap<>();
		String endpointHost = GenericVariables.endpoint;
		String arrGetParam[] = { endpointHost + GenericVariables.userApi_Post, "" };

		HashMap<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		String data = GenericHelpers.readFileAsString(GenericVariables.payloadPath + "userCreation.txt");
		String newData = data.replaceAll("userId", "121").replaceAll("createdTime", "2020-10-28T13:56:59:868Z");
		JSONObject objGetJSON = new JSONObject(newData);
		
		//Response GetResponse = restapi.call("post", arrGetParam, newData, header);

		Response GetResponse = restapi.call("post", arrGetParam, objGetJSON, header, formParams, "");
		

		//Response GetResponse = restapi.call("post", arrGetParam, objGetJSON, header, formParams, "");
		System.out.println(GetResponse.asString());
		System.out.println(GetResponse.asString());
		System.out.println(GetResponse.getStatusCode() + " " + GetResponse.getStatusLine());
	}
}
