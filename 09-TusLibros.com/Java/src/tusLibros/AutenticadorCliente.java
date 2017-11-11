package tusLibros;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutenticadorCliente {
	
	public static final String ERROR_ID_CLIENTE_UTILIZADA = "Ese Id ya esta siendo utilizado";
	private List<Cliente> clientesCreados = new ArrayList<Cliente>();
	private Map<Object, Double> catalogo;
	private MerchantProcessor merch;
	public AutenticadorCliente(Map<Object, Double> catalogo, MerchantProcessor merch) {
		this.catalogo = catalogo;
		this.merch = merch;
	}
	public void crearCliente(int id, String password){
		for(Cliente cl : clientesCreados){
			if(cl.getId() == id){
				throw new Error(ERROR_ID_CLIENTE_UTILIZADA);
			}
		}
		Cliente nuevo = new Cliente(id, password,catalogo,merch);
		clientesCreados.add(nuevo);
	}
	public List<Cliente> getClientesCreados() {
		return clientesCreados;
	}

}
