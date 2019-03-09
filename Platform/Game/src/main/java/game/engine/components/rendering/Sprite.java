package game.engine.components.rendering;

import javafx.scene.image.Image;

public class Sprite {

	protected static final String FOLDER = "Sprites";

	protected final Image image;
	protected final String name;

	protected Sprite(String fileName) {
		this.name = fileName;
		this.image = new Image(FOLDER + "/" + fileName + ".png");
	}

	public Image getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}

}
