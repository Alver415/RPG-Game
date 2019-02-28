package game.engine.systems;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.engine.Vector2D;
import game.engine.components.transforms.Transform;

public class MovementSystem extends GameSystem<Transform> {

	public static final MovementSystem INSTANCE = new MovementSystem();

	private MovementSystem() {
	}

	@Override
	public void tick(double dt) {
		for (Transform transform : components) {
			Vector2D position = transform.getPosition();
			Vector2D velocity = transform.getVelocity();
			Vector2D acceleration = transform.getAcceleration();
			
			Vector2D newVelocity = velocity.add(acceleration.scalar(dt));
			Vector2D newPosition = position.add(velocity.scalar(dt));
			
			transform.setVelocity(newVelocity);
			transform.setPosition(newPosition);
		}
	}
}
