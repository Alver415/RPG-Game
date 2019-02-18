package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameApplication extends Application {

	private Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		Scene scene = FXMLLoader.load(getClass().getResource("../fxml/scenes/login.fxml"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");

		Facade.setApplication(this);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});

		primaryStage.show();
	}

	public Stage getStage() {
		return this.stage;
	}

}
