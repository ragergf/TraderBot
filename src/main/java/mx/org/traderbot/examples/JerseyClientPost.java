package mx.org.traderbot.examples;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;

import mx.org.traderbot.order.vo.Order;

public class JerseyClientPost {

	final static Logger logger = Logger.getLogger(JerseyClientPost.class);
	
	  public static void main(String[] args) {

		try {

			String username = "0f854661384701222820a08561f6b11b";
            String password = "d7dbca90bd84bdfae8547c5d00629db3";
            
            ObjectMapper mapper = new ObjectMapper();
            Order order = new Order();
            order.setSymbol("btcusd");
            order.setQuantity("0.24");
            order.setPrice("8500");
            order.setSide("buy");
            order.setType("limit");
            order.setTimeInForce("GTC");
            
			//Convert object to JSON string
			String jsonInString = mapper.writeValueAsString(order);
			logger.info(jsonInString);

            ClientConfig clientConfig = new DefaultClientConfig();
            Client client = Client.create(clientConfig);
            

	        final HTTPBasicAuthFilter authFilter = new HTTPBasicAuthFilter(username, password);
	        client.addFilter(authFilter);
	        client.addFilter(new LoggingFilter());

			WebResource webResource = client
			   .resource("https://api.hitbtc.com/api/2/order");

//			String jsonInString = "{\"symbol\":\"btcusd\",\"side\":\"buy\",\"quantity\":\"0.25\",\"price\":\"8000\",\"type\":\"limit\",\"timeInForce\":\"GTC\"}";
			
			client.addFilter(new LoggingFilter());

			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, jsonInString);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			logger.info("Output from Server .... \n");
			String output = response.getEntity(String.class);
			logger.info(output);

		  } catch (Exception e) {

			e.printStackTrace();

		  }

		}
	}
