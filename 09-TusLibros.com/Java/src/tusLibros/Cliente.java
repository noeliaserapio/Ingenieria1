package tusLibros;


import java.util.HashMap;

import java.util.Map;

public class Cliente {

	private int id;
	private String password;
	private Map<Object, Double> catalogo;
	private Map<Long, Carrito> carritos = new HashMap<Long, Carrito>();//id, carrito
	private Multiconjunto<Object,Integer> libroDeCompras = new Multiconjunto<Object,Integer>();//producto, cantidad
	public static final String NO_SE_ENCUENTRA_CARRITO = "Este cliente no posee el carrito buscado";
	
	public Cliente(int id, String password,Map<Object, Double> catalogo) {
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
			agregarNuevoCarrito();
		}
	}
	
	public long agregarNuevoCarrito() {
		Carrito nuevoCarrito = new Carrito(catalogo);
		carritos.put( Carrito.getNumeroCarrito(), nuevoCarrito);
		return Carrito.getNumeroCarrito();
	}
	
	
	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
	public Map<Object, Double> getCatalogo() {
		return catalogo;
	}

	public Map<Long, Carrito> getCarritos() {
		return carritos;
	}
	
	public Multiconjunto<Object, Integer> getLibroDeCompras() {
		return libroDeCompras;
	}
	
	public long checkOutCarrito(long idCarrito, TarjetaDeCredito tarjCred) {	
		if(!carritos.containsKey(idCarrito)) throw new Error(NO_SE_ENCUENTRA_CARRITO);
		Cajero op = new Cajero(carritos.get(idCarrito), tarjCred,  libroDeCompras);
		return op.checkOut().getNumeroTransaccion();
	}
	
	

}
