package tusLibros;

import java.util.Map;
import java.util.Set;


public class Carrito {
	
	private static long numeroCarrito =1;
	public static final long TIEMPO_PERMITIDO_INACTIVIDAD_CARRITO_MILLIS = 1800000;
	public static final String ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO = "No se puede	agregar un producto que no esta en el catalogo";
	public static final String ERROR_CANTIDAD_NO_PUEDE_SER_NEGATIVA_O_CERO = "La cantidad a agregar no puede ser negativa ni cero";
	public static final String ERROR_NO_SE_PUEDE_SEGUIR_USANDO_EL_CARRITO = "El carrito deja de ser valido debido na que supera el tiempo de inactividad";
	public static final String ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO = "No se puede	hacer checkout de un carrito vacio";

	
	
	private  Multiconjunto<Object, Integer> productos = new Multiconjunto<Object, Integer>();
	private Map<Object, Double> catalogo;
	private long ultimoAccesoCarrito;
	
	public Carrito(Map<Object, Double>  catalogo){
		numeroCarrito++;
		this.catalogo = catalogo;
		ultimoAccesoCarrito = System.currentTimeMillis();
	}
	
	public boolean esVacio(){
		return productos.esVacio();
	}
	
	public void vaciar(){
		productos.clear();
	}
	
	public void researUltimoAcceso(){
		if(System.currentTimeMillis() - ultimoAccesoCarrito <= TIEMPO_PERMITIDO_INACTIVIDAD_CARRITO_MILLIS){
			ultimoAccesoCarrito = System.currentTimeMillis();
		}else{
			throw new Error(ERROR_NO_SE_PUEDE_SEGUIR_USANDO_EL_CARRITO);
		}
	}
	
	public static long getNumeroCarrito() {
		return numeroCarrito;
	}

	public void agregar(Object producto, Integer cantidad){
		researUltimoAcceso();
		if(cantidad < 1) throw new Error(ERROR_CANTIDAD_NO_PUEDE_SER_NEGATIVA_O_CERO);
		if(catalogo.containsKey(producto)){
			productos.agregar(producto, cantidad);
		}else{
			throw new Error(ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO);
		}
	}
	
	public Integer cantidad(Object producto){
		return productos.cantidad(producto);
	}

	public Set<Object> listar() {
		return productos.claves();
	}	
	
	public Map<Object, Double> catalogo(){
		return catalogo;
	}

	public Multiconjunto<Object, Integer> getProductos() {
		return productos;
	}
	
	
	public int checkOut(){
		researUltimoAcceso();
		int total = 0;
		if(this.esVacio()) throw new Error(ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO);
		Set<Object> productos = this.listar();
		for(Object producto : productos){
			Integer cantidad = this.cantidad(producto);
			total += catalogo.get(producto) * cantidad;
		}
		return total;
	}

	

	

	
}