package mx.org.traderbot.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.chart.candles.vo.SMA;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.vo.DataBot;

//@Configuration
//@EnableScheduling
@Component("modelEstrategiaBaja")
@Scope("prototype")
public class EstrategiaBajaModel {
	@Autowired
	@Qualifier("DataBot")
	DataBot dataBot;
	boolean compra=false;
	double totalUSD=1000;
	double precio=0;
	double cantidad=0;
	double total=0;
	double fee = 0;
	double totalFinal =0;
	double totalBTC =0;
	
	final static Logger logger = Logger.getLogger(EstrategiaBajaModel.class);
	final static Logger loggerEB = Logger.getLogger("EstrategiaBaja");
	final static Logger loggerEB2 = Logger.getLogger("EstrategiaBaja2");
	final static Logger loggerEB_5 = Logger.getLogger("EstrategiaBaja_5");
	final static Logger loggerEB2_5 = Logger.getLogger("EstrategiaBaja2_5");
	
	static Logger loggerAux;
	
	public EstrategiaBajaModel()
	{
		loggerEB.info("Iniciando Estrategia Baja");
	}
		
    
//    @Scheduled(fixedDelay = 2000)
    public void run(String period,double difCompra, double difVenta) {
    	double [] estrategiaBajaPuntos  = new double[4];
        try {
//        	loggerEB.info("Iniciando Estrategia Baja");
      	   estrategiaBaja(period, estrategiaBajaPuntos, difCompra, difVenta);
      	   if(period.equalsIgnoreCase("M3"))
      	   {
	      	   dataBot.getCamporMACDLine().setText(estrategiaBajaPuntos[0] + "");
	      	   dataBot.getCampoSignalLine().setText(estrategiaBajaPuntos[1] + "");
	      	   dataBot.getCampoEMA13().setText(estrategiaBajaPuntos[2] + "");
	      	   dataBot.getCampoEMA34().setText(estrategiaBajaPuntos[3] + "");
      	   }
      	
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    }
    
    public void run2(String period, double difCompra, double difVenta) {
    	double [] estrategiaBajaPuntos  = new double[4];
        try {
//        	loggerEB.info("Iniciando Estrategia Baja 2");
      	   estrategiaBaja2(period, estrategiaBajaPuntos,difCompra, difVenta);
//      	   dataBot.getCamporMACDLine().setText(estrategiaBajaPuntos[0] + "");
//      	   dataBot.getCampoSignalLine().setText(estrategiaBajaPuntos[1] + "");
//      	   dataBot.getCampoEMA13().setText(estrategiaBajaPuntos[2] + "");
//      	   dataBot.getCampoEMA34().setText(estrategiaBajaPuntos[3] + "");
//      	
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    }
    
    public void run3(String period, double difCompra, double difVenta) {
    	double [] estrategiaBajaPuntos  = new double[4];
        try {
      	   scalping(period, estrategiaBajaPuntos,difCompra, difVenta);
      	 if(period.equalsIgnoreCase("M5"))
    	   {
	      	   dataBot.getCamporMACDLine().setText(estrategiaBajaPuntos[2] + "");
	      	   dataBot.getCampoSignalLine().setText(estrategiaBajaPuntos[3] + "");
	      	   dataBot.getCampoEMA13().setText(estrategiaBajaPuntos[0] + "");
	      	   dataBot.getCampoEMA34().setText(estrategiaBajaPuntos[1] + "");
    	   }
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    }  
    
    public void scalping(String period, double [] estrategiaBajaPuntos, double difCompra, double difVenta) throws BotException, Exception
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
		
		if(period.equals("M3"))
		{
			loggerAux = loggerEB;
		}else if(period.equals("M5"))
		{
			loggerAux = loggerEB2;
		}else if(period.equals("M15"))
		{
			loggerAux = loggerEB_5;
		}else if(period.equals("M30"))
		{
			loggerAux = loggerEB2_5;
		}
		
		if( estrategiaBajaPuntos[1] > estrategiaBajaPuntos[0] && (estrategiaBajaPuntos[1] - estrategiaBajaPuntos[0] > difVenta) && compra)
		{
			compra = false;
			loggerAux.info("VENTA ");
			precio = Double.parseDouble(dataBot.getPrice().getText());
			total = (Math.round((precio*totalBTC) * 100.0) / 100.0);
			fee = (Math.round((total * 0.001) * 100.0) / 100.0);
			totalFinal =(Math.round((total - fee) * 100.0) / 100.0);
			totalUSD += (Math.round((totalFinal) * 100.0) / 100.0);
			totalBTC = 0;
			
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
		}else if(volumen > estrategiaBajaPuntos[2] &&estrategiaBajaPuntos[0] > estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[0] - estrategiaBajaPuntos[1] > difCompra) && !compra)
		{
			compra = true;
			loggerAux.info("COMPRA ");
			precio = Double.parseDouble(dataBot.getPrice().getText());
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
//		loggerAux.info("\tvolumen: " + volumen);
//		loggerAux.info("\tSMA_10_volumen: " + estrategiaBajaPuntos[2]);
//		loggerAux.info("\tEMA13: " + estrategiaBajaPuntos[0]);
//		loggerAux.info("\tEMA34: " + estrategiaBajaPuntos[1]);
//		
		
    }

	public void estrategiaBaja (String period, double [] estrategiaBajaPuntos, double difCompra, double difVenta) throws BotException, Exception
	{
		CandleModel model = new CandleModel();	
		
		if(period.equals("M3"))
		{
			loggerAux = loggerEB;
		}else if(period.equals("M5"))
		{
			loggerAux = loggerEB_5;
		}
				
		Candle [] candles = model.getCandles(period, "100");
		
		List<EMA> ema_M15_13 = model.movingAverageExponencial( 13, candles);
		List<EMA> ema_M15_34 = model.movingAverageExponencial( 34, candles);
		
		List<EMA> ema_M15_12 = model.movingAverageExponencial( 12, candles);
		List<EMA> ema_M15_26 = model.movingAverageExponencial( 26, candles);
		
		List<EMA> ema_signal ;
		
		candles = new Candle[74]; 
		for(int i = 26 ; i < 100 ; i++)
		{
			candles[i-26] =  new Candle(ema_M15_12.get(i).getEma() - ema_M15_26.get(i).getEma(), ema_M15_12.get(i).getCandle().getTimestamp());
//			logger.info( "MACD: " + candles[i-26].getClose());								
		}
		
		ema_signal = model.movingAverageExponencial(9, candles);
		
		logger.info("Macd Line: "+ema_signal.get(ema_signal.size()-1).getCandle().getClose());
		logger.info("Signal Line: "+ema_signal.get(ema_signal.size()-1).getEma());	
		
		estrategiaBajaPuntos[0] = ema_signal.get(ema_signal.size()-1).getCandle().getClose();
		estrategiaBajaPuntos[1] = ema_signal.get(ema_signal.size()-1).getEma();
		estrategiaBajaPuntos[2] = ema_M15_13.get(ema_M15_13.size()-1).getEma();
		estrategiaBajaPuntos[3] = ema_M15_34.get(ema_M15_34.size()-1).getEma();
		
		if(estrategiaBajaPuntos[2] < estrategiaBajaPuntos[3])
		{
			if(estrategiaBajaPuntos[0] < estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[1] - estrategiaBajaPuntos[0] > difVenta) && compra)
			{
				compra = false;
				loggerAux.info("VENTA ");
				precio = Double.parseDouble(dataBot.getPrice().getText());
				total = (Math.round((precio*totalBTC) * 100.0) / 100.0);
				fee = (Math.round((total * 0.001) * 100.0) / 100.0);
				totalFinal =(Math.round((total - fee) * 100.0) / 100.0);
				totalUSD += (Math.round((totalFinal) * 100.0) / 100.0);
				totalBTC = 0;
				
				loggerAux.info("\t\tcantidad: " + cantidad);				
				loggerAux.info("\t\tprecio: " + precio);
				loggerAux.info("\t\ttotal: " + total);
				loggerAux.info("\t\tfee: " + fee);
				loggerAux.info("\t\ttotalFinal: " + totalFinal);
				loggerAux.info("\t\ttotalUSD: " + totalUSD);
				
				
				
				loggerAux.info("\tprecio: " + dataBot.getPrice().getText());
				loggerAux.info("\tMACD: " + estrategiaBajaPuntos[0]);
				loggerAux.info("\tSignal: " + estrategiaBajaPuntos[1]);
				loggerAux.info("\tEMA13: " + estrategiaBajaPuntos[2]);
				loggerAux.info("\tEMA34: " + estrategiaBajaPuntos[3]);
			}
			else if(estrategiaBajaPuntos[0] > estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[0] - estrategiaBajaPuntos[1] > difCompra) && !compra)
			{
				compra = true;
				loggerAux.info("COMPRA ");
				precio = Double.parseDouble(dataBot.getPrice().getText());
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
				
				loggerAux.info("\tMACD: " + estrategiaBajaPuntos[0]);
				loggerAux.info("\tSignal: " + estrategiaBajaPuntos[1]);
				loggerAux.info("\tEMA13: " + estrategiaBajaPuntos[2]);
				loggerAux.info("\tEMA34: " + estrategiaBajaPuntos[3]);
			}
		}
		
	}
	
