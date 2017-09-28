package portfolio;

public class AccountVisitor {
	
	public double sumar(Withdraw transaction) {
		return (-transaction.value());
	}
	
	public double sumar(Deposit transaction) {
		return (transaction.value());
	}
	
	public double sumar(TransferAccountTransactionDraw transaction) {
		return (-transaction.value());
	}
	
	public double sumar(TransferAccountTransactionDeposit transaction) {
		return ( transaction.value());
	}



	
}
