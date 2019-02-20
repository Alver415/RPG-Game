package frontend.fxml.controllers;

import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.NotificationPane;

import backend.PasswordAuthentication;
import backend.User;
import frontend.Facade;
import frontend.fxml.components.Message.Type;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class SettingsController {

	@FXML
	private Tab					loginTab;
	@FXML
	private Tab					signUpTab;
	@FXML
	private NotificationPane	notificationPane;
	@FXML
	private TextField			login_usernameField;
	@FXML
	private PasswordField		login_passwordField;
	@FXML
	private TextField			signUp_usernameField;
	@FXML
	private TextField			signUp_emailField;
	@FXML
	private PasswordField		signUp_passwordField;
	@FXML
	private PasswordField		signUp_passwordConfirmField;
	@FXML
	private Button				submitButton;

	@FXML
	protected void submit() {
		if (loginTab.isSelected()) {
			login();
		} else if (signUpTab.isSelected()) {
			signUp();
		}
	}

	@FXML
	protected void login() {
		String username = login_usernameField.getText();
		String password = login_passwordField.getText();

		boolean success = Facade.checkPassword(username, password);

		if (success) {
			Facade.addMessage("Success", "You successfully Logged In!", Type.SUCCESS);
			Facade.startGame();
		} else {
			Facade.addMessage("Failure", "Incorrect Username or Password", Type.NORMAL);
		}

	}

	@FXML
	protected void signUp() {
		String username = signUp_usernameField.getText();

		boolean userExists = Facade.checkUserExists(username);
		if (!userExists) {
			String password = signUp_passwordField.getText();
			String passwordConfirm = signUp_passwordConfirmField.getText();
			if (!StringUtils.equals(password, passwordConfirm)) {
				Facade.addMessage("Failure", "Passwords did not match!", Type.NORMAL);
				return;
			}
			String email = signUp_emailField.getText();
			String passwordHash = new PasswordAuthentication().hash(password.toCharArray());

			User newUser = new User(username);
			newUser.setEmail(email);
			newUser.setPassword(passwordHash);

			Facade.save(newUser);
			Facade.addMessage("Success", "You successfully Signed Up!", Type.SUCCESS);
		} else if (userExists) {
			Facade.addMessage("Failure", "That Username already exists!", Type.NORMAL);
		}
	}
}