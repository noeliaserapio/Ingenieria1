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
	public static final int MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA = 30;

	
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
		int anio = fecha.getYear();
		Month mes = fecha.getMonth();
		YearMonth.of(anio, mes);
		return fechaExpiracion.isBefore(YearMonth.of(fecha.getYear(), fecha.getMonth()));
	}
		
	private boolean esValida(double cantidadDeTransaccion){
		String[] transacionSplit = new Double(cantidadDeTransaccion).toString().split(".");
		boolean esValidaCantTransaccion = true;
		if(transacionSplit.length == 2){
			esValidaCantTransaccion = (transacionSplit[1].length() <=2) && (transacionSplit[0].length() <=15);
		}else{
			esValidaCantTransaccion = (transacionSplit[1].length() <=15);
		}
		
		if(nombreDuenio.length()>MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA){
			return false;
		}else if(numeroTarjeta.length() != 16){
			return false;
		}else if(!esValidaCantTransaccion){
			return false;
		}
		return true;
	}

}

