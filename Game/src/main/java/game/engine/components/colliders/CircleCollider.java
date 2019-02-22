package game.engine.components.colliders;

public class CircleCollider extends Collider{

	private double radius;
	
	public CircleCollider(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
}
