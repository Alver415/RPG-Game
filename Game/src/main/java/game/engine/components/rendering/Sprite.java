package game.engine.components.rendering;

import javafx.scene.image.Image;

public class Sprite {

	public static final Sprite	TRAINER		= new Sprite("trainer");
	
	public static final Sprite	BULBASAUR	= new Sprite("bulbasaur");
	public static final Sprite	CHARMANDER	= new Sprite("charmander");
	public static final Sprite	SQUIRTLE	= new Sprite("squirtle");
	public static final Sprite	PIKACHU		= new Sprite("pikachu");

	public static final Sprite PALLET_TOWN = new Sprite("pallet_town");

	private static final String FOLDER = "Sprites";

	private final Image image;
	private final String name;

	private Sprite(String fileName) {
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
