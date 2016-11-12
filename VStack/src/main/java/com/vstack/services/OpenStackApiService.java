package com.vstack.services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenStackApiService implements IOpenStackAPIService {
//	String username="facebook10211240548909366";
//	String password="8EokyoCGWJmcKz06";

	private String token;
		
	private static final String HOST="http://localhost";
	
	private static final String KEYSTONE_IDENTITY = HOST+":5000/v3.0";
	private static final String NOVA_COMPUTE = HOST+":8774/v2.1";
	private static final String GLANCE_IMAGE = HOST+":9292/v3.0";
	private static final String NEUTRON_NETWORK = HOST+":9696/v3.0";
	

	public OpenStackApiService() {
		// TODO Auto-generated constructor stub
		
	}

	public OpenStackApiService(String username, String password) {
		// TODO Auto-generated constructor stub
		try {
			setToken(getTokenSimple(username, password));
			//System.out.println(jsonToken);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	}

	@Override
	public String getInstanceList() {
		// TODO Auto-generated method stub
		String json="";
		try {
			String url = NOVA_COMPUTE+"/servers";
			json=executeHttpGetRequest(getToken(), url);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;		
	}

	@Override
	public String getFlavorList() {
		// TODO Auto-generated method stub
		String json="";
		try {
			String url = NOVA_COMPUTE+"/flavors";
			json=executeHttpGetRequest(getToken(), url);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String getImageList() {
		// TODO Auto-generated method stub
		String json="";
		try {
			String url = NOVA_COMPUTE+"/images";
			json=executeHttpGetRequest(getToken(), url);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	//The API URL is the same with get instance list, 
	//however for create new instance it's post request
	@Override
	public String launchInstance(String jsonInput) {
		// TODO Auto-generated method stub
		String json="";
		try {
			String url = NOVA_COMPUTE+"/servers";
			json=executeHttpPostRequest(getToken(), url,jsonInput);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	//Pauses a server. Changes its status to PAUSED.
	//Specify the pause action in the request body.
	//Policy defaults enable only users with the administrative role
	//or the owner of the server to perform this operation. Cloud providers can change these permissions through the policy.json file.
	@Override
	public void pauseInstance(String server_id) {
		// TODO Auto-generated method stub
		String url = NOVA_COMPUTE+"/servers/"+server_id+"/action";
		String body="{\"pause\": null}";
		try {
			executeHttpPostRequest(getToken(), url, body);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startInstance(String server_id) {
		// TODO Auto-generated method stub
		String url = NOVA_COMPUTE+"/servers/"+server_id+"/action";
		String body="{\"os-start\": null}";
		try {
			executeHttpPostRequest(getToken(), url, body);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void  stopInstance(String server_id) {
		// TODO Auto-generated method stub
		String url = NOVA_COMPUTE+"/servers/"+server_id+"/action";
		String body="{\"os-stop\": null}";
		try {
			executeHttpPostRequest(getToken(), url, body);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void resumeInstance(String server_id) {

		String url = NOVA_COMPUTE+"/servers/"+server_id+"/action";
		String body="{\"resume\": null}";
		try {
			executeHttpPostRequest(getToken(), url, body);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteInstance(String server_id) {
		// TODO Auto-generated method stub
		String url = NOVA_COMPUTE+"/servers/"+server_id+"/action";
		String body="{\"forceDelete\": null}";
		try {
			executeHttpPostRequest(getToken(), url, body);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Author: Tran.Pham
	//Create date: Oct 1st, 2016
	//Desc: execute the http call to url
	public String executeHttpPostRequest(String token, String url, String body)
			throws IOException, ClientProtocolException, JSONException {

		 
		HttpPost Req = new HttpPost(url);		
	    Req.addHeader("Content-Type", "application/json");
	    Req.addHeader("accept", "application/json");
	    if(!token.isEmpty())
	    	Req.addHeader("X-Auth-Token", token);
	    if(!body.isEmpty()){
			HttpEntity entity = new ByteArrayEntity(body.getBytes()); 	
			Req.setEntity(entity);
	    }
	    
	    return executeHttpRequest(Req);
	}
	
	//Author: Tran.Pham
	//Create date: Oct 1st, 2016
	//Desc: execute the http call to url
	public String executeHttpGetRequest(String token, String url)
			throws IOException, ClientProtocolException, JSONException {

		 
		HttpGet Req = new HttpGet(url);		
	    Req.addHeader("Content-Type", "application/json");
	    Req.addHeader("accept", "application/json");
	    if(!token.isEmpty())
	    	Req.addHeader("X-Auth-Token", token);
	    
	    return executeHttpRequest(Req);
	}

	public String executeHttpRequest(HttpUriRequest request) throws IOException, ClientProtocolException {
//		DefaultHttpClient httpClient1 = new DefaultHttpClient();
		HttpClient httpClient1=HttpClientBuilder.create().build();
	    HttpResponse response1 = httpClient1.execute(request);
	    int statusCode=response1.getStatusLine().getStatusCode();
	    switch(statusCode)
	    {
			case 200: break;
			case 300: 
				throw new RuntimeException("Multiple version of API detected : "+ statusCode);
	    	default:
	    		throw new RuntimeException("Failed : HTTP error code : "+ statusCode);	    		
	    };
	    		
	   BufferedReader br = new BufferedReader(new InputStreamReader((response1.getEntity().getContent())));
	
	   String jsonData="";
	   String line;
	   while ((line = br.readLine()) != null) 
			jsonData += line + "\n";
		
	   httpClient1.getConnectionManager().shutdown();
	   
	   return jsonData;
	}

	//Author: Tran.Pham
	//Create date: Oct 1st, 2016
	//Desc: execute the http call to url
	public String getTokenSimple(String username, String password)
			throws RuntimeException, JSONException, IOException {
		
		String url = KEYSTONE_IDENTITY+"auth/tokens";
		String reqBody="{\"auth\":{\"passwordCredentials\":{\"username\":\""+username
				+ "\",\"password\":\""+password+"\"}}}";
		String jsonData=executeHttpPostRequest("", url, reqBody);
		
		JSONObject json= new JSONObject(jsonData);
		JSONObject jsonToken= json.getJSONObject("access").getJSONObject("token");
		return jsonToken.getString("id");
	}

	String getToken() {
		return token;
	}

	void setToken(String token) {
		this.token = token;
	}

}
