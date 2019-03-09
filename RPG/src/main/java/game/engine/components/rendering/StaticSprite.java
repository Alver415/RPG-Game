package game.engine.components.rendering;

public class StaticSprite extends Sprite {

	public static final StaticSprite	PALLET_TOWN	= new StaticSprite("pallet_town");
	public static final StaticSprite	TRAINER		= new StaticSprite("trainer");

	public static final StaticSprite	BULBASAUR	= new StaticSprite("bulbasaur");
	public static final StaticSprite	CHARMANDER	= new StaticSprite("charmander");
	public static final StaticSprite	SQUIRTLE	= new StaticSprite("squirtle");
	public static final StaticSprite	PIKACHU		= new StaticSprite("pikachu");

	protected StaticSprite(String fileName) {
		super(fileName);
	}
	
	}
