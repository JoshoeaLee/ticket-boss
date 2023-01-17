package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import javafx.scene.image.Image;
import model.BrowsingPageEvent;

/**
 * READ ME!
 * This is a partial copy of the EventDatabase class but with the methods
 * slightly changed so that it can be more easily tested.
 * This class is only used for testing as is not used in any other part 
 * of the program.
 * 
 * Changes made were -> Having the loadData methods take Files as parameters
 * instead of having specific files coded within the method. (This way I can test
 * a variety of files).
 * 
 * Having the loadDetailData() method be part of the loadEventData() method.
 * This is just to streamline the process of testing.
 * 
 * Making the loadEventData method return the arraylist of browsing page events.
 * Removing the loadImageData() method as it won't be tested using JUNIT tests.
 *
 */
public class TestEventDatabase {
	ArrayList<BrowsingPageEvent> browsingPageEvents;
	private Scanner scanner;

    
    public TestEventDatabase() {
    	browsingPageEvents = new ArrayList<BrowsingPageEvent>();
  
    }
    
    public ArrayList<BrowsingPageEvent> getBrowsingPageEventData(){
    	return browsingPageEvents;
    }
    
    /*
     * Read basic event info from file.
     * Basic event info includes event name, price, location, popularity, price and type
     */
    public ArrayList<BrowsingPageEvent> loadEventData(File eventFile, File descriptionFile) {   //File Parameters added
    	
    	//Pre-existing file code removed here
    	try {
			scanner = new Scanner(eventFile);
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				Scanner entryScan = new Scanner(entry);
				while (entryScan.hasNext()) {
					String name = entryScan.next().trim();
					double price = Double.parseDouble(entryScan.next().trim());
					String location = entryScan.next().trim();
					String popularity = entryScan.next().trim();
					String date = entryScan.next().trim();
					String type = entryScan.next().trim();
					BrowsingPageEvent ev = new BrowsingPageEvent(name, price, location, popularity, date, type);
					browsingPageEvents.add(ev);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	loadDetailData(descriptionFile); //Description Data method called here
    	return browsingPageEvents; //This method returns this arraylist now.
    }
    
    /*
     * Read event description from file
     */
    public void loadDetailData(File descriptionFile) {
    	int i = 0;
    	try {
			scanner = new Scanner(descriptionFile);
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				browsingPageEvents.get(i).setDescription(entry);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }


    //If the passed event does not exist in the database, add it to the database
    public void addEvent(BrowsingPageEvent browsingPageEvent) {
    	if(!isEventExisted(browsingPageEvent)) {
    		this.browsingPageEvents.add(browsingPageEvent);
    	}	
    }
  
    //Check event exists in the database or not
    public boolean isEventExisted(BrowsingPageEvent browsingPageEvent) {
    	boolean exist = false;
    	for(BrowsingPageEvent bpe : this.browsingPageEvents) {
    		if(bpe.getId().equals(browsingPageEvent.getId())) {
    			exist = true;
    			break;
    		}
    	}
    	return exist;
    }
    
}
