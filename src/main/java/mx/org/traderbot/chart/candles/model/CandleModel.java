package mx.org.traderbot.chart.candles.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.StyleSheet.ListPainter;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.chart.candles.vo.PSAR;
import mx.org.traderbot.chart.candles.vo.PSAR_V2;
import mx.org.traderbot.chart.candles.vo.RSI;
import mx.org.traderbot.chart.candles.vo.SMA;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.util.Constantes;


@Component
@Scope("prototype")
public class CandleModel {
	final static Logger logger = Logger.getLogger(CandleModel.class);
	
	public List<SMA> movingAverage( int sizeSMA, Candle[] candles, int ind)
	{		
		List<SMA> smaList = new ArrayList<SMA>();
		SMA smaObject = null;
		
		double sma = 0;
		
		for(int i = 0 ; i < candles.length ;  i++)
		{
//			logger.info(candles[i].getClose());
			smaObject = new SMA();
			smaObject.setCandle(candles[i]);
			if(i - sizeSMA + 2 > 0)
			{
				sma = 0;
				for(int j = (i - sizeSMA +1) ; j < i+1 ; j++ )
				{
//					logger.info("\t"+candles[j].getClose());
					sma += candles[j].getVolume();
				}				
				sma = sma / sizeSMA;
				sma = Math.round(sma * 100000d) / 100000d;
//				logger.info("\t\t" + sma);
				
				smaObject.setSma(new Double(sma));
				
			}
			else
			{
				smaObject.setSma(null);
			}
			smaList.add(smaObject);
		}
		
		return smaList;
	}
	
	public List<SMA> movingAverage( int sizeSMA, Candle[] candles)
	{				
		List<SMA> smaList = new ArrayList<SMA>();
		SMA smaObject = null;
		
		double sma = 0;
		
		for(int i = 0 ; i < candles.length ;  i++)
		{
//			logger.info(candles[i].getClose());
			smaObject = new SMA();
			smaObject.setCandle(candles[i]);
			if(i - sizeSMA + 2 > 0)
			{
				sma = 0;
				for(int j = (i - sizeSMA +1) ; j < i+1 ; j++ )
				{
//					logger.info("\t"+candles[j].getClose());
					sma += candles[j].getClose();
				}				
				sma = sma / sizeSMA;
				sma = Math.round(sma * 100000d) / 100000d;
//				logger.info("\t\t" + sma);
				
				smaObject.setSma(new Double(sma));
				
			}
			else
			{
				smaObject.setSma(null);
			}
			smaList.add(smaObject);
		}
		
		return smaList;
		
//		for(Candle c : candles)
//		{
//			logger.info(c.getClose() +", "+c.getTimestamp().toString());
//		}
		
	}
	
	public List<EMA> movingAverageExponencial(int sizeSMA, Candle[] candles)
	{
		List<EMA> emaList = new ArrayList<EMA>();
		List<SMA> smaList = new ArrayList<SMA>();
		EMA emaObject = null;
		
		double ema=0;
		double precio=0;
		double emaAnterior=0;
		double multiplier=0;
		
		smaList = movingAverage(sizeSMA, candles);
		
//		for(int i = 1 ; i < smaList.size() ;  i++)
//		{
//			logger.info(smaList.get(i-1).getCandle().getClose());
//			logger.info(smaList.get(i-1).getSma() + "\n");			
//		}
		
//		logger.info("EMA's");
		for(int i = 0 ; i < smaList.size() ;  i++)
		{				
			emaObject = new EMA(smaList.get(i));
			if(i > sizeSMA-1 )
			{
//				logger.info("i : "+i);				
				ema=calculaEMA(smaList.get(i).getCandle().getClose(), emaList.get(i-1).getEma(), sizeSMA);
				emaObject.setEma(ema);
			}
			emaList.add(emaObject);			
		}			
		
		return emaList;
	}
	
