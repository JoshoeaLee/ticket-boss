package application;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.BrowsingPageEvent;
import model.BusinessLogic;

public class FavoriteController {

	private BusinessLogic businessLogic;
	private Parent root;
	private Scene scene;
	private Stage stage;
	private ObservableList<BrowsingPageEvent> oList = FXCollections.observableArrayList();
	@FXML private Button checkOutButton;
	@FXML private Button removeButton;
	@FXML private ListView<BrowsingPageEvent> eventList;
	@FXML private ImageView eventImage;
	@FXML private Text discription;
	@FXML private TextArea eventName;
	@FXML private TextArea type;
	@FXML private TextArea eventLocation;
	@FXML private TextArea date;
	@FXML private TextArea popularityRanking;
	@FXML private TextArea priceRange;
	@FXML private ImageView header;
	/*
	 * Pass the business logic and account user log in with
	 * show the saved events in the listView
	 * show the details of the first event in the saved event list
	 */
	public void initData(BusinessLogic businessLogic) {
		this.businessLogic = businessLogic;
		for(BrowsingPageEvent bpm : this.businessLogic.getCurrentUser().getSavedEvent()) {
			oList.add(bpm);
		}
		for(BrowsingPageEvent bpm : oList) {
			eventList.getItems().add(bpm);
		}
		if(!this.businessLogic.getCurrentUser().getSavedEvent().isEmpty()) {
			BrowsingPageEvent bpe = this.businessLogic.getCurrentUser().getSavedEvent().get(0);
			if(bpe!=null) {
				eventImage.setImage(bpe.getImage()); 
				discription.setText("Description: " + bpe.getDescription());
				eventName.setText(bpe.getName());
				type.setText(bpe.getType());
				eventLocation.setText("Location: " + bpe.getLocation());
				date.setText("Date: " + bpe.getDate());
				popularityRanking.setText("Popularity Ranking: " + bpe.getPopularityRanking());
				priceRange.setText("Price: " + bpe.getPrice());
			}
		}
		showPictures();
	}
	
	public void showPictures() {	
		//Showing header image
		File headerFile = new File("./image/Header.png");
        Image headerImage = new Image(headerFile.toURI().toString());
        header.setImage(headerImage);
	}
	
	/*
	 * Go back to homepage and pass the business logic back
	 */
	@FXML
	private void homePageButtonPressed(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
		root = loader.load();
		HomeController controller = loader.getController();
		controller.initData(businessLogic);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);	
		stage.setScene(scene);
		stage.show();	 
	}

	@FXML
	private void checkOutButtonPressed(ActionEvent event) throws IOException {
	}
	
	/*
	 * When user click the remove button, this method will call the remove method in the Account class
	 * and try to remove the selected event, with a message of whether the event is removed successfully
	 */
	@FXML
	private void removeButtonPressed(ActionEvent event) throws IOException {
		BrowsingPageEvent bpe = eventList.getSelectionModel().getSelectedItem();
			if(this.businessLogic.getCurrentUser().removeEvent(bpe)) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("FavoriteView.fxml"));
				root = loader.load();
				FavoriteController controller = loader.getController();
				controller.initData(businessLogic);
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);	
				stage.setScene(scene);
				stage.show();	 
			}
	}
	
	/*
	 * When clicking a saved event in the listview, show the detail of the event
	 */
	@FXML
	private void eventListClicked(MouseEvent event) throws IOException {
			BrowsingPageEvent bpe = eventList.getSelectionModel().getSelectedItem();
			if(bpe!=null) {
				eventImage.setImage(bpe.getImage()); 
				discription.setText("Description: " + bpe.getDescription());
				eventName.setText(bpe.getName());
				type.setText(bpe.getType());
				eventLocation.setText("Location: " + bpe.getLocation());
				date.setText("Date: " + bpe.getDate());
				popularityRanking.setText("Popularity Ranking: " + bpe.getPopularityRanking());
				priceRange.setText("Price: " + bpe.getPrice());
			}
	}

}
