package com.vstack.openstack.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.vstack.services.IOpenStackAPIService;
import com.vstack.services.VStackException;
import com.vstack.services.VStackUtils;

public class FlavorService implements IOpenStackAPIService{
	
	private static Logger logger = Logger.getLogger("FlavorService");
	
	
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
}
