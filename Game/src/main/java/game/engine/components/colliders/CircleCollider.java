package game.engine.components.colliders;

import game.engine.Vector2D;

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

	@Override
	public void handleCollision(Collider other) {
		if (other instanceof CircleCollider) {
			handleCollision((CircleCollider) other);
		}
	}

	public void handleCollision(CircleCollider other) {
		Vector2D aCenter = getCenter();
		Vector2D bCenter = other.getCenter();

		Vector2D difference = aCenter.subtract(bCenter);
	
		double aRadius = getRadius();
		double bRadius = getRadius();
		
		double distance = aCenter.distance(bCenter);
		
		double overlap = aRadius + bRadius - distance;
		
		getEntity().getTransform().move(difference.normalize().scalar(overlap * 0.5));
	}
	
}
