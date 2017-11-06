package tusLibros;

public class Cuenta {
	
	public static final String CUENTA_SIN_FONDOS = "La cuenta no posee saldo";
	public static final String SALDO_INSUFICIENTE = "El saldo de la cuenta es insuficiente para realizar la operación";
	
	double saldo;
	TarjetaDeCredito tarj;
	
	public Cuenta(double saldo, TarjetaDeCredito tarj) {
		this.saldo = saldo;
		this.tarj = tarj;
	}
	public double getSaldo() {
		return saldo;
	}
	public TarjetaDeCredito getTarj() {
		return tarj;
	}
	public void debitar(double valor) {
		if(saldo == 0) throw new Error(CUENTA_SIN_FONDOS);
		if(saldo< valor) throw new Error(SALDO_INSUFICIENTE);
		saldo -= valor;
	}
	
	

}
