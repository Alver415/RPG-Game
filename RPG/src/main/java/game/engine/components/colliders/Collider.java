package game.engine.components.colliders;

import game.engine.Vector2D;
import game.engine.components.Component;

public abstract class Collider extends Component{

	protected boolean isStatic = false;

	public Vector2D getCenter() {
		return parent.getTransform().getPosition();
	}
	
	public abstract void handleCollision(Collider other);

	public void tick(double dt) {
	}
}
