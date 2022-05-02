package mx.org.traderbot.chart.candles.vo;

public class EMA extends SMA{
	private Double ema;
	
	public EMA(SMA sma)
	{
		this.candle = sma.getCandle();
		this.sma = sma.getSma();
		this.ema = sma.getSma();
	}
	
	public EMA(Candle candle)
	{		
		this.candle = new Candle();
		this.candle.setClose(candle.getClose());
		this.candle.setMax(candle.getMax());
		this.candle.setMin(candle.getMin());
		this.candle.setOpen(candle.getOpen());
		this.candle.setTimestamp(candle.getTimestamp());
		this.candle.setVolume(candle.getVolume());
		this.candle.setVolumeQuote(candle.getVolumeQuote());
		
		this.sma = new Double(0);
		this.ema = new Double(0);
	}

	public Double getEma() {
		return ema;
	}

	public void setEma(Double ema) {
		this.ema = ema;
	}
}
