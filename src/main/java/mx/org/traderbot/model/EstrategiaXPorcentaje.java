package mx.org.traderbot.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.chart.candles.vo.SMA;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.vo.DataBot;

public class EstrategiaXPorcentaje {
	@Autowired
	@Qualifier("DataBot")
	DataBot dataBot;
	boolean compra=false;
	double precioCompra=0;	
	double totalCompra=0;
	double totalUSD=1000;
	double precio=0;
	double cantidad=0;
	double total=0;
	double fee = 0;
	double totalFinal =0;
	double totalBTC =0;
	
	final static Logger loggerProcentaje = Logger.getLogger("EstrategiaXPorcentaje");
	static Logger loggerAux;
	
	public void estrategiaXPorcentaje(String period, double [] estrategiaBajaPuntos, double difCompra, double difVenta) throws BotException, Exception
    {
    	CandleModel model = new CandleModel();
    	
    	double precio = 0;
    	double volumen = 0;
    	
    	Candle [] candles = model.getCandles(period, "100");
    	volumen = candles[candles.length - 1].getVolume();
    	List<EMA> ema_MN_13 = model.movingAverageExponencial( 13, candles);
		List<EMA> ema_MN_34 = model.movingAverageExponencial( 34, candles);
		List<SMA> sma_MN_10_vol = model.movingAverage( 10, candles, 1);
		
		estrategiaBajaPuntos[0] = ema_MN_13.get(ema_MN_13.size()-1).getEma();
		estrategiaBajaPuntos[1] = ema_MN_34.get(ema_MN_34.size()-1).getEma();
		estrategiaBajaPuntos[2] = sma_MN_10_vol.get(sma_MN_10_vol.size()-1).getSma();
		
		
		precio = candles[candles.length - 1].getClose();
		
		estrategiaBajaPuntos[3] = volumen;
		
		if(period.equals("M15"))
		{
			loggerAux = loggerProcentaje;
		}
//		}else if(period.equals("M5"))
//		{
//			loggerAux = loggerEB2;
//		}else if(period.equals("M15"))
//		{
//			loggerAux = loggerEB_5;
//		}else if(period.equals("M30"))
//		{
//			loggerAux = loggerEB2_5;
//		}
		
		if(compra)
		{
			double dif;	
			double totalMovimiento=0;
			loggerAux.info("VENTA ");
			
			totalCompra = totalFinal;
			
			precio = Double.parseDouble(dataBot.getPrice().getText());
			total = (Math.round((precio*totalBTC) * 100.0) / 100.0);
			fee = (Math.round((total * 0.001) * 100.0) / 100.0);
			totalMovimiento =(Math.round((total - fee) * 100.0) / 100.0);
			dif = totalMovimiento - totalCompra;
			
			loggerAux.info("\t\t\ttotalCompra: " + totalCompra);				
			loggerAux.info("\t\t\ttotalFinal: " + totalFinal);
			loggerAux.info("\t\t\tDif: " + dif);
			
			if(dif>0 && dif > (totalCompra * 0.009) )
			{
				compra = false;
				totalUSD += (Math.round((totalFinal) * 100.0) / 100.0);											
				totalBTC = 0;
				loggerAux.info("VENDIDO ");
				loggerAux.info("\t\tcantidad: " + cantidad);				
				loggerAux.info("\t\tprecio: " + precio);
				loggerAux.info("\t\ttotal: " + total);
				loggerAux.info("\t\tfee: " + fee);
				loggerAux.info("\t\ttotalFinal: " + totalFinal);
				loggerAux.info("\t\ttotalUSD: " + totalUSD);
				
				loggerAux.info("\tvolumen: " + volumen);
				loggerAux.info("\tSMA_10_volumen: " + estrategiaBajaPuntos[2]);
				loggerAux.info("\tEMA13: " + estrategiaBajaPuntos[0]);
				loggerAux.info("\tEMA34: " + estrategiaBajaPuntos[1]);
			}
															
		}else if(volumen > (estrategiaBajaPuntos[2]-1) &&estrategiaBajaPuntos[0] > estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[0] - estrategiaBajaPuntos[1] > difCompra) && !compra)
		{
			compra = true;
			loggerAux.info("COMPRA ");
			precio = Double.parseDouble(dataBot.getPrice().getText());
			precioCompra = precio;
			cantidad = (Math.floor((totalUSD/precio) * 100.0) / 100.0);
			total = (Math.round((precio*cantidad) * 100.0) / 100.0);
			fee = (Math.round((total*0.001) * 100.0) / 100.0);
			totalFinal = (Math.round((total+fee) * 100.0) / 100.0);
			totalUSD -=  totalFinal;
			totalBTC = cantidad;
			
			loggerAux.info("\t\tcantidad: " + cantidad);				
			loggerAux.info("\t\tprecio: " + precio);
			loggerAux.info("\t\ttotal: " + total);
			loggerAux.info("\t\tfee: " + fee);
			loggerAux.info("\t\ttotalFinal: " + totalFinal);
			loggerAux.info("\t\ttotalUSD: " + totalUSD);
			loggerAux.info("\t\tAprox USD: " + (totalUSD+(precio * cantidad)));
			
			
			loggerAux.info("\tvolumen: " + volumen);
			loggerAux.info("\tSMA_10_volumen: " + estrategiaBajaPuntos[2]);
			loggerAux.info("\tEMA13: " + estrategiaBajaPuntos[0]);
			loggerAux.info("\tEMA34: " + estrategiaBajaPuntos[1]);
		}

		
    }

}
