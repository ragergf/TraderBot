package mx.org.traderbot.fibo.vo;

import org.apache.log4j.Logger;

import mx.org.traderbot.dao.RestfulDAO;
import mx.org.traderbot.order.factory.OrderFactory;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.util.Constantes;

public class Soporte {
	private final static Logger logger = Logger.getLogger(Soporte.class);
	Order ventaFit;
	Order compra;
	Punto punto;
	Order venta;
	Order compraFit;
	
	public Soporte(){}
	
	public Soporte(Punto punto)
	{
		this.punto = punto;
		OrderFactory factory = new OrderFactory();
		ventaFit = factory.newOrder(Constantes.SYMBOl_BTCUSD, Constantes.SELL, Constantes.LIMIT_TYPE, Constantes.TIME_IN_FORCE);
		compra = factory.newOrder(Constantes.SYMBOl_BTCUSD, Constantes.BUY, Constantes.LIMIT_TYPE, Constantes.TIME_IN_FORCE);
		venta = factory.newOrder(Constantes.SYMBOl_BTCUSD, Constantes.SELL, Constantes.LIMIT_TYPE, Constantes.TIME_IN_FORCE);
		compraFit = factory.newOrder(Constantes.SYMBOl_BTCUSD, Constantes.BUY, Constantes.LIMIT_TYPE, Constantes.TIME_IN_FORCE);
		calculaOrdenes();
		
	}
	
	public void calculaOrdenes()
	{
		double puntoSoporte = punto.getValor();
		
		double valorOrden = 0;
		
		valorOrden = puntoSoporte + Constantes.DIFERENCIA_COMPRA + Constantes.DIFERENCIA_VENTA_FIT;
		logger.info("ventaFit: " + valorOrden);
		ventaFit.setPrice(valorOrden + "");
		
		valorOrden = puntoSoporte + Constantes.DIFERENCIA_COMPRA;
		logger.info("compra: " + valorOrden);
		compra.setPrice(valorOrden + "");
		
		valorOrden = puntoSoporte - Constantes.DIFERENCIA_VENTA;
		logger.info("venta: " + valorOrden);
		venta.setPrice(valorOrden + "");
						
		valorOrden = puntoSoporte - (Constantes.DIFERENCIA_VENTA + Constantes.DIFERENCIA_COMPRA_FIT) ;
		logger.info("compraFit: " + valorOrden);
		compraFit.setPrice(valorOrden + "");
		
		
		
	}

	public Order getVentaFit() {
		return ventaFit;
	}

	public void setVentaFit(Order ventaFit) {
		this.ventaFit = ventaFit;
	}

	public Order getCompra() {
		return compra;
	}

	public void setCompra(Order compra) {
		this.compra = compra;
	}

	public Punto getPunto() {
		return punto;
	}

	public void setPunto(Punto punto) {
		this.punto = punto;
		calculaOrdenes();
	}

	public Order getVenta() {
		return venta;
	}

	public void setVenta(Order venta) {
		this.venta = venta;
	}

	public Order getCompraFit() {
		return compraFit;
	}

	public void setCompraFit(Order compraFit) {
		this.compraFit = compraFit;
	}
	
	
}
