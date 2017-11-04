package tusLibros;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void test08LaListaDeProductosDeUnCarritoVacioEsVacia(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		catalogo.put(elem, 250);
		Carrito carrito = new Carrito(catalogo);	
		String lista = carrito.listar();
		assertTrue(catalogo.containsKey(elem));
		assertTrue(carrito.esVacio());
		assertTrue(lista.isEmpty());
		
	}
	
	@Test
	public void test09LaListaDeProductosDeUnCarritoConProductosEsNoVacia(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		int elem = 1;
		catalogo.put(elem, 250);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 2);
		String lista = carrito.listar();
		assertTrue(catalogo.containsKey(elem));
		assertTrue(!carrito.esVacio());
		assertNotEquals(lista, "");
		
	}  
	
	@Test
	public void test10CheckOutDeUnCarritoVacioDaError(){
		Map<Object, Integer> catalogo = new HashMap<Object, Integer>();
		Carrito carrito = new Carrito(catalogo);
		Calendar fecha = new GregorianCalendar();
		TarjetaDeCredito tarjeta = new TarjetaDeCredito("1234567890123456", "022022", "Lopez Jose");
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
	public void test11AlHacerCheckOutDeUnCarritoMeCobraLaSumaDelPrecioDeSusProductos(){
		
		//TODO hay que hacer este test   

				
	}
	
	
	@Test
	public void test12AlcrearUnClienteElMismoSeEncuentraEnElSistema(){
		//TODO hay que hacer este test
		AutenticadorCliente auten = new AutenticadorCliente();
		auten.crearCliente(0, "password");
		assertTrue(auten.getClientesCreados().contains(new Cliente(0,"password",new HashMap<Object,Integer>())));
				
	}
	
	@Test
	public void test13NoSePuedeCrearUnClienteConIdYaExistente(){
		//TODO hay que hacer este test

				
	}
	
	@Test
	public void test14AlcrearUnClienteSuListaDeCarritosEsVacia(){
		//TODO hay que hacer este test
				
	}

	
	
	
	
	
}