	PSAR calculaSAR(double max, double min, double close, PSAR prevPsar, boolean falling)
	{
		PSAR psar = new PSAR();
		boolean auxFalling;
		
		if(prevPsar==null)
		{
			if(falling)
			{
				psar.setEp(min);			
				psar.setSar(max);	
			}
			else
			{
				psar.setEp(max);				
				psar.setSar(min);
			}
								
			psar.setAf(0.02);			
			
		}
		else
		{
			if(falling)
			{
				psar.setSar(prevPsar.getSar() - (prevPsar.getAf() * (prevPsar.getSar() - prevPsar.getEp())));
				psar.setEp(Math.min(min, prevPsar.getEp()));
				close = max;
			}
			else
			{
				psar.setSar(prevPsar.getSar() + (prevPsar.getAf() * (prevPsar.getEp() - prevPsar.getSar())));
				psar.setEp(Math.max(max, prevPsar.getEp()));
				close = min;
			}
			
			
			if (prevPsar.getEp() != psar.getEp())
			{
				double af = (prevPsar.getAf()<0.2)?prevPsar.getAf()+0.02:0.2;							
				psar.setAf(af);
			}
			else
			{
				psar.setAf(prevPsar.getAf());
			}					
		}
		
		if(close <= psar.getSar())
		{
			auxFalling = true;
		}else
		{
			auxFalling = false;
		}
		psar.setFalling(auxFalling);
		return psar;
	}
	
	public List<PSAR> getParabolicSAR(Candle[] candles)
	{
		List<PSAR> listPSAR = new ArrayList<PSAR>();
		double max=0;
		double min=0;
		boolean falling = false;
		
		listPSAR.add(new PSAR());
		
		for(int i = 1 ; i < candles.length ; i++)
		{
			PSAR psar = null;
			
			if( i == 1 )
			{
				if(candles[i-1].getClose() > candles[i].getClose())
				{
					max = Math.max(candles[i-1].getMax(), candles[i].getMax());
					min = candles[i].getMin();
					falling = true;
				}
				else
				{
					max = candles[i].getMax();
					min = Math.min(candles[i-1].getMin(), candles[i-1].getMin());
					falling = false;
				}
				psar = calculaSAR(max, min, candles[i].getClose(), null, falling);
			}
			else
			{
				max =Math.max(max, candles[i].getMax());
				min =Math.min(min, candles[i].getMin());
				psar = calculaSAR(candles[i].getMax(), candles[i].getMin(), candles[i].getClose(), listPSAR.get(i-1),listPSAR.get(i-1).isFalling());
				
				if(psar.isFalling() != listPSAR.get(i-1).isFalling())
				{
					if(psar.isFalling())
						psar = calculaSAR(max, candles[i].getMin(), candles[i].getClose(), null, psar.isFalling());
					else
						psar = calculaSAR(candles[i].getMax(), min, candles[i].getClose(), null, psar.isFalling());
					max = candles[i].getMax();
					min = candles[i].getMin();
				}
			}
			
			psar.setMax(max);
			psar.setMin(min);
			listPSAR.add(psar);
		}
		
		return listPSAR;
	}
	
	
	public List<PSAR_V2> getParabolicSAR_V2(Candle[] candles)
	{
		List<PSAR_V2> listPSAR_V2 = new ArrayList<PSAR_V2>();
		int size = candles.length;
		double max = 0;
		double min = 0;
		PSAR_V2  psar = null;
		
		listPSAR_V2.add(new PSAR_V2(0, 0, 0, candles[0], 0,false));
		max = candles[0].getMax();
		min = candles[0].getMin();
		
		for(int i = 1; i < size ; i++)
		{
			psar = new PSAR_V2();
			if(i==1)
			{
				if(max > candles[i].getMax())
				{
					psar.setSar(max);
					psar.setEp(candles[i].getMin());
					psar.setFalling(true);
				}
				else if(min < candles[i].getMin())
				{
					psar.setSar(candles[i].getMax());
					psar.setEp(min);
					psar.setFalling(false);
				}
				psar.setAf(0.02);
				
								
				
				max = candles[i].getMax();
				min = candles[i].getMin();
			}
			else
			{
				psar.setSar(listPSAR_V2.get(i-1).getSar_man());
				
				if(candles[i].getMax() > psar.getSar() &&  psar.getSar() > candles[i].getMin())
				{
					max = candles[i].getMax();
					min = candles[i].getMin();
					psar.setSar(listPSAR_V2.get(i-1).getEp());
					if(listPSAR_V2.get(i-1).isFalling())
					{
						psar.setEp(max);
					}
					else
					{
						psar.setEp(min);
					}
					psar.setFalling(!listPSAR_V2.get(i-1).isFalling());
					psar.setAf(0.02);									
				}
				else
				{
					max = Math.max(max, candles[i].getMax());
					min = Math.min(min, candles[i].getMin());
					
					if(listPSAR_V2.get(i-1).isFalling())
					{
						psar.setEp(min);
					}
					else
					{
						psar.setEp(max);
					}
					psar.setFalling(listPSAR_V2.get(i-1).isFalling());
					if(psar.getEp() != listPSAR_V2.get(i-1).getEp() && listPSAR_V2.get(i-1).getAf() < 0.2)
					{
						psar.setAf(listPSAR_V2.get(i-1).getAf() + 0.02);
					}
					else
					{
						psar.setAf(listPSAR_V2.get(i-1).getAf());
					}					
				}
			}					
			psar.setSar_man(psar.getSar() + ((psar.getEp() - psar.getSar()) * psar.getAf()));
			if(psar.isFalling() && candles[i].getMax() > psar.getSar_man())
			{
				 psar.setSar_man(candles[i].getMax());
			}
			else if(!psar.isFalling() && candles[i].getMin() < psar.getSar_man())
			{
				psar.setSar_man(candles[i].getMin());
			}
			listPSAR_V2.add(psar);
		}
		
		return listPSAR_V2;
	}
	
