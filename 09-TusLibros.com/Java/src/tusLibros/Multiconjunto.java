package tusLibros;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.lang.Integer;


public class Multiconjunto<E,Integer> extends TreeMap<E,Integer>{

	private  Map<E,Integer> elementos = new TreeMap<E,Integer>();
	
	public Integer put(E elemento, Integer cantidad) { 
		Integer viejoValor = null;
        if( !elementos.containsKey(elemento)){
        	elementos.put(elemento, cantidad);
        } else { 
        	viejoValor =  elementos.get(elemento);
			Integer sumaCantidad = ((Integer) new java.lang.Integer(((java.lang.Integer) viejoValor).intValue() + ((java.lang.Integer) cantidad).intValue()));
			elementos.put(elemento, sumaCantidad);
        }
        return viejoValor;
    }
	
	public boolean isEmpty() {
		return elementos.isEmpty();
	}
	
	public Integer cantidad(E elemento) {
		return elementos.get(elemento);
	}
	
	public String listar(){
		String res = "";
		Set<E> keys = elementos.keySet();
		for(E key : keys){
			res += key + "|" + elementos.get(key);
		}
		return res;
	}
	
}
