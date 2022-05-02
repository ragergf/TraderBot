package mx.org.traderbot.estrategias.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import mx.org.traderbot.estrategias.vo.InfoBalance;
import mx.org.traderbot.estrategias.vo.InfoEstrategiaView;

public abstract class EstrategiaBotImpl implements EstrategiaBot {
	
	int id;
	String period;
	int sizeEmaRapida;
	int sizeEmaLenta;
	
	String nombreEstrategia;
	int tiempo;
	
	@Autowired
	InfoEstrategiaView infoEstrategiaView;
	
	@Autowired
	InfoBalance infoBalance;
	
	Logger logger;
	

	
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
		
		logger = Logger.getLogger("Estrategia_"+id);
	}

	
	public void setPeriod(String period) {
		// TODO Auto-generated method stub
		this.period = period;
	}

	
	public void initLog(Logger log) {
		// TODO Auto-generated method stub
		logger = log;
	}
	
	
	public void setSizeEmaRapida(int sizeEmaRapida) {
		// TODO Auto-generated method stub
		this.sizeEmaRapida = sizeEmaRapida;
	}
	
	
	public void setSizeEmaLenta(int sizeEmaLenta) {
		// TODO Auto-generated method stub
		this.sizeEmaLenta = sizeEmaLenta;
	}
	
	public InfoBalance getBalance() {
		// TODO Auto-generated method stub
		return infoBalance;
	}
	
	public void setNombreEstrategia(String nombreEstrategia) {
		// TODO Auto-generated method stub
		this.nombreEstrategia = nombreEstrategia;
		this.infoBalance.setNombreEstrategia(nombreEstrategia);
	}
	
	
	public String getNombreEstrategia() {
		// TODO Auto-generated method stub
		return this.nombreEstrategia;
	}
	
	
	public void setTiempo(int tiempo) {
		// TODO Auto-generated method stub
		this.tiempo = tiempo;
	}
	
}
