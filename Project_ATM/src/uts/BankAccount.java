package uts;

public class BankAccount {

	public static final String ADMIN_NUMBER = "1111"; // REPLACE THIS WITH YOUR NIM
	public static final String ADMIN_NAME = "Yohanes Kenny"; // REPLACE THIS WITH YOUR NAME
	public static final String DEFAULT_PASSWORD = "1111";
	private AccountType accountType = AccountType.REGULAR;
	private String accountNumber;
	private String name;
	private String password = DEFAULT_PASSWORD;
	private double balance = 0.0;

	public void setName(String name) {
		this.name = name;
	}

	public BankAccount(String accountNumber, String name) {
		this.accountNumber = accountNumber;
		this.name = name;
	}

	public BankAccount(String accountNumber, String name, AccountType accountType) {
		this.accountNumber = accountNumber;
		this.name = name;
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public double save(double amount) {
		balance += amount;
		return balance;
	}

	public double withdraw(double amount) {
		balance -= amount;
		return balance;	}

	public String getName() {
		return name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	public String toString() {
		return "{" + this.accountNumber + ", " + this.name + ", " + this.balance + "}";
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}