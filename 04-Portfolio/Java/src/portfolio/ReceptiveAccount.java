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

import java.util.ArrayList;
import java.util.List;

public class ReceptiveAccount implements SummarizingAccount {

	private ArrayList<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
	
	public double balance() {
		double balance = 0.0;
		for (AccountTransaction transaction : transactions) {
			balance = balance + transaction.value();
		}
		
		return balance;
	}

	public void register(AccountTransaction transaction) {
		transactions.add(transaction);
	}
	
	public boolean registers(AccountTransaction transaction) {
		return transactions.contains(transaction);
	}

	public boolean manages(SummarizingAccount account) {
		return this == account;
	}
	
	public List<AccountTransaction> transactions() {
		return new ArrayList<AccountTransaction>(transactions);
	}

}
