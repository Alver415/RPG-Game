package game.engine.components.rigidbody;

import game.engine.Vector2D;

public class Force{

	//Impact forces are removed each time they're accounted for and don't scale to deltaTime.
	private final boolean isImpact;
	private final Vector2D vector;

	public Force(Vector2D vector) {
		this(vector, true);
	}
	public Force(Vector2D vector, boolean isImpact) {
		this.vector = vector;
		this.isImpact = isImpact;
	}

	public boolean isContinuous() {
		return !isImpact;
	}

	public boolean isImpact() {
		return isImpact;
	}

	public Vector2D getVector() {
		return vector;
	}
	
	

	
}
