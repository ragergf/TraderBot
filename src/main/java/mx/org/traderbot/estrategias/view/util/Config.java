package mx.org.traderbot.estrategias.view.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.org.traderbot.estrategias.vo.EstrategiaVo;

public class Config {
//	public final static Logger [] loggers = {
//			Logger.getLogger("Estrategia_1"),
//			Logger.getLogger("Estrategia_2"),
//			Logger.getLogger("Estrategia_3"),
//			Logger.getLogger("Estrategia_4"),
//			Logger.getLogger("Estrategia_5"),
//			Logger.getLogger("Estrategia_6"),
//			Logger.getLogger("Estrategia_7"),
//			Logger.getLogger("Estrategia_8"),
//			Logger.getLogger("Estrategia_9"),
//			Logger.getLogger("Estrategia_10")
//	};


	public static List<EstrategiaVo> getConfiguracionEstrategias()
	{
		List<EstrategiaVo> estrategiasVo;
		estrategiasVo = new ArrayList<EstrategiaVo>();
//    	estrategiasVo.add(new EstrategiaVo("Scalping", "M1", 1000));
//    	estrategiasVo.add(new EstrategiaVo("StopProfitLoss", "M15", 4000));
//		estrategiasVo.add(new EstrategiaVo("ScalpingEMARapidaBaja", "M15", 4000));
//    	estrategiasVo.add(new EstrategiaVo("ScalpingEMARapida", "M15", 10000));
//    	estrategiasVo.add(new EstrategiaVo("ScalpingEMARapida", "M30", 10000));
//    	estrategiasVo.add(new EstrategiaVo("Scalping", "M15", 4000));
//    	estrategiasVo.add(new EstrategiaVo("StopVolatil", "M15", 5000));
//    	estrategiasVo.add(new EstrategiaVo("MonitorOrden", "M15", 4000));		
		
//		estrategiasVo.add(new EstrategiaVo("ScalpingEMALimite", "M3", 5000, 13, 26));
//		estrategiasVo.add(new EstrategiaVo("ScalpingEMALimite2", "M3", 15000, 13, 26));
//		estrategiasVo.add(new EstrategiaVo("ScalpingPSAR1", "M1", 17000, 9, 21));
//		estrategiasVo.add(new EstrategiaVo("ScalpingPSAR1", "M3", 16000, 9, 21));
//		estrategiasVo.add(new EstrategiaVo("ScalpingPSAR1", "M5", 25000, 9, 21));
//		estrategiasVo.add(new EstrategiaVo("ScalpingPSAR3", "M5", 15000, 9, 21));
		estrategiasVo.add(new EstrategiaVo("MouseBot", "H1", 10000, 9, 21));
		estrategiasVo.add(new EstrategiaVo("ScalpingPSAR4", "H1", 12000, 9, 21));
		estrategiasVo.add(new EstrategiaVo("ScalpingPSAR5", "H1", 12000, 9, 21));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMALimite3", "M15", 20000, 13, 26));		
//		ScalpingPSAR1
//		estrategiasVo.add(new EstrategiaVo("ScalpingCheve", "M60", 20000, 9, 26));
//		estrategiasVo.add(new EstrategiaVo("MonitorOrden", "M1", 10000, 13, 26));


		
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 10000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 15000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 20000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 25000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 30000, 9, 26));
		
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 10000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 15000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 20000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 25000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 30000, 10, 26));
		
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 10000, 13, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 15000, 13, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 20000, 13, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 25000, 13, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M1", 30000, 13, 26));
		
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 10000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 15000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 20000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 25000, 9, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 30000, 9, 26));
		
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 10000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 15000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 20000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 25000, 10, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M3", 30000, 10, 26));
		
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M15", 10000, 26, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M15", 15000, 26, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M15", 20000, 26, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M15", 25000, 26, 26));
		estrategiasVo.add(new EstrategiaVo("ScalpingEMAs", "M15", 30000, 26, 26));
		
		
		estrategiasVo.add(new EstrategiaVo("Resumen", "M1", 5000));
		
    	return estrategiasVo;
	}
}
