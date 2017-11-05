package tusLibros;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cajero {
	
	private Carrito carrito;
	private Calendar fecha;
	private TarjetaDeCredito tarjeta;
	private Multiconjunto<Object,Integer> libroDeVentas;
	
	public static final String ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO = "No se puede	hacer checkout de un carrito vacio";
	
	public Cajero(Carrito carrito, Calendar fecha, TarjetaDeCredito tarjeta, Multiconjunto<Object,Integer> libroDeVentas){
		this.carrito = carrito;
		this.fecha = fecha;
		this.tarjeta = tarjeta;
		this.libroDeVentas = libroDeVentas;
	}
	
	public int checkOut(){
		int total = 0;
		if(carrito.esVacio()) throw new Error(ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO);
		Set<Object> productos = carrito.listar();
		for(Object producto : productos){
			Integer cantidad = carrito.cantidad(producto);
			Map<Object, Integer> catalogo = carrito.catalogo();
			total += catalogo.get(producto) * cantidad;
			libroDeVentas.agregar(producto, cantidad);
		}
		return total;
	}
	
	public Multiconjunto<Object,Integer> libroDeVentas(){
		return libroDeVentas;
	}

}
