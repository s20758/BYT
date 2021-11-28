package b_Money;

import java.text.DecimalFormat;

public class Currency {
	private String name;
	private Double rate;
	
	/**
	 * New Currency
	 * The rate argument of each currency indicates that Currency's "universal" exchange rate.
	 * Imagine that we define the rate of each currency in relation to some universal currency.
	 * This means that the rate of each currency defines its value compared to this universal currency.
	 *
	 * @param name The name of this Currency
	 * @param rate The exchange rate of this Currency
	 */
	Currency (String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	/** Convert an amount of this Currency to its value in the general "universal currency"
	 * (As mentioned in the documentation of the Currency constructor)
	 * 
	 * @param amount An amount of cash of this currency.
	 * @return The value of amount in the "universal currency"
	 */
	public Integer universalValue(Integer amount) {
		//for amount==0 because of multiplication the result will be 0
		if (amount == 0) { return 0; }
		//we turn amount into char[], and then assemble a string 
		//with 2 last digits placed after a dot at the end
		char[] arr = amount.toString().toCharArray();
		int len = arr.length;
		String s = "";
		for (int i = 0; i < len - 2; i++) {
			s += arr[i];
		}
		s += "." + arr[len-2] + arr[len-1];
		//main operation
		Double res = Double.parseDouble(s) * this.rate;
		//apply required formatting
		return Integer.parseInt(new DecimalFormat("0.00").format(res).toString().replaceAll("\\,", ""));
	}

	/** Get the name of this Currency.
	 * @return name of Currency
	 */
	public String getName() {
		return this.name;
	}
	
	/** Get the rate of this Currency.
	 * 
	 * @return rate of this Currency
	 */
	public Double getRate() {
		return this.rate;
	}
	
	/** Set the rate of this currency.
	 * 
	 * @param rate New rate for this Currency
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	/** Convert an amount from another Currency to an amount in this Currency
	 * 
	 * @param amount Amount of the other Currency
	 * @param othercurrency The other Currency
	*/
	public Integer valueInThisCurrency(Integer amount, Currency othercurrency) {
		//for amount==0 because of multiplication the result will be 0
		if (amount == 0) { return 0; }
		//we turn amount into char[], and then assemble a string 
		//with 2 last digits placed after a dot at the end
		char[] arr = amount.toString().toCharArray();
		int len = arr.length;
		String s = "";
		for (int i = 0; i < len - 2; i++) {
			s += arr[i];
		}
		s += "." + arr[len-2] + arr[len-1];
		//main operation
		Double res = Double.parseDouble(s) * othercurrency.rate / this.rate;
		//apply required formatting
		return Integer.parseInt(new DecimalFormat("0.00").format(res).toString().replaceAll("\\,", ""));
	}
}
