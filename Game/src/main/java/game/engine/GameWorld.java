package game.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import frontend.settings.Control;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.PlayerController;
import game.engine.components.controllers.TargetedProjectileBehavior;
import game.engine.components.rendering.Render;
import game.engine.systems.AttributeSystem;
import game.engine.systems.BehaviorSystem;
import game.engine.systems.CollisionSystem;
import game.engine.systems.GameSystem;
import game.engine.systems.MovementSystem;
import game.engine.systems.RenderingSystem;
import javafx.scene.paint.Color;

public class GameWorld {

	public final static GameWorld INSTANCE = new GameWorld();

	private final GameTimer timer;
	private final InputHandler inputHandler;
	
	private final Map<Long, Entity> entities;

	private final BehaviorSystem behaviorSystem;
	private final AttributeSystem attributeSystem;
	private final MovementSystem movementSystem;
	private final CollisionSystem collisionSystem;
	private final RenderingSystem renderingSystem;

	private GameWorld() {
		this.entities = new HashMap<>();
		this.inputHandler = new InputHandler();
		this.behaviorSystem = BehaviorSystem.INSTANCE;
		this.attributeSystem = AttributeSystem.INSTANCE;
		this.movementSystem = MovementSystem.INSTANCE;
		this.collisionSystem = CollisionSystem.INSTANCE;
		this.renderingSystem = RenderingSystem.INSTANCE;

		this.timer = new GameTimer() {
			@Override
			public void tick(double dt) {
				process(behaviorSystem, dt);
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
	public void remove(Entity entity) {
		this.entities.remove(entity.getId());
	}
	
	public Entity getEntity(Long id) {
		return this.entities.get(id);
	}

	public Set<Entity> getEntities() {
		return new HashSet<>(entities.values());
	}
	public BehaviorSystem getInputSystem() {
		return behaviorSystem;
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

	public GameTimer getGameTimer() {
		return timer;
	}

	public double getGameTime() {
		return timer.getGameTime();
	}

	public double getDeltaTime() {
		return timer.getDeltaTime();
	}

	public double getFPS() {
		return timer.getFPS();
	}
	
	public Set<Control> getControls(){
		return inputHandler.getControls();
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

}
