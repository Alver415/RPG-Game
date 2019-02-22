package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Entity;
import game.engine.components.Render;
import gameplay.Vector2D;
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
			if (entity.hasRender()) {
				renders.add(entity.getRender());
			}
		}
		
		for (Render render : renders) {
			render(render);
		}
	}
	
	protected void render(Render render) {
		graphicsContext.setStroke(Color.BLACK);
		Vector2D position = render.getPosition().asVector();
		Color color = render.getColor();
		double radius = render.getRadius();
		drawCircle(position, radius, color);
	}
	
	private void drawCircle(Vector2D center, double radius, Color color) {
		graphicsContext.setFill(color);
		double diameter = radius * 2;
		double x = center.getX();
		double y = center.getY();
		double relativeX = (x + width / 2) - radius / 2;
		double relativeY = ((height - y) - height / 2) - radius;
		graphicsContext.strokeOval(relativeX, relativeY, diameter, diameter);
		graphicsContext.fillOval(relativeX, relativeY, diameter, diameter);
	}

	
}
