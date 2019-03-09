package game.engine.components.rendering.render;

import game.engine.components.rendering.Render;
import game.engine.geometry.Circle;
import game.engine.geometry.Rectangle;
import game.engine.geometry.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BasicShapeRender extends Render{

	private Color color;
	private Shape shape;
	
	public BasicShapeRender(Shape shape, Color color) {
		super();
		this.shape = shape;
		this.color = color;
	}
	
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void draw(GraphicsContext gc, double x, double y, double w, double h) {
		gc.setFill(getColor());
		if (shape instanceof Circle) {
			gc.fillOval(x, y, w, h);
		}
		if (shape instanceof Rectangle) {
			gc.fillRect(x, y, w, h);
		}
	}

	@Override
	public double getWidth() {
		return shape.getWidth();
	}

	@Override
	public double getHeight() {
		return shape.getHeight();
	}
}
