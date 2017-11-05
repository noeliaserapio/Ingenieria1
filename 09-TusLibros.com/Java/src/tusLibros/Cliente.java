package tusLibros;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {

	private int id;
	private String password;
	private Map<Object, Integer> catalogo;
	private List<Carrito> carritos=new ArrayList<Carrito>();
	private Map<Object,Integer> libroDeCompras = new HashMap<Object,Integer>();
	
	public Cliente(int id, String password,Map<Object, Integer> catalogo) {
		this.id = id;
		this.password = password;
		this.catalogo = catalogo;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cliente){
			return (id == ((Cliente) obj).getId());
		}else{
			return false;
		}
	}
	
	public void agregarNuevosCarritos(int cantidad) {
		for(int i=0;i<cantidad;i++){
			carritos.add(new Carrito(catalogo));
		}
	}
	
	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
	public Map<Object, Integer> getCatalogo() {
		return catalogo;
	}

	public List<Carrito> getCarritos() {
		return carritos;
	}
	
	public Map<Object, Integer> getLibroDeCompras() {
		return libroDeCompras;
	}

}
