package mx.org.traderbot.order.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Order {
	private String id;
	private String clientOrderId;
	private String symbol;
	private String side;
	private String status;
	private String type;
	private String timeInForce;
	private String quantity;
	private String price;
	private String cumQuantity;
	private String createdAt;
	private String updatedAt;
	private String stopPrice;
	private String expireTime;
	
	private TradesReport [] tradesReport;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientOrderId() {
		return clientOrderId;
	}
	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTimeInForce() {
		return timeInForce;
	}
	public void setTimeInForce(String timeInForce) {
		this.timeInForce = timeInForce;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCumQuantity() {
		return cumQuantity;
	}
	public void setCumQuantity(String cumQuantity) {
		this.cumQuantity = cumQuantity;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStopPrice() {
		return stopPrice;
	}
	public void setStopPrice(String stopPrice) {
		this.stopPrice = stopPrice;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}		
	
	
	
	public TradesReport[] getTradesReport() {
		return tradesReport;
	}
	public void setTradesReport(TradesReport[] tradesReport) {
		this.tradesReport = tradesReport;
	}
	public String toString()
	{
		return "id : "+id
				+"\nclientOrderId : "+clientOrderId
				+"\nsymbol : "+symbol
				+"\nside : "+side
				+"\nstatus : "+status
				+"\ntype : "+type
				+"\ntimeInForce : "+timeInForce
				+"\nquantity : "+quantity
				+"\nprice : "+price
				+"\ncumQuantity : "+cumQuantity
				+"\ncreatedAt : "+createdAt
				+"\nupdatedAt : "+updatedAt
				+"\nstopPrice : "+stopPrice
				+"\nexpireTime : "+expireTime;
	}
}
