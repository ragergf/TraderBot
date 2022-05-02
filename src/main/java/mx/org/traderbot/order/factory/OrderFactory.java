package mx.org.traderbot.order.factory;

import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Constantes;

public class OrderFactory {
	public Order newOrder(String symbol, String side, String type, String timeInForce )
	{
		Order order= new Order();
		order.setQuantity(Constantes.QUANTITY);
        order.setSymbol(symbol);
        order.setSide(side);
        order.setType(type);
        order.setTimeInForce(timeInForce);
		return order;
	}
}
