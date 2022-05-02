package mx.org.traderbot.fibo.vo;

public class HiloBotVo {
	Class clase;
	String period;	
	
	public HiloBotVo(Class clase, String period)
	{
		this.clase = clase;
		this.period = period;
	}

	public Class getClase() {
		return clase;
	}

	public void setClase(Class clase) {
		this.clase = clase;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	
	
}
