package game.engine.components.rendering;

import game.engine.Vector2D;
import game.engine.components.Component;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Render extends Component {

	private Color borderColor = Color.rgb(0, 0, 0, 0);
	
	private double zIndex;
	
	public final void draw(GraphicsContext gc, double x, double y, double w, double h) {
		drawInternal(gc, x, y, w, h);
	}

	protected abstract void drawInternal(GraphicsContext gc, double x, double y, double w, double h);
	
	public Vector2D getCenter() {
		return parent.getTransform().getPosition();
	}

	public abstract double getWidth();
	
	public abstract double getHeight();
	
	public Color getBorderColor() {
		return borderColor;
	}
	
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public double getzIndex() {
		return zIndex;
	}

	public void setzIndex(double zIndex) {
		this.zIndex = zIndex;
	}

	protected static Color randomColor() {
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		return Color.rgb(r, g, b);
	}

}
