package com.barosanu.view;

import javax.naming.OperationNotSupportedException;

import com.barosanu.controller.AbstractController;
import com.barosanu.controller.AddAccountController;
import com.barosanu.controller.ComposeEmailController;
import com.barosanu.controller.EmailDetailsController;
import com.barosanu.controller.MainController;
import com.barosanu.controller.ModelAccess;
import com.barosanu.model.EmailMessageBean;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewFactory {

	public static ViewFactory defaultFactory = new ViewFactory();
	
	private static boolean mainViewInitialized = false;

	private final String DEFAULT_CSS = "style.css";
	private final String EMAIL_DETAILS_FXML = "EmailDetailsLayout.fxml";
	private final String MAIN_SCREEN_FXML = "MainLayout.fxml";
	private final String COMPOSE_EMAIL_FXML = "ComposeEmailLayout.fxml";
	private final String ADD_ACCOUNT_EMAIL_FXML = "AddAccountLayout.fxml";

	private ModelAccess modelAccess = new ModelAccess();

	public Scene getMainScene() throws OperationNotSupportedException {
		if (!mainViewInitialized) {
			AbstractController mainController = new MainController(modelAccess);
			mainViewInitialized = true;
			return initializeScene(MAIN_SCREEN_FXML, mainController);
		} else {
			throw new OperationNotSupportedException("Main Scene allready initialized!!!!");
		}

	}

	public Scene getEmailDetailsScene() {
		AbstractController emailDetailsController = new EmailDetailsController(modelAccess);
		return initializeScene(EMAIL_DETAILS_FXML, emailDetailsController);
	}

	public Scene getComposeEmailScene() {
		AbstractController composeEmailController = new ComposeEmailController(modelAccess);
		return initializeScene(COMPOSE_EMAIL_FXML, composeEmailController);
	}
	
	public Scene getAddAccountScene(){
		AbstractController addAccountController = new AddAccountController(modelAccess);
		return initializeScene(ADD_ACCOUNT_EMAIL_FXML, addAccountController);
	}

	public Scene getComposeEmailScene(EmailMessageBean initialMessage, int type) {
		AbstractController composeEmailController;
		try {
			composeEmailController = new ComposeEmailController(modelAccess, initialMessage, type);
		} catch (Exception e) {
			composeEmailController = new ComposeEmailController(modelAccess);
			e.printStackTrace();
		}
		return initializeScene(COMPOSE_EMAIL_FXML, composeEmailController);
	}

	public Node resolveIcon(String treeItemValue) {
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
		try {
			if (lowerCaseTreeItemValue.contains("inbox")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/inbox.png")));
			} else if (lowerCaseTreeItemValue.contains("sent")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/sent2.png")));
			} else if (lowerCaseTreeItemValue.contains("spam")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/spam.png")));
			} else if (lowerCaseTreeItemValue.contains("@")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
			} else if (lowerCaseTreeItemValue.isEmpty()) {
				return null;
			} else {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
			}
		} catch (NullPointerException e) {
			System.out.println("Invalid image location!!!");
			e.printStackTrace();
			returnIcon = new ImageView();
		}

		returnIcon.setFitHeight(16);
		returnIcon.setFitWidth(16);

		return returnIcon;
	}

	private Scene initializeScene(String fxmlPath, AbstractController controller) {
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		try {
			loader = new FXMLLoader(getClass().getResource(fxmlPath));
			loader.setController(controller);
			parent = loader.load();
		} catch (Exception e) {
			return null;
		}

		scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
		return scene;
	}

}