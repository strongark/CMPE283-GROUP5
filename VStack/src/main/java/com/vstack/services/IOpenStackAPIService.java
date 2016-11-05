package com.vstack.services;

public interface IOpenStackAPIService {
	
	public String API_VERSION2 = "/v2/";
	public String API_VERSION3 = "/v3/";
	
	//GET APIs
	public String AUTH_API = API_VERSION3 + "auth/token";
	public String GET_PROJECT_API = API_VERSION3 + "projects";
	public String GET_FLAVORS = API_VERSION3 + "flavors";
	public String GET_IMAGES = API_VERSION3 + "images";
	
	//POST APIs
	public String CREATE_PROJECT_API ="";
	public String CREATE_FLAVOR = "flavor";
	

	///call shell command: $nova list
	public String getInstanceList();
	
	///call shell command: $openstack flavor
	public String getFlavorList();
	
	///call shell command: $openstack image list
	public String getImageList();

	///call shell command: 
	///$nova boot --flavor FLAVOR_ID --image IMAGE_ID --key-name KEY_NAME \
	///--user-data USER_DATA_FILE --security-groups SEC_GROUP_NAME --meta KEY=VALUE  INSTANCE_NAME
	public String launchInstance(String jsonInput);

	///call shell command: $nova pause INSTANCE_NAME
	public void pauseInstance(String jsonInput);
	
	///call shell command: $nova unpause INSTANCE_NAME
	public void startInstance(String jsonInput);	

	///call shell command: $nova suspend INSTANCE_NAME
	public void stopInstance(String jsonInput);
	
	///call shell command: $nova resume INSTANCE_NAME
	public void resumeInstance(String jsonInput);	

	///call shell command: $nova delete INSTANCE_NAME
	public String deleteInstance(String jsonInput);	


}
