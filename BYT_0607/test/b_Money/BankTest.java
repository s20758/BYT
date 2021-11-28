package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		//here we check if our getName method is able to return correct names
		
		assertEquals("SweBank", "SweBank", SweBank.getName());
		assertEquals("Nordea", "Nordea", Nordea.getName());
		assertEquals("DanskeBank", "DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		//here we check if our getCurrency method is able to return correct currencies
		
		assertEquals("SEK", SEK, SweBank.getCurrency());
		assertEquals("SEK", SEK, Nordea.getCurrency());
		assertEquals("DKK", DKK, DanskeBank.getCurrency());
	}
	
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		//here we test if the account is opened successfully
		
		//failed initially
		SweBank.openAccount("Ann");
		assertTrue("Swebank has Ann account", SweBank.accountlist.containsKey("Ann"));
	}

	@Test (expected = AccountExistsException.class)
	public void testOpenAccountException() throws AccountExistsException, AccountDoesNotExistException {
		//here we test if it throws an exception when account exists already
		
		//failed initially
		SweBank.openAccount("Ulrika");	
	}
	
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		//here we test if it deposits money successfully
		
		//failed initially
		Integer init_amount = Nordea.accountlist.get("Bob").getBalance().add(new Money(10000, SEK)).universalValue();
		Nordea.deposit("Bob", new Money(10000, SEK));
		assertEquals(Nordea.accountlist.get("Bob").getBalance().universalValue(), init_amount);
	}

	@Test (expected = AccountDoesNotExistException.class)
	public void testDepositException() throws AccountDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist
		
		//failed initially
		Nordea.deposit("NonExistingAccount", new Money(10000, SEK));
	}
	
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		//here we test if it withdraws money successfully
		
		//failed initially
		Integer init_amount = Nordea.accountlist.get("Bob").getBalance().sub(new Money(10000, SEK)).universalValue();
		Nordea.withdraw("Bob", new Money(10000, SEK));
		assertEquals(Nordea.accountlist.get("Bob").getBalance().universalValue(), init_amount);
	}

	@Test (expected = AccountDoesNotExistException.class)
	public void testWithdrawException() throws AccountDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist
		
		DanskeBank.withdraw("NonExistingAccount", new Money(10000, DKK));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		//here we test if it gets correct balance
		
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
	}
	
	@Test (expected = AccountDoesNotExistException.class)
	public void testGetBalanceException() throws AccountDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist
		
		SweBank.getBalance("NonExistingAccount");
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		//here we test if transfer is completed successfully
		
		//failed initially
		
		//1st transfer
		Integer init_Ulrika = SweBank.accountlist.get("Ulrika").getBalance().sub(new Money(3300, SEK)).universalValue();
		Integer init_Bob = Nordea.accountlist.get("Bob").getBalance().add(new Money(3300, SEK)).universalValue();
		
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(3300, SEK));	
		
		assertEquals(SweBank.accountlist.get("Ulrika").getBalance().universalValue(), init_Ulrika);
		assertEquals(Nordea.accountlist.get("Bob").getBalance().universalValue(), init_Bob);
		
		//2nd transfer
		Integer init_Bob_2 = SweBank.accountlist.get("Bob").getBalance().sub(new Money(4550, DKK)).universalValue();
		Integer init_Ulrika_2 = SweBank.accountlist.get("Ulrika").getBalance().add(new Money(4550, DKK)).universalValue();
		
		SweBank.transfer("Bob", "Ulrika", new Money(4550, DKK));
		
		assertEquals(SweBank.accountlist.get("Bob").getBalance().universalValue(), init_Bob_2);
		assertEquals(SweBank.accountlist.get("Ulrika").getBalance().universalValue(), init_Ulrika_2);
	}
	
	@Test (expected = AccountDoesNotExistException.class)
	public void testTransferException() throws AccountDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist
		
		SweBank.transfer("NonExistentAccount", "Ulrika", new Money(4550, DKK));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//here we test if timed payments are properly added/removed
		
		//failed initially
		SweBank.addTimedPayment("Bob", "Coffee", 100, 10, new Money(20000, SEK), Nordea, "Ulrika");
		assertTrue("Timed payment is added", SweBank.accountlist.get("Bob").timedpayments.containsKey("Coffee"));
		
		SweBank.removeTimedPayment("Bob", "Coffee");
		assertFalse("Timed payment is deleted", SweBank.accountlist.get("Bob").timedpayments.containsKey("Coffee"));
		
		SweBank.tick();
	}
	
	@Test (expected = AccountDoesNotExistException.class)
	public void testTimedPaymentException() throws AccountDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist
		
		SweBank.removeTimedPayment("NonExistantAccount", "Coffee");
	}
}
