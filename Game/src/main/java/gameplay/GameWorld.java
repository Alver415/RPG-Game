package gameplay;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import frontend.Renderer;
import frontend.control.ComputerController;
import frontend.control.Controller;
import frontend.control.PlayerController;
import frontend.fxml.GameInputHandler;
import gameplay.entity.Entity;
import javafx.animation.AnimationTimer;

public class GameWorld {

	private final Set<Entity> entities;

	private final Set<Renderer>	renderers;
	private final GameInputHandler	inputHandler;
	private final Set<Controller>	controllers;
	private final GameTimer		timer;

	public GameWorld() {
		this.entities = new HashSet<Entity>();
		this.renderers = new HashSet<>();
		this.controllers = new HashSet<>();
		this.inputHandler = new GameInputHandler();
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

	public void addController(Controller controller) {
		this.controllers.add(controller);
	}

	public void render() {
		for (Renderer renderer : renderers) {
			renderer.render();
		}
	}

	public void processInput() {
		for (Controller controller : controllers) {
			controller.tick();
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

	public GameInputHandler getinputHandler() {
		return inputHandler;
	}

	public class GameTimer extends AnimationTimer {

		private long lastTick = 0;

		@Override
		public void handle(long nanoTime) {
			double dt = ((nanoTime - lastTick) / 1000000000d);
			lastTick = nanoTime;

			processInput();
			tick(dt);
			render();
		}

	}

	public void addHuman(Entity entity) {
		this.addEntity(entity);
		this.addController(new PlayerController(inputHandler, entity));
	}

	public void addComputer(Entity entity) {
		this.addEntity(entity);
		this.addController(new ComputerController(entity));
	}

}
