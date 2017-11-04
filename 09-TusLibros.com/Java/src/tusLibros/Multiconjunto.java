package tusLibros;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.lang.Integer;


public class Multiconjunto<E,Integer> {

	private  Map<E,Integer> elementos = new HashMap<E,Integer>();
	
	public void agregar(E elemento, Integer cantidad) { 
        if( !elementos.containsKey(elemento)){
        	elementos.put(elemento, cantidad);
        } else { 
        	Integer viejoValor =  elementos.get(elemento);
			Integer sumaCantidad = ((Integer) new java.lang.Integer(((java.lang.Integer) viejoValor).intValue() + ((java.lang.Integer) cantidad).intValue()));
			elementos.put(elemento, sumaCantidad);
        }
    }
	
	public boolean esVacio() {
		return elementos.isEmpty();
	}
	
	public Integer cantidad(E elemento) {
		return elementos.get(elemento);
	}
	
	public Set<E> claves(){
		return elementos.keySet();
	}
	
}
