/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package numero;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestNumero {

	protected Numero cero;
	protected Numero uno;
	protected Numero dos;
	protected Numero tres;
	protected Numero cuatro;
	protected Numero cinco;
	
	protected Numero unQuinto;
	protected Numero dosQuintos;
	protected Numero tresQuintos;
	protected Numero dosVeinticincoavos;
	protected Numero unMedio;
	protected Numero cincoMedios;
	protected Numero seisQuintos;
	protected Numero cuatroMedios;
	protected Numero dosCuartos;

	@Before
	public void setUp (){
		//inicializar los numeros
		
		cero = new Entero(0);
		uno = new Entero(1);
		dos = new Entero(2);
		tres = new Entero(3);
		cuatro = new Entero(4);
		cinco = new Entero(5);

		unQuinto = uno.dividido(cinco);
		dosQuintos = dos.dividido(cinco);
		tresQuintos = tres.dividido(cinco);
		dosVeinticincoavos = dos.dividido(new Entero(25));
		unMedio = uno.dividido(dos);
		cincoMedios = cinco.dividido(dos);
		seisQuintos = new Entero(6).dividido(cinco);
		cuatroMedios = cuatro.dividido(dos);
		dosCuartos = dos.dividido(cuatro);
		
	}
	
	@Test
	public void test01EsCeroDevuelveTrueSoloParaElCero () {
		assertTrue (cero.esCero());
		assertFalse (uno.esCero());
	}

	@Test
	public void test02EsUnoDevuelveTrueSoloParaElUno () {
		assertTrue (uno.esUno());
		assertFalse (cero.esUno());
	}

	@Test
	public void test03SumaDeEnteros(){
		assertEquals (dos,uno.mas(uno));
	}
	
	@Test
	public void test04MultiplicacionDeEnteros(){
		assertEquals(cuatro, dos.por(dos));
	}

	@Test
	public void test05DivisionDeEnteros(){
		assertEquals(uno, dos.dividido(dos));
	}
	
	@Test
	public void test06SumaDeFracciones(){
		Numero sieteDecimos = new Entero(7).dividido(new Entero(10));
		assertEquals (sieteDecimos,unQuinto.mas(unMedio));
		/* 
		 * La suma de fracciones es:
		 * 
		 * a/b + c/d = (a.d + c.b) / (b.d)
		 * 
		 * SI ESTAN PENSANDO EN LA REDUCCION DE FRACCIONES NO SE PREOCUPEN!
		 * TODAVIA NO SE ESTA TESTEANDO ESE CASO
		 */
	}

	@Test
	public void test07MultiplicacionDeFracciones(){
		assertEquals (dosVeinticincoavos,unQuinto.por(dosQuintos));
		/* 
		 * La multiplicación de fracciones es:
		 * 
		 * (a/b) * (c/d) = (a.c) / (b.d)
		 * 
		 * SI ESTAN PENSANDO EN LA REDUCCION DE FRACCIONES NO SE PREOCUPEN!
		 * TODAVIA NO SE ESTA TESTEANDO ESE CASO
		 */
	}
	
	@Test
	public void test08DivisionDeFracciones(){
		assertEquals (cincoMedios,unMedio.dividido(unQuinto));
		/* 
		 * La división de fracciones es:
		 * 
		 * (a/b) / (c/d) = (a.d) / (b.c)
		 * 
		 * SI ESTAN PENSANDO EN LA REDUCCION DE FRACCIONES NO SE PREOCUPEN!
		 * TODAVIA NO SE ESTA TESTEANDO ESE CASO
		 */
	}

	/* 
	 * Ahora empieza lo lindo! - Primero hacemos que se puedan sumar enteros con fracciones
	 * y fracciones con enteros 
	 */
	@Test
	public void test09SumaDeEnteroYFraccion(){
		assertEquals (seisQuintos,uno.mas(unQuinto));
	}
	
	@Test
	public void test10SumaDeFraccionYEntero(){
		assertEquals (seisQuintos,unQuinto.mas(uno));
	}

	/* 
	 * Hacemos lo mismo para la multipliación
	 */
	@Test
	public void test11MultiplicacionDeEnteroPorFraccion(){
		assertEquals(dosQuintos,dos.por(unQuinto));
	}
	
	@Test
	public void test12MultiplicacionDeFraccionPorEntero(){
		assertEquals(dosQuintos,unQuinto.por(dos));
	}
	
	/* 
	 * Hacemos lo mismo para la division
	 */
	@Test
	public void test13DivisionDeEnteroPorFraccion(){
		assertEquals(cincoMedios,uno.dividido(dosQuintos));
	}

	@Test
	public void test14DivisionDeFraccionPorEntero(){
		assertEquals(dosVeinticincoavos,dosQuintos.dividido(cinco));
	}
	
	/* 
	 * Ahora si empezamos con problemas de reducción de fracciones
	 */
	@Test
	public void test15UnaFraccionPuedeSerIgualAUnEntero(){
		assertEquals(dos,cuatroMedios);
	}
	
	@Test
	public void test16LasFraccionesAparentesSonIguales(){
		assertEquals(unMedio,dosCuartos);
		/*
		 * Las fracciones se reducen utilizando el maximo comun divisor (mcd)
		 * Por lo tanto, para a/b, sea c = mcd (a,b) => a/b reducida es:
		 * (a/c) / (b/c).
		 * 
		 * Por ejemplo: a/b = 2/4 entonces c = 2. Por lo tanto 2/4 reducida es:
		 * (2/2) / (4/2) = 1/2
		 * 
		 * Para obtener el mcd pueden usar el algoritmo de Euclides que es:
		 * 
		 * mcd (a,b) = 
		 * 		si b = 0 --> a
		 * 		si b != 0 -->mcd(b, restoDeDividir(a,b))
		 * 	
		 * Ejemplo:
		 * mcd(2,4) ->
		 * mcd(4,restoDeDividir(2,4)) ->
		 * mcd(4,2) ->
		 * mcd(2,restoDeDividir(4,2)) ->
		 * mcd(2,0) ->
		 * 2
		 */
	}
	
	@Test
	public void test17LaSumaDeFraccionesPuedeDarEntero(){
		assertEquals (uno,unMedio.mas(unMedio));
	}
	
	@Test
	public void test18LaMultiplicacionDeFraccionesPuedeDarEntero(){
		assertEquals(dos,cuatro.por(unMedio));
	}
	
	@Test
	public void test19LaDivisionDeEnterosPuedeDarFraccion(){
		assertEquals(unMedio, dos.dividido(cuatro));
	}

	@Test
	public void test20LaDivisionDeFraccionesPuedeDarEntero(){
		assertEquals(uno, unMedio.dividido(unMedio));
	}
	
	@Test
	public void test21NoSePuedeDividirEnteroPorCero(){
		try {
			uno.dividido(cero);
			fail();
		}
		catch (RuntimeException e) {
			assertEquals(Numero.DESCRIPCION_DE_ERROR_NO_SE_PUEDE_DIVIDIR_POR_CERO,e.getMessage());
		}
	}

	@Test
	public void test22NoSePuedeDividirFraccionPorCero(){
		try {
			unQuinto.dividido(cero);
			fail();
		}
		catch (RuntimeException e) {
			assertEquals(Numero.DESCRIPCION_DE_ERROR_NO_SE_PUEDE_DIVIDIR_POR_CERO,e.getMessage());
		}
	}

	// Este test puede ser redundante dependiendo de la implementación realizada 
	@Test
	public void test23NoSePuedeCrearFraccionConDenominadorCero(){
		try {
			crearFraccionCon(uno,cero);
			fail();
		}
		catch (RuntimeException e) {
			assertEquals(Numero.DESCRIPCION_DE_ERROR_NO_SE_PUEDE_DIVIDIR_POR_CERO,e.getMessage());
		}
	}

	public Numero crearFraccionCon(Numero numerador,Numero denominador) {
		return numerador.dividido(denominador);
	}

}
