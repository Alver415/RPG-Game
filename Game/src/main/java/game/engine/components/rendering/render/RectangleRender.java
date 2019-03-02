package game.engine.components.rendering.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleRender extends BasicRender{

	private double width;
	private double height;
	
	public RectangleRender(Color color, double size) {
		this(color,size, size);
	}
	public RectangleRender(Color color, double width, double height) {
		super(color);
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}

	@Override
	protected void drawInternal(GraphicsContext gc, double x, double y, double w, double h) {
		gc.setFill(getColor());
		gc.fillRect(x, y, w, h);
		gc.setStroke(getBorderColor());
		gc.strokeRect(x, y, w, h);
	}
}
