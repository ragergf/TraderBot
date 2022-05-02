package mx.org.traderbot.estrategias.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.activeorder.model.ActiveOrderModel;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.model.OrderModel;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Constantes;
import mx.org.traderbot.util.Util;

@Component("ScalpingEMALimite")
@Scope("prototype")
public class ScalpingEMALimite extends EstrategiaBotImpl{
	@Autowired
	CandleModel candleModel;
	
	@Autowired
	OrderModel orderModel;
	
	@Autowired
	Order order;
	
	@Autowired
	ActiveOrderModel activeOrderModel;
	
	long tiempoInicial=0;
	long tiempoFinal=0;
	long procesamiento=0;
	long restante=0;
	
	Date fechaInicio;
	Date fechaFin;
	
	double ema_rapida=0;
	double ema_lenta=0;
	double cantidad=0.01;
	double fee = 0.001;
	double DIF = 0.17;
	double COMPRA = 8;
	
	Order[] orders;

	
	public void run() {
		// TODO Auto-generated method stub
		try{			
			scalpingEMALimite();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public long restante()
	{
		long aux =0;
		fechaFin = new Date();
		tiempoInicial=fechaInicio.getTime();
		tiempoFinal=fechaFin.getTime();
		
		procesamiento=tiempoFinal - tiempoInicial;
		aux = tiempo - procesamiento;
		
		logger.info("tiempo de procesamiento: " + procesamiento);
		logger.info("tiempo de restante: " + (tiempo - procesamiento));
		
		return aux;
	}
	
	public void scalpingEMALimite() throws BotException, Exception
	{
		double ema_dif=0;
		
		fechaInicio = new Date();
		Candle [] candles = candleModel.getCandles(period, "100", Constantes.SYMBOl_BTCUSD);
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	List<EMA> list_ema_rapida = candleModel.movingAverageExponencial( sizeEmaRapida, candles);
		List<EMA> list_ema_lenta = candleModel.movingAverageExponencial( sizeEmaLenta, candles);		
		
		ema_rapida = Util.redondear(list_ema_rapida.get(list_ema_rapida.size()-1).getEma(),10000);
		ema_lenta = Util.redondear(list_ema_lenta.get(list_ema_lenta.size()-1).getEma(), 10000);	
		
		logger.info("ema_rapida: " + ema_rapida);
		
		//COLOCAR VENTA
		if(infoBalance.getTotal_cripto() != 0 && infoBalance.getPrecio() > ema_rapida)
		{
			infoBalance.setPuedeVender(true);
		}
		else if(infoBalance.getTotal_cripto() != 0 && infoBalance.getPrecio() > ema_lenta)
			infoBalance.setPuedeVender(false);
//		//COLOCAR COMPRA
//		else if(infoBalance.getTotal_cripto() == 0 && infoBalance.getPrecio() < ema_rapida)
//		{
//			infoBalance.setPuedeComprar(true);
//		}		
////		VENTA
//		else		
//		if(infoBalance.getTotal_cripto() != 0 && infoBalance.getPrecio() < ema_rapida 
//				&& infoBalance.isPuedeVender()
//				)
//		{
//			logger.info("VENTA: " + infoBalance.getPrecio());
//			trade(Constantes.SELL);			
//		}
//		//COMPRA
//		else if(infoBalance.getTotal_cripto() == 0 && infoBalance.getPrecio() > ema_rapida 
//				&& infoBalance.isPuedeComprar()
//				)
//		{
//			logger.info("COMPRA: " + infoBalance.getPrecio());
//			trade(Constantes.BUY);
//		}			
		
		if(ema_rapida < ema_lenta)
		{
			ema_dif = (ema_lenta - ema_rapida) /7;
			ema_dif = Util.truncar(ema_dif);			
			if(infoBalance.getTotal_cripto() != 0 && (ema_rapida - ema_dif) < infoBalance.getPrecio() && (ema_lenta) > infoBalance.getPrecio() && infoBalance.isPuedeVender())
				trade(Constantes.SELL);
//			else if(infoBalance.getTotal_cripto() == 0 && infoBalance.getPrecio() > ema_lenta)
//			{
//				infoBalance.setPrecio_compra(infoBalance.getPrecio() - DIF);
//				trade(Constantes.BUY);
//			}
			else if((infoBalance.getTotal_cripto() == 0 && infoBalance.isPuedeComprar()))
			{
				infoBalance.setPrecio_compra(infoBalance.getPrecio_venta() - COMPRA);
				trade(Constantes.BUY);
			}
		}
		else
		{
			if(infoBalance.getTotal_cripto()==0)
			{
				infoBalance.setPrecio_compra(infoBalance.getPrecio() - DIF);
				trade(Constantes.BUY);
			}
		}
		
		imprime_info();

		
		
	}
	
	public void trade(String side) throws BotException, Exception
	{
		boolean ejecutado=false;
		logger.info("Orden Limite("+side+"): " + infoBalance.getPrecio());		
		if(side.equalsIgnoreCase(Constantes.SELL))
			order = orderModel.trade(side, cantidad, (infoBalance.getPrecio() + DIF),  Constantes.SYMBOl_BTCUSD);
		else
			order = orderModel.trade(side, cantidad, infoBalance.getPrecio_compra(),  Constantes.SYMBOl_BTCUSD);
		logger.info(order);
		try
		{
			order = activeOrderModel.getOneActiveOrder(order.getClientOrderId(), 16000);
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
		}
		
		if(!ejecutado)
		{
			logger.info("Despues de Monitorear....");
			logger.info(order);			
			logger.info("Cancelando orden....");
			if(order != null &&( order.getStatus().trim().equalsIgnoreCase("new") || order.getStatus().trim().equalsIgnoreCase("canceled")))
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
		
		if(ejecutado)
		{
			if(side.equalsIgnoreCase(Constantes.SELL))
			{
				infoBalance.setTotal_cripto(0);
				infoBalance.setPuedeComprar(true);
				infoBalance.setPuedeVender(false);
				infoBalance.setPrecio_venta(infoBalance.getPrecio());
//				infoBalance.setPuedeVender(false);
			}else
			{
				infoBalance.setTotal_cripto(cantidad);
				infoBalance.setPuedeComprar(false);
				infoBalance.setPrecio_venta(0);
//				infoBalance.setPuedeComprar(false);
			}
			infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
			imprime_info();
			logger.info("Transaccion Realizada(No Trans= "+infoBalance.getNumTransacciones()+" - "+side+") ");
			logger.info(infoBalance);
		}
		imprime_info();

	}
	
	public void imprime_info()
	{
		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
		{
			double aux=0;
			String info =
					  "\nema rapida:\t\t" + ema_rapida
					+ "\nema lenta:\t\t" + ema_lenta
				
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
