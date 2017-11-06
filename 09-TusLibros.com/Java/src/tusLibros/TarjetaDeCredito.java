package tusLibros;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TarjetaDeCredito {
	
	private String numeroTarjeta;
	private YearMonth fechaExpiracion;
	private String nombreDuenio;
	
	public static final String ERROR_TARJETA_DEBE_TENER_16_CARACTERES = "La tarjeta debe tener 16 caracteres";
	public static final String ERROR_TARJETA_DEBEN_SER_DIGITOS = "La tarjeta debe tener todos dígitos";
	public static final String ERROR_DUENIO_FORMATO_INVALIDO = "El dueño de la tarjeta debe comenzar con letras mayúsculas y solo puede contener letras mayúsculas y espacios.";
	
	public TarjetaDeCredito(String numeroTarjeta, YearMonth fechaExpiracion,String nombreDuenio){
		validarTarjeta(numeroTarjeta, nombreDuenio);
		this.numeroTarjeta = numeroTarjeta;
		this.fechaExpiracion = fechaExpiracion;
		this.nombreDuenio = nombreDuenio;
	}
	
	private boolean validarTarjeta(String numeroTarjeta, String nombreDuenio){
		
		if(numeroTarjeta.length() != 16) throw new Error(ERROR_TARJETA_DEBE_TENER_16_CARACTERES);
	
		if(!numeroTarjeta.matches("[0-9]+")) throw new Error(ERROR_TARJETA_DEBEN_SER_DIGITOS);
		
		if(!nombreDuenio.matches("[A-Z]+((\\s*[A-Z]*)|(\\s*[A-Z]*\\s*))")) throw new Error(ERROR_DUENIO_FORMATO_INVALIDO);
		return true;
	}

	public boolean estasVencidaAEstaFecha(LocalDate fecha){
		return fechaExpiracion.isBefore(YearMonth.of(fecha.getYear(), fecha.getMonth()));
	}
		
}

