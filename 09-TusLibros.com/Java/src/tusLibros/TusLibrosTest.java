package tusLibros;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TusLibrosTest {
	
	@Test
	public void test01CuandoSeCreaUnCarritoEstaVacio(){
		List<Object> catalogo = new ArrayList<Object>();
		Carrito carrito = new Carrito(catalogo);	
		assertTrue(carrito.esVacio());
	}
	
	@Test
	public void test02CuandoSeAgregaUnProductoAlCarritoNoEstaVacio(){
		List<Object> catalogo = new ArrayList<Object>();
		int elem = 1;
		catalogo.add(elem);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 1);
		assertTrue(catalogo.contains(elem));
		assertTrue(!carrito.esVacio());
	}
	
	@Test
	public void test03NoSePuedeAgregaUnProductoQueNoEsDeLaEditorialAlCarrito(){
		List<Object> catalogo = new ArrayList<Object>();
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
	public void test04CuandoSeAgreganVariosProductosDeUnTipoAlCarritoNoEstaVacio(){
		List<Object> catalogo = new ArrayList<Object>();
		int elem = 1;
		catalogo.add(elem);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 5);
		assertTrue(catalogo.contains(elem));
		assertTrue(!carrito.esVacio());
	}

	@Test
	public void test05NoSePuedenAgregarProductosDeUnTipoQueNoEsDeLaEditorialAlCarrito(){
		List<Object> catalogo = new ArrayList<Object>();
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
		List<Object> catalogo = new ArrayList<Object>();
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
		List<Object> catalogo = new ArrayList<Object>();
		int elem = 1;
		catalogo.add(elem);
		Carrito carrito = new Carrito(catalogo);	
		carrito.agregar(elem, 2);
		carrito.agregar(elem, 5);
		assertTrue(catalogo.contains(elem));
		assertTrue(!carrito.esVacio());
		assertEquals(carrito.cantidad(elem), new Integer(5+2));
		
	}

}
