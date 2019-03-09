package frontend;

import javafx.scene.Scene;

public enum AppScene{

	LOGIN("Login", "../fxml/scenes/login.fxml"),
	SETTINGS("Settings", "../fxml/scenes/settings.fxml"),
	GAME("Game", "../fxml/scenes/game.fxml");
	
	private final String fxmlLocation;
	private final String title;
	private Scene scene;
	
	AppScene(String fxmlLocation){
		this.title = this.name();
		this.fxmlLocation = fxmlLocation;
	}
	AppScene(String title, String fxmlLocation){
		this.title = title;
		this.fxmlLocation = fxmlLocation;
	}
	
	public String getFxmlLocation() {
		return fxmlLocation;
	}

	public String getTitle() {
		return title;
	}
	
	/** Lazy initialize scenes 
	 */
	public Scene getScene() {
		if (scene == null) {
			scene = SceneLoader.load(fxmlLocation);
		}
		return scene;
	}
	
}