package mx.org.traderbot.chart.candles.vo;

public class SMA {
	protected Candle candle;
	protected Double sma;
	
	public Candle getCandle() {
		return candle;
	}
	public void setCandle(Candle candle) {
		this.candle = candle;
	}
	public Double getSma() {
		return sma;
	}
	public void setSma(Double sma) {
		this.sma = sma;
	}
}