	public void estrategiaBaja2 (String period, double [] estrategiaBajaPuntos,double difCompra, double difVenta) throws BotException, Exception
	{
		CandleModel model = new CandleModel();	
		
		if(period.equals("M3"))
		{
			loggerAux = loggerEB2;
		}else if(period.equals("M5"))
		{
			loggerAux = loggerEB2_5;
		}
				
		Candle [] candles = model.getCandles(period, "100");
		
//		List<EMA> ema_M15_13 = model.movingAverageExponencial( 13, candles);
//		List<EMA> ema_M15_34 = model.movingAverageExponencial( 34, candles);
		
		List<EMA> ema_M15_12 = model.movingAverageExponencial( 12, candles);
		List<EMA> ema_M15_26 = model.movingAverageExponencial( 26, candles);
		
		List<EMA> ema_signal ;
		
		candles = new Candle[74]; 
		for(int i = 26 ; i < 100 ; i++)
		{
			candles[i-26] =  new Candle(ema_M15_12.get(i).getEma() - ema_M15_26.get(i).getEma(), ema_M15_12.get(i).getCandle().getTimestamp());
//			logger.info( "MACD: " + candles[i-26].getClose());								
		}
		
		ema_signal = model.movingAverageExponencial(9, candles);
		
		logger.info("Macd Line: "+ema_signal.get(ema_signal.size()-1).getCandle().getClose());
		logger.info("Signal Line: "+ema_signal.get(ema_signal.size()-1).getEma());	
		
		estrategiaBajaPuntos[0] = ema_signal.get(ema_signal.size()-1).getCandle().getClose();
		estrategiaBajaPuntos[1] = ema_signal.get(ema_signal.size()-1).getEma();
//		estrategiaBajaPuntos[2] = ema_M15_13.get(ema_M15_13.size()-1).getEma();
//		estrategiaBajaPuntos[3] = ema_M15_34.get(ema_M15_34.size()-1).getEma();
		
//		if(estrategiaBajaPuntos[2] < estrategiaBajaPuntos[3])
//		{
			if(estrategiaBajaPuntos[0] < estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[1] - estrategiaBajaPuntos[0] > difVenta) && compra)
			{
				compra = false;
				loggerAux.info("VENTA ");
				precio = Double.parseDouble(dataBot.getPrice().getText());
				total = (Math.round((precio*totalBTC) * 100.0) / 100.0);
				fee = (Math.round((total * 0.001) * 100.0) / 100.0);
				totalFinal =(Math.round((total - fee) * 100.0) / 100.0);
				totalUSD += (Math.round((totalFinal) * 100.0) / 100.0);
				totalBTC = 0;
				
				loggerAux.info("\t\tcantidad: " + cantidad);				
				loggerAux.info("\t\tprecio: " + precio);
				loggerAux.info("\t\ttotal: " + total);
				loggerAux.info("\t\tfee: " + fee);
				loggerAux.info("\t\ttotalFinal: " + totalFinal);
				loggerAux.info("\t\ttotalUSD: " + totalUSD);
				
				
				
//				loggerAux.info("\tprecio: " + dataBot.getPrice().getText());
				loggerAux.info("\tMACD: " + estrategiaBajaPuntos[0]);
				loggerAux.info("\tSignal: " + estrategiaBajaPuntos[1]);
//				loggerEB.info("\tEMA13: " + estrategiaBajaPuntos[2]);
//				loggerEB.info("\tEMA34: " + estrategiaBajaPuntos[3]);
			}
			else if(estrategiaBajaPuntos[0] > estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[0] - estrategiaBajaPuntos[1] > difCompra) && !compra)
			{
				compra = true;
				loggerAux.info("COMPRA ");
				precio = Double.parseDouble(dataBot.getPrice().getText());
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
				
				loggerAux.info("\tMACD: " + estrategiaBajaPuntos[0]);
				loggerAux.info("\tSignal: " + estrategiaBajaPuntos[1]);
//				loggerEB.info("\tEMA13: " + estrategiaBajaPuntos[2]);
//				loggerEB.info("\tEMA34: " + estrategiaBajaPuntos[3]);
			}
//		}
		
	}
	
	public static void main(String ...args)
	{
		double cantidad=0, precio=10000, totalUSD=1130;
		
		cantidad = (Math.floor((totalUSD/precio) * 100.0) / 100.0) ;
		
		System.out.println(cantidad);
	}
	
}
