package tusLibros;

import static org.junit.Assert.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TusLibrosTest {
	
	@Test
	public void test01CuandoSeCreaUnCarritoEstaVacio(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		Carrito carrito = new Carrito(catalogo);	
		assertTrue(carrito.esVacio());
	}
	
	@Test
	public void test02CuandoSeAgregaUnProductoAlCarritoNoEstaVacio(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		catalogo.put(elem, 250);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 1);
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
	}
	
	@Test
	public void test03NoSePuedeAgregaUnProductoQueNoEsDeLaEditorialAlCarrito(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
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
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		Integer cantidad = 5;
		catalogo.put(elem, 250);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, cantidad);
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
		assertEquals(carrito.cantidad(elem), cantidad);
	}

	@Test
	public void test05NoSePuedenAgregarProductosDeUnTipoQueNoEsDeLaEditorialAlCarrito(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
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
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
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
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		catalogo.put(elem, 250);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 2);
		carrito.agregar(elem, 5);
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
		assertEquals(carrito.cantidad(elem), new Integer(5+2));
		
	}
	
	@Test
	public void test08AlAgregarProductosDeDistintoTipoTodosFiguranConSusRespectivasCantidades(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250);
		catalogo.put(elem2, 400);
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
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		catalogo.put(elem, 250);
		Carrito carrito = new Carrito(catalogo);	
		Set<Object> lista = carrito.listar();
		assertTrue(catalogo.containsKey(elem));
		assertTrue(carrito.esVacio());
		assertTrue(lista.isEmpty());
		
	}
	
	@Test
	public void test10LaListaDeProductosDeUnCarritoConProductosEsNoVacia(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		catalogo.put(elem, 250);
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
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("123456789012456", YearMonth.now(), "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_TARJETA_DEBE_TENER_16_CARACTERES, e.getMessage());
		}
		
	}
	
	@Test
	public void test12NoSePuedeCrearUnaTarjetaConCaracteresQueNoSeanDigitos(){
		try {
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("s123456789012456", YearMonth.now(), "LOPEZ JOSE");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_TARJETA_DEBEN_SER_DIGITOS, e.getMessage());
		}
		
	}
	
	@Test
	public void test13NoSePuedeCrearUnaTarjetaConDuenioQueNoComienceConLetrasMayusculas(){
		try {
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", YearMonth.now(), " ");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_DUENIO_FORMATO_INVALIDO, e.getMessage());
		}
		
	}
	
	@Test
	public void test14NoSePuedeCrearUnaTarjetaQueContengaCaracteresDistintosALetrasMayusculasYEspacios(){
		try {
			TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", YearMonth.now(), "JOSE   v");
			fail();
		} catch (Error e){
			assertEquals(TarjetaDeCredito.ERROR_DUENIO_FORMATO_INVALIDO, e.getMessage());
		}
		
	}
	
	@Test
	public void test15CheckOutDeUnCarritoVacioDaError(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		Carrito carrito = new Carrito(catalogo);
		Calendar fecha = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", YearMonth.now(), "LOPEZ JOSE");
		Map<Object,Integer> libroDeVentas = new HashMap<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, fecha, tarjeta, libroDeVentas);
		
		try {
			cajero.checkOut();
			fail();
		} catch (Error e){
			assertEquals(Cajero.ERROR_NO_SE_PUEDE_HACER_CHECKOUT_DE_CARRITO_VACIO,e.getMessage());
			assertTrue(cajero.libroDeVentas().isEmpty());
			assertTrue(catalogo.isEmpty());
			assertTrue(carrito.esVacio());
		}
		
	}
	
	@Test
	public void test16AlHacerCheckOutDeUnCarritoConUnElementoMeCobraElPrecioDeEseElemento(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		Carrito carrito = new Carrito(catalogo);
		int elem = 1;
		catalogo.put(elem, 250);
		carrito.agregar(elem, 1);
		Calendar fecha = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", YearMonth.now(), "LOPEZ JOSE");
		Map<Object,Integer> libroDeVentas = new HashMap<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, fecha, tarjeta, libroDeVentas);
		
		int total = cajero.checkOut();
		
		assertTrue(catalogo.containsKey(elem));
		assertTrue(carrito.listar().contains(elem)); 
		assertEquals(carrito.cantidad(elem), new Integer(1));
		assertTrue(cajero.libroDeVentas().containsKey(elem));
		assertEquals(total, 250);
				
	}  
	
	@Test
	public void test17AlHacerCheckOutDeUnCarritoMeCobraLaSumaDelPrecioDeSusProductos(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		Carrito carrito = new Carrito(catalogo);
		int elem1 = 1;
		int elem2 = 2;
		catalogo.put(elem1, 250);
		catalogo.put(elem2, 310);
		carrito.agregar(elem1, 6);
		carrito.agregar(elem2, 4);
		Calendar fecha = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", YearMonth.now(), "LOPEZ JOSE");
		Map<Object,Integer> libroDeVentas = new HashMap<Object, Integer>(); 
		Cajero cajero = new Cajero(carrito, fecha, tarjeta, libroDeVentas);
		
		int total = cajero.checkOut();
		
		assertTrue(catalogo.containsKey(elem1));
		assertTrue(catalogo.containsKey(elem2));
		assertTrue(carrito.listar().contains(elem1)); 
		assertTrue(carrito.listar().contains(elem2));
		assertEquals(carrito.cantidad(elem1), new Integer(6));
		assertEquals(carrito.cantidad(elem2), new Integer(4));
		assertTrue(cajero.libroDeVentas().containsKey(elem1));
		assertTrue(cajero.libroDeVentas().containsKey(elem2));
		assertEquals(total, 250*6+310*4);
				
	}  

	
	// ). Si se puede crear una tarjeta vencida.
	// Lo que no voy a poder hacer es comprar con una tarjeta vencida.
	// La tarjeta  verifica si esta vencida (antropormofismo: darle responsabilidad humana a un objeto).
	// estasvencida() o estasvencidaAEstaFecha() (se debe usar la segunda, pasar una fecha).
	// Se desacopla la clase tarjeta de credito de la fecha.
	// cuando hay una nueva lista de precios se crea una nueva lista, no se modifica.
	// al carrito le puedo pedir su precio.
	// cajero new. Checkout con este carrito, tarjeta, fecha. cajero persona. Problema cuando hago el checkout
	// 
	// cajero new carrito, fecha y tarjeta. cajero por venta.
	
	// se calcula el precio correctamente. devuelve el total el cajero.
	
	// no se puede hacer el checkout con un tarjeta expirada ()
	
	// en el mercan procesor (antes de enviar el nombre del owner de la tarjeta se debe truncar su longitud
	//a 30 si por ejemplo es 40.
	// el test tiene que estar en control de todo.
	
	// La interface traduce, es un adapter. Del lado externo devulevo un codigo de retorno y lo transformo
	// en una excepcion si devuelve un error. 
	
	// cuando testeo el cajero, simulo la cara interna (la que lanza la excepcion).
	// 1 ro configuro objeto simulador.
	// 2 checkout llamando a la cara interna del merchan procesor con la cara interna.
	
	// no puedo hacer checkout de tarjeta robada, el cajaro no habla con el mercna procesor cuando la tarjeta
	//esta vencida.<
	// en el checkout tengo qe debitar del mercan procesor.
	


	@Test
	public void testXAlcrearUnClienteElMismoSeEncuentraEnElSistema(){
		//TODO hay que hacer este test
		AutenticadorCliente auten = new AutenticadorCliente();
		auten.crearCliente(0, "password");
		assertTrue(auten.getClientesCreados().contains(new Cliente(0,"password",new HashMap<Object,Integer>())));
				
	}
	
	@Test
	public void testYNoSePuedeCrearUnClienteConIdYaExistente(){
		//TODO hay que hacer este test
		
		try {
			AutenticadorCliente auten = new AutenticadorCliente();
			auten.crearCliente(0, "password");
			auten.crearCliente(0, "pass");
			fail();
		} catch (Error e){
			assertEquals(AutenticadorCliente.ERROR_ID_CLIENTE_UTILIZADA,e.getMessage());
		}

				
	}
	
	@Test
	public void testZAlcrearUnClienteSuListaDeCarritosEsVacia(){	
		AutenticadorCliente auten = new AutenticadorCliente();
		auten.crearCliente(0, "password");
		for(Cliente cl : auten.getClientesCreados() ){
			if(cl.getId() == 0){
				assertTrue(cl.getCarritos().isEmpty());
				break;
			}
		}			
	}
	
	@Test
	public void testXAlcrearUnClienteSuLibroDeComprasEsVacia(){	
		AutenticadorCliente auten = new AutenticadorCliente();
		auten.crearCliente(0, "password");
		for(Cliente cl : auten.getClientesCreados() ){
			if(cl.getId() == 0){
				assertTrue(cl.getLibroDeCompras().keySet().isEmpty());
				break;
			}
		}			
	}
	
	
}

