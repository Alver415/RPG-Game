package game.engine.components.colliders;

import game.engine.Vector2D;
import game.engine.components.Component;
import game.engine.geometry.AABB;
import game.engine.geometry.Shape;

public class Collider extends Component{

	protected Shape shape;

	public Collider(Shape shape) {
		this.shape = shape;
	}

	public Vector2D getPosition() {
		return parent.getPosition();
	}

	public final Shape getShape() {
		this.shape.setCenter(getPosition());
		return this.shape;
	}

	public AABB getAABB() {
		return getShape().getAABB();
	}
	
	public void onCollision(Collider other) {
		// Override to add functionality
	}

	public double getWidth() {
		return getAABB().getWidth();
	}
	public double getHeight() {
		return getAABB().getHeight();
	}

}
