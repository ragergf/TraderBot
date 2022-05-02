package mx.org.traderbot.estrategias.model;

import java.awt.AWTException;
import java.awt.Robot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.activeorder.model.ActiveOrderModel;
import mx.org.traderbot.balance.model.BalanceModel;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.PSAR;
import mx.org.traderbot.order.model.OrderModel;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Util;

@Component("MouseBot")
@Scope("prototype")
public class MouseBot extends EstrategiaBotImpl{
	
	@Autowired
	CandleModel candleModel;
	
	@Autowired
	OrderModel orderModel;
	
	@Autowired
	Order order;
	
	@Autowired
	BalanceModel balanceModel;
	
	@Autowired
	ActiveOrderModel activeOrderModel;	
	
	PSAR psar = null;
	PSAR prevPsar = null;
	Order[] orders;
	int xCoord = 500;
	int yCoord = 650;

	
	public void run() {
		// TODO Auto-generated method stub
		try{			
			logger.info("Iniciando Hilo");
			logger.info("ROBOT");		   
			if(xCoord <= 1)
				xCoord = 650;

		    // Move the cursor
		    Robot robot = new Robot();
//		    do
//		    {
		    	robot.mouseMove(xCoord, yCoord);
		    	logger.info("xCoord" + xCoord);		    	
		    	xCoord-=10;
//		    }while(xCoord > 1);
		    
			logger.error("Terminando Hilo");
			imprime_info();
		    
		} catch (AWTException e) {
			e.printStackTrace();				
		}catch(Exception e)
		{
			logger.error(e);
			logger.error("Terminando Hilo con ERROR");
		}
	}
	
	public void imprime_info()
	{		
		double aux=0;
		String info =					
				  "\nX:\t\t" + xCoord
				+ "\nY:\t\t" + yCoord;						
		
		infoEstrategiaView.getTextArea_info().setText(info);		
	}

}
