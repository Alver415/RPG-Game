package game.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import frontend.settings.Control;
import game.engine.systems.AttributeSystem;
import game.engine.systems.BehaviorSystem;
import game.engine.systems.CollisionSystem;
import game.engine.systems.GameSystem;
import game.engine.systems.MovementSystem;
import game.engine.systems.RenderingSystem;

public class GameWorld {

	public final static GameWorld INSTANCE = new GameWorld();

	private final GameTimer timer;
	private final InputHandler inputHandler;
	
	private final Map<Long, GameObject> entities;

	private final BehaviorSystem	behaviorSystem;
	private final MovementSystem	movementSystem;
	private final AttributeSystem	attributeSystem;
	private final CollisionSystem	collisionSystem;
	private final RenderingSystem	renderingSystem;

	private GameWorld() {
		this.entities = new HashMap<>();
		this.inputHandler = new InputHandler();
		this.behaviorSystem = BehaviorSystem.INSTANCE;
		this.movementSystem = MovementSystem.INSTANCE;
		this.attributeSystem = AttributeSystem.INSTANCE;
		this.collisionSystem = CollisionSystem.INSTANCE;
		this.renderingSystem = RenderingSystem.INSTANCE;

		this.timer = new GameTimer() {
			@Override
			public void tick(double dt) {
				process(behaviorSystem, dt);
				process(movementSystem, dt);
				process(attributeSystem, dt);
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

	public void addGameObject(GameObject gameObject) {
		this.entities.put(gameObject.getId(), gameObject);
	}
	public void remove(GameObject gameObject) {
		this.entities.remove(gameObject.getId());
	}
	
	public GameObject getGameObject(Long id) {
		return this.entities.get(id);
	}

	public Set<GameObject> getEntities() {
		return new HashSet<>(entities.values());
	}
	public BehaviorSystem getInputSystem() {
		return behaviorSystem;
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
