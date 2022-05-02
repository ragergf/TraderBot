package mx.org.traderbot.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import mx.org.traderbot.balance.vo.Balance;
import mx.org.traderbot.estrategias.vo.InfoEstrategiaView;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.spring.config.AppConfig;
import mx.org.traderbot.util.Constantes;

@Component
@Scope("prototype")
public class TestAPI {
	
	@Autowired
	DataService<Balance> balanceService;
	
	public static void main(String ...args) throws BotException, Exception
	{		
		AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
		TestAPI test = (TestAPI)context.getBean(TestAPI.class);
		test.obtenBalance();
	}
	
	public void obtenBalance() throws BotException, Exception
	{
		balanceService = new DataService<Balance>();
		
		Object obj = balanceService.getData(Constantes.BALANCE_TYPE, Balance[].class, "");
		
		Balance [] balances = (Balance [])obj;
		
		for(Balance balance: balances)
		{			
			System.out.println(balance.getCurrency());
			System.out.println(balance.getAvailable());
		}
	}
}
