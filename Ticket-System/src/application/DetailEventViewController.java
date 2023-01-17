package application;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BrowsingPageEvent;
import model.BusinessLogic;

public class DetailEventViewController {
	
	private BusinessLogic businessLogic;
	private BrowsingPageEvent selectedEvent;
	private Parent root;
	private Scene scene;
	private Stage stage;
	@FXML private Button gobackButton;
	@FXML private Button favoriteButton;
	@FXML private Button purchaseButton;
	@FXML private Label eventName;
	@FXML private Label eventDate;
	@FXML private Label eventPrice;
	@FXML private Label eventLocation;
	@FXML private Label eventType;
	@FXML private Label eventPopularity;
	@FXML private Text eventDescription;
	@FXML private ImageView seatingImage;
	@FXML private ImageView detailHeaderImage;
	@FXML private ImageView eventImage;
	
	
	public void initData(BrowsingPageEvent browsingPageEvent, BusinessLogic businessLogic) {
		//Pass the business logic from login page
		this.businessLogic = businessLogic;
		//Pass the selected event when users click on homepage
		selectedEvent = browsingPageEvent;
		//Displays the header and available seating images.
		showHeaderandSeating(); 
		displayData();
	}
	
	/*
	 * Display selected event info and image 
	 */
	public void displayData() {
		eventName.setText(selectedEvent.getName());
		eventDate.setText(selectedEvent.getDate().toString());
		eventPrice.setText(selectedEvent.getPrice().toString());
		eventLocation.setText(selectedEvent.getLocation());
		eventPopularity.setText(selectedEvent.getPopularityRanking());
		eventType.setText(selectedEvent.getType());
		eventDescription.setText(selectedEvent.getDescription());
		eventImage.setImage(selectedEvent.getImage());
	}
	
	/*
	 * Go back to home page and pass the same business logic back
	 */
	public void gobackButtonPressed(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
		root = loader.load();
		HomeController controller = loader.getController();
		controller.initData(businessLogic);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();	 
	}
	
	/*
	 * When the user clicks the favorite button, this method will call the saveEvent method in the Account class
	 * and pop up a window to tell the user if the event is added successfully or not
	 */
	public void favoriteButtonPressed(ActionEvent event) throws IOException {
		if(this.businessLogic.getCurrentUser()!=null) {
			String message = this.businessLogic.getCurrentUser().saveEvent(selectedEvent);
			Dialog<String> dialog = new Dialog<String>();
			dialog.setContentText(message);
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			dialog.show();
		} else {
			Dialog<String> dialog = new Dialog<String>();
			dialog.setContentText("You need to log in before saving an event");
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			dialog.show();
		}
	}
	
	/**
	 * Shows the header image and available seating image. 
	 * Also turns off the purchase button.
	 */
	public void showHeaderandSeating() {
		//Showing header image
				File file = new File("./image/Header.png");
		        Image image = new Image(file.toURI().toString());
				detailHeaderImage.setImage(image);
				
		//Showing seating image
				File file2 = new File("./image/seating.jpeg");
		        Image image2 = new Image(file2.toURI().toString());
				seatingImage.setImage(image2);
				
		//Turn off Purchase Button which can't be used
				purchaseButton.setDisable(true);
				
	}
	
}
