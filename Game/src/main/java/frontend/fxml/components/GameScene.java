package frontend.fxml.components;

import frontend.Renderer;
import frontend.control.ComputerController;
import frontend.control.PlayerController;
import frontend.fxml.GameInputHandler;
import frontend.settings.Setting;
import gameplay.GameWorld;
import gameplay.entity.Entity;
import gameplay.entity.Position;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameScene extends Scene implements Renderer {

	private final GameWorld gameWorld;

	private final GameInputHandler	inputHandler;
	private final GraphicsContext	gc;

	public GameScene(GameWorld gameWorld) {
		this(new Pane(), gameWorld);
	}

	private GameScene(Pane root, GameWorld gameWorld) {
		super(root);
		this.gameWorld = gameWorld;
		this.gameWorld.addRenderer(this);

		double width = Setting.SCREEN_WIDTH.get();
		double height = Setting.SCREEN_HEIGHT.get();
		root.setPrefWidth(width);
		root.setPrefHeight(height);

		Canvas canvas = new GameCanvas();
		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());

		root.getChildren().add(canvas);
		this.gc = canvas.getGraphicsContext2D();

		this.inputHandler = new GameInputHandler();
		setOnMouseMoved(inputHandler);
		setOnMousePressed(inputHandler);
		setOnMouseReleased(inputHandler);
		setOnKeyPressed(inputHandler);
		setOnKeyReleased(inputHandler);

		gameWorld.addInputHandler(inputHandler);
	}

	public void addHuman(Entity entity) {
		this.inputHandler.addController(new PlayerController(inputHandler, entity));
	}

	public void addComputer(Entity entity) {
		this.inputHandler.addController(new ComputerController(entity));
	}

	@Override
	public void render(GameWorld world) {
		gc.clearRect(0, 0, getWidth(), getHeight());
		gc.setFill(Color.RED);
		gc.setStroke(Color.BLACK);
		for (Entity entity : world.getEntities()) {
			render(entity);
		}
		renderBoundingBox();
	}

	private void renderBoundingBox() {
		gc.setStroke(Color.RED);
		gc.strokeRect(0, 0, getWidth(), getHeight());
	}

	private void render(Entity entity) {
		drawCircle(entity.getPosition(), 25);
	}

	private void drawCircle(Position center, double radius) {
		double x = center.getX();
		double y = center.getY();
		double relativeX = (x + getWidth() / 2) - radius / 2;
		double relativeY = ((getHeight() - y) - getHeight() / 2) - radius / 2;
		gc.strokeOval(relativeX, relativeY, radius, radius);
		gc.fillOval(relativeX, relativeY, radius, radius);
	}

}