	public List<RSI> relativeStrengthIndex(int sizeRSI, Candle[] candles)
	{
		List<RSI> rsiList= new ArrayList<RSI>();
		
		int length = candles.length;
		double gain=0;
		double loss=0;
		double prevGain=0;
		double prevLoss=0;
		double change=0;
		double rs;
		
		rsiList.add(new RSI(candles[0], 0,0,0));
		
		
		for(int i = 1; i < length ; i ++)
		{
			gain = 0;
			loss = 0;
			change=candles[i].getClose() - candles[i-1].getClose();
			
			if(change < 0)
			{
				loss = Math.abs(change);
				gain = 0;
			}
			else if(change > 0)
			{
				gain = Math.abs(change);
				loss = 0;
			}
			
			rsiList.add(new RSI(candles[i], gain, loss, 0));
		}
		
		
		
		for (int i = sizeRSI ; i < length ; i ++)
		{
			gain = 0;
			loss = 0;
			prevGain=0;
			prevLoss=0;
			
			if(i == sizeRSI)
			{
				for (int j = 0 ; j < sizeRSI ; j ++)
				{
					gain += rsiList.get(i-j).getGain();
					loss += rsiList.get(i-j).getLoss();
				}
				
				gain = gain / sizeRSI;
				loss = loss / sizeRSI;						
				
				rsiList.get(i).setAvgGain(gain);
				rsiList.get(i).setAvgLoss(loss);
			}else
			{
				prevGain = rsiList.get(i-1).getAvgGain();
				prevLoss = rsiList.get(i-1).getAvgLoss();
				gain = rsiList.get(i).getGain();
				loss = rsiList.get(i).getLoss();
				
				rsiList.get(i).setAvgGain(((prevGain*13)+gain)/14);
				rsiList.get(i).setAvgLoss(((prevLoss*13)+loss)/14);
			}
			
			rs = rsiList.get(i).getAvgGain() / rsiList.get(i).getAvgLoss();
			rsiList.get(i).setRsi(100 - (100 /(1 + rs)));
			
			System.out.println(rsiList.get(i).getRsi());
		}
		
		return rsiList;
	}
	
	public Candle[] getCandles(String period, String limit) throws BotException, Exception
	{
		Candle [] candles = null;
		DataService<Candle> candleService = new DataService<Candle>();
		
		Object array=candleService.getData(Constantes.CANDLE_TYPE, Candle[].class, "period=" + period + "&limit=" + limit );
		candles = (Candle[])array;		
		
		return candles;
	}
	
	public Candle[] getCandles(String period, String limit, String symbol) throws BotException, Exception
	{
		Candle [] candles = null;
		DataService<Candle> candleService = new DataService<Candle>();
		
		Object array=candleService.getData(Constantes.CANDLE_TYPE, Candle[].class, symbol + "_" + "period=" + period + "&limit=" + limit );
		candles = (Candle[])array;		
		
		return candles;
	}
	
	
	public void init(String period, String limit) throws BotException, Exception
	{
		List <SMA> smaList = null;
		List <EMA> emaList = null;
		Candle [] candles;
		DataService<Candle> candleService = new DataService<Candle>();
		
		Object array=candleService.getData(Constantes.CANDLE_TYPE, Candle[].class, "period=" + period + "&limit=" + limit );
		candles = (Candle[])array;
		
		
		emaList = new ArrayList<EMA>();
		emaList = movingAverageExponencial(12, candles);
						
	}
	
	public double calculaEMA(double precio, double emaAnterior, int sizeSMA)
	{
		double ema = 0;		
		double multiplier = (2/((double)sizeSMA+1));
		
//		logger.info("Precio: " + precio);
//		logger.info("emaAnterior: " + emaAnterior);
//		logger.info("multiplier: " + multiplier);
		
		ema = ((precio - emaAnterior)*multiplier) + emaAnterior;
		ema = Math.round(ema * 100000d) / 100000d;
//		logger.info("EMA: "+ ema);
		
		return ema;
	}
	
