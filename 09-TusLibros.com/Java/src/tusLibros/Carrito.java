package tusLibros;

import java.util.ArrayList;
import java.util.List;


public class Carrito {
	
	private List<Object> productos = new ArrayList<Object>();
	private List<Object> catalogo;
	
	public static final String ERROR_AL_AGREGAR_PRODUCTO = "No se puede	agregar un producto que no esta en el catalogo";
	
	public Carrito(List<Object> catalogo){
		this.catalogo = catalogo;
	}
	
	public boolean esVacio(){
		return productos.isEmpty();
	}
	
	public void agregar(Object producto){
		if(catalogo.contains(producto)){
			productos.add(producto);
		}else{
			throw new Error(ERROR_AL_AGREGAR_PRODUCTO);
		}
	}
	
}
