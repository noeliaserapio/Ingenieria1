package tusLibros;

import java.util.List;

public class MerchantSimulator implements MerchantProcessor{

	public static final String ERROR_TARJETA_ROBADA = "La tarjeta es robada";
	public static final String ERROR_TARJETA_INVALIDA = "La tarjeta no existe o es invalida";

	private List<TarjetaDeCredito> listaTarjetasRobadas;
	private List<Cuenta> cuentas;
	
	public MerchantSimulator(List<TarjetaDeCredito> listaTarjetasRobadas, List<Cuenta> cuentas) {
		this.listaTarjetasRobadas = listaTarjetasRobadas;
		this.cuentas = cuentas;
	}

	@Override
	public void debitarTarjeta(TarjetaDeCredito tarj, String cantidadTransaccion) {
		if(listaTarjetasRobadas.contains(tarj)) throw new Error(ERROR_TARJETA_ROBADA);
		for(Cuenta cu : cuentas ){
			if(cu.getTarj().equals(tarj)) {
				cu.debitar(Double.parseDouble(cantidadTransaccion));
				return;
			}
		}
		throw new Error(ERROR_TARJETA_INVALIDA);
	}

}
