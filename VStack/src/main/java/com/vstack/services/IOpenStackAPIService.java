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
	
}
