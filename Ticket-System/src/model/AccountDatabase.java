package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountDatabase {
	ArrayList<Account> accounts;
	private Scanner scanner;
	private File accountInfo;
	
	public AccountDatabase() {
    	accounts = new ArrayList<Account>();
    	loadAccountData();
    }
	
	/*
	 * Read account data file from the disk,
	 * the file includes account name and password
	 */
	public void loadAccountData() {
		accountInfo = new File("./account.txt");
		try {
			scanner = new Scanner(accountInfo);
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				Scanner entryScan = new Scanner(entry);
				while (entryScan.hasNext()) {
					String name = entryScan.next().trim();
					String password = entryScan.next().trim();
					Account ac = new Account(name, password);
					accounts.add(ac);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Check if the account exsits in the database, if so, return true
    public boolean isAccountExisted(Account account) {
    	boolean exist = false;
    	for(Account a : this.accounts) {
    		if(a.getUserID() == account.getUserID()) {
    			exist = true;
    			break;
    		}
    	}
    	return exist;
    }
    
    //Return the account object based on account's username
    public Account searchAccountByUserName(String username) {
    	for(Account a : this.accounts) {
    		if(a.getUserName().equals(username)) {
    			return a;  			
    		}
    	}	
    	return null;
    }
    
    public void addAccount(Account account) {
    	accounts.add(account);	
    }
    
    public void removeAccount(Account account) {
    	accounts.remove(account);
    }

    public ArrayList<Account> getAccounts(){
    	return this.accounts;
    }
    
}
