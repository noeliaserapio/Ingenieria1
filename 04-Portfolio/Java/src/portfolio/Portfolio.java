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
	
	private List<SummarizingAccount> sumarizingAccounts = new ArrayList<SummarizingAccount>();
	
	
	
	public static Portfolio createWith(SummarizingAccount anAccount1, SummarizingAccount anAccount2) {
		Portfolio aPortfolio = new Portfolio();
		aPortfolio.addAccount(anAccount1);
		aPortfolio.addAccount(anAccount2);
		return aPortfolio;
	}

	public Portfolio(){
	}

	public double balance() {
		double balance = 0.0;
		for(SummarizingAccount account :sumarizingAccounts){
			balance += account.balance();
		}
		return balance;
	}
	
	public boolean registers(AccountTransaction transaction) {
		for(SummarizingAccount account :sumarizingAccounts){
			if(account.registers(transaction)) return true;
		}
		return false;
	}

	public List<AccountTransaction> transactionsOf(SummarizingAccount account) {
		List<AccountTransaction> accountTransactionResult = new ArrayList<AccountTransaction>();
		if(this.manages(account)){
			accountTransactionResult.addAll(account.transactions());
		}else{
			throw new RuntimeException(ACCOUNT_NOT_MANAGED);
		}
		return accountTransactionResult;
	}
	
	public boolean manages(SummarizingAccount account) {
		if(this == account){
			return true;
		}else{
			for(SummarizingAccount summAccount :sumarizingAccounts){
				if(summAccount.manages(account)) return true;
			}
			return false;	
		}
	}
	
	public List<AccountTransaction> transactions() {
		List<AccountTransaction> accountTransactionResult = new ArrayList<AccountTransaction>();
		for(SummarizingAccount account :sumarizingAccounts){
			accountTransactionResult.addAll(account.transactions());
		}
		return accountTransactionResult;
	}

	public void addAccount(SummarizingAccount anAccount) {
		if(this.manages(anAccount)) throw new RuntimeException(ACCOUNT_ALREADY_MANAGED);
		sumarizingAccounts.add(anAccount);
	}
}
