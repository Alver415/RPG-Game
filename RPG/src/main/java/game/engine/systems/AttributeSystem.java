package game.engine.systems;

import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.attributes.Value;

public class AttributeSystem extends GameSystem<AttributeMap> {

	public AttributeSystem() {
		super(AttributeMap.class);
	}

	@Override
	public void tick(double dt) {
		for (AttributeMap attributes : components) {
			Value health = attributes.get(AttributeType.HEALTH);
			health.add(2 * dt);
		}
	}
}
