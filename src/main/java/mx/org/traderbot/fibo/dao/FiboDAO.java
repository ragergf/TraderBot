package mx.org.traderbot.fibo.dao;

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

import mx.org.traderbot.fibo.vo.Fibo;
import mx.org.traderbot.fibo.vo.Punto;
import mx.org.traderbot.model.DataTableModel;
import mx.org.traderbot.util.Constantes;

public class FiboDAO {

	final static Logger logger = Logger.getLogger(FiboDAO.class);
	
	public Fibo getFibo()
	{
		Fibo fibo=new Fibo();
		fibo.setSoportesResistencias(new ArrayList<Punto>());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			fibo = mapper.readValue(new File(Constantes.ARCHIVO_FIBO), Fibo.class);
			
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
		
		return fibo;
	}
	
	public void setFibo(Fibo fibo)
	{			
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
	
	public Fibo delete(Fibo fibo, int index)
	{
		
		fibo.getSoportesResistencias().remove(index);
		
		setFibo(fibo);
		
		return fibo;
	}
	
	public Fibo add(Fibo fibo, Punto punto)
	{
		boolean add = true;
		for(Punto p : fibo.getSoportesResistencias())
		{
			if(p.compareTo(punto) == 0)
				add=false;
				
		}
		
		if(add)
		{
			fibo.getSoportesResistencias().add(punto);		
			Collections.sort(fibo.getSoportesResistencias());		
			setFibo(fibo);
		}
		
		return fibo;
	}
	
	
}
