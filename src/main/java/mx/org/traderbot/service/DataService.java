package mx.org.traderbot.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.dao.RestfulDAO;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.exception.ErrorBot;
import mx.org.traderbot.util.Constantes;

@Component
@Scope("prototype")
public class DataService<T> {
	final static Logger logger = Logger.getLogger(DataService.class);
	
	public T getData(int type, Class clase, String params) throws BotException, Exception
	{
		T data=null;
		int method=Constantes.GET;
		RestfulDAO dao = new RestfulDAO();			
		ObjectMapper mapper = new ObjectMapper();
		String url = null;
		
		switch(type)
		{
			case Constantes.ORDERBOOK_TYPE:
				url= Constantes.ORDERBOOK_URL;
				method = Constantes.GET;
				break;
			case Constantes.TRADE_TYPE:
				url= Constantes.TRADE_URL;
				method = Constantes.GET;
				break;
			case Constantes.ACTIVEORDERS_SYMBOL_TYPE:
				url= Constantes.ORDER_URL;
				method = Constantes.GET;
				break;
			case Constantes.CANCEL_ORDER_TYPE:
				url= Constantes.ORDER_URL;
				method=Constantes.DELETE;
				break;
				
			case Constantes.ORDER_BUY_SELL_TYPE:
				url= Constantes.ORDER_URL;
				method=Constantes.POST;
				break;
			case Constantes.CANDLE_TYPE:
				if(params.contains("_"))
				{
					url= Constantes.CANDLE_URL + "/" + params.split("_")[0];
					if  (params.split("_").length>1)
					{
						params = params.split("_")[1];						
					}else
						params = null;					
				}else
					url= Constantes.CANDLE_URL + "/BTCUSD";
				method=Constantes.GET;
				break;
			case Constantes.BALANCE_TYPE:
				url= Constantes.BALANCE_URL;
				method=Constantes.GET;
				break;				
			case Constantes.ACTIVE_ONE_ORDER_SYMBOL_TYPE:
				
				
//				dao = new RestfulDAO(16000);	
				url= Constantes.ORDER_URL+ "/" + params.split("_")[0];
				if  (params.split("_").length>1)
				{
					params = params.split("_")[1];
					dao = new RestfulDAO(Integer.parseInt(params.split("=")[1])+2000);
				}
				else
					params = null;
				method=Constantes.GET;
				break;
				
				
		}

		try {
			String json = null;
			
			switch(method)
			{
				case Constantes.GET:
					json = dao.getJson(url, params);
					break;
				case Constantes.POST:
					json = dao.postJson(url, params);
					break;
				case Constantes.PUT:
					json = dao.putJson(url, params);
					break;
				case Constantes.DELETE:
					json = dao.deleteJson(url, params);
					break;
			}				
			// Convert JSON string from file to Object
			if(json.contains("\"error\":"))
			{
				ErrorBot error = mapper.readValue(json, ErrorBot.class);
				BotException exceptionBot = new BotException();
				exceptionBot.setError(error);
				throw exceptionBot;
			}
			else
			{
				data = (T) mapper.readValue(json, clase);
			}		
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		
		return data;
	}
}
