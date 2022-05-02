package mx.org.traderbot.fibo.model;

import java.util.Collections;

import org.apache.log4j.Logger;

import mx.org.traderbot.fibo.dao.FiboDAO;
import mx.org.traderbot.fibo.vo.Fibo;
import mx.org.traderbot.fibo.vo.Punto;
import mx.org.traderbot.util.Constantes;

public class FiboModel {
	final static Logger logger = Logger.getLogger(FiboModel.class);
	
	public Fibo calcular_retroceso(Fibo fibo, double max, double min)
	{
		Punto punto = null;
		boolean add=true;
		double soporte=0;
		
		logger.info("max: " + max + ", min: " +min);
		
		if(fibo == null)
			fibo = new Fibo();				
		
		for(double f : Constantes.RETROCESO_FIBONACCI)
		{
			add = true;
			soporte = ((max - min) * (f)) + min;
			logger.info("f: " + f + ", soporte: " + soporte);
			soporte = Math.floor(soporte * 100) / 100;
			punto = new Punto(soporte);
			for(Punto e : fibo.getSoportesResistencias())
			{
				if(punto.compareTo(e) == 0)
				{
					add = false;
					break;
				}
			}
			if(add)
			{
				fibo.getSoportesResistencias().add(punto);
			}
		}
		
		Collections.sort(fibo.getSoportesResistencias());
		
		return fibo;
	}
}
