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
import mx.org.traderbot.util.Constantes;

@Component("ScalpingEMARapida")
@Scope("prototype")
public class ScalpingEMARapida 
//extends EstrategiaBotImpl 
{
//	
////	@Autowired
////	InfoBalance infoBalance;
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		try
//		{
//			scalpingEMARapida();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public void scalpingEMARapida() throws BotException
//	{
//		double ema_34=0;
//		double ema_13=0;
//		double diferenciaProfit=0;
//		double profit=0;
//		double cantidad=0;
//		boolean vende = false;
//		double porcentajeActivacionCompra= 0.9998;
//		
//		double porcentajePerdida = 0.9993;
//		double porcentajeProfit = 1.0030;
//
//		
////		infoBalance.setVoyAComprar(false);
//    	CandleModel model = new CandleModel();
//    	Candle [] candles = model.getCandles(period, "100");
//    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
//    	
//    	List<EMA> ema_MN_13 = model.movingAverageExponencial( 9, candles);
//		List<EMA> ema_MN_34 = model.movingAverageExponencial( 26, candles);		
//		
//		ema_34 = ema_MN_34.get(ema_MN_34.size()-1).getEma();
//		ema_34 = (Math.round((ema_34) * 100.0) / 100.0);
//		
//		ema_13 = ema_MN_13.get(ema_MN_13.size()-1).getEma();
//		ema_13 = (Math.round((ema_13) * 100.0) / 100.0);
//		
//		if(ema_13 > ema_34 && (ema_13 - ema_34 > 2))
//		{
//			if(infoBalance.getTotal_cripto()>0 && !infoBalance.isPuedeComprar())//VENTA
//			{
//				if(infoBalance.getPrecio() < infoBalance.getPrecio_stop_loss())
//				{
//					vende = true;
//				}
//				else if (infoBalance.getPrecio()>= infoBalance.getPrecio_stop_loss() && infoBalance.getPrecio() < infoBalance.getPrecio_stop_profit())
//				{
//					if(infoBalance.isRevasoProfit())
//					{
//						profit = ((infoBalance.getPrecio_stop_profit() - infoBalance.getPrecio_compra())*0.75);
//						if( infoBalance.getPrecio() < (infoBalance.getPrecio_compra() + profit) )
//						{
//							vende = true;
//						}
//					}
//				}
//				if(infoBalance.getPrecio() >= infoBalance.getPrecio_stop_profit())
//				{
//					infoBalance.setRevasoProfit(true);
//					diferenciaProfit = ((infoBalance.getPrecio() - infoBalance.getPrecio_stop_profit())*0.02);
//					diferenciaProfit = (Math.round((diferenciaProfit) * 100.0) / 100.0);
//					infoBalance.setPrecio_stop_profit(infoBalance.getPrecio_stop_profit()+diferenciaProfit);
//				}
//				
//				if(vende)
//				{
//					logger.info("VENTA ");
//					
//					infoBalance.setTotal( (Math.round((infoBalance.getPrecio()*infoBalance.getTotal_cripto()) * 100.0) / 100.0));
//					infoBalance.setFee(  (Math.round((infoBalance.getTotal()*0.001) * 100.0) / 100.0));
//					infoBalance.setTotal_final((Math.round((infoBalance.getTotal() - infoBalance.getFee()) * 100.0) / 100.0));
//					
//					infoBalance.setTotal_usd(infoBalance.getTotal_usd() + infoBalance.getTotal_final());
//					infoBalance.setTotal_cripto( 0);
//																	
//					logger.info("\t\tprecio: " + infoBalance.getPrecio());
//					logger.info("\t\ttotal: " + infoBalance.getTotal());
//					logger.info("\t\tfee: " + infoBalance.getFee());
//					logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
//					logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
//					logger.info("\t\tporcentaje profit: " + infoBalance.getPrecio() + profit);
//					logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());
//					
//					infoBalance.setPrecio_stop_loss(0);
//					infoBalance.setPrecio_stop_profit(0);
//					infoBalance.setPrecio_compra(0);
//					infoBalance.setZona_compra(0);					
//					infoBalance.setRevasoProfit(false);
//				}
//					
//			}else if(infoBalance.isPuedeComprar())//Compra
//			{
//				if(Math.abs(infoBalance.getPrecio() - infoBalance.getPrecio_compra())<=2 && infoBalance.getTotal_cripto()==0)
//				{					
//					logger.info("COMPRA ");
//
//					cantidad = (Math.floor((infoBalance.getTotal_usd()/infoBalance.getPrecio()) * 100.0) / 100.0);
//					infoBalance.setTotal( (Math.round((infoBalance.getPrecio()*cantidad) * 100.0) / 100.0));
//					infoBalance.setFee(  (Math.round((infoBalance.getTotal()*0.001) * 100.0) / 100.0));
//					infoBalance.setTotal_final((Math.round((infoBalance.getTotal() + infoBalance.getFee()) * 100.0) / 100.0));
//					
//					infoBalance.setTotal_usd(infoBalance.getTotal_usd() - infoBalance.getTotal_final());
//					infoBalance.setTotal_cripto( cantidad);
//					
//					if(infoBalance.getZona_compra() == Constantes.ZONA_EMA13)
//						infoBalance.setPrecio_stop_loss((Math.round((ema_34) * 100.0) / 100.0));
//					else
//						infoBalance.setPrecio_stop_loss((Math.round((ema_34*0.9995) * 100.0) / 100.0));
//					
//					
//					infoBalance.setPrecio_stop_profit((Math.round((infoBalance.getPrecio() + 15) * 100.0) / 100.0));
//					
//					infoBalance.setPrecio_compra(infoBalance.getPrecio());
//					
//					logger.info("\t\tprecio: " + infoBalance.getPrecio());
//					logger.info("\t\ttotal: " + infoBalance.getTotal());
//					logger.info("\t\tfee: " + infoBalance.getFee());
//					logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
//					logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
//					logger.info("\t\tapraxUSD: " + infoBalance.getTotal_usd() + infoBalance.getTotal_final());
//					logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());
//					infoBalance.setPuedeComprar(false);
//				
//					
//					
//				}else if (infoBalance.getPrecio() < ema_34)
//				{
//					infoBalance.setPrecio_compra( ema_34 );
//					infoBalance.setZona_compra(Constantes.ZONA_EMA34);
//				}
//			}		
//			else if( !infoBalance.isPuedeComprar() && infoBalance.getTotal_cripto()==0
//					&& infoBalance.getPrecio() < (ema_13 * porcentajeActivacionCompra) 
//					&& infoBalance.getPrecio() > ema_34)//Activa Compra
//			{
//				infoBalance.setPuedeComprar(true);
//				infoBalance.setPrecio_compra(ema_13);
//				infoBalance.setZona_compra(Constantes.ZONA_EMA13);
//			}
//		}
//			
//		
//		
//		
//		
//		
//		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
//		{
//			double aux=0;
//			String info = "precio activacion:\t"+(Math.round((ema_13 * porcentajeActivacionCompra) * 100.0) / 100.0)
//					+ "\nema 13:\t\t" + ema_13
//					+ "\nema 34:\t\t" + ema_34
//				
//					+ "\n"+ infoBalance.toString();
//			
//			if(profit>0)			
//				info += "\nporcentaje profit: " + infoBalance.getPrecio() + profit;			
//			
//			infoEstrategiaView.getTextArea_info().setText(info);
//			
//			infoEstrategiaView.getTextField_usd().setText(infoBalance.getTotal_usd()+"");
//			infoEstrategiaView.getTextField_cripto().setText(infoBalance.getTotal_cripto()+"");
//			infoEstrategiaView.getTextField_nombreLog().setText("Estrategia_"+id+".log");
//			
//			if(infoBalance.getTotal_cripto()>0)
//			{
//				
//				aux = ((infoBalance.getTotal_cripto()*infoBalance.getPrecio()) + infoBalance.getTotal_usd());
//				infoEstrategiaView.getTextField_usdAprox().setText(aux + "");
//				infoEstrategiaView.getTextField_criptoAprox().setText(infoBalance.getTotal_cripto()+"");
//			}else
//			{
//				aux = (Math.floor((infoBalance.getTotal_usd()/infoBalance.getPrecio()) * 100.0) / 100.0);
//				infoEstrategiaView.getTextField_usdAprox().setText(infoBalance.getTotal_usd()+"");
//				infoEstrategiaView.getTextField_criptoAprox().setText(aux+"");
//			}
//			
//			
//		}
//	}
	
}
