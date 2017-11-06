package tusLibros;

import java.util.List;
import java.util.Map;

public class InterfaceRest {
	
	private AutenticadorCliente auten;
	public static final String NO_SE_ENCUENTRA_CLIENTE = "No se encuentra el cliente buscado";
	public static final String CONTRASENIA_INVALIDA = "La contraseña es invalida";
	public static final String NO_SE_ENCUENTRA_CARRITO = "No se encuentra el carrito buscado";
	
	public InterfaceRest(Map<Object, Double> catalogo) {
		auten = new AutenticadorCliente(catalogo);
	}
	
	public List<Cliente> getClientesCreados() {
		return auten.getClientesCreados();
	}
	
	public void crearCliente(int id, String password){
		auten.crearCliente(id, password);
	}
	
	public long crearCarrito(int idCliente, String password){
		for(Cliente cl : auten.getClientesCreados()){
			if(cl.getId() == idCliente){
				if(cl.getPassword().equals(password)){
					return cl.agregarNuevoCarrito();
				}else{
					throw new Error(CONTRASENIA_INVALIDA);
				}		
			}
		}
		throw new Error(NO_SE_ENCUENTRA_CLIENTE);
	}
	
	public void agregarACarrito(long idCarrito, Object producto,int cantidad){
		for(Cliente cl : auten.getClientesCreados()){
			for(Long idCarr : cl.getCarritos().keySet()){
				if(idCarr == idCarrito){
					cl.getCarritos().get(idCarr).agregar(producto, cantidad);
					return;
				}
			}
		}
		throw new Error(NO_SE_ENCUENTRA_CARRITO);
	}
	
	public Multiconjunto<Object, Integer> listarCarrito(long idCarrito){
		for(Cliente cl : auten.getClientesCreados()){
			for(Long idCarr : cl.getCarritos().keySet()){
				if(idCarr == idCarrito){
					return cl.getCarritos().get(idCarr).getProductos();
				}
			}
		}
		throw new Error(NO_SE_ENCUENTRA_CARRITO);
	}
	
	public Multiconjunto<Object, Integer> listarComprasCliente(int idCliente, String password){
		for(Cliente cl : auten.getClientesCreados()){
			if(cl.getId() == idCliente){
				if(cl.getPassword().equals(password)){
					return cl.getLibroDeCompras();
				}else{
					throw new Error(CONTRASENIA_INVALIDA);
				}		
			}
		}
		throw new Error(NO_SE_ENCUENTRA_CLIENTE);
	}
	
	public long checkOutCarrito(long idCarrito, String numeroTarjeta, String fechaExpiracion, String dueñoTarjeta){
		for(Cliente cl : auten.getClientesCreados()){
			for(Long idCarr : cl.getCarritos().keySet()){
				if(idCarr == idCarrito){
					TarjetaDeCredito tarjCred = new TarjetaDeCredito(numeroTarjeta,fechaExpiracion,dueñoTarjeta);
					return cl.checkOutCarrito(idCarrito, tarjCred);
				}
			}
		}
		throw new Error(NO_SE_ENCUENTRA_CARRITO);
	}
	
	
	
	
	

}
