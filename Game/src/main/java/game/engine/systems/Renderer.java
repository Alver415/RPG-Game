package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Entity;
import game.engine.components.Render;
import gameplay.entity.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer extends GameSystem{

	private final GraphicsContext graphicsContext;
	
	private double width;
	private double height;
	
	public Renderer(Canvas canvas) {
		this.graphicsContext = canvas.getGraphicsContext2D();
	}

	@Override
	public void process(Set<Entity> entities, double dt) {
		width = graphicsContext.getCanvas().getWidth();
		height = graphicsContext.getCanvas().getHeight();
		graphicsContext.clearRect(0, 0, width, height);
		
		Set<Render> renders = new HashSet<Render>();
		
		for (Entity entity : entities) {
			if (entity.hasComponent(Render.class)) {
				renders.add(entity.getComponent(Render.class));
			}
		}
		
		for (Render render : renders) {
			render(render);
		}
	}
	
	protected void render(Render render) {
		graphicsContext.setStroke(Color.BLACK);
		Position position = render.getPosition();
		Color color = render.getColor();
		drawCircle(position, 25, color);
	}
	
	private void drawCircle(Position center, double radius, Color color) {
		graphicsContext.setFill(color);
		double x = center.getX();
		double y = center.getY();
		double relativeX = (x + width / 2) - radius / 2;
		double relativeY = ((height - y) - height / 2) - radius / 2;
		graphicsContext.strokeOval(relativeX, relativeY, radius, radius);
		graphicsContext.fillOval(relativeX, relativeY, radius, radius);
	}

	
}
