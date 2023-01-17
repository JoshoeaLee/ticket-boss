package model;

import java.util.ArrayList;

/*
 * Business logic works as a bridge between UI/Controller and database
 */
public class BusinessLogic {
	EventDatabase eventDatabase;
	AccountDatabase accountDatabase;
	ArrayList<BrowsingPageEvent> events;
	Account currentUser;
	
	public BusinessLogic() {
		eventDatabase = new EventDatabase();
		accountDatabase = new AccountDatabase();
		events = eventDatabase.getBrowsingPageEventData();
	}
	
	public ArrayList<BrowsingPageEvent> getBrowsingPageEventData() {
		return events;
	}
	
	public AccountDatabase getAccountDatabase() {
		return accountDatabase;
	}
	
	public Account getCurrentUser() {
		return this.currentUser;
	}

	/*
	 * After a user log in, check in the accountDataBase
	 * and select the account with the same username, and set it to
	 * be the current user
	 */
	public void setCurrentUser(String userName) {
		if(!this.accountDatabase.getAccounts().isEmpty()) {
			for(Account a : this.accountDatabase.getAccounts()) {
				if(a.getUserName().equals(userName)) {
					this.currentUser = a;
				}
			}
		}
	}

	public void checkAccountValid(String name,String password) {
		
    }
	
    public void createAccount() {
    }

    public void deleteAccount() {
    }

    public void selectSeat() {
    }

    public void buySeat() {
    }

    public void cancelSeat() {
    }

    public void filterEvent() {
    }

	/*
	 * Add event into account's favorite event list
	 * If account doesn't exist in database, return error message
	 * If event doesn't exist in database, return error message
	 */
    public String addSavedEvent(Account account, BrowsingPageEvent browsingPageEvent) {
    	String message = "";
    	if(!accountDatabase.isAccountExisted(account)) {
    		message = "The account does not exist";
    		return message;
    	} 
    	if(!eventDatabase.isEventExisted(browsingPageEvent)) {
    		message = "The event does not exist";
    		return message;
    	}
    	return account.saveEvent(browsingPageEvent);
    }

}
