package tusLibros;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TusLibrosTest {
	
	@Test
	public void test01CuandoSeCreaUnCartEstaVacio(){
		Cart cart = new Cart();	
		assertTrue(cart.isEmpty());
	}

}
