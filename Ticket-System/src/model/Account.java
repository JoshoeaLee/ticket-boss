package model;

import java.util.ArrayList;

/*
 * Data model for user account information
 * includes userName, password, user ID and saved events
 */
public class Account {
	String userName;
    String password;
    int userID;
    static int id_num = 0;
    ArrayList<BrowsingPageEvent> savedEvents;
    
    public Account(String userName, String password) {
    	this.userName = userName;
        this.password = password;
        savedEvents = new ArrayList<BrowsingPageEvent>();
        this.setID();
    }

    /*
     * Check if the password user entered matches the password the account holds
     */
    public boolean isValidPassword(String inputPassword) {
    	if(inputPassword!=null) {
    		if(inputPassword.equals(this.password)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public void setID() {
		id_num++;
		this.userID = id_num;		
	}

	public ArrayList<BrowsingPageEvent> getSavedEvent() {
		return savedEvents;
	}
	
	/*
	 * Add event the user selects into account's favorite list with returne messages for 3 scenarios
	 * 1. event added successfully
	 * 2. duplicated event cannot be added again
	 * 3. have not selected a event,namely the event is null
	 */
	public String saveEvent(BrowsingPageEvent browsingPageEvent) {
		if(browsingPageEvent!=null) {
			for(BrowsingPageEvent bpe : this.savedEvents) {
	    		if(bpe.getId().equals(browsingPageEvent.getId())) {
	    			return "This event is already added to favourite";
	    		}
	    	}
	    	this.savedEvents.add(browsingPageEvent);
	    	return "Event added to favourite";
		}
		return "No event to save";
	}
	
	/*
	 * remove an event from the saved events list with 3 scenarios
	 * 1. the event to remove is null
	 * 2. the event to remove is not in the save event list
	 * 3. the event is removed successfully 
	 */
	public boolean removeEvent(BrowsingPageEvent browsingPageEvent) {
		if(browsingPageEvent!=null) {
			for(BrowsingPageEvent bpe : this.savedEvents) {
	    		if(bpe.getId().equals(browsingPageEvent.getId())) {
	    			this.savedEvents.remove(browsingPageEvent);
	    			return true;
	    		}
	    	}
		}
		return false;
	}
    
}
