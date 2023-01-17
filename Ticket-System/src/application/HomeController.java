package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import model.BrowsingPageEvent;
import model.BusinessLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/*
 * This controller is for the homepage that unlogged users see.
 */
public class HomeController{
	private Scene scene;
	private Stage stage;
	private Parent root; 
	@FXML private ImageView selectedImage;
	@FXML private ImageView headerImage;
	@FXML private ChoiceBox<String> searchChoice;
	@FXML private Button logoutButton;
	@FXML private Button favoriteButton;
	@FXML private Button viewEventButton;
	@FXML private TableView<BrowsingPageEvent> eventTable;	
	@FXML private TableColumn<BrowsingPageEvent, String> nameColumn;
	@FXML private TableColumn<BrowsingPageEvent, Double> priceColumn;
	@FXML private TableColumn<BrowsingPageEvent, String> locationColumn;
	@FXML private TableColumn<BrowsingPageEvent, String> popularityColumn;
	@FXML private TableColumn<BrowsingPageEvent, String> dateColumn;
	@FXML private Label warningLabel;
	
	private BrowsingPageEvent selectedEvent;
	private ArrayList<BrowsingPageEvent>browsingPageEventData;
	private BusinessLogic businessLogic;
	
	/*
	 * Add business logic to the page and setup event information shown on table
	 */
	public void initData(BusinessLogic businessLogic) {
		this.businessLogic = businessLogic;
		setDataForTable();
		eventTable.setItems(getEvent());
		
		//Front-End
		showPictures(); //Creates a listener which sees which object is selected and shows a picture of that event
		eventTable.getSelectionModel().select(0);  //Selects the first object by default
		fillFakeChoiceBox();
	}
	
	/*
	 * Display event name, price, location, popularity and date info on table view
	 */
	public void setDataForTable() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<BrowsingPageEvent, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<BrowsingPageEvent, Double>("price"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<BrowsingPageEvent, String>("location"));
		popularityColumn.setCellValueFactory(new PropertyValueFactory<BrowsingPageEvent, String>("popularityRanking"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<BrowsingPageEvent, String>("date"));	
	}
	
	/**
	 *Shows the header image
	 * Causes the event image of the selected event to be shown at the right of the screen
	 */
	public void showPictures() {
		
		//Showing header image
		File file = new File("./image/Header.png");
        Image image = new Image(file.toURI().toString());
		headerImage.setImage(image);
		
		//Controls the event images
		eventTable.getSelectionModel().selectedItemProperty().addListener(selected->{
			
			selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			selectedImage.setImage(selectedEvent.getImage());
		}
		);
	}
	
	/*
	 * Change to detail event page and pass the data of the event user selects
	 */
	public void viewEventButtonPressed(ActionEvent event) throws IOException {
		selectedEvent = eventTable.getSelectionModel().getSelectedItem();
		if(selectedEvent == null) {
			warningLabel.setText("Please select an event!");
		}
		else {
			//Load into detail event page view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailEventView.fxml"));
			root = loader.load();
			//Pass selected event data to the controller
			DetailEventViewController controller = loader.getController();
			controller.initData(selectedEvent, businessLogic);		
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);	
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	/*
	 * Go to favorite page and pass the business logic and account user log in with to favorite controller
	 */
	public void favoriteButtonPressed(ActionEvent event) throws IOException {
		if(this.businessLogic.getCurrentUser()!=null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FavoriteView.fxml"));
			root = loader.load();
			FavoriteController controller = loader.getController();
			controller.initData(businessLogic);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);	
			stage.setScene(scene);
			stage.show();	
		} else {
			Dialog<String> dialog = new Dialog<String>();
			dialog.setContentText("You need to log in before going to favorite page");
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			dialog.show();
		}
	}
	
	//Add event into JavaFX list so table view can display event info
	public ObservableList<BrowsingPageEvent> getEvent(){
		ObservableList<BrowsingPageEvent> event = FXCollections.observableArrayList();
		browsingPageEventData = businessLogic.getBrowsingPageEventData();
		for (BrowsingPageEvent e: browsingPageEventData) {
			event.add(e);
		}
		return event;
	}
	
	public void fillFakeChoiceBox() {
		searchChoice.getItems().addAll("Name", "Type", "Location");
		searchChoice.getSelectionModel().select(0);
	}
}
