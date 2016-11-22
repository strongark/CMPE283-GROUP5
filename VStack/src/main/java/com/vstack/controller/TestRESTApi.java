package com.vstack.controller;

import java.util.List;
import java.util.Map;

import com.vstack.beans.ComputeInstance;
import com.vstack.beans.InstanceDetails;
import com.vstack.beans.OpenstackConnection;
import com.vstack.openstack.services.AuthService;
import com.vstack.openstack.services.FlavorService;
import com.vstack.openstack.services.InstanceService;

public class TestRESTApi {

	public static void main(String[] args) {
		try{
		
				OpenstackConnection connection = new OpenstackConnection();
				connection.setServer("10.0.0.26");
				connection.setUsername("admin");
				connection.setPassword("admin_user_secret");
				 
				AuthService service = new AuthService();
				service.setAuthToken(connection);
				String token = service.getAuthToken();
				
				/*FlavorService flavorAPI = new FlavorService();
				Map<String, String> flavorList = flavorAPI.getFlavors(connection.getServer(), token);
				for (Map.Entry<String, String> entry : flavorList.entrySet()) {
					System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
				}*/
				
				InstanceService instanceService = new InstanceService(connection.getServer(), token);
				InstanceDetails details = instanceService.getInstanceByName("instnace1");
				
				Map<String, String> ipaddresses = details.getAddresses();
				for (Map.Entry<String, String> entry : ipaddresses.entrySet()) {
					System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
	}
}