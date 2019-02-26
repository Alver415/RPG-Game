package game.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.engine.systems.AttributeSystem;
import game.engine.systems.CollisionSystem;
import game.engine.systems.GameSystem;
import game.engine.systems.InputSystem;
import game.engine.systems.MovementSystem;
import game.engine.systems.RenderingSystem;

public class GameWorld {

	public final static GameWorld INSTANCE = new GameWorld();

	private final GameTimer timer;
	private final Map<Long, Entity> entities;

	private final InputSystem inputSystem;
	private final AttributeSystem attributeSystem;
	private final MovementSystem movementSystem;
	private final CollisionSystem collisionSystem;
	private final RenderingSystem renderingSystem;

	private GameWorld() {
		this.entities = new HashMap<>();
		this.inputSystem = InputSystem.INSTANCE;
		this.attributeSystem = AttributeSystem.INSTANCE;
		this.movementSystem = MovementSystem.INSTANCE;
		this.collisionSystem = CollisionSystem.INSTANCE;
		this.renderingSystem = RenderingSystem.INSTANCE;

		this.timer = new GameTimer() {
			@Override
			public void tick(double dt) {
				process(inputSystem, dt);
				process(attributeSystem, dt);
				process(movementSystem, dt);
				process(collisionSystem, dt);
				process(renderingSystem, dt);
			}
		};
		this.timer.start();
	}

	private void process(GameSystem<?> system, double dt) {
		if (system != null) {
			system.tick(dt);
		}
	}

	public void addEntity(Entity entity) {
		this.entities.put(entity.getId(), entity);
	}
	
	public Entity getEntity(Long id) {
		return this.entities.get(id);
	}

	public Set<Entity> getEntities() {
		return new HashSet<>(entities.values());
	}
	public InputSystem getInputSystem() {
		return inputSystem;
	}

	public MovementSystem getMovementSystem() {
		return movementSystem;
	}

	public CollisionSystem getCollisionSystem() {
		return collisionSystem;
	}

	public RenderingSystem getRenderingSystem() {
		return renderingSystem;
	}

	public GameTimer getTimer() {
		return timer;
	}

	
}
