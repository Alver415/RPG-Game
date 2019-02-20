package frontend;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneLoader{

	
	
	public static Scene load(AppScene scene) {
		try {
			String fxmlLocation = scene.getFxmlLocation();
			URL resource = SceneLoader.class.getResource(fxmlLocation);
			return FXMLLoader.load(resource);
		} catch (IOException e) {
			return new Scene(new Parent() {});
		}
		
	}
}
