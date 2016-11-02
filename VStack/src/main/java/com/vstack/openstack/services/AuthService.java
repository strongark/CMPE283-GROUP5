package com.vstack.openstack.services;

import com.vstack.beans.OpenstackConnection;
import com.vstack.services.IOpenStackAPIService;

public class AuthService implements IOpenStackAPIService{
	
	//hardcoded value
	public static String authToken = "d340583d683dd11c0076";
	public static String compute_url;
	
	
	public void setAuthToken(OpenstackConnection connection) {
		
		//Assuming http protocol
		compute_url = "http://" + connection.getServer() + ":" + connection.getPort();
		
		//TODO API Call 
		String authURL = compute_url + AUTH_API ; 
		
		
		
	}
	
}
