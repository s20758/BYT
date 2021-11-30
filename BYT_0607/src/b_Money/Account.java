package b_Money;

import java.util.Hashtable;

public class Account {
	private final String name;
	private Money content;
	private final Hashtable<String, TimedPayment> timedpayments;

	Account(String name, Money content, Hashtable<String, TimedPayment> timedpayments) {
		this.name = name;
		this.content = content;
		this.timedpayments = timedpayments;
	}

	/**
	 * Get the name of this account
	 * @return name of this account
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the content of this account
	 * @return content of this account
	 */
	public Money getContent() {
		return this.content;
	}

	/**
	 * Get the list of timed payments of this account
	 * @return list of timed payments of this account
	 */
	public Hashtable<String, TimedPayment> getTimedPayments(){
		return this.timedpayments;
	}

	/**
	 * Add a timed payment
	 * @param id Id of timed payment
	 * @param interval Number of ticks between payments
	 * @param next Number of ticks till first payment
	 * @param amount Amount of Money to transfer each payment
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 */
	public void addTimedPayment(String id, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) throws TimedPaymentExistsException {
		//wrong initially --failed by testAddRemoveTimedPayment()
		//added exception check
		if (timedPaymentExists(id)) {
			throw new TimedPaymentExistsException();
		} else {
			TimedPayment tp = new TimedPayment(interval, next, amount, this, tobank, toaccount);
			timedpayments.put(id, tp);
		}
	}
	
	/**
	 * Remove a timed payment
	 * @param id Id of timed payment to remove
	 */
	public void removeTimedPayment(String id) throws TimedPaymentDoesNotExistException {
		//wrong initially --failed by testAddRemoveTimedPayment()
		//added exception check
		if (!timedPaymentExists(id)) {
			throw new TimedPaymentDoesNotExistException();
		} else {
			timedpayments.remove(id);
		}
	}
	
	/**
	 * Check if a timed payment exists
	 * @param id Id of timed payment to check for
	 */
	public boolean timedPaymentExists(String id) {
		return timedpayments.containsKey(id);
	}

	/**
	 * A time unit passes in the system
	 */
	public void tick() {
		//removed one tick()
		for (TimedPayment tp : timedpayments.values()) {
			tp.tick();
		}
	}
	
	/**
	 * Deposit money to the account
	 * @param money Money to deposit.
	 */
	public void deposit(Money money) {
		content = content.add(money);
	}
	
	/**
	 * Withdraw money from the account
	 * @param money Money to withdraw.
	 */
	public void withdraw(Money money) {
		content = content.sub(money);
	}

	/**
	 * Get balance of account
	 * @return Amount of Money currently on account
	 */
	public Integer getBalance() {
		//replaced return content;
		return content.getAmount();
	}

	/* Everything below belongs to the private inner class, TimedPayment */
	static class TimedPayment {
		private final int interval;
		private int next;
		private final Account fromaccount;
		private final Money amount;
		private final Bank tobank;
		private final String toaccount;
		
		TimedPayment(Integer interval, Integer next, Money amount, Account fromaccount, Bank tobank, String toaccount) {
			this.interval = interval;
			this.next = next;
			this.amount = amount;
			this.fromaccount = fromaccount;
			this.tobank = tobank;
			this.toaccount = toaccount;
		}

		/* Return value indicates whether or not a transfer was initiated */
		public Boolean tick() {
			if (next == 0) {
				next = interval;

				fromaccount.withdraw(amount);
				try {
					tobank.deposit(toaccount, amount);
				}
				catch (AccountDoesNotExistException e) {
					/* Revert transfer.
					 * In reality, this should probably cause a notification somewhere. */
					fromaccount.deposit(amount);
				}
				return true;
			}
			else {
				next--;
				return false;
			}
		}
	}

}
