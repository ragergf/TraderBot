package mx.org.traderbot.estrategias.model;

import java.util.Date;
import java.util.EventObject;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.activeorder.model.ActiveOrderModel;
import mx.org.traderbot.balance.model.BalanceModel;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.chart.candles.vo.PSAR;
import mx.org.traderbot.chart.candles.vo.PSAR_V2;
import mx.org.traderbot.chart.candles.vo.RSI;
import mx.org.traderbot.chart.candles.vo.SMA;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.model.OrderModel;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Constantes;
import mx.org.traderbot.util.Util;

@Component("ScalpingPSAR5")
@Scope("prototype")
public class ScalpingPSAR5 extends EstrategiaBotImpl{
	
	@Autowired
	CandleModel candleModel;
	
	@Autowired
	OrderModel orderModel;
	
	@Autowired
	Order order;
	
	@Autowired
	BalanceModel balanceModel;
	
	@Autowired
	ActiveOrderModel activeOrderModel;
	
	double ema_rapida=0;
	double ema_lenta=0;
	boolean ejecutado=false;
	double macd=0;
	double signal=0;
	double macd_1=0;
	double signal_1=0;
	double macd_2=0;
	double signal_2=0;	
	double DIF = 0.27;
	double rsi=0;
	double limite=0;
	double volumen_media;
	double volumen;
	
	double cantidad=0.15;
	boolean retraso;
	
	PSAR_V2 psar = null;
	PSAR_V2 prevPsar1 = null;
	PSAR_V2 prevPsar2 = null;
//	PSAR prevPsar3 = null;
	
	
	
	Order[] orders;

	
	public void run() {
		// TODO Auto-generated method stub
		try{			
			logger.info("Iniciando Hilo");
			scalpingPSAR4();
			logger.error("Terminando Hilo");
		}catch(Exception e)
		{
			logger.error(e);
			logger.error("Terminando Hilo con ERROR");
		}
	}
	
	
	public void scalpingPSAR4() throws BotException, Exception
	{
		double ema_dif=0;
		
		logger.info("Inicia scalpingPSAR4");
		
		if(!infoBalance.isComenzo())
		{
			
			infoBalance.setTotal_usd(0);			
//			infoBalance.setPrecio_venta(8125);
//			infoBalance.setStop(7370);
			infoBalance.setPostura_venta(true);
//			infoBalance.setSeñal_1(true);
//			cantidad = 0.14;
			infoBalance.setComenzo(true);
		}
		
		retraso = true;
		
		Candle [] candles = candleModel.getCandles(period, "200", Constantes.SYMBOl_BTCUSD);
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	
//		List<EMA> list_ema_lenta = candleModel.movingAverageExponencial( sizeEmaLenta, candles);	
//		List<EMA> list_ema_rapida = candleModel.movingAverageExponencial( sizeEmaRapida, candles);
//		List<RSI> list_rsi = candleModel.relativeStrengthIndex(14, candles);
		List<PSAR_V2> listPSAR = candleModel.getParabolicSAR_V2(candles);
//		List<SMA> list_volumen_media = candleModel.movingAverage( 10, candles, 1);
		
//		List<EMA> listMACD = candleModel.estrategiaMACD(candles);
		
		
//		logger.info("Macd Line: "+ema_signal.get(ema_signal.size()-1).getCandle().getClose());
//		logger.info("Signal Line: "+ema_signal.get(ema_signal.size()-1).getEma());
		
//		volumen_media = list_volumen_media.get(list_volumen_media.size()-1).getSma();
		volumen = candles[candles.length-1].getVolume();
//		ema_lenta = Util.redondear(list_ema_lenta.get(list_ema_lenta.size()-1).getEma());	
//		ema_rapida = Util.redondear(list_ema_rapida.get(list_ema_rapida.size()-1).getEma());
//		macd = Util.redondear(listMACD.get(listMACD.size()-1).getCandle().getClose());
//		signal = Util.redondear(listMACD.get(listMACD.size()-1).getEma());
		
//		macd_1 = Util.redondear(listMACD.get(listMACD.size()-2).getCandle().getClose());
//		signal_1 = Util.redondear(listMACD.get(listMACD.size()-2).getEma());
		
//		macd_2 = Util.redondear(listMACD.get(listMACD.size()-3).getCandle().getClose());
//		signal_2 = Util.redondear(listMACD.get(listMACD.size()-3).getEma());
		
		psar = listPSAR.get(listPSAR.size()-1);
//		prevPsar1 = listPSAR.get(listPSAR.size()-2);
//		prevPsar2 = listPSAR.get(listPSAR.size()-3);
//		prevPsar3 = listPSAR.get(listPSAR.size()-4);
		
		
//		rsi = Util.truncar(list_rsi.get(list_rsi.size()-1).getRsi());	
				
		balance();
		cantidad = Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio());
		
		
		if (!infoBalance.isPostura_venta())
		{//POSTURA DE COMPRA		
			if(psar.isFalling())
				logicaStopProfit();			
			
			if(infoBalance.getPrecio() > infoBalance.getStop())
			{
				transaccionSemiInstantanea(false);		
			}
		}else
		{//POSTURA DE VENTA			
			if(!psar.isFalling())
			{
				infoBalance.setPuedeVender(true);
				infoBalance.setStop(psar.getSar_man());
			}
						
			if(infoBalance.getPrecio() < infoBalance.getStop()
					&&
				infoBalance.isPuedeVender())
			{
				transaccionSemiInstantanea(true);
			}
		}	
		balance();

