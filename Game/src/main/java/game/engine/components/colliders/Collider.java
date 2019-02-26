package game.engine.components.colliders;

import game.engine.Vector2D;
import game.engine.components.Component;

public abstract class Collider extends Component{

	public Collider(){
		super(Component.Type.COLLIDER);
	}
	
	public Vector2D getCenter() {
		return entity.getTransform().getPosition();
	}
	
	public abstract void handleCollision(Collider other);
}
