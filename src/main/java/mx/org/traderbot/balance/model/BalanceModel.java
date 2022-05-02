package mx.org.traderbot.balance.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.balance.vo.Balance;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.estrategias.vo.InfoBalance;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.util.Constantes;
import mx.org.traderbot.util.Util;

@Component
@Scope("prototype")
public class BalanceModel {	
	
	final static Logger logger = Logger.getLogger(BalanceModel.class);
	
	@Autowired
	DataService<Balance> balanceService;
	
	public double disponible(String currency) throws BotException, Exception
	{
		Balance balance = null;
		
		balance = obtenBalance(currency);
		logger.info("balance.getAvailable(): " + balance.getAvailable());
		if(balance != null)
			return balance.getAvailable();
		else
			return 0;
	}
	
	public Balance obtenBalance(String currency) throws BotException, Exception
	{
		Balance balance = null;
		
		Object obj = balanceService.getData(Constantes.BALANCE_TYPE, Balance[].class, "");
		
		Balance [] balances = (Balance [])obj;
		
		for(Balance bal: balances)
		{			
			if(currency.equalsIgnoreCase(bal.getCurrency()))
			{
				balance = bal;
				break;
			}
		}
		
		return balance;
	}
	
	public void conciliacion(InfoBalance infoBalance, String side) throws BotException, Exception
	{
		
		double btc_wallet = Util.truncar(disponible("BTC"));
		double btc_infoBalanace = Util.truncar(infoBalance.getTotal_cripto());
		
		logger.info("btc_wallet: "+ btc_wallet);
		logger.info("btc_infoBalanace: "+ btc_infoBalanace);
		
		if(btc_infoBalanace != btc_infoBalanace)
		{
			infoBalance.setNumTransacciones(infoBalance.getNumTransacciones() + 1);
			infoBalance.setTotal_cripto(btc_wallet);
			if(side.equalsIgnoreCase(Constantes.SELL))
			{
				infoBalance.setPrecio_venta(infoBalance.getPrecio_venta());
			}else
			{
				infoBalance.setPrecio_venta(0);
			}
			
			logger.info("conciliacion realizada: "+ side);	
			
		}
		
		
		
		
	}
	
	public void conciliacion(InfoBalance infoBalance) throws BotException, Exception
	{
		
		double btc_wallet = Util.truncar(disponible("BTC"));
		double usd_wallet = Util.truncar(disponible("USD"));
		double btc_infoBalanace = Util.truncar(infoBalance.getTotal_cripto());
		
		
		logger.info("btc_wallet: "+ btc_wallet);
		logger.info("btc_infoBalanace: "+ btc_infoBalanace);
		
		infoBalance.setTotal_usd(usd_wallet);
		
		if(btc_wallet != btc_infoBalanace)
		{										
			infoBalance.setTotal_cripto(btc_wallet);
		}	
	}
}
