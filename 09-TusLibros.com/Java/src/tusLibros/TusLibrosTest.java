package tusLibros;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
		carrito.agregar(elem);
		assertTrue(catalogo.contains(elem));
		assertTrue(!carrito.esVacio());
	}
	
	@Test
	public void test03NoSePuedeAgregaUnProductoQueNoEsDeLaEditorialAlCarrito(){
		List<Object> catalogo = new ArrayList<Object>();
		int elem = 1;
		Carrito carrito = new Carrito(catalogo);	
	
		try {
			carrito.agregar(elem);
			fail();
		} catch (Error e){
			assertEquals(Carrito.ERROR_AL_AGREGAR_PRODUCTO,e.getMessage());
			assertTrue(catalogo.isEmpty());
			assertTrue(carrito.esVacio());
		}
		
	}

}
