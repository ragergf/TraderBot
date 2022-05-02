package mx.org.traderbot.chart.candles.vo;

public class RSI {
	Candle candle;
	double gain;
	double loss;
	double avgGain;
	double avgLoss;
	double rsi;			
	
	public RSI(Candle candle, double gain, double loss, double rsi) {
		super();
		this.candle = candle;
		this.gain = gain;
		this.loss = loss;
		this.rsi = rsi;
	}
	public Candle getCandle() {
		return candle;
	}
	public void setCandle(Candle candle) {
		this.candle = candle;
	}
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		this.gain = gain;
	}
	public double getLoss() {
		return loss;
	}
	public void setLoss(double loss) {
		this.loss = loss;
	}
	public double getRsi() {
		return rsi;
	}
	public void setRsi(double rsi) {
		this.rsi = rsi;
	}
	public double getAvgGain() {
		return avgGain;
	}
	public void setAvgGain(double avgGain) {
		this.avgGain = avgGain;
	}
	public double getAvgLoss() {
		return avgLoss;
	}
	public void setAvgLoss(double avgLoss) {
		this.avgLoss = avgLoss;
	}
	
}
