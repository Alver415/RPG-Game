package gameplay.items.consumables;

import gameplay.entity.Attribute;

public class HealthPotion extends Potion {

	public HealthPotion(String name) {
		super(name);
	}

	@Override
	public void consume() {
		target.getAttribute(Attribute.HEALTH).add(50);
	}

}
