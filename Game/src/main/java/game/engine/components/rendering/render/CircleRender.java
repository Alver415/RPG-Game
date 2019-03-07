package game.engine.components.rendering.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleRender extends BasicRender{

	private double diameter;
	
	public CircleRender(Color color, double diameter) {
		super(color);
		this.diameter = diameter / 2;
	}
	
	public double getWidth() {
		return diameter;
	}
	
	public double getHeight() {
		return diameter;
	}

	public double getDiameter() {
		return diameter;
	}

	public double getRadius() {
		return diameter / 2;
	}

	@Override
	protected void drawInternal(GraphicsContext gc, double x, double y, double w, double h) {
		gc.setFill(getColor());
		gc.fillOval(x, y, w, h);
		gc.setStroke(getBorderColor());
		gc.strokeOval(x, y, w, h);
	}
}
