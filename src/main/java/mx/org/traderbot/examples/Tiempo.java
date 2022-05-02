package mx.org.traderbot.examples;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tiempo {
	public static void main(String ...args)
	{
		Date fechaInicio = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.println("Hora y fecha: "+hourdateFormat.format(fechaInicio));
		
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date fechaFin = new Date();
		hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.println("Hora y fecha: "+hourdateFormat.format(fechaFin));
		long tiempoInicial=fechaInicio.getTime();
		long tiempoFinal=fechaFin.getTime();
		System.out.println("tiempoInicial: " + tiempoInicial);
		System.out.println("tiempoFinal: " + tiempoFinal);
		long resta=tiempoFinal - tiempoInicial;
		//el metodo getTime te devuelve en mili segundos para saberlo en mins debes hacer
		resta=resta;
		
		System.out.println("resta: " + resta);
		
		String cadena = "hola";
		System.out.println();
	}
}