		imprime_info();
		
		if(retraso)
		{
			Thread.sleep(27000);
		}
		
	}
	
	public void logicaStopProfit()
	{
		
		if( 
				(
				infoBalance.getPrecio() < infoBalance.getPrecio_venta() + Constantes.UMBRAL_LOSS
				&&
				infoBalance.getPrecio() > infoBalance.getPrecio_venta() - Constantes.UMBRAL_FIT
				)
				||
				infoBalance.getPrecio() > infoBalance.getPrecio_venta() + Constantes.UMBRAL_LOSS
		)
		{
			infoBalance.setStop(infoBalance.getPrecio_venta() + Constantes.UMBRAL_LOSS);
		}		
		else if(
				
				infoBalance.getPrecio() < infoBalance.getPrecio_venta() - Constantes.UMBRAL_FIT
		)
		{
			if(psar.getSar_man()>infoBalance.getPrecio_venta() - Constantes.UMBRAL_FIT)
				infoBalance.setStop(infoBalance.getPrecio_venta() - Constantes.UMBRAL_FIT);
			else
				infoBalance.setStop(psar.getSar_man());
		}				
	}
	
	public void logicaStopReventa()
	{
		
	}
	
	
	public void transaccionSemiInstantanea(boolean venta) throws BotException, Exception
	{		
		String side;		
		retraso=false;
		if(venta)
		{
			logger.info("vendiendo");
			side = Constantes.SELL;
			infoBalance.setPrecio_venta( infoBalance.getPrecio() + DIF);
		}
		else
		{
			logger.info("comprando");
			side = Constantes.BUY;
			infoBalance.setPrecio_compra( infoBalance.getPrecio() - DIF);
		}
		trade(side);
	}
	
	public void trade(String side) throws BotException, Exception
	{
		
		logger.info("Orden Limite("+side+"): " + infoBalance.getPrecio());		
		if(side.equalsIgnoreCase(Constantes.SELL))
			order = orderModel.trade(side, infoBalance.getTotal_cripto(), infoBalance.getPrecio_venta(),  Constantes.SYMBOl_BTCUSD);
		else
			order = orderModel.trade(side, cantidad, infoBalance.getPrecio_compra(),  Constantes.SYMBOl_BTCUSD);
		
		logger.info(order);
		
		if(order.getStatus().toLowerCase().contains("filled") && !order.getStatus().toLowerCase().contains("partial"))
			ejecutado = true;
		else
		{
			boolean conError=false;
			do{		
				conError = false;
				try
				{
					order = activeOrderModel.getOneActiveOrder(order.getClientOrderId(), tiempo);
				}catch(BotException e)
				{
					if(e.getError().getError().getCode().equalsIgnoreCase("20002"))
					{
						logger.info("Error: ", e);
						ejecutado = true;
					}
				}
				catch(Exception e)
				{
					logger.error(e);
					conError = true;
				}
			}while(conError);
		}
		
		if(!ejecutado)
		{
			logger.info("Despues de Monitorear....");
			logger.info(order);			
			logger.info("Cancelando orden....");
			if(order != null &&( order.getStatus().trim().equalsIgnoreCase("new") || order.getStatus().trim().equalsIgnoreCase("canceled")) || order.getStatus().toLowerCase().contains("partial"))
			{
				orders = orderModel.cancelarOrdenes();
				if(orders!=null && orders.length == 0)
				{
					logger.info("No habia que cancelar, ejecutado");
					ejecutado = true;
				}
			}
			else
			{
				ejecutado = true;
			}
		}				
		
//		balance();
		
		imprime_info();
//		balanceModel.conciliacion(infoBalance, side);
	}
	
	public void balance() throws BotException, Exception
	{
		String side ="";
		balanceModel.conciliacion(infoBalance);
		ejecutado = false;
		if(infoBalance.isPostura_venta() && infoBalance.getTotal_cripto() == 0)
		{
			infoBalance.setPostura_venta(false);
			ejecutado = true;
			side = Constantes.SELL;
			
		}
		else if (!infoBalance.isPostura_venta() && Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio()) == 0)
		{
			infoBalance.setPostura_venta(true);
			infoBalance.setPuedeVender(false);
			ejecutado = true;
			side = Constantes.BUY;
		}
		
		if(ejecutado)
		{
			
			if(side.equalsIgnoreCase(Constantes.SELL))
			{
//				infoBalance.setTotal_cripto(0);
//				infoBalance.setContador(1);
//				infoBalance.setPrecio_compra(0);
			}else
			{				
//				infoBalance.setTotal_cripto(cantidad);
//				infoBalance.setPrecio_venta(0);
			}
			infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
			imprime_info();
			logger.info("Transaccion Realizada(No Trans= "+infoBalance.getNumTransacciones()+" - "+side+") ");
			logger.info(infoBalance);
			
			if(!infoBalance.isPostura_venta())
				Thread.sleep(90000);
		}				
	}
	
	public void imprime_info()
	{
		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
		{
			double aux=0;
			String info =					
					  "\nCantidad:\t\t" + cantidad
					+ "\npsar:\t\t" + psar
					+ "\nprevPsar1:\t\t" + prevPsar1
					+ "\nprevPsar2:\t\t" + prevPsar2
//					+ "\nprevPsar3:\t\t" + prevPsar3	
					+ "\n"+ infoBalance.toString();						
			
			infoEstrategiaView.getTextArea_info().setText(info);
			
			infoEstrategiaView.getTextField_usd().setText(infoBalance.getTotal_usd()+"");
			infoEstrategiaView.getTextField_cripto().setText(infoBalance.getTotal_cripto()+"");
			infoEstrategiaView.getTextField_nombreLog().setText("Estrategia_"+id+".log");
			
			if(infoBalance.getTotal_cripto()>0)
			{
				
				aux = Util.truncar((infoBalance.getTotal_cripto()*infoBalance.getPrecio()) + infoBalance.getTotal_usd());
				infoEstrategiaView.getTextField_usdAprox().setText(aux + "");
				infoEstrategiaView.getTextField_criptoAprox().setText(infoBalance.getTotal_cripto()+"");
			}else
			{
				aux = Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio());
				infoEstrategiaView.getTextField_usdAprox().setText(infoBalance.getTotal_usd()+"");
				infoEstrategiaView.getTextField_criptoAprox().setText(aux+"");
			}					
		}
	}

}

