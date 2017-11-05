package tusLibros;

import java.util.Map;
import java.util.Set;


public class Carrito {
	
	private static int numeroCarrito =1;
	private  Multiconjunto<Object, Integer> productos = new Multiconjunto<Object, Integer>();

	private Map<Object, Integer> catalogo;
	
	public static final String ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO = "No se puede	agregar un producto que no esta en el catalogo";
	public static final String ERROR_CANTIDAD_NO_PUEDE_SER_NEGATIVA_O_CERO = "La cantidad a agregar no puede ser negativa ni cero";
	
	public Carrito(Map<Object, Integer>  catalogo){
		numeroCarrito++;
		this.catalogo = catalogo;
	}
	
	public boolean esVacio(){
		return productos.esVacio();
	}
	
	public static int getNumeroCarrito() {
		return numeroCarrito;
	}

	public void agregar(Object producto, Integer cantidad){
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
	
	public Map<Object, Integer> catalogo(){
		return catalogo;
	}

	public Multiconjunto<Object, Integer> getProductos() {
		return productos;
	}
	

	
}