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
import mx.org.traderbot.util.Util;

@Component("ScalpingEMAs")
@Scope("prototype")
public class ScalpingEMAs extends EstrategiaBotImpl{
//	@Autowired
//	InfoBalance infoBalance;
	
	@Autowired
	CandleModel candleModel;
	
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			scalpingEMAs();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void scalpingEMAs() throws BotException, Exception
	{
		double ema_rapida=0;
		double ema_lenta=0;
		double cantidad=0;
		double fee = 0.001;
		
		
		Candle [] candles = candleModel.getCandles(period, "100");
    	infoBalance.setPrecio(candles[candles.length - 1].getClose());
    	
    	List<EMA> list_ema_rapida = candleModel.movingAverageExponencial( sizeEmaRapida, candles);
		List<EMA> list_ema_lenta = candleModel.movingAverageExponencial( sizeEmaLenta, candles);		
		
		ema_rapida = Util.redondear(list_ema_rapida.get(list_ema_rapida.size()-1).getEma());
		ema_lenta = Util.redondear(list_ema_lenta.get(list_ema_lenta.size()-1).getEma());				
		
		//ema_rapida >= ema_lenta		
		
		//VENTA
		if(infoBalance.getTotal_cripto() != 0 && infoBalance.getPrecio() < ema_rapida)
		{
//			infoBalance.setPrecio(infoBalance.getEma_rapida());

			
			infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*infoBalance.getTotal_cripto()));
			infoBalance.setFee(Util.redondear(infoBalance.getTotal()* fee));
			infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() - infoBalance.getFee()));
			
			infoBalance.setTotal_usd(infoBalance.getTotal_usd() + infoBalance.getTotal_final());
			infoBalance.setTotal_cripto( 0);
															
			logger.info("\t\tprecio: " + infoBalance.getPrecio());
			logger.info("\t\ttotal: " + infoBalance.getTotal());
			logger.info("\t\tfee: " + infoBalance.getFee());
			logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
			logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
			logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());	
			logger.info("\t\tema_lenta: " + ema_lenta);
			logger.info("\t\tema_rapida: " + ema_rapida);
			infoBalance.setPrecio_compra(infoBalance.getPrecio());
			infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
			infoBalance.setPuedeComprar(true);
		}
		//COMPRA
		else if(infoBalance.getTotal_cripto() == 0 && infoBalance.getPrecio() > ema_rapida)
		{
			logger.info("COMPRA ");
			
//			infoBalance.setPrecio(infoBalance.getEma_rapida());
			
			cantidad = Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio());
			infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*cantidad));
			infoBalance.setFee(Util.redondear(infoBalance.getTotal()*fee));
			infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() + infoBalance.getFee()));
			
			infoBalance.setTotal_usd(infoBalance.getTotal_usd() - infoBalance.getTotal_final());
			infoBalance.setTotal_cripto( cantidad);
			
			infoBalance.setPrecio_compra(infoBalance.getPrecio());
							
			logger.info("\t\tprecio: " + infoBalance.getPrecio());
			logger.info("\t\ttotal: " + infoBalance.getTotal());
			logger.info("\t\tfee: " + infoBalance.getFee());
			logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
			logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
			logger.info("\t\tapraxUSD: " + infoBalance.getTotal_usd() + infoBalance.getTotal_final());
			logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());
			logger.info("\t\tema_lenta: " + ema_lenta);
			logger.info("\t\tema_rapida: " + ema_rapida);				
			
			infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
			infoBalance.setPuedeComprar(false);
		}
		
