package mx.org.traderbot.estrategias.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.fibo.vo.Punto;
import mx.org.traderbot.util.Constantes;

@Component
@Scope("prototype")
public class InfoBalance implements Comparable{
	//Actual
	
	String nombreEstrategia;
	
	boolean comenzo = false;
	
	boolean señal_1;
	boolean señal_2;
	
	double stopProfit=0;
	
	double total_usd = 1000;
	double total_cripto=0;
	boolean start= true;
	double precio;
	double fee;
	double total;
	double total_final;
	double precio_compra;	
	double precio_venta=0;
	int numTransacciones;			
	double aprox_final;
	
	boolean postura_venta;
	boolean sobre_compra;
	
	double stop;
	
	boolean puedeComprar;
	boolean puedeVender;
	
	int eventosXBajada = 0;
	int eventosXSubida = 0;
	
	double ultimoPSARRising = 0;
	double ultimoPSARFalling = 0;
	
	boolean ventaLenta;
	
	int contador=0;
	
	public String toString()
	{
		return   "precio:\t\t"+precio
				+"\nstop:\t\t"+stop
				+"\nseñal_1:\t\t"+señal_1
				+"\nseñal_2:\t\t"+señal_2
				+"\npostura_venta:\t\t"+postura_venta				
				+"\nprecio_compra:\t\t"+precio_compra
				+"\nprecio_venta:\t\t"+precio_venta
				+"\ntotal_usd:\t\t"+total_usd
				+"\ntotal_cripto:\t\t"+total_cripto				
				+"\nfee:\t\t"+fee
				+"\ntotal:\t\t"+total
				+"\ntotal_final:\t\t"+total_final
				+"\npuedeComprar:\t\t"+puedeComprar
				+"\npuedeVender:\t\t"+puedeVender
				+"\ncontador:\t\t"+contador
				+"\nstart:\t\t"+start
				+"\ncomenzo:\t\t"+comenzo
				+"\nnumTransacciones:\t\t"+numTransacciones;
	}
	
	public double getTotal_usd() {
		return total_usd;
	}
	public void setTotal_usd(double total_usd) {
		this.total_usd = total_usd;
	}
	public double getTotal_cripto() {
		return total_cripto;
	}
	public void setTotal_cripto(double total_cripto) {
		this.total_cripto = total_cripto;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getTotal_final() {
		return total_final;
	}
	public void setTotal_final(double total_final) {
		this.total_final = total_final;
	}	
	
	public double getPrecio_compra() {
		return precio_compra;
	}

	public void setPrecio_compra(double precio_compra) {
		this.precio_compra = precio_compra;
	}

	public int getNumTransacciones() {
		return numTransacciones;
	}

	public void setNumTransacciones(int numTransacciones) {
		this.numTransacciones = numTransacciones;
	}	
	
	public double getAprox_final() {
		return total_usd + (total_cripto * precio);
	}

	public void setAprox_final(double aprox_final) {
		this.aprox_final = aprox_final;
	}
	
	public String getNombreEstrategia() {
		return nombreEstrategia;
	}

	public void setNombreEstrategia(String nombreEstrategia) {
		this.nombreEstrategia = nombreEstrategia;
	}	

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public boolean isPuedeComprar() {
		return puedeComprar;
	}

	public void setPuedeComprar(boolean puedeComprar) {
		this.puedeComprar = puedeComprar;
	}

	public boolean isPuedeVender() {
		return puedeVender;
	}

	public void setPuedeVender(boolean puedeVender) {
		this.puedeVender = puedeVender;
	}		

	public boolean isComenzo() {
		return comenzo;
	}

	public void setComenzo(boolean comenzo) {
		this.comenzo = comenzo;
	}

	public double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(double precio_venta) {
		this.precio_venta = precio_venta;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isVentaLenta() {
		return ventaLenta;
	}

	public void setVentaLenta(boolean ventaLenta) {
		this.ventaLenta = ventaLenta;
	}		

	public double getStop() {
		return stop;
	}

	public void setStop(double stop) {
		this.stop = stop;
	}

	public boolean isPostura_venta() {
		return postura_venta;
	}

	public void setPostura_venta(boolean postura_venta) {
		this.postura_venta = postura_venta;
	}		

	public boolean isSobre_compra() {
		return sobre_compra;
	}

	public void setSobre_compra(boolean sobre_compra) {
		this.sobre_compra = sobre_compra;
	}	

	public boolean isSeñal_1() {
		return señal_1;
	}

	public void setSeñal_1(boolean señal_1) {
		this.señal_1 = señal_1;
	}

	public boolean isSeñal_2() {
		return señal_2;
	}

	public void setSeñal_2(boolean señal_2) {
		this.señal_2 = señal_2;
	}
	
	

	public int getEventosXBajada() {
		return eventosXBajada;
	}

	public void setEventosXBajada(int eventosXBajada) {
		this.eventosXBajada = eventosXBajada;
	}

	public int getEventosXSubida() {
		return eventosXSubida;
	}

	public void setEventosXSubida(int eventosXSubida) {
		this.eventosXSubida = eventosXSubida;
	}

	public double getUltimoPSARRising() {
		return ultimoPSARRising;
	}

	public void setUltimoPSARRising(double ultimoPSARRising) {
		this.ultimoPSARRising = ultimoPSARRising;
	}

	public double getUltimoPSARFalling() {
		return ultimoPSARFalling;
	}

	public void setUltimoPSARFalling(double ultimoPSARFalling) {
		this.ultimoPSARFalling = ultimoPSARFalling;
	}
	

	public double getStopProfit() {
		return stopProfit;
	}

	public void setStopProfit(double stopProfit) {
		this.stopProfit = stopProfit;
	}

	public int compareTo(Object o) {
		Double compareElement = new Double(((InfoBalance)o).getAprox_final());
		Double Element = new Double(this.getAprox_final());
		return compareElement.compareTo(Element);
	}
}
