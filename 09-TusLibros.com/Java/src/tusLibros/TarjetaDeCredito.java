package tusLibros;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TarjetaDeCredito {
	
	private String numeroTarjeta;
	private String fechaExpiracion;
	private String nombreDuenio;
	public static final int MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA = 30;

	
	public TarjetaDeCredito(String numeroTarjeta, String fechaExpiracion,String nombreDuenio){
		validarTarjeta(numeroTarjeta, fechaExpiracion, nombreDuenio);
		this.numeroTarjeta = numeroTarjeta;
		this.fechaExpiracion = fechaExpiracion;
		this.nombreDuenio = nombreDuenio;
	}
	
	private boolean validarTarjeta(String numeroTarjeta, String fechaExpiracion,String nombreDuenio){
		return true;
	}

	public boolean estasVencidaAEstaFecha(Calendar fecha){
		// hacer la logica
		return true;
	}
	
	public boolean estasvencidaActualmente(){
		int mes = Integer.parseInt(fechaExpiracion.substring(0, 2));
		int anio = Integer.parseInt(fechaExpiracion.substring(2, 6));
		
		GregorianCalendar ahora = new GregorianCalendar();
		GregorianCalendar vencTarj = new GregorianCalendar(anio, mes-1, 1);
		
		if(ahora.before(vencTarj) || ahora.equals(vencTarj)){
			return false;
		}else{
			return true;
		}
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
		}else if(fechaExpiracion.length() != 6){
			return false;
		}
		return true;
	}

}

