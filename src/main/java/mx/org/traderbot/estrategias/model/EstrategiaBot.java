package mx.org.traderbot.estrategias.model;

import org.apache.log4j.Logger;

import mx.org.traderbot.estrategias.vo.InfoBalance;

public interface EstrategiaBot {
	public void run();
	public void setId(int id);
	public void setPeriod(String period);
	public void setSizeEmaRapida(int size);
	public void setSizeEmaLenta(int size);
	public void setNombreEstrategia(String  nombreEstrategia);
	public String getNombreEstrategia();
	public void initLog(Logger log);	
	public void setTiempo(int tiempo);
	
	public InfoBalance getBalance();
	
}
