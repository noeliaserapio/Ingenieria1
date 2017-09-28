/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package portfolio;

public class Transfer {
	
	private double value;

	private TransferAccountTransactionDeposit transactionDeposit;
	private TransferAccountTransactionDraw transactionDraw;

	public static Transfer registerFor(double value, ReceptiveAccount fromAccount,
			ReceptiveAccount toAccount) {

		Transfer transf = new Transfer(value);
		
		TransferAccountTransactionDeposit nuevaTransactionDeposit = new TransferAccountTransactionDeposit(value,transf);
		TransferAccountTransactionDraw nuevaTransactionDraw = new TransferAccountTransactionDraw(value,transf);
		
		transf.setTransactionDeposit(nuevaTransactionDeposit);
		transf.setTransactionDraw(nuevaTransactionDraw);
		
		fromAccount.register(nuevaTransactionDraw);
		toAccount.register(nuevaTransactionDeposit);
		
		return transf;
	}

	public double value() {
		return value;
	}
	
	public Transfer (double value) {
		this.value = value;
	}
	
	public void setTransactionDeposit(TransferAccountTransactionDeposit transactionDeposit) {
		this.transactionDeposit = transactionDeposit;
	}

	public void setTransactionDraw(TransferAccountTransactionDraw transactionDraw) {
		this.transactionDraw = transactionDraw;
	}


	public TransferLeg depositLeg() {
		return transactionDeposit;
	}
	
	public TransferLeg withdrawLeg() {
		return transactionDraw;
	}


}
