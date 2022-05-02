package mx.org.traderbot.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import mx.org.traderbot.fibo.model.FiboModel;
import mx.org.traderbot.orderbook.vo.OrderBook;
import mx.org.traderbot.trade.vo.Trade;

public class JacksonExample {
	
	final static Logger logger = Logger.getLogger(JacksonExample.class);
	
//	public static void main(String[] args) {
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		//For testing
//		User user = createDummyUser();
//		
//		try {
//			//Convert object to JSON string and save into file directly 
//			mapper.writeValue(new File("D:\\user.json"), user);
//			
//			//Convert object to JSON string
//			String jsonInString = mapper.writeValueAsString(user);
//			logger.info(jsonInString);
//			
//			//Convert object to JSON string and pretty print
//			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//			logger.info(jsonInString);
//			
//			
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	
	public static void main(String[] args) {
//		String output = "";
//		logger.info("iniciando");
//		
//		try {
//			String username = "e914646125eadc2c2fe551d5b6e1a6e0";
//	        String password = "58ab6f27acdf126c639902e63f6881b6";
//
//
//            ClientConfig clientConfig = new DefaultClientConfig();
//
//            
//
//            Client client = Client.create(clientConfig);
//            
//            //
//
//
//                final HTTPBasicAuthFilter authFilter = new HTTPBasicAuthFilter(username, password);
//                client.addFilter(authFilter);
//                client.addFilter(new LoggingFilter());
//
//			logger.info("Antes de invocar");
////			Client client = Client.create();
////			client.addFilter(new HTTPBasicAuthFilter(user, password));
//			
////			String input = "{'symbol':'btcusd', 'side': 'buy', 'quantity': '0.25', 'price': '8000', 'type':'limit' }";
////			String input = "symbol:btcusd";
//			String input = "{\"symbol\":\"btcusd\"}";
////			String input = " {\"symbol\":\"btcusd\", \"side\": \"buy\", \"quantity\": \"0.25\", \"price\": \"8000\", \"type\":\"limit\" }";
//			
//
//			WebResource webResource = client
//					.resource("https://api.hitbtc.com/api/2/order");
//			
//			
//
//			ClientResponse response = webResource.accept("application/text")
//					.post(ClientResponse.class, input);
//
//			logger.info("despues de invocar");
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus() );
//			}
//
//			output = response.getEntity(String.class);
//			
//			logger.info("Output from Server .... \n");
//			logger.info(output);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//		}
//

		ObjectMapper mapper = new ObjectMapper();
		Product prod = new Product();
		Department dep = new Department();
		
		dep.setId(2l);
		
		prod.setId(1l);
		prod.setName("paracetamol");
		prod.setDepartmentId(dep);
		
		try {
			System.out.println(mapper.writeValueAsString(prod));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//
//			// Convert JSON string from file to Object
////			TypeReference<List<Trade>> typeReference = new TypeReference() {};
//			Trade []orderBook = (Trade[]) mapper.readValue(output, Trade[].class);
//			logger.info(orderBook);
//
//			// Convert JSON string to Object
////			String jsonInString = "{\"age\":33,\"messages\":[\"msg 1\",\"msg 2\"],\"name\":\"mkyong\"}";
////			User user1 = mapper.readValue(jsonInString, User.class);
////			logger.info(user1);
//
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

	private static User createDummyUser(){
		
		User user = new User();
		
		user.setName("mkyong");
		user.setAge(33);

		List<String> msg = new ArrayList<String>();
		msg.add("hello jackson 1");
		msg.add("hello jackson 2");
		msg.add("hello jackson 3");

		user.setMessages(msg);
		
		return user;
		
	}
}