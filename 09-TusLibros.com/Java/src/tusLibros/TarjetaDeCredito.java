package tusLibros;

import java.util.Calendar;

public class TarjetaDeCredito {
	
	private String numeroTarjeta;
	private String fechaExpiracion;
	private String nombreDuenio;
	
	public TarjetaDeCredito(String numeroTarjeta, String fechaExpiracion,String nombreDuenio){
		this.numeroTarjeta = numeroTarjeta;
		this.fechaExpiracion = fechaExpiracion;
		this.nombreDuenio = nombreDuenio;
	}
	
	public boolean estasvencidaAEstaFecha(Calendar fecha){
		// hacer la logica
		return true;
	}
	
	private boolean validarTarjeta(String numeroTarjeta, String fechaExpiracion,String nombreDuenio){
		return true;
	}

}
