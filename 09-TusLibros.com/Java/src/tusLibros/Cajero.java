package tusLibros;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cajero {
	
	private Carrito carrito;
	private Calendar fecha;
	private TarjetaDeCredito tarjeta;
	private Map<Object,Integer> libroDeVentas = new HashMap<Object, Integer>();  // puede que sea multiconj
	
	public static final String ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO = "No se puede	hacer checkout de un carrito vacio";
	
	public Cajero(Carrito carrito, Calendar fecha, TarjetaDeCredito tarjeta){
		this.carrito = carrito;
		this.fecha = fecha;
		this.tarjeta = tarjeta;
	}
	
	public Integer checkOut(){
		if(carrito.esVacio()) throw new Error(ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO);
		return 0;
	}
	
	public Map<Object,Integer> libroDeVentas(){
		return libroDeVentas;
	}

}
