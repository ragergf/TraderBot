package mx.org.traderbot.estrategias.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.EMA;
import mx.org.traderbot.chart.candles.vo.SMA;
import mx.org.traderbot.estrategias.vo.InfoBalance;
import mx.org.traderbot.estrategias.vo.InfoEstrategiaView;
import mx.org.traderbot.exception.BotException;

@Component("Scalping")
@Scope("prototype")
public class Scalping 
//extends EstrategiaBotImpl 
{
	
////	@Autowired
////	InfoBalance infoBalance;
//	
//
//	@Override
//	public void run() {
//		
//		try
//		{
//			scalping();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public void scalping() throws BotException
//    {
//		double ema_34=0;
//		double diferenciaProfit=0;
//		double cantidad=0;
//		boolean vende = false;
//		double porcentajeActivacionCompra= 0.997;
//		double porcentajePerdida = 0.998;
//		double porcentajeProfit = 1.0026;
////		infoBalance.setVoyAComprar(false);
//    	CandleModel model = new CandleModel();
//    	Candle [] candles = model.getCandles(period, "100");
//    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
//    	
////    	List<EMA> ema_MN_13 = model.movingAverageExponencial( 13, candles);
//		List<EMA> ema_MN_34 = model.movingAverageExponencial( 34, candles);		
//		ema_34 = ema_MN_34.get(ema_MN_34.size()-1).getEma();
//		ema_34 = (Math.round((ema_34) * 100.0) / 100.0);
//		
//		if(infoBalance.getTotal_cripto()>0 && !infoBalance.isPuedeComprar())//VENTA
//		{
//			if((infoBalance.getPrecio() < infoBalance.getPrecio_stop_loss()) 
//					|| (infoBalance.isRevasoProfit() && infoBalance.getPrecio() < infoBalance.getPrecio_stop_profit()) )
//			{
//				vende = true;
//			}
//			else if(infoBalance.getPrecio() > infoBalance.getPrecio_stop_profit())
//			{
//				infoBalance.setRevasoProfit(true);
//				diferenciaProfit = ((infoBalance.getPrecio() - infoBalance.getPrecio_stop_profit())/2);
//				diferenciaProfit = (Math.round((diferenciaProfit) * 0.90) / 100.0);
//				infoBalance.setPrecio_stop_profit(infoBalance.getPrecio_stop_profit()+diferenciaProfit);
//			}
//			
//			if(vende)
//			{
//				logger.info("VENTA ");
//				
//				infoBalance.setTotal( (Math.round((infoBalance.getPrecio()*infoBalance.getTotal_cripto()) * 100.0) / 100.0));
//				infoBalance.setFee(  (Math.round((infoBalance.getTotal()*0.001) * 100.0) / 100.0));
//				infoBalance.setTotal_final((Math.round((infoBalance.getTotal() - infoBalance.getFee()) * 100.0) / 100.0));
//				
//				infoBalance.setTotal_usd(infoBalance.getTotal_usd() + infoBalance.getTotal_final());
//				infoBalance.setTotal_cripto( 0);
//																
//				logger.info("\t\tprecio: " + infoBalance.getPrecio());
//				logger.info("\t\ttotal: " + infoBalance.getTotal());
//				logger.info("\t\tfee: " + infoBalance.getFee());
//				logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
//				logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
//				logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());
//				
//				infoBalance.setPrecio_stop_loss(0);
//				infoBalance.setPrecio_stop_profit(0);
//			}
//				
//		}else if(infoBalance.isPuedeComprar())//Compra
//		{
//			if(Math.abs(infoBalance.getPrecio() - ema_34)<=2 && infoBalance.getTotal_cripto()==0)
//			{
//				
//				logger.info("COMPRA ");
//
//				cantidad = (Math.floor((infoBalance.getTotal_usd()/infoBalance.getPrecio()) * 100.0) / 100.0);
//				infoBalance.setTotal( (Math.round((infoBalance.getPrecio()*cantidad) * 100.0) / 100.0));
//				infoBalance.setFee(  (Math.round((infoBalance.getTotal()*0.001) * 100.0) / 100.0));
//				infoBalance.setTotal_final((Math.round((infoBalance.getTotal() + infoBalance.getFee()) * 100.0) / 100.0));
//				
//				infoBalance.setTotal_usd(infoBalance.getTotal_usd() - infoBalance.getTotal_final());
//				infoBalance.setTotal_cripto( cantidad);
//				
//				infoBalance.setPrecio_stop_loss((Math.round((infoBalance.getPrecio() * porcentajePerdida)) * 100 / 100.0));
//				infoBalance.setPrecio_stop_profit((Math.round((infoBalance.getPrecio() * porcentajeProfit)) * 100 / 100.0));
//				logger.info("\t\tprecio: " + infoBalance.getPrecio());
//				logger.info("\t\ttotal: " + infoBalance.getTotal());
//				logger.info("\t\tfee: " + infoBalance.getFee());
//				logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
//				logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
//				logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());
//				infoBalance.setPuedeComprar(false);
//			
//				
//				
//			}
//		}		
//		else if( !infoBalance.isPuedeComprar() && infoBalance.getTotal_cripto()==0 && infoBalance.getPrecio() < (ema_34 * porcentajeActivacionCompra))//Activa Compra
//		{
//			infoBalance.setPuedeComprar(true);
//		}
//		
//			
//		
//		
//		
//		
//		
//		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
//		{
//			String info = "precio activacion:\t"+(ema_34 * porcentajeActivacionCompra)
//					+ "\nema 34:\t\t" + ema_34
//					+ "\n"+ infoBalance.toString();
//			infoEstrategiaView.getTextArea_info().setText(info);
//			
//			infoEstrategiaView.getTextField_usd().setText(infoBalance.getTotal_usd()+"");
//			infoEstrategiaView.getTextField_cripto().setText(infoBalance.getTotal_cripto()+"");
//			infoEstrategiaView.getTextField_nombreLog().setText("Estrategia_"+id+".log");
//			
//		}
//		
////		List<SMA> sma_MN_10_vol = model.movingAverage( 10, candles, 1);
//		
////		estrategiaBajaPuntos[0] = ema_MN_13.get(ema_MN_13.size()-1).getEma();
////		estrategiaBajaPuntos[1] = ema_MN_34.get(ema_MN_34.size()-1).getEma();
////		estrategiaBajaPuntos[2] = sma_MN_10_vol.get(sma_MN_10_vol.size()-1).getSma();
////		
////		
////		precio = candles[candles.length - 1].getClose();
////		
////		estrategiaBajaPuntos[3] = volumen;
////		
////		
////		
////		if( estrategiaBajaPuntos[1] > estrategiaBajaPuntos[0] && (estrategiaBajaPuntos[1] - estrategiaBajaPuntos[0] > difVenta) && compra)
////		{
////			compra = false;
////			logger.info("VENTA ");
////			precio = Double.parseDouble(dataBot.getPrice().getText());
////			total = (Math.round((precio*totalBTC) * 100.0) / 100.0);
////			fee = (Math.round((total * 0.001) * 100.0) / 100.0);
////			totalFinal =(Math.round((total - fee) * 100.0) / 100.0);
////			totalUSD += (Math.round((totalFinal) * 100.0) / 100.0);
////			totalBTC = 0;
////			
////			logger.info("\t\tcantidad: " + cantidad);				
////			logger.info("\t\tprecio: " + precio);
////			logger.info("\t\ttotal: " + total);
////			logger.info("\t\tfee: " + fee);
////			logger.info("\t\ttotalFinal: " + totalFinal);
////			logger.info("\t\ttotalUSD: " + totalUSD);
////			
////			
////			
////			
////			logger.info("\tvolumen: " + volumen);
////			logger.info("\tSMA_10_volumen: " + estrategiaBajaPuntos[2]);
////			logger.info("\tEMA13: " + estrategiaBajaPuntos[0]);
////			logger.info("\tEMA34: " + estrategiaBajaPuntos[1]);
////		}else if(volumen > estrategiaBajaPuntos[2] &&estrategiaBajaPuntos[0] > estrategiaBajaPuntos[1] && (estrategiaBajaPuntos[0] - estrategiaBajaPuntos[1] > difCompra) && !compra)
////		{
////			compra = true;
////			logger.info("COMPRA ");
////			precio = Double.parseDouble(dataBot.getPrice().getText());
////			cantidad = (Math.floor((totalUSD/precio) * 100.0) / 100.0);
////			total = (Math.round((precio*cantidad) * 100.0) / 100.0);
////			fee = (Math.round((total*0.001) * 100.0) / 100.0);
////			totalFinal = (Math.round((total+fee) * 100.0) / 100.0);
////			totalUSD -=  totalFinal;
////			totalBTC = cantidad;
////			
////			logger.info("\t\tcantidad: " + cantidad);				
////			logger.info("\t\tprecio: " + precio);
////			logger.info("\t\ttotal: " + total);
////			logger.info("\t\tfee: " + fee);
////			logger.info("\t\ttotalFinal: " + totalFinal);
////			logger.info("\t\ttotalUSD: " + totalUSD);
////			logger.info("\t\tAprox USD: " + (totalUSD+(precio * cantidad)));
////			
////			
////			logger.info("\tvolumen: " + volumen);
////			logger.info("\tSMA_10_volumen: " + estrategiaBajaPuntos[2]);
////			logger.info("\tEMA13: " + estrategiaBajaPuntos[0]);
////			logger.info("\tEMA34: " + estrategiaBajaPuntos[1]);
////		}
//		
//    }

}
