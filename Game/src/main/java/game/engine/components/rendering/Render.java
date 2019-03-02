package game.engine.components.rendering;

import game.engine.Vector2D;
import game.engine.components.Component;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.systems.RenderingSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Render extends Component {

	private Color borderColor = Color.rgb(0, 0, 0, 0);
	
	private double zIndex;
	
	public final void draw(GraphicsContext gc, Viewport viewport) {
		Vector2D worldCenter = getCenter();
		Vector2D canvasCenter = RenderingSystem.INSTANCE.worldToCanvas(worldCenter);

		double z = viewport.getZoom(); // view zoom
		
		double w = getWidth() * z; // relative width
		double h = getHeight() * z; // relative height
		double x = canvasCenter.getX() - w / 2;
		double y = canvasCenter.getY() - h / 2;

		draw(gc, x, y, w, h);
		drawCollision(gc, x, y, w, h);
	}

	protected abstract void draw(GraphicsContext gc, double x, double y, double w, double h);
	
	protected void drawCollision(GraphicsContext gc, double x, double y, double w, double h) {
		Collider c = parent.getCollider();
		if (c != null) {
			gc.setStroke(getBorderColor());
			if (c instanceof RectangleCollider) {
				gc.strokeRect(x, y, w, h);
			} else if (c instanceof CircleCollider) {
				gc.strokeOval(x, y, w, h);
			}
		}
		
	}
	
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
