package mx.org.traderbot.chart.candles.vo;

public class PSAR {
	
	double sar;
	double ep;
	double af;
	boolean falling;
	
	double max;
	double min;
	
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
	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	@Override
	public String toString() {
		return "PSAR [sar=" + sar + ", ep=" + ep + ", af=" + af + ", falling=" + falling + ", max=" + max + ", min="
				+ min + "]";
	}
	
}
