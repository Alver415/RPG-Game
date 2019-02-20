package gameplay;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import frontend.Renderer;
import frontend.fxml.GameInputHandler;
import gameplay.entity.Entity;
import javafx.animation.AnimationTimer;

public class GameWorld {

	private final Set<Entity> entities;

	private final Set<Renderer>	renderers;
	private final Set<GameInputHandler>	inputHandlers;
	private final GameTimer		timer;

	public GameWorld() {
		this.entities = new HashSet<Entity>();
		this.renderers = new HashSet<>();
		this.inputHandlers = new HashSet<>();
		this.timer = new GameTimer();
	}

	private void tick(double dt) {
		for (Entity entity : entities) {
			entity.tick(dt);
		}
	}

	public void addEntity(Entity player) {
		this.entities.add(player);
	}

	public void addRenderer(Renderer renderer) {
		this.renderers.add(renderer);
	}

	public void render() {
		for (Renderer renderer : renderers) {
			renderer.render();
		}
	}

	public void addInputHandler(GameInputHandler inputHandler) {
		this.inputHandlers.add(inputHandler);
	}

	public void processInput() {
		for (GameInputHandler inputHandler : inputHandlers) {
			inputHandler.process();
		}
	}
	public void stop() {
		timer.stop();
	}

	public void start() {
		timer.start();
	}

	public Collection<Entity> getEntities() {
		return Collections.unmodifiableSet(entities);
	}

	public class GameTimer extends AnimationTimer {

		private long lastTick = System.nanoTime();

		@Override
		public void handle(long nanoTime) {
			double dt = ((nanoTime - lastTick) / 1000000000d);
			lastTick = nanoTime;

			processInput();
			tick(dt);
			render();
		}

	}

}
