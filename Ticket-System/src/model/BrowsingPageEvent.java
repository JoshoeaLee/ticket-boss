package model;

import javafx.scene.image.Image;

/*
 * Data model for the event we display on homepage
 */
public class BrowsingPageEvent {

	String description;
    Image imageFile;
    String type;
    Double price;
    String name;
    String date;
    String location;
    String popularityRanking;
    String id;
    static int id_num = 1;
    boolean isEnd;
    boolean isCancelled;
    boolean isStarted;
	
	public BrowsingPageEvent(String name, Double price, String location, String popularityRanking, String date,
			String type) {
		this.type = type;
		this.name = name;
		this.price = price;
		this.date = date;
		this.location = location;
		this.popularityRanking = popularityRanking;
		this.setId();
	}

	public void setId() {
		this.id = this.type + "_" + this.name + "_" + this.location + "_" + this.date + "_" + id_num;
		id_num++;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getImage() {
		return imageFile;
	}

	public void setImage(Image imageFile) {
		this.imageFile = imageFile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double priceRange) {
		this.price = priceRange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPopularityRanking() {
		return popularityRanking;
	}

	public void setPopularityRanking(String popularityRanking) {
		this.popularityRanking = popularityRanking;
	}
	
	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	@Override
	public String toString() {
		return this.name;
	}
    
	
	
}
