package gui.components;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import gameplay.Position;
import gameplay.entity.Entity;
import gui.Player;
import gui.eventHandlers.GameInputHandler;
import gui.eventHandlers.PlayerInputProcessor;
import gui.settings.Setting;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameScene extends Scene {

	private final GameInputHandler	inputHandler;
	private final GraphicsContext	gc;
	private final AnimationTimer	timer;

	private final Set<Entity>	entities;
	private final Player		player;

	public GameScene() {
		this(new Group());
	}

	private GameScene(Group root) {
		super(root);
		Integer width = Setting.SCREEN_WIDTH.get();
		Integer height = Setting.SCREEN_HEIGHT.get();
		Canvas canvas = new Canvas(width, height);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();

		inputHandler = new GameInputHandler();
		setOnMouseMoved(inputHandler);
		setOnMousePressed(inputHandler);
		setOnMouseReleased(inputHandler);
		setOnKeyPressed(inputHandler);
		setOnKeyReleased(inputHandler);

		entities = new HashSet<Entity>();
		Entity entity = new Entity("Player Entity");
		entities.add(entity);

		player = new Player(entity);

		inputHandler.addInputProcessor(new PlayerInputProcessor(player));

		timer = new AnimationTimer() {
			@Override
			public void handle(long nanoTime) {
				inputHandler.process();
				for (Entity entity : entities) {
					entity.tick();
				}
				render(nanoTime);
			}
		};
		timer.start();
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

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	private void render(long nanoTime) {
		gc.clearRect(0, 0, getWidth(), getHeight());
		for (Entity entity : entities) {
			render(entity);
		}
	}

	private void render(Entity entity) {
		gc.setStroke(Color.RED);
		Position position = entity.getPosition();
		double x = position.getX();
		double y = position.getY();

		gc.fillOval(x, y, 10, 10);
	}

}
