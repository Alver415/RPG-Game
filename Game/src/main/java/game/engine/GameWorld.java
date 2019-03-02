package game.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import frontend.settings.Control;
import game.engine.components.Component;
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
	private final List<GameSystem<?>>	gameSystems;

	private GameWorld() {
		this.entities = new HashMap<>();
		this.inputHandler = new InputHandler();
		this.gameSystems = Collections.synchronizedList(new ArrayList<>());

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

	public <T extends Component> void register(Component component) {
		for (GameSystem<?> gameSystem : gameSystems) {
			gameSystem.register(component);
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

	public void toggleTime() {
		this.getRenderingSystem().toggleTime();
	}

	public void zoomIn() {
		this.getRenderingSystem().zoomOut();
	}

	public void zoomOut() {
		this.getRenderingSystem().zoomOut();
	}
}
