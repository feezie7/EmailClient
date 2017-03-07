package com.barosanu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.barosanu.controller.services.CreateAndRegisterEmailAccountService;
import com.barosanu.model.EmailConstants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAccountController extends AbstractController implements Initializable{
	

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField addressField;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    void loginBtnAction() {
    	//TODO add validation
    	//TODO add props handling
    	statusLabel.setText("");
    	CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService = 
    			new CreateAndRegisterEmailAccountService(
    					addressField.getText(),
    					passwordField.getText(), 
    					getModelAccess());
    	createAndRegisterEmailAccountService.start();
    	statusLabel.setText("logging in....");
    	createAndRegisterEmailAccountService.setOnSucceeded(e->{
    		if(createAndRegisterEmailAccountService.getValue() != EmailConstants.LOGIN_STATE_SUCCEDED){
    			statusLabel.setText("an error occured...");
    		}else{
    			//close the window
    			Stage stage = (Stage)addressField.getScene().getWindow();//just getting a reference to the stage
    			stage.close();
    		}
    	});
    }
    

	public AddAccountController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}