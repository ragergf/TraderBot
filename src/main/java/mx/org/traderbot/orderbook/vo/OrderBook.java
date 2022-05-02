package mx.org.traderbot.orderbook.vo;

import java.util.List;

public class OrderBook {
	private List<OrderResume> ask;
	private List<OrderResume> bid;
	public List<OrderResume> getAsk() {
		return ask;
	}
	public void setAsk(List<OrderResume> ask) {
		this.ask = ask;
	}
	public List<OrderResume> getBid() {
		return bid;
	}
	public void setBid(List<OrderResume> bid) {
		this.bid = bid;
	}
		
}
