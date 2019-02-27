package game.engine.components.rendering;

import javafx.scene.image.Image;

public class Sprite {

	public static final Sprite	BULBASAUR	= new Sprite("bulbasaur.png");
	public static final Sprite	CHARMANDER	= new Sprite("charmander.png");
	public static final Sprite	SQUIRTLE	= new Sprite("squirtle.png");
	public static final Sprite	PIKACHU		= new Sprite("pikachu.png");

	public static final Sprite PALLET_TOWN = new Sprite("pallet_town.png");

	private static final String FOLDER = "Sprites";

	private final Image image;

	private Sprite(String location) {
		this.image = new Image(FOLDER + "/" + location);
	}

	public Image getImage() {
		return image;
	}

}
