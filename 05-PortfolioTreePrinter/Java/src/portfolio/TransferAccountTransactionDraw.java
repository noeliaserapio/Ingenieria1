package portfolio;

public class TransferAccountTransactionDraw extends TransferLeg{

	private double value;
	private Transfer trans;
	
	public TransferAccountTransactionDraw(double value, Transfer trans) {
		this.value = value;
		this.trans = trans;
	}
	
	public double value() {
		return value;

	}

	public Transfer transfer() {
		return trans;
	}
	
	public double sumar() {
		return -value;
	}
	

}
