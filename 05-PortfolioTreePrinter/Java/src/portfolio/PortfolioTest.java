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

import java.util.Hashtable;
import java.util.List;

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
		Withdraw withdraw = Withdraw.registerForOn(50,account);
		
		assertEquals(50.0,account.balance(),0.0);
		assertEquals(50.0,withdraw.value(),0.0);
	}

	@Test
	public void test04PortfolioBalanceIsSumOfManagedAccountsBalance(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
	 	
		Deposit.registerForOn(100,account1);
		Deposit.registerForOn(200,account2);
		
		assertEquals(300.0,complexPortfolio.balance(),0.0);
	}

	@Test
	public void test05PortfolioCanManagePortfolios(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);
		
		Deposit.registerForOn(100,account1);
		Deposit.registerForOn(200,account2);
		Deposit.registerForOn(300,account3);
		assertEquals(600.0,composedPortfolio.balance(),0.0);
	}

	@Test
	public void test06ReceptiveAccountsKnowsRegisteredTransactions(){
		ReceptiveAccount account = new ReceptiveAccount ();
		Deposit deposit = Deposit.registerForOn(100,account);
		Withdraw withdraw = Withdraw.registerForOn(50,account);
		
		assertTrue(account.registers(deposit));
		assertTrue(account.registers(withdraw));
	}

	@Test
	public void test07ReceptiveAccountsDoNotKnowNotRegisteredTransactions(){
		ReceptiveAccount account = new ReceptiveAccount ();
		Deposit deposit = new Deposit (100);
		Withdraw withdraw = new Withdraw (50);
		
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
	
	@Test
	public void test18aTransferShouldRegistersATransferDepositOnToAccount(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Transfer transfer = Transfer.registerFor(100,fromAccount, toAccount);
		
		assertTrue(toAccount.registers(transfer.depositLeg()));
	}

	@Test
	public void test18bTransferShouldRegistersATransferWithdrawOnFromAccount(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Transfer transfer = Transfer.registerFor(100,fromAccount, toAccount);
		
		assertTrue(fromAccount.registers(transfer.withdrawLeg()));
	}

	@Test
	public void test18cTransferLegsKnowTransfer(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Transfer transfer = Transfer.registerFor(100,fromAccount, toAccount);
		
		assertEquals(transfer.depositLeg().transfer(),transfer.withdrawLeg().transfer());
	}

	@Test
	public void test18dTransferKnowsItsValue(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Transfer transfer = Transfer.registerFor(100,fromAccount, toAccount);
		
		assertEquals(100,transfer.value(),0.0);
	}

	@Test
	public void test18eTransferShouldWithdrawFromFromAccountAndDepositIntoToAccount(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Transfer.registerFor(100,fromAccount, toAccount);
		
		assertEquals(-100.0, fromAccount.balance(),0.0);
		assertEquals(100.0, toAccount.balance(),0.0);
	}

	@Test
	public void test19AccountSummaryShouldProvideHumanReadableTransactionsDetail(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Deposit.registerForOn(100,fromAccount);
		Withdraw.registerForOn(50,fromAccount);
		Transfer.registerFor(100,fromAccount, toAccount);
		
		List<String> lines = accountSummaryLines(fromAccount);
		
		assertEquals(3,lines.size());
		assertEquals("Depósito por 100.0", lines.get(0));
		assertEquals("Extracción por 50.0", lines.get(1));
		assertEquals("Transferencia por -100.0", lines.get(2));
	}

	private List<String> accountSummaryLines(ReceptiveAccount fromAccount) {
		throw new UnsupportedOperationException();
	}

	@Test
	public void test20ShouldBeAbleToBeQueryTransferNet(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Deposit.registerForOn(100,fromAccount);
		Withdraw.registerForOn(50,fromAccount);
		Transfer.registerFor(100,fromAccount, toAccount);
		Transfer.registerFor(250,toAccount, fromAccount);
		
		assertEquals(150.0,accountTransferNet(fromAccount),0.0);
		
		assertEquals(-150.0,accountTransferNet(toAccount),0.0);
	}

	private double accountTransferNet(ReceptiveAccount account) {
		throw new UnsupportedOperationException();
	}

	@Test
	public void test21CertificateOfDepositShouldWithdrawInvestmentValue(){
		ReceptiveAccount account = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Deposit.registerForOn(1000,account);
		Withdraw.registerForOn(50,account);
		Transfer.registerFor(100,account, toAccount);
		CertificateOfDeposit.registerFor(100,30,0.1,account);
		
		assertEquals(100.0,investmentNet(account),0.0);
		assertEquals(750.0,account.balance(),0.0);
	}

	private double investmentNet(ReceptiveAccount account) {
		throw new UnsupportedOperationException();
	}

	@Test
	public void test22ShouldBeAbleToQueryInvestmentEarnings(){
		ReceptiveAccount account = new ReceptiveAccount ();
		
		CertificateOfDeposit.registerFor(100,30,0.1,account);
		CertificateOfDeposit.registerFor(100,60,0.15,account);

		double investmentEarnings = 
			100.0*(0.1/360)*30 +
			100.0*(0.15/360)*60;
		
		assertEquals(investmentEarnings,investmentEarnings(account),0.0);
	}

	private double investmentEarnings(ReceptiveAccount account) {
		throw new UnsupportedOperationException();
	}

	@Test
	public void test23AccountSummaryShouldWorkWithCertificateOfDeposit(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Deposit.registerForOn(100,fromAccount);
		Withdraw.registerForOn(50,fromAccount);
		Transfer.registerFor(100,fromAccount, toAccount);
		CertificateOfDeposit.registerFor(1000, 30, 0.1, fromAccount);
		
		List<String> lines = accountSummaryLines(fromAccount);
		
		assertEquals(4,lines.size());
		assertEquals("Depósito por 100.0", lines.get(0));
		assertEquals("Extracción por 50.0", lines.get(1));
		assertEquals("Transferencia por -100.0", lines.get(2));
		assertEquals("Plazo fijo por 1000.0 durante 30 días a una tna de 0.1", lines.get(3));
	}

	@Test
	public void test24ShouldBeAbleToBeQueryTransferNetWithCertificateOfDeposit(){
		ReceptiveAccount fromAccount = new ReceptiveAccount ();
		ReceptiveAccount toAccount = new ReceptiveAccount ();

		Deposit.registerForOn(100,fromAccount);
		Withdraw.registerForOn(50,fromAccount);
		Transfer.registerFor(100,fromAccount, toAccount);
		Transfer.registerFor(250,toAccount, fromAccount);
		CertificateOfDeposit.registerFor(1000, 30, 0.1, fromAccount);
		
		assertEquals(150.0,accountTransferNet(fromAccount),0.0);
		assertEquals(-150.0,accountTransferNet(toAccount),0.0);
	}

	@Test
	public void test25PortfolioTreePrinter(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);

		Hashtable<SummarizingAccount, String> accountNames = new Hashtable<SummarizingAccount, String>();
		accountNames.put(composedPortfolio, "composedPortfolio");
		accountNames.put(complexPortfolio, "complexPortfolio");
		accountNames.put(account1, "account1");
		accountNames.put(account2, "account2");
		accountNames.put(account3, "account3");
		
		List<String> lines = portfolioTreeOf(composedPortfolio, accountNames);
		
		assertEquals(5, lines.size());
		assertEquals("composedPortfolio", lines.get(0));
		assertEquals(" complexPortfolio", lines.get(1));
		assertEquals("  account1", lines.get(2));
		assertEquals("  account2", lines.get(3));
		assertEquals(" account3", lines.get(4));
		
	}

	private List<String> portfolioTreeOf(Portfolio composedPortfolio,
			Hashtable<SummarizingAccount, String> accountNames) {
		throw new UnsupportedOperationException();
	}

	@Test
	public void test26ReversePortfolioTreePrinter(){
		ReceptiveAccount account1 = new ReceptiveAccount ();
		ReceptiveAccount account2 = new ReceptiveAccount ();
		ReceptiveAccount account3 = new ReceptiveAccount ();
		Portfolio complexPortfolio = Portfolio.createWith(account1,account2);
		Portfolio composedPortfolio = Portfolio.createWith(complexPortfolio,account3);

		Hashtable<SummarizingAccount, String> accountNames = new Hashtable<SummarizingAccount, String>();
		accountNames.put(composedPortfolio, "composedPortfolio");
		accountNames.put(complexPortfolio, "complexPortfolio");
		accountNames.put(account1, "account1");
		accountNames.put(account2, "account2");
		accountNames.put(account3, "account3");
		
		List<String> lines = reversePortfolioTreeOf(composedPortfolio, accountNames);
		
		assertEquals(5, lines.size());
		assertEquals(" account3", lines.get(0));
		assertEquals("  account2", lines.get(1));
		assertEquals("  account1", lines.get(2));
		assertEquals(" complexPortfolio", lines.get(3));
		assertEquals("composedPortfolio", lines.get(4));
		
	}

	private List<String> reversePortfolioTreeOf(Portfolio composedPortfolio,
			Hashtable<SummarizingAccount, String> accountNames) {
		throw new UnsupportedOperationException();
	}
}
