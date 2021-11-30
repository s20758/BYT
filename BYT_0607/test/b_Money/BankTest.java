package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	Hashtable<String, Account> sw_list, nor_list, dan_list;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		sw_list = new Hashtable<>();
		nor_list = new Hashtable<>();
		dan_list = new Hashtable<>();
		SweBank = new Bank("SweBank", SEK, sw_list);
		Nordea = new Bank("Nordea", SEK, nor_list);
		DanskeBank = new Bank("DanskeBank", DKK, dan_list);
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
	public void testOpenAccount() throws AccountExistsException {
		//here we test if the account is opened successfully
		
		//failed initially
		assertFalse("Swebank doesn't have Ann account", SweBank.getAccountList().containsKey("Ann"));
		SweBank.openAccount("Ann");
		assertTrue("Swebank has Ann account", SweBank.getAccountList().containsKey("Ann"));
	}

	@Test (expected = AccountExistsException.class)
	public void testOpenAccountException() throws AccountExistsException {
		//here we test if it throws an exception when account exists already

		//failed initially
		SweBank.openAccount("Ulrika");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		//here we test if it deposits money successfully

		//failed initially
		Integer init_amount = Nordea.getAccountList().get("Bob").getContent().add(new Money(10000, SEK)).universalValue();
		Nordea.deposit("Bob", new Money(10000, SEK));
		assertEquals(Nordea.getAccountList().get("Bob").getContent().universalValue(), init_amount);
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
		Nordea.deposit("Bob", new Money(10000, SEK));

		//simply withdrawing
		Integer init_amount = Nordea.getAccountList().get("Bob").getContent().sub(new Money(5000, SEK)).universalValue();
		Nordea.withdraw("Bob", new Money(5000, SEK));
		assertEquals(Nordea.getAccountList().get("Bob").getContent().universalValue(), init_amount);

		//withdrawing all money left
		Integer init_amount_2 = Nordea.getAccountList().get("Bob").getContent().sub(new Money(5000, SEK)).universalValue();
		Nordea.withdraw("Bob", new Money(5000, SEK));
		assertEquals(Nordea.getAccountList().get("Bob").getContent().universalValue(), init_amount_2);

		//withdrawing from an empty account
		Nordea.withdraw("Bob", new Money(1000, SEK));
		assertEquals(Integer.valueOf(0), Nordea.getAccountList().get("Bob").getContent().universalValue());
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

		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.deposit("Bob", new Money(10000, SEK));

		//1st transfer
		//successful
		Integer init_Ulrika = SweBank.getAccountList().get("Ulrika").getContent().sub(new Money(3000, SEK)).universalValue();
		Integer init_Bob = Nordea.getAccountList().get("Bob").getContent().add(new Money(3000, SEK)).universalValue();

		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(3000, SEK));

		assertEquals(SweBank.getAccountList().get("Ulrika").getContent().universalValue(), init_Ulrika);
		assertEquals(Nordea.getAccountList().get("Bob").getContent().universalValue(), init_Bob);

		//unsuccessful
		Integer init_Ulrika_2 = SweBank.getAccountList().get("Ulrika").getBalance();
		Integer init_Bob_2 = Nordea.getAccountList().get("Bob").getBalance();

		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(10000, SEK));
		assertEquals(init_Ulrika_2, SweBank.getBalance("Ulrika"));
		assertEquals(init_Bob_2, Nordea.getBalance("Bob"));

		//2nd transfer
		Integer init_Bob_3 = SweBank.getAccountList().get("Bob").getContent().sub(new Money(4550, DKK)).universalValue();
		Integer init_Ulrika_3 = SweBank.getAccountList().get("Ulrika").getContent().add(new Money(4550, DKK)).universalValue();

		SweBank.transfer("Bob", "Ulrika", new Money(4550, DKK));

		assertEquals(SweBank.getAccountList().get("Bob").getContent().universalValue(), init_Bob_3);
		assertEquals(SweBank.getAccountList().get("Ulrika").getContent().universalValue(), init_Ulrika_3);
	}

	@Test (expected = AccountDoesNotExistException.class)
	public void testTransferException() throws AccountDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist

		SweBank.transfer("NonExistentAccount", "Ulrika", new Money(4550, DKK));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException, TimedPaymentExistsException, TimedPaymentDoesNotExistException {
		//here we test if timed payments are properly added/removed

		//failed initially
		SweBank.addTimedPayment("Bob", "Coffee", 100, 10, new Money(20000, SEK), Nordea, "Ulrika");
		assertTrue("Timed payment is added", SweBank.getAccountList().get("Bob").getTimedPayments().containsKey("Coffee"));

		SweBank.removeTimedPayment("Bob", "Coffee");
		assertFalse("Timed payment is deleted", SweBank.getAccountList().get("Bob").getTimedPayments().containsKey("Coffee"));
		
		SweBank.tick();
	}
	
	@Test (expected = AccountDoesNotExistException.class)
	public void testTimedPaymentAccountException() throws AccountDoesNotExistException, TimedPaymentExistsException, TimedPaymentDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist

		SweBank.addTimedPayment("NonExistantAccount", "Coffee", 30,10, new Money(100, SEK), Nordea, "Someone");
		SweBank.removeTimedPayment("NonExistantAccount", "Coffee");
	}

	@Test (expected = TimedPaymentExistsException.class)
	public void testTimedPaymentExistsException() throws AccountDoesNotExistException, TimedPaymentExistsException {
		//here we test if it throws an exception when account doesn't exist

		SweBank.addTimedPayment("Ulrika", "Coffee", 30,10, new Money(100, SEK), Nordea, "Someone");
		SweBank.addTimedPayment("Ulrika", "Coffee", 30,10, new Money(100, SEK), Nordea, "Someone");
	}

	@Test (expected = TimedPaymentDoesNotExistException.class)
	public void testTimedPaymentException() throws AccountDoesNotExistException, TimedPaymentDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist

		SweBank.removeTimedPayment("Ulrika", "Payment");
	}
}
