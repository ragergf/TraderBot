package mx.org.traderbot.estrategias.vo;

public class EstrategiaVo {
	String bean;
	String periodo;
	int delayTimer;
	
	int ema_rapida;
	int ema_lenta;
	
	public EstrategiaVo(String bean, String periodo, int delayTimer)
	{
		this.bean = bean;
		this.periodo = periodo;
		this.delayTimer = delayTimer;
	}
	
	public EstrategiaVo(String bean, String periodo, int delayTimer, int ema_rapida, int ema_lenta)
	{
		this.bean = bean;
		this.periodo = periodo;
		this.delayTimer = delayTimer;
		this.ema_rapida = ema_rapida;
		this.ema_lenta = ema_lenta;
		
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public int getDelayTimer() {
		return delayTimer;
	}

	public void setDelayTimer(int delayTimer) {
		this.delayTimer = delayTimer;
	}

	public int getEma_rapida() {
		return ema_rapida;
	}

	public void setEma_rapida(int ema_rapida) {
		this.ema_rapida = ema_rapida;
	}

	public int getEma_lenta() {
		return ema_lenta;
	}

	public void setEma_lenta(int ema_lenta) {
		this.ema_lenta = ema_lenta;
	}
	
	
}
