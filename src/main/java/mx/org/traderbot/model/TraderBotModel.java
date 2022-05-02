package mx.org.traderbot.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.org.traderbot.examples.TradeView;
import mx.org.traderbot.fibo.vo.FiboSoporte;
import mx.org.traderbot.fibo.vo.Soporte;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Constantes;

public class TraderBotModel {
	private final static Logger logger = Logger.getLogger(TraderBotModel.class);
	private FiboSoporte fiboSoporte;
	
	
	public TraderBotModel()
	{}	
	
	public List<Order> getListProximasOrdenes(String precio, FiboSoporte fiboSoporte)
	{
		List<Order> ordenes= new ArrayList<Order>();
		
		Soporte soporte = ordenProxima(precio, fiboSoporte);
		if(soporte!= null)
		{
			ordenes.add(soporte.getVentaFit());
			ordenes.add(soporte.getCompra());
			ordenes.add(soporte.getVenta());
			ordenes.add(soporte.getCompraFit());
			
		}
		
		
		
		return ordenes;
	}
	
	public Soporte ordenProxima(String precio, FiboSoporte fiboSoporte)
	{
		Soporte soporte = null;
		double valorPrecio = 0;
		double rangoMax = 0;
		double rangoMin = 0;
		boolean proximo= false;
		logger.info("Constantes.RANGO_DE_ACTIVACION " + Constantes.RANGO_DE_ACTIVACION);
		if(precio != null && !precio.trim().equals(""))
		{
			valorPrecio = Double.parseDouble(precio);
			logger.info("valorPrecio: " + valorPrecio);
						
			for(Soporte sop : fiboSoporte.getSoportes())
			{			
				logger.info("Valor Punto: " +sop.getPunto().getValor());
				rangoMax = (sop.getPunto().getValor() + Constantes.RANGO_DE_ACTIVACION) - valorPrecio;
				logger.info("rango max: " + rangoMax);
				rangoMin = (sop.getPunto().getValor()) -valorPrecio;
				logger.info("rango min: " + rangoMin);
				
				
				if((rangoMax <  (Constantes.RANGO_DE_ACTIVACION - Constantes.RANGO_DE_VACIO) && rangoMax >0))
				{
					proximo= true;
				}
				
				if(((rangoMin-Constantes.RANGO_DE_VACIO) <  (Constantes.RANGO_DE_ACTIVACION) && ((rangoMin-Constantes.RANGO_DE_VACIO)) >0))
				{
					proximo= true;
				}
				
				if(proximo)
				{
					soporte = sop;
					break;
				}
			}
		}
		return soporte;
	}

	public FiboSoporte getFiboSoporte() {
		return fiboSoporte;
	}

	public void setFiboSoporte(FiboSoporte fiboSoporte) {
		this.fiboSoporte = fiboSoporte;
	}
	
}