	public void estrategiaBaja (String period) throws BotException, Exception
	{
		CandleModel model = new CandleModel();
		int time = 0;	
		
		while(time < 100)
		{		
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
//				logger.info( "MACD: " + candles[i-26].getClose());								
			}
			
			ema_signal = model.movingAverageExponencial(9, candles);
			
			logger.info("Macd Line: "+ema_signal.get(ema_signal.size()-1).getCandle().getClose());
			logger.info("Signal Line: "+ema_signal.get(ema_signal.size()-1).getEma());
			time++;
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public List<EMA> estrategiaMACD(Candle [] candles)
	{
		List<EMA> listMACD = new ArrayList<EMA>();
		
		List<EMA> ema_M15_12 = movingAverageExponencial( 12, candles);
		List<EMA> ema_M15_26 = movingAverageExponencial( 26, candles);

		candles = new Candle[74]; 
		for(int i = 26 ; i < 100 ; i++)
		{
			candles[i-26] =  new Candle(ema_M15_12.get(i).getEma() - ema_M15_26.get(i).getEma(), ema_M15_12.get(i).getCandle().getTimestamp());//									
		}
		
		listMACD = movingAverageExponencial(9, candles);
		
		return listMACD;
	}
	
	
	public void actualizaEMASignal(List<EMA> ema_signal, List<EMA> ema_M15_12, List<EMA> ema_M15_26, int size, Candle[] candles)
	{
		int candlesSize = candles.length;
		int ema_M15_12_size = ema_M15_12.size();
		int ema_M15_26_size = ema_M15_26.size();
		int ema_signal_size = ema_signal.size();
		EMA ema;
		
		
		if (ema_M15_12.get(ema_M15_12_size -1).getCandle().getTimestamp().equals(candles[candlesSize-1].getTimestamp()))
		{
			ema_signal.get(ema_signal_size-1).getCandle().setClose(ema_M15_12.get(ema_M15_12_size-1).getEma() - ema_M15_26.get(ema_M15_26_size-1).getEma());
			ema_signal.get(ema_signal_size-1).setEma(new Double(calculaEMA(ema_signal.get(ema_signal_size-1).getCandle().getClose(), ema_signal.get(ema_signal_size-2).getEma(), size)));
		}
		else
		{
			ema_signal.remove(99);
			ema_signal.remove(0);
			ema = new EMA(new Candle(ema_M15_12.get(ema_M15_12_size-2).getEma() - ema_M15_26.get(ema_M15_26_size-2).getEma(), ema_M15_26.get(ema_M15_26_size-2).getCandle().getTimestamp()));
			ema.setEma(calculaEMA(ema.getCandle().getClose(), ema_M15_12.get(ema_M15_12_size-3).getEma(), 9));
			ema_signal.add(ema);
			
			ema = new EMA(new Candle(ema_M15_12.get(ema_M15_12_size-1).getEma() - ema_M15_26.get(ema_M15_26_size-1).getEma(), ema_M15_26.get(ema_M15_26_size-1).getCandle().getTimestamp()));
			ema.setEma(calculaEMA(ema.getCandle().getClose(), ema_M15_12.get(ema_M15_12_size-2).getEma(), 9));
			ema_signal.add(ema);
		}
	}
	
	public void actualizaEMA(List<EMA> emaList, int size, Candle [] candles)
	{
//		CandleModel model = new CandleModel();
		EMA emaAux=null;
	
		
		int emaListSize = emaList.size();
		int candlesSize = candles.length;
		
		emaAux = emaList.get(emaListSize-1);
		if (emaAux.getCandle().getTimestamp().equals(candles[candlesSize-1].getTimestamp()))
		{
			emaAux.setCandle(candles[candlesSize-1]);
			emaAux.setEma(calculaEMA(emaAux.getCandle().getClose(), emaList.get(emaListSize-2).getEma(), size));
		}
		else
		{
			emaList.remove(emaListSize-1);
			emaList.remove(0);
			emaList.add(new EMA(candles[candlesSize-2]));
			
			emaList.get(emaListSize-2).setEma(new Double (calculaEMA(candles[candlesSize-2].getClose(),emaList.get(emaListSize-3).getEma(), size)));
			emaList.add(new EMA(candles[candlesSize-1]));
			emaList.get(emaListSize-1).setEma(new Double (calculaEMA(candles[candlesSize-1].getClose(),emaList.get(emaListSize-2).getEma(), size)));
		}
		
		
	}
}
