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

public class Fraccion extends Numero {

	protected Entero numerador;
	protected Entero denominador;
	
	public static Numero dividir(Entero dividendo, Entero divisor ) {
		
		if(divisor.esCero()) throw new RuntimeException (Numero.DESCRIPCION_DE_ERROR_NO_SE_PUEDE_DIVIDIR_POR_CERO);
		if(dividendo.esCero()) return dividendo;
		
		Entero maximoComunDivisor = dividendo.maximoComunDivisorCon(divisor);
		Entero numerador = dividendo.divisionEntera(maximoComunDivisor);
		Entero denominador = divisor.divisionEntera(maximoComunDivisor);
		
		if (denominador.esUno()) return numerador;
		
		return new Fraccion(numerador,denominador);
	}
	
	private Fraccion(Entero numerador, Entero denominador){
		//Por construccion esto no puede pasar, y como el constructor es privado
		//no se puede ejecutar con denominador 1, pero dejo la verificacion para
		//que quede bien explicito que el denominador no puede ser 1
	    if(denominador.esUno()) throw new Error("Denominador no puede ser 1");

		this.numerador = numerador;
		this.denominador = denominador;
	}
	
	public Entero numerador (){
		return numerador;
	}
	
	public Entero denominador(){
		return denominador;
	}

	@Override
	public boolean esCero() {
		return false;
	}

	@Override
	public boolean esUno() {
		return false;
	}

	@Override
	public boolean equals(Object anObject){
		if (anObject instanceof Fraccion) 
			return equals((Fraccion) anObject);
		else
			return false;
	}
	
	public boolean equals(Fraccion aFraccion){
		return numerador.por(aFraccion.denominador()).equals( denominador.por(aFraccion.numerador()));
	}
	
	@Override
	public int hashCode() {
		return numerador.hashCode() / denominador.hashCode();
	}
	
	@Override
	public Numero mas(Numero sumando) {
		return sumando.sumarFraccion(this);
//		Fraccion sumandoAsFraccion = (Fraccion) sumando;
//		return numerador.por(sumandoAsFraccion.denominador()).
//				mas(denominador.por(sumandoAsFraccion.numerador())).
//			dividido(denominador.por(sumandoAsFraccion.denominador()));
	}

	@Override
	public Numero por(Numero multiplicador) {
		return multiplicador.multiplicarFraccion(this);
	//	if (multiplicador instanceof Fraccion) {
	//		Fraccion multiplicadorAsFraccion = (Fraccion) multiplicador;
	//		return numerador.por(multiplicadorAsFraccion.numerador()).
	//				dividido(denominador.por(multiplicadorAsFraccion.denominador()));
	//	}else{
	//		return numerador.por(multiplicador).dividido(denominador);
	//	}
	}
	
	@Override
	public Numero dividido(Numero divisor) {
		return divisor.dividirFraccion(this);
//		if (divisor instanceof Fraccion){
////			Fraccion divisorAsFraccion = (Fraccion) divisor;
//			return numerador.por(((Fraccion) divisor).denominador()).
//				dividido(denominador.por(((Fraccion) divisor).numerador()));
//		}else{
//			return this.numerador().dividido(this.denominador().por(divisor));
//		}
	}

	@Override
	public Numero sumarEntero(Entero sumando) {
		return sumando.sumarFraccion(this);
	//	Numero denominador =  this.denominador();
	//	Numero numerador = sumando.por(denominador).mas(this.numerador());
	//	return numerador.dividido(denominador);
	}

	@Override
	public Numero sumarFraccion(Fraccion sumando) {	
		return numerador.por(sumando.denominador()).
				mas(denominador.por(sumando.numerador())).
			dividido(denominador.por(sumando.denominador()));
	}
	
	@Override
	public Numero multiplicarEntero(Entero multiplicador) {
		return multiplicador.multiplicarFraccion(this);
	//	return numerador.por(multiplicador).dividido(denominador);
	}

	@Override
	public Numero multiplicarFraccion(Fraccion multiplicador) {
		Fraccion multiplicadorAsFraccion = (Fraccion) multiplicador;
		return numerador.por(multiplicadorAsFraccion.numerador()).
				dividido(denominador.por(multiplicadorAsFraccion.denominador()));
	}

	@Override
	public Numero dividirEntero(Entero dividendo) {
		Entero numerador =  (Entero) dividendo.por(this.denominador());
		Entero denominador = this.numerador();
		return Fraccion.dividir(numerador,denominador);
	}

	@Override
	public Numero dividirFraccion(Fraccion dividendo) {
		Entero numerador =  (Entero) dividendo.numerador().por(this.denominador());
		Entero denominador = (Entero) dividendo.denominador().por(this.numerador());
		return Fraccion.dividir(numerador,denominador);
	}

}
