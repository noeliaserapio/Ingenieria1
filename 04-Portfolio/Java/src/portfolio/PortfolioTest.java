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

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PortfolioTest {

	@Test
	public void test01ReceptiveAccountHaveZeroAsBalanceWhenCreated(){
		ReceptiveAccount account = new ReceptiveAccount ();

		assertEquals(0.0,account.balance(),0.0);
	}
	
	@Test
	public void test02DepositIncreasesBalanceOnTransactionValue(){
		ReceptiveAccount account = new ReceptiveAccount ();
		Deposit.registerForOn(100,account);
		
		assertEquals(100.0,account.balance(),0.0);
		
	}

	@Test
	public void test03WithdrawDecreasesBalanceOnTransactionValue(){
		ReceptiveAccount account = new ReceptiveAccount ();
		Deposit.registerForOn(100,account);
		Withdraw.registerForOn(-50,account);
		
		assertEquals(50.0,account.balance(),0.0);
	}

	@Test
	public void test04PortfolioBalanceIsSumOfManagedAccountsBalance(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		Portfolio complexPortfolio = new Portfolio();
		complexPortfolio.addAccount(account1);
		complexPortfolio.addAccount(account2);
	 	
		Deposit.registerForOn(100,account1);
		Deposit.registerForOn(200,account2);
		
		assertEquals(300.0,complexPortfolio.balance(),0.0);
	}

	@Test
	public void test05PortfolioCanManagePortfolios(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.
				createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.
				createWith(complexPortfolio,account3);
		
		Deposit.registerForOn(100,account1);
		Deposit.registerForOn(200,account2);
		Deposit.registerForOn(300,account3);
		assertEquals(600.0,composedPortfolio.balance(),0.0);
	}

	@Test
	public void test06ReceptiveAccountsKnowsRegisteredTransactions(){
		ReceptiveAccount account = new ReceptiveAccount ();
		Deposit deposit = Deposit.registerForOn(100,account);
		Withdraw withdraw = Withdraw.registerForOn(-50,account);
		
		assertTrue(account.registers(deposit));
		assertTrue(account.registers(withdraw));
	}

	@Test
	public void test07ReceptiveAccountsDoNotKnowNotRegisteredTransactions(){
		ReceptiveAccount account = new ReceptiveAccount ();
		Deposit deposit = new Deposit (100);
		Withdraw withdraw = new Withdraw (-50);
		
		assertFalse(account.registers(deposit));
		assertFalse(account.registers(withdraw));
	}
	
	@Test
	public void test08PortofoliosKnowsTransactionsRegisteredByItsManagedAccounts(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);
		
		Deposit deposit1 = Deposit.registerForOn(100,account1);
		Deposit deposit2 = Deposit.registerForOn(200,account2);
		Deposit deposit3 = Deposit.registerForOn(300,account3);
		
		assertTrue(composedPortfolio.registers(deposit1));
		assertTrue(composedPortfolio.registers(deposit2));
		assertTrue(composedPortfolio.registers(deposit3));
	}

	@Test
	public void test09PortofoliosDoNotKnowTransactionsNotRegisteredByItsManagedAccounts(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);
		
		Deposit deposit1 = new Deposit(100);
		Deposit deposit2 = new Deposit(200);
		Deposit deposit3 = new Deposit(300);
		
		assertFalse(composedPortfolio.registers(deposit1));
		assertFalse(composedPortfolio.registers(deposit2));
		assertFalse(composedPortfolio.registers(deposit3));
	}

	@Test
	public void test10ReceptiveAccountManageItSelf(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		
		assertTrue(account1.manages(account1));
	}

	@Test
	public void test11ReceptiveAccountDoNotManageOtherAccount(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		
		assertFalse(account1.manages(account2));
	}
	
	@Test
	public void test12PortfolioManagesComposedAccounts(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		
		assertTrue(complexPortfolio.manages(account1));
		assertTrue(complexPortfolio.manages(account2));
		assertFalse(complexPortfolio.manages(account3));
	}

	@Test
	public void test13PortfolioManagesComposedAccountsAndPortfolios(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);
		
		assertTrue(composedPortfolio.manages(account1));
		assertTrue(composedPortfolio.manages(account2));
		assertTrue(composedPortfolio.manages(account3));
		assertTrue(composedPortfolio.manages(complexPortfolio));
	}

	@Test
	public void test14AccountsKnowsItsTransactions(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		
		Deposit deposit1 = Deposit.registerForOn(100,account1);
		
		assertEquals(1,account1.transactions().size());
		assertTrue(account1.transactions().contains(deposit1));
	}
	
	@Test
	public void test15PortfolioKnowsItsAccountsTransactions(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);
		
		Deposit deposit1 = Deposit.registerForOn(100,account1);
		Deposit deposit2 = Deposit.registerForOn(200,account2);
		Deposit deposit3 = Deposit.registerForOn(300,account3);
		
		assertEquals(3,composedPortfolio.transactions().size());
		assertTrue(composedPortfolio.transactions().contains(deposit1));
		assertTrue(composedPortfolio.transactions().contains(deposit2));
		assertTrue(composedPortfolio.transactions().contains(deposit3));
	}

	@Test
	public void test16CanNotCreatePortfoliosWithRepeatedAccount(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		try {
			Portfolio.createWith(account1,account1);
			fail();
		}catch (RuntimeException invalidPortfolio) {
			assertEquals(Portfolio.ACCOUNT_ALREADY_MANAGED, invalidPortfolio.getMessage()); 
		}
		
	}

	@Test
	public void test17CanNotCreatePortfoliosWithAccountsManagedByOtherManagedPortfolio(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		try {
			Portfolio.createWith(complexPortfolio,account1);
			fail();
		}catch (RuntimeException invalidPortfolio) {
			assertEquals(Portfolio.ACCOUNT_ALREADY_MANAGED, invalidPortfolio.getMessage()); 
		}
	}
}
