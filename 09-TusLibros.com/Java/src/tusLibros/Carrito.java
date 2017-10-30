package tusLibros;

import java.util.List;
import java.util.Map;


public class Carrito {
	
	private  Multiconjunto<Object, Integer> productos = new Multiconjunto<Object, Integer>();
	private List<Object> catalogo;
	
	public static final String ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO = "No se puede	agregar un producto que no esta en el catalogo";
	public static final String ERROR_CANTIDAD_NO_PUEDE_SER_NEGATIVA_O_CERO = "La cantidad a agregar no puede ser negativa ni cero";
	
	public Carrito(List<Object> catalogo){
		this.catalogo = catalogo;
	}
	
	public boolean esVacio(){
		return productos.isEmpty();
	}
	
	public void agregar(Object producto, Integer cantidad){
		if(cantidad < 1) throw new Error(ERROR_CANTIDAD_NO_PUEDE_SER_NEGATIVA_O_CERO);
		if(catalogo.contains(producto)){
			productos.put(producto, cantidad);
		}else{
			throw new Error(ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO);
		}
	}
	
	public Integer cantidad(Object producto){
		return productos.cantidad(producto);
	}

	public String listar() {
		return productos.listar();
	}		
}
