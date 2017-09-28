package portfolio;

public class TransferAccountTransactionDeposit implements TransferLeg{

	private double value;
	private Transfer trans;
	
	
	public TransferAccountTransactionDeposit(double value, Transfer trans) {
		this.value = value;
		this.trans = trans;
	}
	
	@Override
	public double value() {
		return value;

	}

	@Override
	public Transfer transfer() {
		return trans;
	}
	
	public double sumar() {
		return value;
	}
	

}
