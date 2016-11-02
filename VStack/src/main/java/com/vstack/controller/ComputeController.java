package com.vstack.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vstack.openstack.services.AuthService;
import com.vstack.openstack.services.FlavorService;
import com.vstack.services.Bootstrap;
import com.vstack.services.IOpenStackAPIService;
import com.vstack.services.VStackException;
import com.vstack.services.VStackUtils;

@Controller
public class ComputeController implements IOpenStackAPIService {

	@Autowired
	ServletContext context;

	@Autowired
	Bootstrap bootstrap;

	@Autowired
	AuthService authentication;

	private static Logger logger = Logger.getLogger("ComputeController");

	/**
	 * Get Flavors
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getFlavor", method = RequestMethod.GET)
	public @ResponseBody String getFlavor(HttpServletResponse response) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		logger.info("\n--------- Getting Flavor Data ---------");

		try {
			
			FlavorService flavorAPI = new FlavorService();
			List<String> flavorList = flavorAPI.getFlavors();
			
			return ow.writeValueAsString(flavorList);

		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
			logger.fatal(VStackUtils.returnExceptionTrace(ex));
			return null;
		}
	}
	
	/**
	 * Get Flavors
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getProject", method = RequestMethod.GET)
	public @ResponseBody String getProject(HttpServletResponse response) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		logger.info("\n--------- Getting Project Data ---------");

		try {
			
			AuthService authentication = new AuthService();
			List<String> projectList = authentication.getOpenstackProjects();
			
			return ow.writeValueAsString(projectList);

		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
			logger.fatal(VStackUtils.returnExceptionTrace(ex));
			return null;
		}
	}
	
	
	

}
