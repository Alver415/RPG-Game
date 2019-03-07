package game.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import frontend.settings.Control;
import game.engine.components.Component;
import game.engine.systems.AttributeSystem;
import game.engine.systems.BehaviorSystem;
import game.engine.systems.CollisionSystem;
import game.engine.systems.GameSystem;
import game.engine.systems.MovementSystem;
import game.engine.systems.RenderingSystem;

public class GameWorld implements Parent {

	public final static GameWorld INSTANCE = new GameWorld();

	private final GameTimer timer;
	private final InputHandler inputHandler;
	
	private final Set<GameObject>		gameObjects;
	private final List<GameSystem<?>>	gameSystems;

	private GameWorld() {
		this.gameObjects = ConcurrentHashMap.newKeySet();
		this.gameSystems = Collections.synchronizedList(new ArrayList<>());

		this.inputHandler = new InputHandler();

		this.gameSystems.add(new BehaviorSystem());
		this.gameSystems.add(new MovementSystem());
		this.gameSystems.add(new AttributeSystem());
		this.gameSystems.add(new CollisionSystem());
		this.gameSystems.add(new RenderingSystem());

		this.timer = new GameTimer() {
			@Override
			public void tick(double dt) {
				for (GameSystem<?> gameSystem : gameSystems) {
					process(gameSystem, dt);
				}
			}
		};
		this.timer.start();
	}

	private void process(GameSystem<?> system, double dt) {
		if (system != null) {
			system.tick(dt);
		}
	}

	public void register(Child child) {
		if(child instanceof Component) {
			for (GameSystem<?> gameSystem : gameSystems) {
				gameSystem.register((Component)child);
			}
		}
		if (child instanceof GameObject) {
			this.gameObjects.add((GameObject) child);
		}
	}

	public Set<GameObject> getGameObjects() {
		return this.gameObjects;
	}

	@Override
	public Set<Child> getChildren() {
		return new HashSet<>(this.gameObjects);
	}

	@Override
	public void addChild(Child newGameObject) {
		if (newGameObject instanceof GameObject) {
			GameObject gameObject = (GameObject) newGameObject;
			this.gameObjects.add(gameObject);
			for (Child child : gameObject.getChildren()) {
				register(child);
			}
		}
	}

	public void remove(GameObject gameObject) {
		this.gameObjects.remove(gameObject);
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

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public RenderingSystem getRenderingSystem() {
		return getGameSystem(RenderingSystem.class);
	}

	private <T extends GameSystem<?>> T getGameSystem(Class<T> clazz) {
		for (GameSystem<?> gameSystem : gameSystems) {
			if (clazz.isAssignableFrom(gameSystem.getClass())) {
				return clazz.cast(gameSystem);
			}
		}
		return null;
	}

	public void toggleDebugDisplay() {
		this.getRenderingSystem().toggleDebugDisplay();
	}

	public void zoomIn() {
		this.getRenderingSystem().zoomIn();
	}

	public void zoomOut() {
		this.getRenderingSystem().zoomOut();
	}

	@Override
	public Parent getRoot() {
		return null;
	}


}
