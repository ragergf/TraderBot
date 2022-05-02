package mx.org.traderbot.fibo.vo;

public class Punto  implements Comparable{
	double valor;
	
	public Punto()
	{
	}
	
	public Punto(double valor)
	{
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	public int compareTo(Object o) {
		Double compareElement = new Double(((Punto)o).getValor());
		Double Element = new Double(this.valor);
		return Element.compareTo(compareElement);
	}
}
