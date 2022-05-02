package mx.org.traderbot.estrategias.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.activeorder.model.ActiveOrderModel;
import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.order.model.OrderModel;
import mx.org.traderbot.order.vo.Order;

@Component("MonitorOrden")
@Scope("prototype")
public class MonitorOrden extends EstrategiaBotImpl{
	
	@Autowired
	OrderModel orderModel;

	@Autowired
	ActiveOrderModel activeOrderModel;

	
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			monitorOrden();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void monitorOrden() throws BotException, Exception
	{
		Order order=null;
//		Order[] orders=activeOrderModel.getActiveOrders();
		Order[] orders=orderModel.cancelarOrdenes();
		String info="";
		logger.info("despues de cancelar");
		if(order != null)
		{
			logger.info("NO hay");
		}else
		{
			logger.info("order.lenght = " + orders.length);
		}
		
//		if(orders!= null)			
//		{
//			for(Order aux : orders)
//			{
//				order = activeOrderModel.getOneActiveOrder(aux.getClientOrderId(), 15000);			
//				info += "\n\n" + order.toString();
//			}
//		}
//		
//		if(infoEstrategiaView.getComboBox_estrategia().getSelectedIndex() == id)
//		{								
//			infoEstrategiaView.getTextArea_info().setText(info);
//		}		
	}

}
