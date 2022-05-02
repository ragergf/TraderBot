package mx.org.traderbot.dao;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;

import mx.org.traderbot.examples.JsonExamples;

public class RestfulDAO {
	final static Logger logger = Logger.getLogger(RestfulDAO.class);
	
	Client client=null;
	int timeout=15000;
	
	public RestfulDAO()
	{
		this.timeout=15000;
		getClient();
	}
	
	public RestfulDAO(int timeout)
	{
		this.timeout=timeout;
		getClient();
	}
	
	public String getJson(String url, String params) throws Exception
	{
		
		String json=null;
		
		
		try {
		
			WebResource webResource=null;
			
			if(params !=  null && !params.trim().equalsIgnoreCase("") )
				webResource = client.resource(url + "?" + params);
			else
				webResource = client.resource(url);
			
			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//			}

			json = response.getEntity(String.class);
			
			logger.info("Output from Server .... \n");
			logger.info(json);

		}catch (ClientHandlerException e) {				  
			  logger.error(e);
			  logger.error("e.getMessage(): "+e.getMessage());
			  if(e.getMessage().contains("timed out") || e.getMessage().contains("Connection reset"))
			  {
				  return getJson(url,  params);
			  }
		  }
		  catch (Exception e) {		
			  logger.error(e);
			  throw e;
		  }
		
		return json;
	}
		
	public String postJson(String url, String params)
	{
		String json=null;
		
		
		try {
			
			WebResource webResource = client.resource(url);
					
			client.addFilter(new LoggingFilter());
			ClientResponse response;
			
			if(params != null && !params.trim().equals(""))
				response = webResource.type("application/json").post(ClientResponse.class, params);
			else
				response = webResource.type("application/json").post(ClientResponse.class);
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//				     + response.getStatus());
//			}
		
			logger.info("Output from Server .... \n");
			json = response.getEntity(String.class);
			logger.info(json);
		
		  }catch (ClientHandlerException e) {				  
			  logger.error(e);
			  logger.error("e.getMessage(): "+e.getMessage());
			  if(e.getMessage().contains("timed out") || e.getMessage().contains("Connection reset"))
			  {
				  return postJson(url,  params);
			  }
		  }
		  catch (Exception e) {		
			  logger.error(e);		
		  }
		
		return json;
	}
	
	public String deleteJson(String url, String params)
	{
		String json=null;
		
		
		try {
		
			WebResource webResource=null;
			
			if(params !=  null && !params.trim().equalsIgnoreCase("") )
				webResource = client.resource(url + "/" + params);
			else
				webResource = client.resource(url);
			
			ClientResponse response = webResource.accept("application/json")
					.delete(ClientResponse.class);

//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//			}

			json = response.getEntity(String.class);
			
			logger.info("Output from Server .... \n");
			logger.info(json);

		}catch (ClientHandlerException e) {				  
			  logger.error(e);
			  logger.error("e.getMessage(): "+e.getMessage());
			  if(e.getMessage().contains("timed out") || e.getMessage().contains("Connection reset"))
			  {
				  return deleteJson(url,  params);
			  }
		  }
		  catch (Exception e) {		
			  logger.error(e);		
		  }
		
		return json;

	}
	
	public String putJson(String url, String params)
	{
		String json=null;
		
			return json;
	}

	public void getClient()
	{
		String username = "e914646125eadc2c2fe551d5b6e1a6e0";
        String password = "58ab6f27acdf126c639902e63f6881b6";

        
		
		ClientConfig clientConfig = new DefaultClientConfig();
        client = Client.create(clientConfig);
//        client.setConnectTimeout(3000);
        client.setReadTimeout(timeout);

        final HTTPBasicAuthFilter authFilter = new HTTPBasicAuthFilter(username, password);
        client.addFilter(authFilter);
        client.addFilter(new LoggingFilter());		
	}
}
