package gameplay.items.consumables;

import gameplay.gameObject.AttributeType;

public class HealthPotion extends Potion {

	public HealthPotion(String name) {
		super(name);
	}

	@Override
	public void consume() {
		target.getAttribute(AttributeType.HEALTH).add(50);
	}

}
