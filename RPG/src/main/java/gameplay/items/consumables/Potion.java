package gameplay.items.consumables;

public abstract class Potion extends Consumable {

	public Potion(String name) {
		super(name);
	}

	@Override
	public abstract void consume();

}
