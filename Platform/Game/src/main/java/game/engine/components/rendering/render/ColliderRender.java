package game.engine.components.rendering.render;

import game.engine.components.colliders.Collider;
import game.engine.components.rendering.Render;
import game.engine.geometry.Circle;
import game.engine.geometry.Rectangle;
import game.engine.geometry.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColliderRender extends Render{
	
	private boolean isCollision = false;
	
	public ColliderRender() {
		super();
	}

	public boolean isCollision() {
		return isCollision;
	}
	public void setCollision(boolean isCollision) {
		this.isCollision = isCollision;
	}

	@Override
	public void draw(GraphicsContext gc, double x, double y, double w, double h) {
		Collider collider = this.getParentGameObject().getCollider();
		if (collider == null) {
			return;
		}
		gc.setStroke(Color.RED);
		Shape shape = collider.getShape();
		if (shape instanceof Circle) {
			gc.strokeOval(x, y, w, h);
		} 
		else if  (shape instanceof Rectangle) {
			gc.strokeRect(x, y, w, h);
			
		}
	}

	@Override
	public double getWidth() {
		Collider collider = this.getParentGameObject().getCollider();
		return collider == null ? 0 : collider.getWidth();
	}

	@Override
	public double getHeight() {
		Collider collider = this.getParentGameObject().getCollider();
		return collider == null ? 0 : collider.getHeight();
	}
	
	
}
