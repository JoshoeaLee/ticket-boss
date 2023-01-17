package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Account;
import model.AccountDatabase;
import model.BusinessLogic;

public class LoginViewController implements Initializable{
	private Scene scene;
	private Stage stage;
	private Parent root;
	private String username;
	private String password;
	private Account account;
	private BusinessLogic businessLogic;
	private AccountDatabase accountDatabase;
	@FXML private Button loginButton;
	@FXML private TextField userNameInput;
	@FXML private TextField passwordInput;
	@FXML private Label warningLabel;
	@FXML private ImageView header;
	@FXML private ImageView banner;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Set the business logic for the whole app
		businessLogic = new BusinessLogic();
		accountDatabase = businessLogic.getAccountDatabase();
		showPictures();
	}
	
	public void showPictures() {	
		//Showing header image
		File headerFile = new File("./image/Header.png");
        Image headerImage = new Image(headerFile.toURI().toString());
        File loginFile = new File("./image/Poster.jpg");
        Image bannerImage = new Image(loginFile.toURI().toString());
        header.setImage(headerImage);
        banner.setImage(bannerImage);
	}
	
	/*
	 * If the username and password user enters are in account database and match each other, 
	 * then log in successfully and go to homepage
	 */
	public void loginButtonPressed(ActionEvent event) throws IOException{
		username = userNameInput.getText();
		password = passwordInput.getText();	
		if(accountCheckPass()) {
			this.businessLogic.setCurrentUser(username);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
			root = loader.load();
			HomeController controller = loader.getController();
			controller.initData(businessLogic);		
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);	
			stage.setScene(scene);
			stage.show();	
		};
	}
	
	public boolean accountCheckPass() {
		//Check if account exists in the database
		account = accountDatabase.searchAccountByUserName(username);
		if(account == null) {
			warningLabel.setText("The account doesn't exist!");
		}
		else {
			//Check If account's password matches the password in database
			if(account.isValidPassword(password)) {
				return true;
			}
			else{
				warningLabel.setText("Please enter the correct password!");
			}
		}
		//return true if the account pass these two checks
		return false;
	}

}
