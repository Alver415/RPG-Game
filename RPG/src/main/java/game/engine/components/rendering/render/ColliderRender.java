package game.engine.components.rendering.render;

import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.rendering.Render;
import javafx.scene.canvas.GraphicsContext;

public class ColliderRender extends Render{

	public ColliderRender() {
		super();
	}

	@Override
	protected void drawInternal(GraphicsContext gc, double x, double y, double w, double h) {
		Collider collider = this.getParentGameObject().getCollider();
		if (collider == null) {
			return;
		}
		gc.setStroke(getBorderColor());
		if (collider instanceof CircleCollider) {
			gc.strokeOval(x, y, w, h);
		} 
		else if  (collider instanceof RectangleCollider) {
			gc.strokeRect(x, y, w, h);
			
		}
	}

	@Override
	public double getWidth() {
		Collider collider = this.getParentGameObject().getCollider();
		if (collider == null) {
			return 0;
		}
		if (collider instanceof CircleCollider) {
			return ((CircleCollider)collider).getDiameter();
		} 
		else if  (collider instanceof RectangleCollider) {
			return ((RectangleCollider)collider).getWidth();
		}
		return 0;
	}

	@Override
	public double getHeight() {
		Collider collider = this.getParentGameObject().getCollider();
		if (collider == null) {
			return 0;
		}
		if (collider instanceof CircleCollider) {
			return ((CircleCollider)collider).getDiameter();
		} 
		else if  (collider instanceof RectangleCollider) {
			return ((RectangleCollider)collider).getHeight();
		}
		return 0;
	}
	
	
}
