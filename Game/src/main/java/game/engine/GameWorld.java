package game.engine;

import java.util.HashSet;
import java.util.Set;

import game.engine.systems.GameSystem;
import gameplay.GameTimer;

public class GameWorld {

	private static GameWorld instance;
	
	private final GameTimer				timer;
	private final Set<GameSystem>		systems;
	private final Set<Entity>			entities;
	
	private GameWorld() {
		this.entities = new HashSet<>();
		this.systems = new HashSet<>();
		this.timer = new GameTimer() {
			@Override
			public void tick(double dt) {
				for (GameSystem system : systems) {
					system.process(entities, dt);
				}
			}
		};
	}
	
	public void addSystem(GameSystem system) {
		this.systems.add(system);
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

	public void stop() {
		timer.stop();
	}

	public void start() {
		timer.start();
	}
	
	public static GameWorld get() {
		if (instance == null) {
			instance = new GameWorld();
		}
		return instance;
	}
}
