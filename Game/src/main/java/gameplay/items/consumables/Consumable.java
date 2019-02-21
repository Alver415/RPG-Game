package gameplay.items.consumables;

import gameplay.entity.Character;
import gameplay.items.Item;

public abstract class Consumable extends Item {

	protected Character target;

	public Consumable(String name) {
		super(name);
	}

	public abstract void consume();

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

}
