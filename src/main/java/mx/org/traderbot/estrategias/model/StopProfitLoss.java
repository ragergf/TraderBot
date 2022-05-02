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


@Component("StopProfitLoss")
@Scope("prototype")
public class StopProfitLoss extends EstrategiaBotImpl {
	
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
			stopProfitLoss();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void stopProfitLoss() throws BotException, Exception
	{
		double ema_34=0;
		double ema_13=0;
		double stop=0;
		double diferencia=300;
		double diferenciaStop=10;
		double cantidad=0;		
		double  precio=0;
				
    	CandleModel model = new CandleModel();
    	Candle [] candles = model.getCandles(period, "100");
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	List<EMA> ema_MN_13 = model.movingAverageExponencial( 13, candles);
		List<EMA> ema_MN_34 = model.movingAverageExponencial( 34, candles);		
		
		ema_34 = ema_MN_34.get(ema_MN_34.size()-1).getEma();
		ema_34 = (Math.round((ema_34) * 100.0) / 100.0);
		
		stop = ema_34 - diferenciaStop;
		
		ema_13 = ema_MN_13.get(ema_MN_13.size()-1).getEma();
		ema_13 = (Math.round((ema_13) * 100.0) / 100.0);
				
		if(ema_13 < ema_34)
		{
			if (infoBalance.getPrecio() > stop)
			{
				infoBalance.setTotal_usd(balanceModel.disponible("USD"));
				cantidad = (Math.floor((infoBalance.getTotal_usd()/infoBalance.getPrecio()) * 100.0) / 100.0);
				precio = infoBalance.getPrecio() - diferencia;
//				orderModel.trade(Constantes.SELL, cantidad, precio);
			}
		}
		
		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
		{
			String info = "\nema 13:\t\t" + ema_13
					+ "\nema 34:\t\t" + ema_34
					+ "\nprecio:\t\t" + precio
					+ "\ncantidad:\t\t" + cantidad
					+ "\ndiferencia:\t\t" + diferencia
					+ "\nstop:\t\t" + stop
					+ "\n"+ infoBalance.toString();
			infoEstrategiaView.getTextArea_info().setText(info);
			
			infoEstrategiaView.getTextField_usd().setText(infoBalance.getTotal_usd()+"");
			infoEstrategiaView.getTextField_cripto().setText(infoBalance.getTotal_cripto()+"");
			infoEstrategiaView.getTextField_nombreLog().setText("Estrategia_"+id+".log");
			
		}
	}		
	
}
