package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Vector2D;
import game.engine.components.transforms.Transform;

public class MovementSystem extends GameSystem<Transform> {

	public static final MovementSystem INSTANCE = new MovementSystem();


	private MovementSystem() {
		super(new HashSet<Transform>());
	}

	@Override
	public void tick(double dt) {
		for (Transform transform : components) {
			transform.tick(dt);
		}
	}
}
