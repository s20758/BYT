package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		//here we test if timed payment is properly added/removed
		
		testAccount.addTimedPayment("test", 30, 10, new Money(10000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("test"));
		testAccount.addTimedPayment("test", 2000, 1000, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("test"));
		testAccount.removeTimedPayment("test");
		assertFalse(testAccount.timedPaymentExists("test"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {	
		//here we test methods timedPaymentExists() and tick()
		
		assertFalse(testAccount.timedPaymentExists("test"));
		testAccount.tick();
	}

	@Test
	public void testAddWithdraw() {
		//here we test if money is deposited/withdrawn correctly
		
		Integer init_amount = testAccount.getBalance().sub(new Money(1000, SEK)).universalValue();
		testAccount.withdraw(new Money(1000, SEK));
		assertEquals(testAccount.getBalance().universalValue(), init_amount);
		
		Integer init_amount_2 = testAccount.getBalance().add(new Money(1000, SEK)).universalValue();
		testAccount.deposit(new Money(1000, SEK));
		assertEquals(testAccount.getBalance().universalValue(), init_amount_2);	
	}
	
	@Test
	public void testGetBalance() {
		//here we test if it gets proper balance
		
		assertTrue(new Money(10000000, SEK).equals(testAccount.getBalance()));
		assertFalse(new Money(2000000, SEK).equals(testAccount.getBalance()));
		assertTrue(new Money(1000000, SEK).equals(SweBank.accountlist.get("Alice").getBalance()));
	}
}