//		if(ema_rapida >= ema_lenta)
//		{
//			
//			//VENTA
//			if(infoBalance.getTotal_cripto() != 0 && ( infoBalance.getPrecio() < ema_rapida || infoBalance.getPrecio() > ema_rapida + 6))
//			{
//				
//				infoBalance.setPrecio(ema_rapida);
//
//				
//				infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*infoBalance.getTotal_cripto()));
//				infoBalance.setFee(Util.redondear(infoBalance.getTotal()*0.0001));
//				infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() - infoBalance.getFee()));
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
//				logger.info("\t\tema_lenta: " + ema_lenta);
//				logger.info("\t\tema_rapida: " + ema_rapida);
//				infoBalance.setPrecio_compra(infoBalance.getPrecio());
//				infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
//				infoBalance.setPuedeComprar(true);
//			}
//			//COMPRA
//			else if(infoBalance.getTotal_cripto() == 0)
//			{
//				if(!infoBalance.isPuedeComprar() && infoBalance.getPrecio() < ema_rapida)
//				{
//					infoBalance.setPuedeComprar(true);
//				}
//				else if(infoBalance.isPuedeComprar() && infoBalance.getPrecio() > ema_rapida)
//				{
//					logger.info("COMPRA ");
//					
//					infoBalance.setPrecio(ema_rapida);
//					
//					cantidad = Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio());
//					infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*cantidad));
//					infoBalance.setFee(Util.redondear(infoBalance.getTotal()*0.0001));
//					infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() + infoBalance.getFee()));
//					
//					infoBalance.setTotal_usd(infoBalance.getTotal_usd() - infoBalance.getTotal_final());
//					infoBalance.setTotal_cripto( cantidad);
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
//					logger.info("\t\tema_lenta: " + ema_lenta);
//					logger.info("\t\tema_rapida: " + ema_rapida);				
//					
//					infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
//					infoBalance.setPuedeComprar(false);
//				}
//			}
//			
//		}
//		else if(ema_rapida < ema_lenta)
//		{
//			//VENTA
//			if(infoBalance.getTotal_cripto() != 0)							
//			{
//				if(!infoBalance.isPuedeComprar() && infoBalance.getPrecio() > ema_rapida)
//				{
//					infoBalance.setPuedeComprar(true);
//				}
//				else if(infoBalance.isPuedeComprar()  && infoBalance.getPrecio() < ema_rapida)
//				{
//					logger.info("VENTA ");
//					
//					infoBalance.setPrecio(ema_rapida);
//					
//					infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*infoBalance.getTotal_cripto()));
//					infoBalance.setFee(Util.redondear(infoBalance.getTotal()*0.0001));
//					infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() - infoBalance.getFee()));
//					
//					infoBalance.setTotal_usd(infoBalance.getTotal_usd() + infoBalance.getTotal_final());
//					infoBalance.setTotal_cripto( 0);
//																	
//					logger.info("\t\tprecio: " + infoBalance.getPrecio());
//					logger.info("\t\ttotal: " + infoBalance.getTotal());
//					logger.info("\t\tfee: " + infoBalance.getFee());
//					logger.info("\t\ttotalFinal: " + infoBalance.getTotal_final());
//					logger.info("\t\ttotalUSD: " + infoBalance.getTotal_usd());
//					logger.info("\t\ttotalCripto: " + infoBalance.getTotal_cripto());	
//					logger.info("\t\tema_lenta: " + ema_lenta);
//					logger.info("\t\tema_rapida: " + ema_rapida);
//					infoBalance.setPrecio_compra(ema_rapida);
//					infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
//					infoBalance.setPuedeComprar(false);
//				}
//			}			
//			//COMPRAR
//			else if(infoBalance.getTotal_cripto() == 0 && infoBalance.getPrecio() > ema_rapida)
//			{
//				
//				
//					logger.info("COMPRA ");
//					
//					infoBalance.setPrecio(ema_rapida - 6);
//					
//					cantidad = Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio());
//					infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*cantidad));
//					infoBalance.setFee(Util.redondear(infoBalance.getTotal()*0.0001));
//					infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() + infoBalance.getFee()));
//					
//					infoBalance.setTotal_usd(infoBalance.getTotal_usd() - infoBalance.getTotal_final());
//					infoBalance.setTotal_cripto( cantidad);
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
//					logger.info("\t\tema_lenta: " + ema_lenta);
//					logger.info("\t\tema_rapida: " + ema_rapida);				
//					
//					infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
//					infoBalance.setPuedeComprar(true);
//			}
//			
			
//			if(!infoBalance.isPuedeComprar() && infoBalance.getPrecio() < (ema_rapida -6 ) && infoBalance.getTotal_cripto() == 0)
//			{
//				infoBalance.setPuedeComprar(true);
//			}
//			else
//			if(!infoBalance.isPuedeComprar() && infoBalance.getPrecio() > ema_rapida && infoBalance.getTotal_cripto() != 0)
//			{
//				logger.info("VENTA ");
//				
//				infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*infoBalance.getTotal_cripto()));
//				infoBalance.setFee(Util.redondear(infoBalance.getTotal()*0.0001));
//				infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() - infoBalance.getFee()));
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
//				infoBalance.setPrecio_compra(ema_rapida);
//				infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
//				infoBalance.setPuedeComprar(true);
//			}
//			//COMPRA
//			else if(infoBalance.isPuedeComprar()  && infoBalance.getTotal_cripto() == 0)
//			{
//				if((infoBalance.getPrecio() < ema_rapida - 6) || (infoBalance.getPrecio() > ema_lenta))
//				{
//					logger.info("COMPRA ");
//					
//					infoBalance.setPrecio(ema_rapida - 6);
//					
//					cantidad = Util.truncar(infoBalance.getTotal_usd()/infoBalance.getPrecio());
//					infoBalance.setTotal(Util.redondear(infoBalance.getPrecio()*cantidad));
//					infoBalance.setFee(Util.redondear(infoBalance.getTotal()*0.0001));
//					infoBalance.setTotal_final(Util.redondear(infoBalance.getTotal() + infoBalance.getFee()));
//					
//					infoBalance.setTotal_usd(infoBalance.getTotal_usd() - infoBalance.getTotal_final());
//					infoBalance.setTotal_cripto( cantidad);
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
//					logger.info("\t\tema_lenta: " + ema_lenta);
//					logger.info("\t\tema_rapida: " + ema_rapida);				
//					
//					infoBalance.setNumTransacciones(infoBalance.getNumTransacciones()+1);
//					infoBalance.setPuedeComprar(false);
//					
//				}
//			}
//			else if( infoBalance.isPuedeComprar() && infoBalance.getTotal_cripto() != 0 && infoBalance.getPrecio()< ema_rapida )
//			{
//				infoBalance.setPuedeComprar(false);
//			}
//		}
		
		//ema_rapida < ema_lenta
		
		
		

		
		
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
