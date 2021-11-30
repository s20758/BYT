package b_Money;

import java.util.Hashtable;

public class Bank {
	//changed to public, so that it's accessible for tests
	private final Hashtable<String, Account> accountlist;
	private final String name;
	private final Currency currency;
	
	/**
	 * New Bank
	 * @param name Name of this bank
	 * @param currency Base currency of this bank (If this is a Swedish bank, this might be a currency class representing SEK)
	 */
	Bank(String name, Currency currency, Hashtable<String,Account> accountlist) {
		this.name = name;
		this.currency = currency;
		this.accountlist = accountlist;
	}
	
	/**
	 * Get the name of this bank
	 * @return Name of this bank
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the Currency of this bank
	 * @return The Currency of this bank
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Get the AccountList of this bank
	 * @return The List of Accounts opened in this bank
	 */
	public Hashtable<String, Account> getAccountList() {
		return accountlist;
	}
	
	/**
	 * Open an account at this bank.
	 * @param accountid The ID of the account
	 * @throws AccountExistsException If the account already exists
	 */
	public void openAccount(String accountid) throws AccountExistsException {
		//wrong initially --failed by testOpenAccount()
		if (accountlist.containsKey(accountid)) {
			throw new AccountExistsException();
		}
		else {
			//replaced accountlist.get(accountid);
			accountlist.put(accountid, new Account(accountid, new Money(0, this.getCurrency()), new Hashtable<>()));
		}
	}
	
	/**
	 * Deposit money to an account
	 * @param accountid Account to deposit to
	 * @param money Money to deposit.
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void deposit(String accountid, Money money) throws AccountDoesNotExistException {
		//wrong initially --failed by testDeposit()
		//replaced accountlist.containsKey(accountid)
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			//replaced Account account = accountlist.get(accountid);
			accountlist.get(accountid).deposit(money);
		}
	}
	
	/**
	 * Withdraw money from an account
	 * @param accountid Account to withdraw from
	 * @param money Money to withdraw
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public void withdraw(String accountid, Money money) throws AccountDoesNotExistException {
		//wrong initially --failed by testWithdraw()
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			//replaced Account account = accountlist.get(accountid); account.deposit(money);
			//here we check if there is enough money on the account to withdraw more
			switch(accountlist.get(accountid).getContent().compareTo(money)) {

				case 0:
				case 1: {
					accountlist.get(accountid).withdraw(money);
					break;
				}
				case -1: {
					try {
						throw new Exception("Trying to withdraw more money that there is on the account");
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Get the balance of an account
	 * @param accountid Account to get balance from
	 * @return Balance of the account
	 * @throws AccountDoesNotExistException If the account does not exist
	 */
	public Integer getBalance(String accountid) throws AccountDoesNotExistException {
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		}
		else {
			return accountlist.get(accountid).getBalance();
		}
	}

	/**
	 * Transfer money between two accounts
	 * @param fromaccount Id of account to deduct from in this Bank
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 * @param amount Amount of Money to transfer
	 * @throws AccountDoesNotExistException If one of the accounts do not exist
	 */
	public void transfer(String fromaccount, Bank tobank, String toaccount, Money amount) throws AccountDoesNotExistException {
		//wrong initially --failed by testTransfer()
		if (!accountlist.containsKey(fromaccount) || !tobank.accountlist.containsKey(toaccount)) {
			throw new AccountDoesNotExistException();
		}
		else {
			//checking withdrawal
			int tmp = getBalance(fromaccount);
			this.withdraw(fromaccount, amount);
			if (tmp == this.getBalance(fromaccount)){
				try {
					throw new Exception("No transfer");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				tobank.deposit(toaccount, amount);
			}
		}		
	}

	/**
	 * Transfer money between two accounts on the same bank
	 * @param fromaccount Id of account to deduct from
	 * @param toaccount Id of receiving account
	 * @param amount Amount of Money to transfer
	 * @throws AccountDoesNotExistException If one of the accounts do not exist
	 */
	public void transfer(String fromaccount, String toaccount, Money amount) throws AccountDoesNotExistException {
		//wrong initially --failed by testTransfer()
		//added exception check
		if (!accountlist.containsKey(fromaccount) || !accountlist.containsKey(toaccount)) {
			throw new AccountDoesNotExistException();
		}
		else {
			//replaced 2nd fromaccount with toaccount
			transfer(fromaccount, this, toaccount, amount);
		}
	}

	/**
	 * Add a timed payment
	 * @param accountid Id of account to deduct from in this Bank
	 * @param payid Id of timed payment
	 * @param interval Number of ticks between payments
	 * @param next Number of ticks till first payment
	 * @param amount Amount of Money to transfer each payment
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 */
	public void addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) throws AccountDoesNotExistException, TimedPaymentExistsException {
		//wrong initially --failed by testTimedPayment()
		//added exception check
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		} else {
			//replaced Account account = accountlist.get(accountid);
			accountlist.get(accountid).addTimedPayment(payid, interval, next, amount, tobank, toaccount);
		}
	}
	
	/**
	 * Remove a timed payment
	 * @param accountid Id of account to remove timed payment from
	 * @param id Id of timed payment
	 */
	public void removeTimedPayment(String accountid, String id) throws AccountDoesNotExistException, TimedPaymentDoesNotExistException {
		//wrong initially --failed by testTimedPayment()
		//added exception check
		if (!accountlist.containsKey(accountid)) {
			throw new AccountDoesNotExistException();
		} else {
			//replaced Account account = accountlist.get(accountid);
			accountlist.get(accountid).removeTimedPayment(id);
		}
	}
	
	/**
	 * A time unit passes in the system
	 */
	public void tick() {
		for (Account account : accountlist.values()) {
			account.tick();
		}
	}	
}
