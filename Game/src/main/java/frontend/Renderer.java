package frontend;

import java.util.Collection;

import frontend.control.ComputerController;
import frontend.control.PlayerController;
import frontend.settings.Setting;
import gameplay.GameWorld;
import gameplay.entity.Entity;
import gameplay.entity.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {

	private final GameWorld gameworld;
	private final Canvas canvas;
	private final GraphicsContext graphicsContext;
	
	private double width;
	private double height;
	
	public Renderer(GameWorld gameWorld, Canvas canvas) {
		this.gameworld = gameWorld;
		this.canvas = canvas;
		this.graphicsContext = canvas.getGraphicsContext2D();
	}

	public void render() {
		width = canvas.getWidth();
		height = canvas.getHeight();

		graphicsContext.clearRect(0, 0, width, height);
		
		renderEntities(gameworld.getEntities());
		renderBoundingBox();
	}

	private void renderEntities(Collection<Entity> entities) {
		graphicsContext.setFill(Color.RED);
		graphicsContext.setStroke(Color.BLACK);
		for (Entity entity : entities) {
			render(entity);
		}
	}

	private void renderBoundingBox() {
		graphicsContext.setStroke(Color.RED);
		graphicsContext.strokeRect(0, 0, width, height);
	}

	private void render(Entity entity) {
		drawCircle(entity.getPosition(), 25);
	}

	private void drawCircle(Position center, double radius) {
		double x = center.getX();
		double y = center.getY();
		double relativeX = (x + width / 2) - radius / 2;
		double relativeY = ((height - y) - height / 2) - radius / 2;
		graphicsContext.strokeOval(relativeX, relativeY, radius, radius);
		graphicsContext.fillOval(relativeX, relativeY, radius, radius);
	}

}
