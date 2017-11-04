package tusLibros;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cajero {
	
	private Carrito carrito;
	private Calendar fecha;
	private TarjetaDeCredito tarjeta;
	private Map<Object,Integer> libroDeVentas;
	
	public static final String ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO = "No se puede	hacer checkout de un carrito vacio";
	
	public Cajero(Carrito carrito, Calendar fecha, TarjetaDeCredito tarjeta, Map<Object,Integer> libroDeVentas){
		this.carrito = carrito;
		this.fecha = fecha;
		this.tarjeta = tarjeta;
		this.libroDeVentas = libroDeVentas;
	}
	
	public Integer checkOut(){
		if(carrito.esVacio()) throw new Error(ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO);
		return 0;
	}
	
	public Map<Object,Integer> libroDeVentas(){
		return libroDeVentas;
	}

}
