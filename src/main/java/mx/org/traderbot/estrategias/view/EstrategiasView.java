package mx.org.traderbot.estrategias.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import mx.org.traderbot.estrategias.model.EstrategiaBot;
import mx.org.traderbot.estrategias.vo.EstrategiaVo;
import mx.org.traderbot.estrategias.vo.InfoEstrategiaView;
import mx.org.traderbot.spring.config.AppConfig;

public class EstrategiasView {
	
	
	public static void main(String... args)
	{
		TraderTestView traderTestView;
	    InfoEstrategiaView infoEstrategiaView;
	    
		AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
		infoEstrategiaView = (InfoEstrategiaView)context.getBean("InfoEstrategiaView");
		traderTestView = (TraderTestView)context.getBean("TraderTestView");
		
		traderTestView.initEstrategias(context);		
		traderTestView.setVisible(true);		
	}
}
 