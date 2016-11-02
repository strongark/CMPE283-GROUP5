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
			
			List<String> flavorList = getFlavors();
			
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
			
			List<String> projectList = getOpenstackProjects();
			
			return ow.writeValueAsString(projectList);

		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
			logger.fatal(VStackUtils.returnExceptionTrace(ex));
			return null;
		}
	}
	
	/**
	 * Get Openstack Projects
	 * 
	 * @throws Exception
	 */
	public List<String> getFlavors() throws  VStackException {
		List<String> flavorList = new ArrayList<String>();
		String url = AuthService.compute_url + GET_FLAVORS;

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);

			// add request header
			request.setHeader("X-Auth-Token", AuthService.authToken);
			HttpResponse response = client.execute(request);

			StringBuffer result = new StringBuffer();
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			// If the GET call returns success code
			if (response.getStatusLine().getStatusCode() == 200) {

				JsonFactory factory = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(factory);
				JsonNode rootNode = mapper.readTree(result.toString());

				Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.getFields();
				while (fieldsIterator.hasNext()) {

					Map.Entry<String, JsonNode> field = fieldsIterator.next();
					if (field.getKey().equals("flavors")) {
						JsonNode root = field.getValue();
						if (root.isArray()) {
							for (int i = 0; i < root.size(); i++) {
								Iterator<Entry<String, JsonNode>> node = root.get(i).getFields();
								while (node.hasNext()) {
									Entry<String, JsonNode> keyVal = node.next();
									if (keyVal.getKey().equals("name")) {

										flavorList.add(keyVal.getValue().asText());
									}
								}
							}
						}
					}
				}
			} else {
				System.err.println("Problem Occured. /flavors API Returned " + response.getStatusLine().getStatusCode());
				throw new VStackException("Error Executing Flavors API");
			}
		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
			VStackUtils.returnExceptionTrace(ex);
			throw new VStackException(ex);
		}
		return flavorList;
	}
	
	/**
	 * Get Openstack Projects
	 * 
	 * @throws Exception
	 */
	public List<String> getOpenstackProjects() throws  VStackException {
		List<String> projectList = new ArrayList<String>();
		String url = AuthService.compute_url + GET_PROJECT_API;

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);

			// add request header
			request.setHeader("X-Auth-Token", AuthService.authToken);
			HttpResponse response = client.execute(request);

			StringBuffer result = new StringBuffer();
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			// If the GET call returns success code
			if (response.getStatusLine().getStatusCode() == 200) {

				JsonFactory factory = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(factory);
				JsonNode rootNode = mapper.readTree(result.toString());

				Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.getFields();
				while (fieldsIterator.hasNext()) {

					Map.Entry<String, JsonNode> field = fieldsIterator.next();
					if (field.getKey().equals("projects")) {
						JsonNode root = field.getValue();
						if (root.isArray()) {
							for (int i = 0; i < root.size(); i++) {
								Iterator<Entry<String, JsonNode>> node = root.get(i).getFields();
								while (node.hasNext()) {
									Entry<String, JsonNode> keyVal = node.next();
									if (keyVal.getKey().equals("name")) {

										projectList.add(keyVal.getValue().asText());
									}
								}
							}
						}
					}
				}
			} else {
				System.err.println("Problem Occured. /projects API Returned " + response.getStatusLine().getStatusCode());
				throw new VStackException("Error Executing Projects API");
			}
		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
			VStackUtils.returnExceptionTrace(ex);
			throw new VStackException(ex);
		}
		return projectList;
	}
}
