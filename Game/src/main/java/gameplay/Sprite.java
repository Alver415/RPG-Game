package gameplay;

import javafx.scene.image.Image;

public enum Sprite {

	BULBASAUR("bulbasaur.png"),
	CHARMANDER("charmander.png"),
	SQUIRTLE("squirtle.png");

	private static final String	FOLDER	= "Sprites";
	private final Image			image;

	Sprite(String location) {
		this.image = new Image(FOLDER + "/" + location);
	}
	
	public Image getImage() {
		return image;
	}

}
