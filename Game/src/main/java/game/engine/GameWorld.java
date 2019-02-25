package game.engine;

import java.util.HashSet;
import java.util.Set;

import game.engine.systems.CollisionSystem;
import game.engine.systems.GameSystem;
import game.engine.systems.InputSystem;
import game.engine.systems.MovementSystem;
import game.engine.systems.RenderingSystem;

public class GameWorld {

	private static GameWorld instance;

	private GameTimer timer;
	private Set<Entity> entities;

	private InputSystem inputSystem;
	private MovementSystem movementSystem;
	private CollisionSystem collisionSystem;
	private RenderingSystem renderingSystem;

	public GameWorld(InputSystem inputSystem, MovementSystem movementSystem, CollisionSystem collisionSystem,
			RenderingSystem renderingSystem) {
		GameWorld.instance = this;
		this.entities = new HashSet<>();
		this.inputSystem = inputSystem;
		this.movementSystem = movementSystem;
		this.collisionSystem = collisionSystem;
		this.renderingSystem = renderingSystem;

		this.timer = new GameTimer() {
			@Override
			public void tick(double dt) {
				inputSystem.process(entities, dt);
				movementSystem.process(entities, dt);
				collisionSystem.process(entities, dt);
				renderingSystem.process(entities, dt);
			}
		};
		this.timer.start();
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

	public static GameWorld get() {
		return instance;
	}

	public GameTimer getTimer() {
		return timer;
	}

	public Set<Entity> getEntities() {
		return entities;
	}
}
