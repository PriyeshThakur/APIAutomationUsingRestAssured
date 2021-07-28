package com.APIAutomation;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Class for making HTTP REST calls
 * 
 * @author Priyesh Thakur
 *
 */
public class RestAPI {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	RequestSpecification httpRequest = null;

	/**
	 * Function to make an REST api call. To be used when we need to pass JSON
	 * object as request body or Form Parameters as body
	 * 
	 * @param strVerb    Value can be "put","post","get","delete", "head",
	 *                   "options", "patch"
	 * @param arrParam   Array of Strings: 1st array element - baseURI E.g.,
	 *                   "http://www.test.com/api/myproject/asset", 2nd array
	 *                   element would be query parameters - Method E.g., "?env=qa"
	 * @param objJSON    JSON body object of the type JSONObject
	 * @param header     HashMap<String, String> - Having key value pair of headers
	 * @param formParams HashMap<String, String> - Having key value pair of form
	 *                   parameters
	 * @param strFuture  Reserved for future use
	 * @return Response Body of the type Response
	 */

	public Response call(String strVerb, String arrParam[], JSONObject objJSON, HashMap<String, String> header,
			HashMap<String, String> formParams, String strFuture) {

		try {

			RestAssured.baseURI = arrParam[0];
			httpRequest = RestAssured.given().relaxedHTTPSValidation().given();
			JSONObject jsonBody = objJSON;
			boolean formParamSet = false;

			addHeader(header);
			formParamSet = addBodyFormParams(formParams);

			if (formParamSet) {
				// Cannot set formParams and body both for API calls
			} else {
				// Add the JSON to the body of the request
				httpRequest.body(jsonBody.toString());
			}

			// Post the request and return the response
			Response response = makeCall(strVerb, arrParam[1]);

			LOGGER.info(strVerb + " call - " + arrParam[0] + arrParam[1] + ", JSON: " + objJSON + ", Status: "
					+ response.getStatusLine() + ". Response Time - " + response.time());
			return response;

		} catch (Exception e) {
			LOGGER.info(strVerb + " call - " + arrParam[0] + arrParam[1] + ", JSON: " + objJSON + ", Exception: "
					+ e.getMessage());
		}
		return null;

	}

	/**
	 * To be used when we need to pass raw text as request body
	 * 
	 * @param strVerb     Value can be "put","post","get","delete", "head",
	 *                    "options", "patch"
	 * @param arrParam    Array of Strings: 1st array element - baseURI E.g.,
	 *                    "http://www.test.com/api/myproject/asset", 2nd array
	 *                    element would be query parameters - Method E.g., "?env=qa"
	 * @param strTextBody Text body
	 * @param header      HashMap<String, String> - Having key value pair of headers
	 * @return Response Body of the type Response
	 */
	public Response call(String strVerb, String arrParam[], String strTextBody, HashMap<String, String> header) {

		try {

			RestAssured.baseURI = arrParam[0];
			httpRequest = RestAssured.given().relaxedHTTPSValidation().given();

			addHeader(header);

			// Add the JSON to the body of the request
			httpRequest.body(strTextBody.toString());

			// Post the request and return the response
			Response response = makeCall(strVerb, arrParam[1]);

			LOGGER.info(strVerb + " call - " + arrParam[0] + arrParam[1] + ", Body: " + strTextBody + ", Status: "
					+ response.getStatusLine() + ". Response Time - " + response.time());
			return response;

		} catch (Exception e) {
			LOGGER.info(strVerb + " call - " + arrParam[0] + arrParam[1] + ", Body: " + strTextBody + ", Exception: "
					+ e.getMessage());
		}
		return null;

	}

	/**
	 * @param header HashMap<String, String> - Having key value pair of headers
	 */
	@SuppressWarnings("rawtypes")
	public void addHeader(HashMap<String, String> header) {
		// Add a header
		if (!header.isEmpty()) {
			Iterator it = header.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				httpRequest.header(pair.getKey().toString(), pair.getValue().toString());
				it.remove(); // avoids a ConcurrentModificationException
			}
		}
	}

	/**
	 * @param formParams HashMap<String, String> - Having key value pair of Form
	 *                   Parameters
	 * @return true if form parameters set successfully, else return false
	 */
	@SuppressWarnings("rawtypes")
	public boolean addBodyFormParams(HashMap<String, String> formParams) {
		// Add form params
		boolean formParamSet = false;
		if (!formParams.isEmpty()) {
			Iterator it = formParams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				httpRequest.formParam(pair.getKey().toString(), pair.getValue().toString());
				it.remove(); // avoids a ConcurrentModificationException
			}
			formParamSet = true;
		}
		return formParamSet;
	}

	/**
	 * @param strVerb        Value can be "put","post","get","delete", "head",
	 *                       "options", "patch"
	 * @param strQueryParams query parameters - Method E.g., "?env=qa"
	 * @return Response Body of the type Response
	 */
	public Response makeCall(String strVerb, String strQueryParams) {

		Response response = null;
		switch (strVerb) {
		case "get":
			response = httpRequest.get(strQueryParams);
			break;
		case "put":
			response = httpRequest.put(strQueryParams);
			break;
		case "delete":
			response = httpRequest.delete(strQueryParams);
			break;
		case "post":
			response = httpRequest.post(strQueryParams);
			break;
		case "head":
			response = httpRequest.head(strQueryParams);
			break;
		case "options":
			response = httpRequest.options(strQueryParams);
			break;
		case "patch":
			response = httpRequest.patch(strQueryParams);
			break;
		default:
			Assert.assertTrue(false, "Invalid parameter passed to RestAPI-call function. Parameter: " + strVerb);
		}

		return response;

	}
}
