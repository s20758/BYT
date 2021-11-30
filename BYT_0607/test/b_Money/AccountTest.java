package b_Money;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea, DanskeBank, SweBank;
	Account testAccount;
	Hashtable<String, Account> nor_list, dan_list, sw_list;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		nor_list = new Hashtable<>();
		dan_list = new Hashtable<>();
		sw_list = new Hashtable<>();
		Nordea = new Bank("Nordea", SEK, nor_list);
		DanskeBank = new Bank("DanskeBank", DKK, dan_list);
		SweBank = new Bank("SweBank", SEK, sw_list);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", new Money(0, SEK), new Hashtable<>());
		testAccount.deposit(new Money(10000000, SEK));
		Nordea.getAccountList().put("Hans", testAccount);
		SweBank.deposit("Alice", new Money(1000000, SEK));
		DanskeBank.openAccount("Jasper");
	}
	
	@Test
	public void testAddRemoveTimedPayment() throws TimedPaymentExistsException, TimedPaymentDoesNotExistException, AccountDoesNotExistException {
		//here we test if timed payment is properly added/removed
		
		testAccount.addTimedPayment("Payment1", 1, 1, new Money(10000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("Payment1"));
		testAccount.tick();
		assertTrue(testAccount.getTimedPayments().get("Payment1").tick());
		assertEquals(Integer.valueOf(1010000), SweBank.getBalance("Alice"));
		assertEquals(Integer.valueOf(9990000), Nordea.getBalance("Hans"));
		//timed payment repeated
		testAccount.tick();
		assertTrue(testAccount.getTimedPayments().get("Payment1").tick());
		assertEquals(Integer.valueOf(1020000), SweBank.getBalance("Alice"));
		assertEquals(Integer.valueOf(9980000), Nordea.getBalance("Hans"));
		testAccount.removeTimedPayment("Payment1");
		testAccount.tick();
		//after removal money stays the same
		assertEquals(Integer.valueOf(1020000), SweBank.getBalance("Alice"));
		assertEquals(Integer.valueOf(9980000), Nordea.getBalance("Hans"));
	}

	@Test (expected = TimedPaymentExistsException.class)
	public void testTimedPaymentExists() throws TimedPaymentExistsException {
		DanskeBank.getAccountList().get("Jasper").addTimedPayment("Payment", 1, 1, new Money(10000, SEK), SweBank, "Alice");
		DanskeBank.getAccountList().get("Jasper").addTimedPayment("Payment", 1, 1, new Money(10000, SEK), SweBank, "Alice");
	}

	@Test (expected = TimedPaymentDoesNotExistException.class)
	public void testTimedPaymentException() throws TimedPaymentDoesNotExistException {
		//here we test if it throws an exception when account doesn't exist

		DanskeBank.getAccountList().get("Jasper").removeTimedPayment("Payment");
	}

	@Test
	public void testTimedPayment() {
		//here we test method timedPaymentExists()
		
		assertFalse(testAccount.timedPaymentExists("test"));
	}

	@Test
	public void testAddWithdraw() throws AccountDoesNotExistException {
		//here we test if money is deposited/withdrawn correctly
		
		Integer init_amount = testAccount.getContent().sub(new Money(1000, SEK)).universalValue();
		testAccount.withdraw(new Money(1000, SEK));
		assertEquals(testAccount.getContent().universalValue(), init_amount);
		
		Integer init_amount_2 = testAccount.getContent().add(new Money(1000, SEK)).universalValue();
		testAccount.deposit(new Money(1000, SEK));
		assertEquals(testAccount.getContent().universalValue(), init_amount_2);

		Nordea.getAccountList().get("Hans").deposit(new Money(2000, DKK));
		assertEquals(Integer.valueOf(10002666), Nordea.getBalance("Hans"));
	}
	
	@Test
	public void testGetBalance() {
		//here we test if it gets proper balance

		assertEquals(Integer.valueOf(10000000), testAccount.getBalance());
		assertNotEquals(Integer.valueOf(200000), testAccount.getBalance());
		assertEquals(Integer.valueOf(1000000), SweBank.getAccountList().get("Alice").getBalance());
	}
}