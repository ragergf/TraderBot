package mx.org.traderbot.chart.candles.vo;

public class PSAR_V2 {
	double sar;
	double ep;
	double af;
	Candle candle;
	double sar_man;
	boolean falling;
	
	public PSAR_V2()
	{
		
	}
		
	public PSAR_V2(double sar, double ep, double af, Candle candle, double sar_man, boolean falling) {
		super();
		this.sar = sar;
		this.ep = ep;
		this.af = af;
		this.candle = candle;
		this.sar_man = sar_man;
		this.falling = falling;
	}
	public double getSar() {
		return sar;
	}
	public void setSar(double sar) {
		this.sar = sar;
	}
	public double getEp() {
		return ep;
	}
	public void setEp(double ep) {
		this.ep = ep;
	}
	public double getAf() {
		return af;
	}
	public void setAf(double af) {
		this.af = af;
	}
	public Candle getCandle() {
		return candle;
	}
	public void setCandle(Candle candle) {
		this.candle = candle;
	}
	
	public double getSar_man() {
		return sar_man;
	}

	public void setSar_man(double sar_man) {
		this.sar_man = sar_man;
	}

	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	@Override
	public String toString() {
		return "PSAR_V2 [sar=" + sar + ", ep=" + ep + ", af=" + af + ", candle=" + candle + ", sar_man=" + sar_man
				+ ", falling=" + falling + "]";
	}
	
	
}
