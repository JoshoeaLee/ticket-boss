package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import javafx.scene.image.Image;

public class EventDatabase {
	ArrayList<BrowsingPageEvent> browsingPageEvents;
	private Scanner scanner;
	private File basicInfo;
	private File detailInfo;
	private File imageInfo;
    
    public EventDatabase() {
    	browsingPageEvents = new ArrayList<BrowsingPageEvent>();
    	loadEventData();
    	loadDetailData();
    	loadImageData();
    }
    
    public ArrayList<BrowsingPageEvent> getBrowsingPageEventData(){
    	return browsingPageEvents;
    }
    
    /*
     * Read basic event info from file.
     * Basic event info includes event name, price, location, popularity, price and type
     */
    public void loadEventData() {
    	basicInfo = new File("EventInfo.txt");
    	try {
			scanner = new Scanner(basicInfo);
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
    	
    }
    
    /*
     * Read event description from file
     */
    public void loadDetailData() {
    	detailInfo = new File("DescriptionTest.txt");
    	int i = 0;
    	try {
			scanner = new Scanner(detailInfo);
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				browsingPageEvents.get(i).setDescription(entry);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * Read event image data from file
     */
    public void loadImageData() {
    	imageInfo = new File("./image.txt");
    	int i = 0;
    	try {
			scanner = new Scanner(imageInfo);
			while (scanner.hasNext()) {
				String entry = scanner.nextLine().trim();
				//Convert image path to image object
				File file = new File(entry);
		        Image image = new Image(file.toURI().toString());
		        browsingPageEvents.get(i).setImage(image);
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
