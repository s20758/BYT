package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, NOK0;
	
	@Before
	public void setUp() {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
		NOK0 = new Money(0, NOK);
	}

	@Test
	public void testGetAmount() {
		//here we check if our getAmount method is able to
		//return positive/negative values, 0, values for objects with uninitialized currency

		//preserving the "2 digits after the decimal point" rule

		assertEquals("10000 as in 100.00", Integer.valueOf(10000), SEK100.getAmount());
		assertEquals("1000 as in 10.00", Integer.valueOf(1000), EUR10.getAmount());
		assertEquals("20000 as in 200.00", Integer.valueOf(20000), SEK200.getAmount());
		assertEquals("2000 as in 20.00", Integer.valueOf(2000), EUR20.getAmount());
		assertEquals("0", Integer.valueOf(0), SEK0.getAmount());
		assertEquals("0", Integer.valueOf(0), EUR0.getAmount());
		assertEquals("-10000 as in -100.00", Integer.valueOf(-10000), SEKn100.getAmount());
		assertEquals("0", Integer.valueOf(0), NOK0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		//here we check if our getCurrency method is able to return correct currencies
		
		assertEquals("SEK", SEK, SEK100.getCurrency());
		assertEquals("EUR", EUR, EUR10.getCurrency());
		assertEquals("SEK", SEK, SEK200.getCurrency());
		assertEquals("EUR", EUR, EUR20.getCurrency());
		assertEquals("SEK", SEK, SEK0.getCurrency());
		assertEquals("EUR", EUR, EUR0.getCurrency());
		assertEquals("SEK", SEK, SEKn100.getCurrency());
		assertEquals("NOK", NOK, NOK0.getCurrency());
	}

	@Test (expected=NullPointerException.class)
	public void testToString() {
		//here we check if method toString is able to correctly format and return 
		//strings for positive/negative values, 0, values for objects with uninitialized currency
		
		assertEquals("100.00 SEK", "100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR", "10.00 EUR", EUR10.toString());
		assertEquals("200.00 SEK", "200.00 SEK", SEK200.toString());
		assertEquals("20.00 EUR", "20.00 EUR", EUR20.toString());
		assertEquals("0 SEK", "0 SEK", SEK0.toString());
		assertEquals("0 EUR", "0 EUR", EUR0.toString());
		assertEquals("-100.00 SEK", "-100.00 SEK", SEKn100.toString());
		//raises NullPointerException since currency isn't initialized
		assertEquals("0 NOK", "0 NOK", NOK0.toString());
	}

	@Test (expected=NullPointerException.class)
	public void testGlobalValue() {
		//here we check if universalValue method is able to return 
		//proper numbers with positive/negative values, 0, values for objects with uninitialized currency
		
		//preserving the "2 digits after the decimal point" rule

		assertEquals("1500 as in 15.00", Integer.valueOf(1500), SEK100.universalValue());
		assertEquals("1500 as in 15.00", Integer.valueOf(1500), EUR10.universalValue());
		assertEquals("3000 as in 30.00", Integer.valueOf(3000), SEK200.universalValue());
		assertEquals("3000 as in 30.00", Integer.valueOf(3000), EUR20.universalValue());
		assertEquals("0", Integer.valueOf(0), SEK0.universalValue());
		assertEquals("0", Integer.valueOf(0), EUR0.universalValue());
		assertEquals("-1500 as in -15.00", Integer.valueOf(-1500), SEKn100.universalValue());
		//raises NullPointerException since currency isn't initialized
		assertEquals("0", Integer.valueOf(0), NOK0.universalValue());
	}

	@Test (expected=NullPointerException.class)
	public void testEqualsMoney() {
		//here we test equals method, making sure money that's supposed to be 
		//equal to some other money is equal, and vice versa, also for uninitialized currency

		assertTrue("Should be equal", SEK100.equals(EUR10));
		assertTrue("Should be equal", SEK200.equals(EUR20));
		assertTrue("Should be equal", SEK0.equals(EUR0));
		assertFalse("Should not be equal", SEK0.equals(EUR10));
		assertFalse("Should not be equal", EUR10.equals(SEKn100));
		assertFalse("Should not be equal", EUR0.equals(SEKn100));
		assertFalse("Should not be equal", SEK100.equals(SEK200));
		//raises NullPointerException since currency isn't initialized
		assertTrue(NOK0.equals(EUR0));
	}

	@Test (expected=NullPointerException.class)
	public void testAdd() {
		//here we test add method, comparing if the amount/currency of money after addition
		//is equal to amount of other money/correct amount, also for uninitialized currency

		assertEquals(SEK200.getAmount(), SEK100.add(SEK100).getAmount());
		assertEquals(SEK200.getCurrency(), SEK100.add(SEK100).getCurrency());

		assertEquals(Integer.valueOf(0), SEK0.add(EUR0).getAmount());
		assertEquals(SEK, SEK0.add(EUR0).getCurrency());

		assertEquals(Integer.valueOf(20000), SEK100.add(EUR10).getAmount());
		assertEquals(SEK, SEK100.add(EUR10).getCurrency());

		assertEquals(Integer.valueOf(0), SEK100.add(SEKn100).getAmount());
		assertEquals(SEK, SEK100.add(SEKn100).getCurrency());

		//raises NullPointerException since currency isn't initialized
		assertEquals(Integer.valueOf(0), NOK0.add(EUR0).getAmount());
	}

	@Test (expected=NullPointerException.class)
	public void testSub() {
		//here we test sub method, comparing if the amount of money after subtraction
		//is equal to amount of other money/correct amount, also for uninitialized currency
		
		assertEquals(SEK100.getAmount(), SEK200.sub(SEK100).getAmount());
		assertEquals(SEK100.getCurrency(), SEK200.sub(SEK100).getCurrency());

		assertEquals(Integer.valueOf(0), SEK100.sub(EUR10).getAmount());
		assertEquals(SEK, SEK100.sub(EUR10).getCurrency());

		assertEquals(Integer.valueOf(-20000), SEKn100.sub(SEK100).getAmount());
		assertEquals(SEK, SEKn100.sub(SEK100).getCurrency());

		assertEquals(Integer.valueOf(0), SEK0.sub(EUR0).getAmount());
		assertEquals(SEK, SEK0.sub(EUR0).getCurrency());

		//raises NullPointerException since currency isn't initialized
		assertEquals(Integer.valueOf(0), NOK0.sub(EUR0).getAmount());
	}

	@Test
	public void testIsZero() {
		//here in method isZero we check if amount of money, that is supposed to be 0
		//is zero, and vice versa
		
		assertFalse("Should not be 0", SEK100.isZero());
		assertFalse("Should not be 0", EUR10.isZero());
		assertFalse("Should not be 0", SEK200.isZero());
		assertFalse("Should not be 0", EUR20.isZero());
		assertTrue("Should be 0", SEK0.isZero());
		assertTrue("Should be 0", EUR0.isZero());
		assertFalse("Should not be 0", SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		//here in method negate we check if negated amount of given money
		//is equal to what it's supposed to be
		//we check if it works for positive/negative values, 0, values for objects with uninitialized currency
		
		//preserving the "2 digits after the decimal point" rule

		assertEquals("-10000 as in -100.00", Integer.valueOf(-10000), SEK100.negate().getAmount());
		assertEquals("SEK", SEK, SEK100.negate().getCurrency());

		assertEquals("0", Integer.valueOf(0), SEK0.negate().getAmount());
		assertEquals("SEK", SEK, SEK0.negate().getCurrency());

		assertEquals("10000 as in 100.00", Integer.valueOf(10000), SEKn100.negate().getAmount());
		assertEquals("SEK", SEK, SEKn100.negate().getCurrency());

		assertEquals(NOK0.getAmount(), NOK0.negate().getAmount());
		assertEquals(NOK, NOK0.negate().getCurrency());
	}

	@Test (expected=NullPointerException.class)
	public void testCompareTo() {
		//here in method compareTo we check if two Moneys are
		//equal/first Money is bigger/first Money is smaller

		assertEquals("0", 0, SEK100.compareTo(EUR10));
		assertEquals("1", 1, SEK0.compareTo(SEKn100));
		assertEquals("-1", -1, SEKn100.compareTo(SEK0));
		//raises NullPointerException since currency isn't initialized
		assertEquals(1, SEK0.compareTo(NOK0));
	}
}