package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Vector2D;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.attributes.Value;
import game.engine.components.transforms.Transform;

public class AttributeSystem extends GameSystem<AttributeMap> {

	public static final AttributeSystem INSTANCE = new AttributeSystem();

	private AttributeSystem() {
		super(new HashSet<AttributeMap>());
	}

	@Override
	public void tick(double dt) {
		for (AttributeMap attributes : components) {
			Value health = attributes.get(AttributeType.HEALTH);
			health.add(2 * dt);
		}
	}
}
