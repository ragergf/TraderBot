package mx.org.traderbot.order.model;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.util.Constantes;

@Component
@Scope("prototype")
public class OrderModel {
	
	@Autowired
	Order order;
	
	@Autowired
	DataService<Order> service;
	
	Logger logger;
	
	public Order trade(String side, double quantity, double price, String symbol) throws BotException, Exception
	{	  		  
		Order response = null;
		order.setSide(side);
		order.setSymbol(symbol);
		order.setQuantity(quantity+"");
		order.setPrice(price+"");
		order.setType("limit");
		order.setTimeInForce("GTC");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(order);			
		    response = service.getData(Constantes.ORDER_BUY_SELL_TYPE, Order.class, jsonInString);
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
		
		return response;
	      
	}

	public Order[] cancelarOrdenes() throws BotException, Exception
	{
		Object obj = service.getData(Constantes.CANCEL_ORDER_TYPE, Order[].class, null);
		Order[] array = (Order[]) obj;
		return array;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
