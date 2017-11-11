package tusLibros;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TusLibrosTest {

	private MerchantProcessor obtenerMerch(){
		
		List<TarjetaDeCredito> listTarjRobadas = new ArrayList<TarjetaDeCredito>();
		listTarjRobadas.add(new TarjetaDeCredito("1234567890123456",  "052020", "GARCIA JOSE"));
		listTarjRobadas.add(new TarjetaDeCredito("1234567890123158",  "052021", "LOPEZ MARIANO"));
		listTarjRobadas.add(new TarjetaDeCredito("1234567890123425",  "052022", "GARCIA MARIANO"));
		Calendar hoy = new GregorianCalendar();

		listTarjRobadas.add(new TarjetaDeCredito("1234567890123459", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE"));
		
		TarjetaDeCredito tarj1 = new TarjetaDeCredito("1234567890123456",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JULIAN");
		TarjetaDeCredito tarj2 = new TarjetaDeCredito("1234567890123148",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ PEDRO");
		TarjetaDeCredito tarj3 = new TarjetaDeCredito("1234567890123149",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JUAN");
		TarjetaDeCredito tarj4 = new TarjetaDeCredito("1234567890123150",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ PABLO");
		
		TarjetaDeCredito tarj5 = new TarjetaDeCredito("1234567890123450", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
		
		Cuenta c1 = new Cuenta(11000.0,tarj1);
		Cuenta c2 = new Cuenta(0.0,tarj2);
		Cuenta c3 = new Cuenta(500.0,tarj3);
		Cuenta c4 = new Cuenta(500.0,tarj4);
		Cuenta c5 = new Cuenta(25000.0,tarj5);
		
		List<Cuenta> listCuentas = new ArrayList<Cuenta>();
		listCuentas.add(c1);
		listCuentas.add(c2);
		listCuentas.add(c3);
		listCuentas.add(c4);
		listCuentas.add(c5);
		return new MerchantSimulator(listTarjRobadas,listCuentas);
		
	}
	
	

	
	@Test
	public void test01CuandoSeCreaUnCarritoEstaVacio(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);	
		assertTrue(carrito.esVacio());
	}
	
	@Test
	public void test02CuandoSeAgregaUnProductoAlCarritoNoEstaVacioYContieneElemento(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		catalogo.put(elem, 250.0);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 1);
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
	}
	
	@Test
	public void test03NoSePuedeAgregaUnProductoQueNoEsDeLaEditorialAlCarrito(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		Carrito carrito = new Carrito(catalogo);	
	
		try {
			carrito.agregar(elem, 1);
			fail();
		} catch (Error e){
			assertEquals(Carrito.ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO,e.getMessage());
			assertTrue(catalogo.isEmpty());
			assertTrue(carrito.esVacio());
		}
		
	}
	
	@Test
	public void test04CuandoSeAgreganVariosProductosDeUnTipoAlCarritoLaCantidadEsLaAgregada(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		Integer cantidad = 5;
		catalogo.put(elem, 250.0);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, cantidad);
		assertTrue(catalogo.containsKey(elem));
		assertEquals(carrito.cantidad(elem), cantidad);
	}

	@Test
	public void test05NoSePuedenAgregarProductosDeUnTipoQueNoEsDeLaEditorialAlCarrito(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		Carrito carrito = new Carrito(catalogo);	
	
		try {
			carrito.agregar(elem, 5);
			fail();
		} catch (Error e){
			assertEquals(Carrito.ERROR_EL_PRODUCTO_NO_ESTA_EN_CATALOGO,e.getMessage());
			assertTrue(catalogo.isEmpty());
			assertTrue(carrito.esVacio());
		}
	}
	
	@Test
	public void test06NoPuedeSerMenorAUnoLaCantidadDeProductosAAgregarAlCarrito(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		Carrito carrito = new Carrito(catalogo);	
		
		try {
			carrito.agregar(elem, 0);
			fail();
		} catch (Error e){
			assertEquals(Carrito.ERROR_CANTIDAD_NO_PUEDE_SER_NEGATIVA_O_CERO,e.getMessage());
			assertTrue(catalogo.isEmpty());
			assertTrue(carrito.esVacio());
		}
		
	}
	
	@Test
	public void test07AlAgregarElMismoProductoVariasVecesLaCantidadEsLaSumaDeTodasLasCantidades(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		catalogo.put(elem, 250.0);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 2);
		carrito.agregar(elem, 5);
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
		assertEquals(carrito.cantidad(elem), new Integer(5+2));
		
	}
	
	@Test
	public void test08AlAgregarProductosDeDistintoTipoTodosFiguranConSusRespectivasCantidades(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem1, 2);
		carrito.agregar(elem2, 5);
		
		assertTrue(catalogo.containsKey(elem1));
		assertTrue(catalogo.containsKey(elem2));
		assertTrue(carrito.listar().contains(elem1)); 
		assertTrue(carrito.listar().contains(elem2));
		assertEquals(carrito.cantidad(elem1), new Integer(2));
		assertEquals(carrito.cantidad(elem2), new Integer(5));
		
	}
	
	
	@Test
	public void test09LaListaDeProductosDeUnCarritoVacioEsVacia(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		catalogo.put(elem, 250.0);
		Carrito carrito = new Carrito(catalogo);	
		Set<Object> lista = carrito.listar();
		assertTrue(catalogo.containsKey(elem));
		assertTrue(carrito.esVacio());
		assertTrue(lista.isEmpty());
		
	}
	
	@Test
	public void test10LaListaDeProductosDeUnCarritoConProductosEsNoVacia(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem = 1;
		catalogo.put(elem, 250.0);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 2);
		Set<Object> lista = carrito.listar();
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
		assertTrue(!lista.isEmpty());
		
	}  
	
	@Test
	public void test11NoSePuedeCrearUnaTarjetaQueNoTiene16Caracteres(){
		try {
			@SuppressWarnings("unused")
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("123456789012456", "051992", "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_TARJETA_DEBE_TENER_16_CARACTERES, e.getMessage());
		}
		
	}
	
	@Test
	public void test12NoSePuedeCrearUnaTarjetaConCaracteresQueNoSeanDigitos(){
		try {
			@SuppressWarnings("unused")
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("s123456789012456",  "051992", "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_TARJETA_DEBEN_SER_DIGITOS, e.getMessage());
		}
		
	}
	
	@Test
	public void test13NoSePuedeCrearUnaTarjetaConDuenioQueNoComienceConLetrasMayusculas(){
		try {
			@SuppressWarnings("unused")
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456",  "051992", " ");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_DUENIO_FORMATO_INVALIDO, e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test14NoSePuedeCrearUnaTarjetaQueContengaCaracteresDistintosALetrasMayusculasYEspacios(){
		try {
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456",  "051992", "JOSE   v");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_DUENIO_FORMATO_INVALIDO, e.getMessage());
		}
		
	}
	
	@Test
	public void test15CheckOutDeUnCarritoVacioDaError(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456",  "051992", "LOPEZ JOSE");
		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>();
		
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		
		try {
			cajero.checkOut();
			fail();
		} catch (Error e){
			assertEquals(Carrito.ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO,e.getMessage());
			assertTrue(cajero.libroDeVentas().esVacio());
			assertTrue(catalogo.isEmpty());
			assertTrue(carrito.esVacio());
		}
		
	}
	
	@Test
	public void test16AlHacerCheckOutDeUnCarritoConUnElementoMeCobraElPrecioDeEseElementoYSeEncuentraLibroVenta(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		int elem = 1;
		catalogo.put(elem, 250.0);
		carrito.agregar(elem, 1);
		Calendar hoy = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JULIAN");

		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		
		double total = cajero.checkOut().getTotalCompra();
		
		assertTrue(catalogo.containsKey(elem));
		assertEquals(1, cajero.libroDeVentas().cantidad(elem));
		assertTrue(total == 250); 
				
	}  
	
	@Test
	public void test17AlHacerCheckOutDeUnCarritoMeCobraLaSumaDelPrecioDeSusProductos(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 310.0);
		carrito.agregar(elem1, 6);
		carrito.agregar(elem2, 4);
		Calendar hoy = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JULIAN");
		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		
		double total = cajero.checkOut().getTotalCompra();
		
		assertTrue(catalogo.containsKey(elem1));
		assertTrue(catalogo.containsKey(elem2));
		assertEquals(6,cajero.libroDeVentas().cantidad(elem1));
		assertEquals(4,cajero.libroDeVentas().cantidad(elem2));
		assertTrue(250*6+310*4 == total );
				
	}  

	@Test
	public void test18AlcrearUnClienteElMismoSeEncuentraEnElSistema(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		assertTrue(interfa.getClientesCreados().contains(new Cliente(0,"password",new HashMap<Object,Double>(),obtenerMerch())));		
	}
	
	@Test
	public void test19NoSePuedeCrearUnClienteConIdYaExistente(){		
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			interfa.crearCliente(0, "pass");
			fail();
		} catch (Error e){
			assertEquals(AutenticadorCliente.ERROR_ID_CLIENTE_UTILIZADA,e.getMessage());
		}

				
	}
	
	@Test
	public void test20AlcrearUnClienteSuListaDeCarritosEsVacia(){	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		for(Cliente cl : interfa.getClientesCreados() ){
			if(cl.getId() == 0){
				assertTrue(cl.getCarritos().isEmpty());
				break;
			}
		}			
	}
	
	@Test
	public void test21AlcrearUnClienteSuLibroDeComprasEsVacia(){	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		for(Cliente cl : interfa.getClientesCreados() ){
			if(cl.getId() == 0){
				assertTrue(cl.getLibroDeCompras().esVacio());
				break;
			}
		}			
	}
	
	@Test
	public void test22CuandoSeAgreganVariosCarritosLaCantidadAumentaEnLaAgregada(){	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		for(Cliente cl : interfa.getClientesCreados() ){
			if(cl.getId() == 0){
				int cantidadAnterior = cl.getCarritos().size();
				cl.agregarNuevosCarritos(5);
				assertEquals(5,cl.getCarritos().size() - cantidadAnterior);
				break;
			}
		}			
	}
	
	@Test
	public void test23TodosLosCarritosDeDistintosClientesTienenDistintoID(){	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		interfa.crearCliente(1, "passw");
		Set<Long> idsCarritosclientes = new HashSet<Long>();
		for(Cliente cl : interfa.getClientesCreados() ){
			cl.agregarNuevosCarritos(7);
			idsCarritosclientes.addAll(cl.getCarritos().keySet());
		}
		assertEquals(14,idsCarritosclientes.size());
	}
	
	@Test
	public void test24MedianteLaInterfaceNosePuedeCrearUnCarritoParaUnIdClienteInexistente() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			interfa.crearCarrito(8, "password");
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.NO_SE_ENCUENTRA_CLIENTE,e.getMessage());
		}
	}
	
	@Test
	public void test25MedianteLaInterfaceNosePuedeCrearUnCarritoParaUnCorrectoIdClienteInvalidaContasenia() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			interfa.crearCarrito(0, "pass");
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.CONTRASENIA_INVALIDA,e.getMessage());
		}
	}
	
	@Test
	public void test26MedianteLaInterfaceAlCrearUnCarritoAumentaLaCantidadDeCarritosDeUnCliente() {	

		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		interfa.crearCarrito(0, "password");
		for(Cliente cl : interfa.getClientesCreados() ){
			if(cl.getId() == 0){
				int cantidadAnterior = cl.getCarritos().size();
				cl.agregarNuevoCarrito();
				assertEquals(1,cl.getCarritos().size() - cantidadAnterior);
				break;
			}
		}
	}
	
	@Test
	public void test27MedianteLaInterfaceNosePuedeAgregarProductoAUnCarritoParaUnIdInexistente() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			Object prod = new Integer(6);
			interfa.agregarACarrito(++idCarrito, prod, 5);
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.NO_SE_ENCUENTRA_CARRITO,e.getMessage());
		}
	}

	@Test
	public void test28MedianteLaInterfaceAlAgregarUnProductoAunCarritoElmismoSeEnCuentraEnELCarrito() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");
		Object prod = new Integer(6);
		int cantidadAnterior=0;
		for(Cliente cl : interfa.getClientesCreados() ){
			if(cl.getId() == 0){
				cl.getCarritos().get(idCarrito);
				cantidadAnterior = cl.getCarritos().get(idCarrito).cantidad(prod);
				break;
			}
		}	
		interfa.agregarACarrito(idCarrito, prod, 5);
		for(Cliente cl : interfa.getClientesCreados() ){
			if(cl.getId() == 0){
				assertEquals(5,cl.getCarritos().get(idCarrito).cantidad(prod) - cantidadAnterior);
				break;
			}
		}		
	}
	
	@Test
	public void test29MedianteLaInterfaceNosePuedeListarUnCarritoParaUnIdClienteInexistente() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			interfa.listarCarrito(++idCarrito);
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.NO_SE_ENCUENTRA_CARRITO,e.getMessage());
		}
	}
	
	@Test
	public void test30MedianteLaInterfaceAlListarUnCarritoSeObtieneQueCuandoNoHayAgregacionesDeProductoEsVacio() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");		
		Multiconjunto<Object, Integer> contenidoCarrito = interfa.listarCarrito(idCarrito);
		assertTrue(contenidoCarrito.esVacio());
	}
	
	
	@Test
	public void test31MedianteLaInterfaceAlListarUnCarritoSePuedeObtenerLaCantidadDeElementodDeUnObject() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");
		Object prod = new Integer(6);
		interfa.agregarACarrito(idCarrito, prod, 5);		
		Multiconjunto<Object, Integer> contenidoCarrito = interfa.listarCarrito(idCarrito);
		assertEquals(5,contenidoCarrito.cantidad(prod));
	}
	
	@Test
	public void test32MedianteLaInterfaceNosePuedeListarLasComprasParaUnIdClienteInexistente() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			interfa.listarComprasCliente(8, "password");
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.NO_SE_ENCUENTRA_CLIENTE,e.getMessage());
		}
	}
	
	@Test
	public void test33MedianteLaInterfaceNosePuedeListarLasComprasParaUnCorrectoIdClienteInvalidaContasenia() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			interfa.listarComprasCliente(0, "pass");
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.CONTRASENIA_INVALIDA,e.getMessage());
		}
	}
	
	@Test
	public void test34MedianteLaInterfaceAlListarLasComprasDeUnClienteSeObtieneQueCuandoNoHayCheckOutElLibroDeComprasEsVacio() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 1;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");
		Object prod = new Integer(6);
		interfa.agregarACarrito(idCarrito, prod, 5);		
		Multiconjunto<Object, Integer> comprasCliente = interfa.listarComprasCliente(0, "password");
		assertTrue(comprasCliente.esVacio());
	}
	
	@Test
	public void test35NoSePuedeHacerCheckOutConTarjetavencida(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 310.0);
		carrito.agregar(elem1, 6);
		carrito.agregar(elem2, 4);
		Calendar hoy = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) -1), "LOPEZ JOSE");
		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		
		try {
			cajero.checkOut();
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_TARJETA_VENCIDA, e.getMessage());
			assertTrue(cajero.libroDeVentas().esVacio());
			assertTrue(!catalogo.isEmpty());
			assertTrue(!carrito.esVacio());
		}				
	} 
	
	@Test
	public void test36NoSePuedeHacerCheckOutConTarjetaConDuenioMayorA30Caracters(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 310.0);
		carrito.agregar(elem1, 6);
		carrito.agregar(elem2, 4);
		Calendar hoy = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456",String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSEXXXXXXXXXXXXXXXXXXXXX");
		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		try {
			cajero.checkOut();
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_NOMBRE_DUENIO_LARGO, e.getMessage());
			assertTrue(cajero.libroDeVentas().esVacio());
			assertTrue(!catalogo.isEmpty());
			assertTrue(!carrito.esVacio());
		}				
	}
	
	@Test
	public void test37NoSePuedeHacerCheckOutConTarjetaConFechaExpiracionMenorDe6Caracters(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 310.0);
		carrito.agregar(elem1, 6);
		carrito.agregar(elem2, 4);
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", "05202", "LOPEZ JOSE");
		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		try {
			cajero.checkOut();
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_FECHA_EXPIRACION_INVALIDA, e.getMessage());
			assertTrue(cajero.libroDeVentas().esVacio());
			assertTrue(!catalogo.isEmpty());
			assertTrue(!carrito.esVacio());
		}				
	}
	
	@Test
	public void test38NoSePuedeHacerCheckOutConTarjetaConFechaExpiracionMayorDe6Caracters(){
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		Carrito carrito = new Carrito(catalogo);
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 310.0);
		carrito.agregar(elem1, 6);
		carrito.agregar(elem2, 4);
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", "0520208", "LOPEZ JOSE");
		Multiconjunto<Object,Integer> libroDeVentas = new Multiconjunto<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, tarjeta, libroDeVentas,obtenerMerch());
		try {
			cajero.checkOut();
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_FECHA_EXPIRACION_INVALIDA, e.getMessage());
			assertTrue(cajero.libroDeVentas().esVacio());
			assertTrue(!catalogo.isEmpty());
			assertTrue(!carrito.esVacio());
		}				
	}
	
	@Test
	public void test39MedianteLaInterfaceNosePuedeHacerCheckOutParaUnCarritoParaConIdInexistente() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 6;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			Object prod = new Integer(6);
			interfa.agregarACarrito(idCarrito, prod, 5);
			Calendar hoy = new GregorianCalendar();
			interfa.checkOutCarrito(++idCarrito, "1234567890123456", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(InterfaceRest.NO_SE_ENCUENTRA_CARRITO,e.getMessage());
		}
	}
	
	@Test
	public void test40MedianteLaInterfaceLuegoDeCheckOutLasComprasDeUnClienteContienenLosElementosDelCarrito() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 7;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");
		Object prod = new Integer(6);
		Object prod2 = new Integer(7);
		interfa.agregarACarrito(idCarrito, prod, 5);
		interfa.agregarACarrito(idCarrito, prod2, 8);
		Calendar hoy = new GregorianCalendar();
		interfa.checkOutCarrito(idCarrito, "1234567890123450", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
		Multiconjunto<Object, Integer> comprasCliente = interfa.listarComprasCliente(0, "password");
		assertEquals(5,comprasCliente.cantidad(prod));
		assertEquals(8,comprasCliente.cantidad(prod2));
	}

	@Test
	public void test41MedianteLaInterfaceLosNumerosDeTransaccionPorCheckOutsSonTodosDistintos() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 7;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");
		long idCarrito2 = interfa.crearCarrito(0, "password");
		long idCarrito3 = interfa.crearCarrito(0, "password");
		Object prod = new Integer(6);
		Object prod2 = new Integer(7);
		interfa.agregarACarrito(idCarrito, prod, 5);
		interfa.agregarACarrito(idCarrito2, prod2, 8);
		interfa.agregarACarrito(idCarrito3, prod2, 9);
		Calendar hoy = new GregorianCalendar();
		long transId1= interfa.checkOutCarrito(idCarrito, "1234567890123450", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
		long transId2= interfa.checkOutCarrito(idCarrito2, "1234567890123450", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
		long transId3= interfa.checkOutCarrito(idCarrito3, "1234567890123450", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
		Set<Long> transIds = new HashSet<Long>();
		transIds.add(transId1);
		transIds.add(transId2);
		transIds.add(transId3);
		assertEquals(3,transIds.size());
	}
	
	@Test
	public void test42MedianteLaInterfaceLuegoDeCheckOutElCarritoQuedaVacio() {	
		Map<Object, Double> catalogo = new HashMap<Object, Double>();
		int elem1 = 7;
		int elem2 = 6;
		catalogo.put(elem1, 250.0);
		catalogo.put(elem2, 400.0);
		InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
		interfa.crearCliente(0, "password");
		long idCarrito = interfa.crearCarrito(0, "password");
		Object prod = new Integer(6);
		Object prod2 = new Integer(7);
		interfa.agregarACarrito(idCarrito, prod, 5);
		interfa.agregarACarrito(idCarrito, prod2, 8);
		Calendar hoy = new GregorianCalendar();
		interfa.checkOutCarrito(idCarrito, "1234567890123450", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
		assertTrue(interfa.listarCarrito(idCarrito).esVacio());	
	}
	
	@Test
	public void test43MedianteLaInterfaceNoSePuedeHacerCheckOutTarjetaRobada() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 7;
			int elem2 = 6;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			Object prod = new Integer(6);
			Object prod2 = new Integer(7);
			interfa.agregarACarrito(idCarrito, prod, 5);
			interfa.agregarACarrito(idCarrito, prod2, 8);
			Calendar hoy = new GregorianCalendar();
			interfa.checkOutCarrito(idCarrito, "1234567890123459", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(MerchantSimulator.ERROR_TARJETA_ROBADA,e.getMessage());
		}
	}
	
	@Test
	public void test44MedianteLaInterfaceNoSePuedeHacerCheckOutTarjetaInexistente() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 7;
			int elem2 = 6;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			Object prod = new Integer(6);
			Object prod2 = new Integer(7);
			interfa.agregarACarrito(idCarrito, prod, 5);
			interfa.agregarACarrito(idCarrito, prod2, 8);
			Calendar hoy = new GregorianCalendar();
			interfa.checkOutCarrito(idCarrito, "1234567890123754", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(MerchantSimulator.ERROR_TARJETA_INVALIDA,e.getMessage());
		}
	}
	
	@Test
	public void test45MedianteLaInterfaceNoSePuedeHacerCheckOutCuentaSinFondos() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 7;
			int elem2 = 6;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			Object prod = new Integer(6);
			Object prod2 = new Integer(7);
			interfa.agregarACarrito(idCarrito, prod, 5);
			interfa.agregarACarrito(idCarrito, prod2, 8);
			Calendar hoy = new GregorianCalendar();
			interfa.checkOutCarrito(idCarrito, "1234567890123148", String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ PEDRO");
			fail();
		} catch (Error e){
			assertEquals(Cuenta.CUENTA_SIN_FONDOS,e.getMessage());
		}
	}
	
	@Test
	public void test46MedianteLaInterfaceNoSePuedeHacerCheckOutCuentaSaldoMenor() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 7;
			int elem2 = 6;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			InterfaceRest interfa = new InterfaceRest(catalogo,obtenerMerch());
			interfa.crearCliente(0, "password");
			long idCarrito = interfa.crearCarrito(0, "password");
			Object prod = new Integer(6);
			Object prod2 = new Integer(7);
			interfa.agregarACarrito(idCarrito, prod, 5);
			interfa.agregarACarrito(idCarrito, prod2, 8);
			Calendar hoy = new GregorianCalendar();
			interfa.checkOutCarrito(idCarrito, "1234567890123149",  String.format("%02d%04d", hoy.get(Calendar.MONTH) +1, hoy.get(Calendar.YEAR) +1), "LOPEZ JUAN");
			fail();
		} catch (Error e){
			assertEquals(Cuenta.SALDO_INSUFICIENTE,e.getMessage());
		}
	}
	
	@Test
	public void test47SiUnCarritoNoEsUsadoPorMasDe30MinutosDejaDeSerValido() {	
		try {
			Map<Object, Double> catalogo = new HashMap<Object, Double>();
			int elem1 = 1;
			int elem2 = 2;
			catalogo.put(elem1, 250.0);
			catalogo.put(elem2, 400.0);
			Carrito carrito = new Carrito(catalogo);	
			carrito.agregar(elem1, 2);
			carrito.setUltimoAccesoCarrito(System.currentTimeMillis()- (Carrito.TIEMPO_PERMITIDO_INACTIVIDAD_CARRITO_MILLIS +5000) );
			carrito.agregar(elem2, 5);
			fail();
		} catch (Error e){
			assertEquals(Carrito.ERROR_NO_SE_PUEDE_SEGUIR_USANDO_EL_CARRITO,e.getMessage());
		}
	}

	
}

