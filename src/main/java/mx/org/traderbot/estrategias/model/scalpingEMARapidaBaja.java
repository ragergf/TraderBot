package mx.org.traderbot.estrategias.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.estrategias.vo.InfoBalance;
import mx.org.traderbot.exception.BotException;

@Component("ScalpingEMARapidaBaja")
@Scope("prototype")
public class scalpingEMARapidaBaja extends EstrategiaBotImpl {
//	@Autowired
//	InfoBalance infoBalance;


	public void run() {
		// TODO Auto-generated method stub
		try
		{
			scalpingEMARapidaBaja();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void scalpingEMARapidaBaja() throws BotException, Exception
	{
		double ema_34=0;
		double ema_rapida=0;

//		infoBalance.setVoyAComprar(false);
    	CandleModel model = new CandleModel();
    	Candle [] candles = model.getCandles(period, "100");
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	List<EMA> ema_MN_13 = model.movingAverageExponencial( 9, candles);
		List<EMA> ema_MN_34 = model.movingAverageExponencial( 26, candles);		
		
		ema_34 = ema_MN_34.get(ema_MN_34.size()-1).getEma();
		ema_34 = (Math.round((ema_34) * 100.0) / 100.0);
		
		ema_rapida = ema_MN_13.get(ema_MN_13.size()-1).getEma();
		ema_rapida = (Math.round((ema_rapida) * 100.0) / 100.0);
		
		

			
		
		
		
		
		
		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
		{
			String info =
					  "\nema 13:\t\t" + ema_rapida
					+ "\nema 34:\t\t" + ema_34
					+ "\n"+ infoBalance.toString();
			infoEstrategiaView.getTextArea_info().setText(info);
			
			infoEstrategiaView.getTextField_usd().setText(infoBalance.getTotal_usd()+"");
			infoEstrategiaView.getTextField_cripto().setText(infoBalance.getTotal_cripto()+"");
			infoEstrategiaView.getTextField_nombreLog().setText("Estrategia_"+id+".log");
			
		}
	}
	
}
