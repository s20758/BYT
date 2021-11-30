package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test (expected=NullPointerException.class)
	public void testGetName() {
		//here we check if our getName method is able to return correct names
		
		assertEquals("SEK", "SEK", SEK.getName());
		assertEquals("DKK", "DKK", DKK.getName());
		assertEquals("EUR", "EUR", EUR.getName());
		//raises NullPointerException since currency isn't initialized
		assertEquals("NOK", NOK.getName());
	}
	
	@Test (expected=NullPointerException.class)
	public void testGetRate() {
		//here we check if our getRate method is able to return correct rates
		
		assertEquals("0.15", Double.valueOf(0.15), SEK.getRate());
		assertEquals("0.20", Double.valueOf(0.20), DKK.getRate());
		assertEquals("1.5", Double.valueOf(1.5), EUR.getRate());
		//raises NullPointerException since currency isn't initialized
		assertEquals(Double.valueOf(1.5), NOK.getRate());
	}
	
	@Test (expected=NullPointerException.class)
	public void testSetRate() {
		//here in method setRate we set the rate of the currency, and then 
		//check with getRate method if it was set correctly
		
		SEK.setRate(0.4);
		assertEquals("0.4", Double.valueOf(0.4), SEK.getRate());
		DKK.setRate(0.6);
		assertEquals("0.6", Double.valueOf(0.6), DKK.getRate());
		EUR.setRate(1.7);
		assertEquals("1.7", Double.valueOf(1.7), EUR.getRate());
		//raises NullPointerException since currency isn't initialized
		NOK.setRate(0.1);
	}
	
	@Test (expected=NullPointerException.class)
	public void testGlobalValue() {
		//here we check if universalValue method is able to return proper numbers
		
		//preserving the "2 digits after the decimal point" rule

		assertEquals("30007 as in 300.07", Integer.valueOf(30007), SEK.universalValue(200050));
		assertEquals("40010 as in 400.10", Integer.valueOf(40010), DKK.universalValue(200050));
		assertEquals("300075 as in 3000.75", Integer.valueOf(300075), EUR.universalValue(200050));
		//raises NullPointerException since currency isn't initialized
		assertEquals(Integer.valueOf(300075), NOK.universalValue(200050));
	}
	
	@Test (expected=NullPointerException.class)
	public void testValueInThisCurrency() {
		//here in valueInThisCurrency method we check if it is able to return 
		//proper numbers with positive/negative values, 0, values for objects with uninitialized currency
		
		//preserving the "2 digits after the decimal point" rule

		assertEquals("2000500 as in 20005.00", Integer.valueOf(2000500), SEK.valueInThisCurrency(200050, EUR));
		assertEquals("150035 as in 1500.35", Integer.valueOf(150035), DKK.valueInThisCurrency(200050, SEK));
		assertEquals("26673 as in 266.73", Integer.valueOf(26673), EUR.valueInThisCurrency(200050, DKK));
		//raises NullPointerException since currency isn't initialized
		assertEquals(Integer.valueOf(300075), NOK.valueInThisCurrency(200050, DKK));
	}

}