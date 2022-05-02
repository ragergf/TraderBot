package mx.org.traderbot.chart.candles.vo;

import java.sql.Timestamp;
import java.util.Date;

public class Candle {
	private Timestamp timestamp;
	private double open;
	private double close;
	private double min;
	private double max;
	private double volume;
	private double volumeQuote;
	
	public Candle()
	{}
	
	public Candle(double valor, Timestamp timestamp)
	{
		this.close = valor;
		this.timestamp = timestamp;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getVolumeQuote() {
		return volumeQuote;
	}
	public void setVolumeQuote(double volumeQuote) {
		this.volumeQuote = volumeQuote;
	}		
	
	
}
