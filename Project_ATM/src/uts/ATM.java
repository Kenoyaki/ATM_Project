package uts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ATM {

	private static ArrayList<BankAccount> bankAccountList;
	private static Scanner scanner;

	public static void initialise() {
		bankAccountList = new ArrayList<>(
				Arrays.asList(new BankAccount(BankAccount.ADMIN_NUMBER, BankAccount.ADMIN_NAME, AccountType.ADMIN)));
		scanner = new Scanner(System.in);
	}

	public static void start() throws IOException {
		boolean exit = false;
		while (!exit) {
			System.out.println();
			System.out.println("=== Login ===");
			System.out.print("Account Number:");
			String accountNumber = scanner.nextLine();
			System.out.print("Password:");
			String password = scanner.nextLine();
			System.out.println("");

			BankAccount bankAccount = null;
			if ((bankAccount = login(accountNumber, password)) == null) {
				System.out.println("Account tidak ditemukan atau password Anda salah!");
				System.out.println("Silahkan coba lagi ...");
				System.out.println();
			} else {
				if (AccountType.ADMIN == bankAccount.getAccountType()) {
					handleAdminMainMenu();
				} else {
					handleClientMainMenu(accountNumber);
				}
			}
		}

	}

	private static void handleClientMainMenu(String accountNumber) {
		boolean loginStatus = true;
		
		do{
			System.out.println("=== Client Menu ===");
			System.out.println("1.Check Balance");
			System.out.println("2.Deposit");
			System.out.println("3.Withdraw");
			System.out.println("4.Transfer");
			System.out.println("0. Exit");

			System.out.print("Pilihan: ");

			String pilihan = scanner.nextLine();
			System.out.println("");

			
			if(pilihan.equals("1")) {
								
				System.out.println("=== Client - Check Balance ===");	
				BankAccount bankAccount = getBankAccount(accountNumber);

				if(bankAccount == null) {
					System.out.println("No Account Found");
					System.out.println("");
				}
				else {
					String clientName = bankAccount.getName();
					double clientBalance = bankAccount.getBalance();

					System.out.println("Account Number: "+accountNumber);
					System.out.println("Name: "+clientName);
					System.out.println("Balance: "+clientBalance);
					System.out.println("");
							
				}
			}
			
			else if(pilihan.equals("2")) {
				System.out.println("=== Client - Deposit ===");	
				BankAccount bankAccount = getBankAccount(accountNumber);

				if(bankAccount == null) {
					System.out.println("No Account Found");
				}
				else {
					String clientName = bankAccount.getName();
					double clientBalance = bankAccount.getBalance();

					System.out.println("Account Number: "+accountNumber);
					System.out.println("Name: "+clientName);
					System.out.println("Balance: "+clientBalance);
					
					System.out.print("Amount to Deposit: ");
					double depositAmount = scanner.nextDouble();
					System.out.println("");
					scanner.nextLine();
					double newAmount = 0;
					if((newAmount = deposit(accountNumber, depositAmount)) != -1){
						System.out.println("Success! '"+depositAmount+"' has been deposited into your account");
						System.out.println("Your new balance is: "+newAmount);
						System.out.println("");
					}
					else {
						System.out.println("Failed to deposit to your account");	
						System.out.println("");
					}
				}
			}
			
			else if(pilihan.equals("3")) {
				System.out.println("=== Client - Withdraw ===");	
				BankAccount bankAccount = getBankAccount(accountNumber);

				if(bankAccount == null) {
					System.out.println("No Account Found");
					System.out.println("");
				}
				else {
					String clientName = bankAccount.getName();
					double clientBalance = bankAccount.getBalance();

					System.out.println("Account Number: "+accountNumber);
					System.out.println("Name: "+clientName);
					System.out.println("Balance: "+clientBalance);
					
					System.out.print("Amount to Withdraw: ");
					double withdrawAmount = scanner.nextDouble();
					System.out.println("");
					scanner.nextLine();
					
					double newWithdrawAmount = withdraw(accountNumber, withdrawAmount);
					
					System.out.println("Success! '"+withdrawAmount+"' has been withdrawn into your account");
					System.out.println("Your new balance is: "+newWithdrawAmount);
					System.out.println("");
				}
				
			}
			else if(pilihan.equals("4")) {
				System.out.println("=== Client - Transfer ===");	
				BankAccount bankAccount = getBankAccount(accountNumber);

				if(bankAccount == null) {
					System.out.println("No Account Found");
					System.out.println("");
				}
				else {
					String clientName = bankAccount.getName();
					Double clientBalance = bankAccount.getBalance();

					System.out.println("Account Number: "+accountNumber);
					System.out.println("Name: "+clientName);
					System.out.println("Balance: "+clientBalance);
					
					System.out.print("Type the recepient's account number: ");
					String accountNumberTransfer = scanner.nextLine();
					System.out.println("");
					
					BankAccount bankAccountTransferTo = getBankAccount(accountNumberTransfer);

					if(bankAccountTransferTo == null) {
						System.out.println("No Account Found");
						System.out.println("");
					}
					else {
						String transferToName = bankAccountTransferTo.getName();
						Double transferToBalance = bankAccountTransferTo.getBalance();

						System.out.println("Name: "+transferToName);
						System.out.println("Balance: "+transferToBalance);
					
						System.out.print("Type the amount to transfer: ");
						double amountToBeTransferred = scanner.nextDouble();
						System.out.println("");
						scanner.nextLine();

						
						clientBalance = transfer(accountNumber,accountNumberTransfer,amountToBeTransferred);
						
						System.out.println("Success! "+amountToBeTransferred+" has been transferred \nfrom account '"+accountNumber+"' to account '"+accountNumberTransfer+"' \nYour new balance is "+clientBalance);
						System.out.println("");

					}
				}
			}
			else if(pilihan.equals("0")) {
				loginStatus = false;
			}
			else {
				System.out.println("Wrong Input");
				System.out.println("");
			}
		}while(loginStatus);
		
	}

	public static double transfer(String accountNumber, String targetNumber, double amount) {
		for(int i = 0; i < bankAccountList.size();i++ ) {
			BankAccount bankAccountTarget = bankAccountList.get(i);
			if(bankAccountTarget.getAccountNumber().equals(targetNumber)) {
				bankAccountTarget.save(amount);
			}
		}
		
		for(int i = 0; i < bankAccountList.size();i++ ) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				bankAccount.withdraw(amount);
				double currentBalance = bankAccount.getBalance();
				return currentBalance;
			}
		}
		return -1;		
	}

	public static double withdraw(String accountNumber, double amount) {
		for(int i = 0; i < bankAccountList.size();i++ ) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				bankAccount.withdraw(amount);
				double currentBalance = bankAccount.getBalance();
				return currentBalance;
			}
		}
		return -1;	
	}

	public static double deposit(String accountNumber, double amount) {
		for(int i = 0; i < bankAccountList.size();i++ ) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				bankAccount.save(amount);
				double currentBalance = bankAccount.getBalance();
				return currentBalance;
			}
		}
		return -1;
	}

	private static void handleAdminMainMenu() {
		boolean loginStatus = true;
		
		while(loginStatus) {
			System.out.println("=== Admin Menu ===");
			System.out.println("1.Add Account");
			System.out.println("2.Update Account");
			System.out.println("3.Remove Account");
			System.out.println("4.View Account");
			System.out.println("0. Exit");

			System.out.print("Pilihan: ");

			String pilihan = scanner.nextLine();
			System.out.println("");
			
			if(pilihan.equals("1")){
				System.out.println("=== Admin - Add New Account ===");
				System.out.print("Account Number: ");
				String newAccNumber = scanner.nextLine();
				System.out.print("Name: ");
				String newAccName = scanner.nextLine();
				System.out.println("");

				if(addBankAccount(newAccNumber, newAccName) == false) {
					System.out.println("Account '"+newAccNumber+"' already exists.");
					System.out.println("Please choose another number");
					System.out.println("");

				}
				else {
					bankAccountList.add(new BankAccount(newAccNumber, newAccName, AccountType.REGULAR));
					System.out.println("New Account with number '"+newAccNumber+"' and name '"+newAccName+"' has been created");
					System.out.println("");
				}
			}
			
			else if(pilihan.equals("2")){
				boolean checked = true;
				
				System.out.println("=== Admin - Update Account ===");
				System.out.print("Account Number: ");
				String newUpdateAccNumber = scanner.nextLine();
				System.out.println("");
				BankAccount bankAccount = getBankAccount(newUpdateAccNumber);

				if(bankAccount == null) {
					System.out.println("No Account Found");
					System.out.println("");
				}
				else {
					String beforeUpdatedName = bankAccount.getName();
					double balance = bankAccount.getBalance();

					System.out.println("Name: "+beforeUpdatedName);
					System.out.println("Balance: "+balance);
					
					System.out.print("Input your new Name: ");
					String newUpdatedName = scanner.nextLine();
					System.out.println("");

					if(checked = updateBankAccount(newUpdateAccNumber, newUpdatedName) == true) {
						System.out.println("Account with Number '"+ newUpdateAccNumber+"' has been updated");
						System.out.println("");

					}
					else {
						System.out.println("Fail to update account.");
						System.out.println("");

					}

				}
						
			}
			else if(pilihan.equals("3")){
				boolean checked = true;
				
				System.out.println("=== Admin - Delete Account ===");
				System.out.print("Account Number: ");
				String newDeleteAccNumber = scanner.nextLine();
				System.out.println("");
				BankAccount bankAccount = getBankAccount(newDeleteAccNumber);

				if(bankAccount == null) {
					System.out.println("No Account Found");
					System.out.println("");
				}
				else {
					String beforeDeletedName = bankAccount.getName();
					Double balance = bankAccount.getBalance();

					System.out.println("Name: "+beforeDeletedName);
					System.out.println("Balance: "+balance);
					

					if(checked = deleteBankAccount(newDeleteAccNumber) == true) {
						System.out.println("Account with Number '"+ newDeleteAccNumber+"' has been deleted");
						System.out.println("");
					}
					else {
						System.out.println("Fail to delete account.");
						System.out.println("");

					}

				}
				
			}
			else if(pilihan.equals("4")){
				System.out.println("=== Admin - View Account ===");
				System.out.print("Your Account Number: ");
				String viewAccNumber = scanner.nextLine();
				System.out.println("");
				BankAccount viewBankAccount = getBankAccount(viewAccNumber);
				
				if(viewBankAccount == null) {
					System.out.println("No Account Found");
					System.out.println("");
				}
				else {
					String viewName = viewBankAccount.getName();
					Double viewBalance = viewBankAccount.getBalance();

					System.out.println("Name: "+viewName);
					System.out.println("Balance: "+viewBalance);
					System.out.println("");

				}
				
			}
			else if(pilihan.equals("0")){
				loginStatus = false;

			}
			
		}
		
	}

	public static boolean updateBankAccount(String accountNumber, String name) {
		for(int i = 0; i < bankAccountList.size();i++) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				bankAccount.setName(name);
				return true;
			}
		}
		return false;
	}

	public static BankAccount getBankAccount(String accountNumber) {
		for(int i = 0; i < bankAccountList.size();i++ ) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				return bankAccount;
			}
		}
		return null;
	}

	public static BankAccount login(String accountNumber, String password) {		
		if(accountNumber.equals(accountNumber) && password.equals(BankAccount.DEFAULT_PASSWORD)) {
			return getBankAccount(accountNumber);
		}
		else {
			return null;
		}
	}

	public static boolean addBankAccount(String accountNumber, String name) {
		for(int i = 0; i < bankAccountList.size();i++) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				return false;
			}
		}
		return true;
	}

	public static boolean deleteBankAccount(String accountNumber) {
		for(int i = 0; i < bankAccountList.size();i++ ) {
			BankAccount bankAccount = bankAccountList.get(i);
			if(bankAccount.getAccountNumber().equals(accountNumber)) {
				bankAccountList.remove(i);
				return true;
			}
		}
		return false;
	}

}
