package mx.org.traderbot.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.fibo.vo.Fibo;
import mx.org.traderbot.fibo.vo.Punto;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.util.Constantes;

public class JsonExamples {
	
	final static Logger logger = Logger.getLogger(JsonExamples.class);
	
	public static void main(String ...args)
	{
//		jsonToObject();
//		objectToJson();
//		activeOrder();
		
		jsonToObject2();
	}
	
	public static void objectToJson()
	{
		Fibo fibo = new Fibo();
		List <Punto> soportesResistencias = new ArrayList<Punto>();
		soportesResistencias.add(new Punto(34.2));
		soportesResistencias.add(new Punto(10.99));
		soportesResistencias.add(new Punto(15.1));
		fibo.setSoportesResistencias(soportesResistencias);
		
		Collections.sort(soportesResistencias);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(fibo);
			logger.info(jsonInString);
			mapper.writeValue(new File(Constantes.ARCHIVO_FIBO), fibo);
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
	}
	
	public static void jsonToObject()
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Fibo fibo = mapper.readValue(new File(Constantes.ARCHIVO_FIBO), Fibo.class);
			
			for (Punto p : fibo.getSoportesResistencias())
			{
				logger.info(p.getValor());
			}						
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("No existe el Fichero!!");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void jsonToObject2()
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			Order fibo = mapper.readValue(new File("D:\\user.json"), Order.class);
			
				
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("No existe el Fichero!!");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void activeOrder() throws BotException, Exception
	{
		DataService<Order> service = new DataService<Order>();
		Object array=service.getData(Constantes.ACTIVEORDERS_SYMBOL_TYPE, Order[].class, "symbol=btcusd");
		Order[] list = (Order[])array;
		for(Order o:list)
		{
			logger.info(o.getId());
		}
	}

}
