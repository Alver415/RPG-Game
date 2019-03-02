package game.engine.components.rendering.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleRender extends BasicRender{

	private double radius;
	
	public CircleRender(Color color, double radius) {
		super(color);
		this.radius = radius;
	}
	
	public double getWidth() {
		return radius * 2;
	}
	
	public double getHeight() {
		return radius * 2;
	}

	public double getRadius() {
		return radius;
	}

	public double getDiameter() {
		return radius;
	}
	
	@Override
	protected void drawInternal(GraphicsContext gc, double x, double y, double w, double h) {
		gc.setFill(getColor());
		gc.fillOval(x, y, w, h);
		gc.setStroke(getBorderColor());
		gc.strokeOval(x, y, w, h);
	}
}
