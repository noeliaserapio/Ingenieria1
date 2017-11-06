package tusLibros;


import java.time.YearMonth;

public class MerchantSimulator implements MerchantProcesor {
	
	public static final int MAXIMA_CANTIDAD_CARACTERES_DUENIO_TARJETA = 30;
	
	private int saldo;
	
	interface Closure {
		public <T extends Throwable> T execute();

	}
		
	public MerchantSimulator(int saldo){
	}
	
	public MerchantSimulator(Closure aClosure){
		aClosure.execute();
		
	}
	
	public void ejecutar(Closure aClosure){
		aClosure.execute();
		
	} 
		
	@Override
	public void debitarDe(String creditCardNumber,
			YearMonth creditCardExpiration, String creditCardOwner,
			double transactionAmout) {
		// TODO Auto-generated method stub
		
	}

	
/*	private boolean esValida(double cantidadDeTransaccion){
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
*/
}
