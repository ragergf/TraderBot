package mx.org.traderbot.estrategias.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.balance.model.BalanceModel;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.estrategias.vo.InfoBalance;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.model.OrderModel;
import mx.org.traderbot.util.Constantes;
import mx.org.traderbot.util.Util;

@Component("StopVolatil")
@Scope("prototype")
public class StopVolatil extends EstrategiaBotImpl{

//	@Autowired
//	InfoBalance infoBalance;
	
	@Autowired
	BalanceModel balanceModel;
	
	@Autowired
	OrderModel orderModel;

	
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			stops();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void stops() throws BotException, Exception
	{
		double stopBuy= 0;
		double stopSell=0;
		
		double difBuy=50;
		double difSell=60;
		
		double cantidad=0;
		double precio=0;
		
		boolean puedeComprar = false;
		
		double ema_34 = 0;
		
		CandleModel model = new CandleModel();
    	Candle [] candles = model.getCandles(period, "100");
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	List<EMA> ema_MN_34 = model.movingAverageExponencial( 26, candles);		
    	
    	ema_34 = ema_MN_34.get(ema_MN_34.size()-1).getEma();
    	ema_34 = Util.redondear(ema_34);
    	
    	
    	stopBuy = ema_34 + difBuy;
    	stopSell = ema_34 - difSell;
    	
    	infoBalance.setTotal_cripto(balanceModel.disponible("BTC"));
    	infoBalance.setTotal_usd(balanceModel.disponible("USD"));
		
    	
    	if(Util.truncar(infoBalance.getTotal_cripto())==0)
    	{
    		puedeComprar=true;
    	}
    	
    	if(infoBalance.getPrecio()>stopBuy && puedeComprar)
    	{	
    		precio = stopBuy-2;
			cantidad = Util.truncar((infoBalance.getTotal_usd()/precio));	
//			if(cantidad > 0)
//				orderModel.trade(Constantes.BUY, cantidad, precio);
    	}
    	else if(infoBalance.getPrecio()<stopSell && !puedeComprar)
    	{
    		cantidad = Util.truncar(infoBalance.getTotal_cripto());
    		precio = stopSell+2;
//    		if(cantidad > 0)
//    			orderModel.trade(Constantes.SELL, cantidad, precio);
    	}    	
    	
    	
    	if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
		{
			double aux=0;
			String info = "Stop Buy:\t\t" + stopBuy
					+ "\nDif Buy:\t\t" + difBuy
					+ "\nStop Sell:\t\t" + stopSell
					+ "\nDif Sell:\t\t" + difSell
					+ "\nema 34:\t\t" + ema_34
					+ "\nPuede Comprar:\t" + puedeComprar
					+ "\nprecio:\t\t" + precio
					+ "\nCantidad:\t\t" + cantidad
					+ "\n\n"+ infoBalance.toString();
			
			
			infoEstrategiaView.getTextArea_info().setText(info);
			
			infoEstrategiaView.getTextField_usd().setText(infoBalance.getTotal_usd()+"");
			infoEstrategiaView.getTextField_cripto().setText(infoBalance.getTotal_cripto()+"");
			infoEstrategiaView.getTextField_nombreLog().setText("Estrategia_"+id+".log");
			
			if(infoBalance.getTotal_cripto()>0)
			{
				
				aux = ((infoBalance.getTotal_cripto()*infoBalance.getPrecio()) + infoBalance.getTotal_usd());
				infoEstrategiaView.getTextField_usdAprox().setText(aux + "");
				infoEstrategiaView.getTextField_criptoAprox().setText(infoBalance.getTotal_cripto()+"");
			}else
			{
				aux = (Math.floor((infoBalance.getTotal_usd()/infoBalance.getPrecio()) * 100.0) / 100.0);
				infoEstrategiaView.getTextField_usdAprox().setText(infoBalance.getTotal_usd()+"");
				infoEstrategiaView.getTextField_criptoAprox().setText(aux+"");
			}
			
			
		}
	}
}
