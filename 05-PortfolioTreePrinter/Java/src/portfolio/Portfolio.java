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

public class Portfolio implements SummarizingAccount{

	public static final String ACCOUNT_NOT_MANAGED = "No se maneja esta cuenta";
	public static final String ACCOUNT_ALREADY_MANAGED = "La cuenta ya está manejada por otro portfolio";
	private List<SummarizingAccount> summarizingAccounts; 
	
	public static Portfolio createWith(SummarizingAccount anAccount, SummarizingAccount anotherAccount) {
		Portfolio portfolio = new Portfolio();
		portfolio.addAccount(anAccount);
		portfolio.addAccount(anotherAccount);
		
		return portfolio;
	}

	public Portfolio(){
		this.summarizingAccounts = new ArrayList<SummarizingAccount>();
	}
	
	public double balance() {
		double saldo = 0;
		for (SummarizingAccount summarizingAccount : summarizingAccounts) {
			saldo = saldo + summarizingAccount.balance();
		}

		return saldo;
	}
	
	public boolean registers(AccountTransaction transaction) {
		for (SummarizingAccount summarizingAccount : summarizingAccounts) 
			if(summarizingAccount.registers(transaction)) return true;
		
		return false;
	}
	
	public boolean manages(SummarizingAccount account) {
		if (this == account ) return true;
		
		for (SummarizingAccount summarizingAccount : summarizingAccounts) 
			if(summarizingAccount.manages(account)) return true;
		
		return false;
	}
	public List<AccountTransaction> transactions() {
		ArrayList<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
		for (SummarizingAccount summarizingAccount : summarizingAccounts)
			transactions.addAll(summarizingAccount.transactions());
		
		return transactions;
		
	}
	
	public void addAccount(SummarizingAccount anAccount) {
		if (manages(anAccount))
			throw new RuntimeException(ACCOUNT_ALREADY_MANAGED);

		summarizingAccounts.add(anAccount);
	}

}
