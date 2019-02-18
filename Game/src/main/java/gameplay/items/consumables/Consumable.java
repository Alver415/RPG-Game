package gameplay.items.consumables;

import gameplay.entity.Entity;
import gameplay.items.Item;

public abstract class Consumable extends Item {

	protected Entity target;

	public Consumable(String name) {
		super(name);
	}

	public abstract void consume();

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

}
