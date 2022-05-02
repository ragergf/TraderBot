package mx.org.traderbot.util;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import mx.org.traderbot.fibo.vo.FiboSoporte;
import mx.org.traderbot.fibo.vo.Soporte;
import mx.org.traderbot.model.TraderBotModel;

public class Util {
	
	 final static Logger logger = Logger.getLogger(Util.class);
	
	public static Object valor(Object objeto, int index)
	{
		Object value= null;
		
		Field[] fields = objeto.getClass().getDeclaredFields();
		fields[index].setAccessible(true);
			
        try
        {
        	value = fields[index].get(objeto);
        }
        catch (Exception e)
        {
            logger.info("error", e);
        }
      
		
		return value;
	}

	public static void printFiboSoporte(FiboSoporte fiboSoporte)
	{	
		ObjectMapper mapper = new ObjectMapper();
		String json;
		
		try
		{
			for(Soporte s : fiboSoporte.getSoportes())
			{
				logger.info("Soporte Fibonacci: "+s.getPunto().getValor());
				
				json = mapper.writeValueAsString(s.getVentaFit());
				logger.info("VentaFit:\n"+json);
				
				json = mapper.writeValueAsString(s.getCompra());
				logger.info("Compra:\n"+json);
				
				json = mapper.writeValueAsString(s.getVenta());
				logger.info("Venta:\n"+json);
				
				json = mapper.writeValueAsString(s.getCompraFit());
				logger.info("CompraFit:\n"+json);
			}			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static double redondear(double valor, double posiciones)
	{
		double redondear=0;
		redondear=(Math.round((valor) * posiciones) / posiciones);
		return redondear;
	}
	
	public static double truncar(double valor, double posiciones)
	{
		double truncar=0;
		truncar=(Math.floor((valor) * posiciones) / posiciones);
		return truncar;
	}
	
	public static double redondear(double valor)
	{
		double redondear=0;
		redondear=(Math.round((valor) * 100.0) / 100.0);
		return redondear;
	}
	
	public static double truncar(double valor)
	{
		double truncar=0;
		truncar=(Math.floor((valor) * 100.0) / 100.0);
		return truncar;
	}
}
