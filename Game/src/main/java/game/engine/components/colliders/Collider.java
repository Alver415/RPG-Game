package game.engine.components.colliders;

import game.engine.Vector2D;
import game.engine.components.Component;
import game.engine.components.transforms.Position;

public abstract class Collider extends Component{

	Collider(){
		
	}
	
	public Vector2D getCenter() {
		return entity.getPosition().asVector();
	}
	
	public abstract void handleCollision(Collider other);
}
