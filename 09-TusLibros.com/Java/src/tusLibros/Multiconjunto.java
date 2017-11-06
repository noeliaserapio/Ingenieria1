package tusLibros;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Multiconjunto<E,Integer> {

	private  Map<E,Integer> elementos = new HashMap<E,Integer>();
	
	public void agregar(E elemento, java.lang.Integer cantidad) { 
        if( !elementos.containsKey(elemento)){
        	elementos.put(elemento, (Integer) cantidad);
        } else { 
        	Integer viejoValor =  elementos.get(elemento);
			Integer sumaCantidad = ((Integer) new java.lang.Integer(((java.lang.Integer) viejoValor).intValue() + ((java.lang.Integer) cantidad).intValue()));
			elementos.put(elemento, sumaCantidad);
        }
    }
	
	public void agregarAll(Multiconjunto<E,Integer> nuevosElementos) { 
		for(E elem : nuevosElementos.claves()){
			agregar(elem,new java.lang.Integer(nuevosElementos.cantidad(elem)));
		}
    }
	
	public void clear(){
		elementos.clear();
	}
	
	public boolean esVacio() {
		return elementos.isEmpty();
	}
	
	public int cantidad(E elemento) {
		if(elementos.containsKey(elemento)){
			return (int) elementos.get(elemento);
		}else{
			return 0;
		}

	}
	
	public Set<E> claves(){
		return elementos.keySet();
	}
	
}
