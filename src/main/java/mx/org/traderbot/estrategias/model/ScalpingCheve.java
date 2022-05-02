package mx.org.traderbot.estrategias.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.activeorder.model.ActiveOrderModel;
import mx.org.traderbot.balance.model.BalanceModel;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.model.OrderModel;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Constantes;
import mx.org.traderbot.util.Util;

@Component("ScalpingCheve")
@Scope("prototype")
public class ScalpingCheve extends EstrategiaBotImpl{
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
	
	long tiempoInicial=0;
	long tiempoFinal=0;
	long procesamiento=0;
	long restante=0;
	
	Date fechaInicio;
	Date fechaFin;
	
	double ema_rapida=0;
	double ema_lenta=0;
	double cantidad=10;
	double fee = 0.001;
	double DIF = 0.18;
	double COMPRA = 10;
	
	Order[] orders;

	
	public void run() {
		// TODO Auto-generated method stub
		try{			
			logger.info("Iniciando Hilo");
			scalpingEMALimite();
			logger.error("Terminando Hilo");
		}catch(Exception e)
		{
			logger.error(e);
			logger.error("Terminando Hilo con ERROR");
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
		double noTrans = 0;
		
		logger.info("Inicia scalpingEMALimite 3");
		noTrans = infoBalance.getTotal();
		
		
		fechaInicio = new Date();
		Candle [] candles = candleModel.getCandles(period, "100", Constantes.SYMBOl_BTCLSK);
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	
		List<EMA> list_ema_lenta = candleModel.movingAverageExponencial( sizeEmaLenta, candles);		
		
		ema_lenta = Util.redondear(list_ema_lenta.get(list_ema_lenta.size()-1).getEma(), 10000);	
		
		logger.info("ema_lenta: " + ema_lenta);
		logger.info("infoBalance.getPrecio(): " + infoBalance.getPrecio());
		
		
		if(
				(infoBalance.getTotal_cripto() != 0)
				||
				(infoBalance.getTotal_cripto() == 0 && infoBalance.getContador() % 6 == 0)
		)
		{
			if (infoBalance.getPrecio() < ema_lenta)
			{
				if(infoBalance.getTotal_cripto() > 0)
				{
					logger.info("vendiendo");
					infoBalance.setPrecio_venta(infoBalance.getPrecio()+DIF);
					trade2(Constantes.SELL);					
				}
			}
			else
			{
				if(infoBalance.getTotal_cripto() == 0)
				{
					logger.info("Comprando");
					infoBalance.setPrecio_compra(infoBalance.getPrecio()-DIF);					
					trade2(Constantes.BUY);
					
				}
			}
		}		
		infoBalance.setContador(infoBalance.getContador()+1);
		imprime_info();
		
	}
	
	public void trade2(String side) throws BotException, Exception
	{
		if(side.equalsIgnoreCase(Constantes.SELL))
		{
			infoBalance.setTotal_cripto(0);
			infoBalance.setPuedeComprar(true);
			infoBalance.setPuedeVender(false);
//			infoBalance.setPrecio_venta(infoBalance.getPrecio());
			if(infoBalance.isPuedeVender())
			{
				infoBalance.setPuedeVender(false);
				infoBalance.setPuedeComprar(true);
			}
			infoBalance.setContador(1);
//			infoBalance.setPuedeVender(false);
		}else
		{
			infoBalance.setTotal_cripto(cantidad);
			infoBalance.setPuedeComprar(false);
			infoBalance.setPrecio_venta(0);
			if(infoBalance.isPuedeComprar())
			{
				infoBalance.setPuedeComprar(false);
			}
//			infoBalance.setPuedeComprar(false);
		}
		infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
		imprime_info();
		logger.info("Transaccion Realizada(No Trans= "+infoBalance.getNumTransacciones()+" - "+side+") ");
		logger.info(infoBalance);
	}
	
	public void trade(String side) throws BotException, Exception
	{
		boolean ejecutado=false;
		logger.info("Orden Limite("+side+"): " + infoBalance.getPrecio());		
		if(side.equalsIgnoreCase(Constantes.SELL))
			order = orderModel.trade(side, cantidad, infoBalance.getPrecio_venta(),  Constantes.SYMBOl_BTCLSK);
		else
			order = orderModel.trade(side, cantidad, infoBalance.getPrecio_compra(),  Constantes.SYMBOl_BTCLSK);
		
		logger.info(order);
		
		if(order.getStatus().equalsIgnoreCase("filled"))
			ejecutado = true;
		else
		{
			boolean conError=false;
			do{		
				conError = false;
				try
				{
					order = activeOrderModel.getOneActiveOrder(order.getClientOrderId(), 20000);
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
//				infoBalance.setPrecio_venta(infoBalance.getPrecio());
				if(infoBalance.isPuedeVender())
				{
					infoBalance.setPuedeVender(false);
					infoBalance.setPuedeComprar(true);
				}
				infoBalance.setContador(1);
//				infoBalance.setPuedeVender(false);
			}else
			{
				infoBalance.setTotal_cripto(cantidad);
				infoBalance.setPuedeComprar(false);
				infoBalance.setPrecio_venta(0);
				if(infoBalance.isPuedeComprar())
				{
					infoBalance.setPuedeComprar(false);
				}
//				infoBalance.setPuedeComprar(false);
			}
			infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
			imprime_info();
			logger.info("Transaccion Realizada(No Trans= "+infoBalance.getNumTransacciones()+" - "+side+") ");
			logger.info(infoBalance);
		}
		imprime_info();
		balanceModel.conciliacion(infoBalance, side);
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
