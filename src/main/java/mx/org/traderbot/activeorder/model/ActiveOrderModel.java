package mx.org.traderbot.activeorder.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.balance.vo.Balance;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.util.Constantes;

@Component
@Scope("prototype")
public class ActiveOrderModel {
	@Autowired
	DataService<Order> service;
	
	public Order getOneActiveOrder(String orderId, int wait) throws BotException, Exception
	{
		Order order = null;
		String params = orderId + "_" + ((wait>0)?("wait="+wait):"");
		order=service.getData(Constantes.ACTIVE_ONE_ORDER_SYMBOL_TYPE, Order.class, params);
		return order;
		
	}
	
	public Order[] getActiveOrders() throws BotException, Exception
	{
		Order[] activeOrder =null;
		Object array=service.getData(Constantes.ACTIVEORDERS_SYMBOL_TYPE, Order[].class, "symbol=btcusd");
		activeOrder = (Order[])array;	  
		
		return activeOrder;
	}
}
