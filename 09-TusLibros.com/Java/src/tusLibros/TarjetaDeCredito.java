package tusLibros;

import java.util.GregorianCalendar;

public class TarjetaDeCredito {
	
	private String numeroTarjeta;

	private String fechaExpiracion;
	private String nombreDuenio;
	public static final int MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA = 30;

	public static final String ERROR_TARJETA_DEBE_TENER_16_CARACTERES = "La tarjeta debe tener 16 caracteres";
	public static final String ERROR_TARJETA_DEBEN_SER_DIGITOS = "La tarjeta debe tener todos dígitos";
	public static final String ERROR_DUENIO_FORMATO_INVALIDO = "El dueño de la tarjeta debe comenzar con letras mayúsculas y solo puede contener letras mayúsculas y espacios.";
	public static final String ERROR_FECHA_EXPIRACION_INVALIDA = "La fecha de expiracion debe tener seis digitos";
	public static final String ERROR_TARJETA_VENCIDA = "La tarjeta esta vencida";
	public static final String ERROR_NOMBRE_DUENIO_LARGO = "El nombre del dueño supera los " + MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA;
	public static final String ERROR_FORMATO_TRANSACTION_AMOUNT = "El formato de la cantidad de transaccion es invalido";


	
	public TarjetaDeCredito(String numeroTarjeta, String fechaExpiracion,String nombreDuenio){
		validarTarjeta(numeroTarjeta, fechaExpiracion, nombreDuenio);
		this.numeroTarjeta = numeroTarjeta;
		this.fechaExpiracion = fechaExpiracion;
		this.nombreDuenio = nombreDuenio;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TarjetaDeCredito){
			return (numeroTarjeta.equals(((TarjetaDeCredito) obj).getNumeroTarjeta()) && nombreDuenio.equals(((TarjetaDeCredito) obj).getNombreDuenio()) && fechaExpiracion.equals(((TarjetaDeCredito) obj).getFechaExpiracion())   );
		}else{
			return false;
		}
	}
	
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public String getFechaExpiracion() {
		return fechaExpiracion;
	}

	public String getNombreDuenio() {
		return nombreDuenio;
	}


	private boolean validarTarjeta(String numeroTarjeta, String fechaExpiracion,String nombreDuenio){
		
		if(numeroTarjeta.length() != 16) throw new Error(ERROR_TARJETA_DEBE_TENER_16_CARACTERES);
	
		if(!numeroTarjeta.matches("[0-9]+")) throw new Error(ERROR_TARJETA_DEBEN_SER_DIGITOS);
		
		if(!nombreDuenio.matches("[A-Z]+((\\s*[A-Z]*)|(\\s*[A-Z]*\\s*))")) throw new Error(ERROR_DUENIO_FORMATO_INVALIDO);
		return true;
	}


	
	public boolean noEstaVencidaActualmente(){
		int mes = Integer.parseInt(fechaExpiracion.substring(0, 2));
		int anio = Integer.parseInt(fechaExpiracion.substring(2, 6));
		
		GregorianCalendar ahora = new GregorianCalendar();
		GregorianCalendar vencTarj = new GregorianCalendar(anio, mes-1, 1);
		
		if(ahora.before(vencTarj) || ahora.equals(vencTarj)){
			return true;
		}else{
			throw new Error(ERROR_TARJETA_VENCIDA);
		}
	}  
	
	public boolean esValidaParaMerchantProccesor(String cantidadDeTransaccion){
		if(!cantidadDeTransaccion.matches("^[0-9]{1,15}+\\.+[0-9]{2}")){
			throw new Error(ERROR_FORMATO_TRANSACTION_AMOUNT);
		}else if(nombreDuenio.length()>MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA){
			throw new Error(ERROR_NOMBRE_DUENIO_LARGO);
		}else if(fechaExpiracion.length() != 6){
			throw new Error(ERROR_FECHA_EXPIRACION_INVALIDA);
		}
		return true;
	}

}

