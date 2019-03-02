package game.engine.components.colliders;

import game.engine.Vector2D;

public class CircleCollider extends Collider{

	private double radius;
	
	public CircleCollider(double radius) {
		this.radius = radius;
	}

	public CircleCollider(double radius, boolean isStatic) {
		this(radius);
		this.isStatic = isStatic;
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
		else if (other instanceof RectangleCollider) {
			handleCollision((RectangleCollider) other);
		}
	}

	public void handleCollision(CircleCollider other) {
		if (this.isStatic) {
			return;
		}
		Vector2D aCenter = getCenter();
		Vector2D bCenter = other.getCenter();

		Vector2D difference = aCenter.subtract(bCenter);
	
		double aRadius = getRadius();
		double bRadius = getRadius();
		
		double distance = aCenter.distance(bCenter);
		double overlap = aRadius + bRadius - distance;
		
		double compensation = other.isStatic ? overlap : overlap * 0.5;
		getParent().move(difference.normalize().scalar(compensation));
	}

	public void handleCollision(RectangleCollider other) {
		if (this.isStatic) {
			return;
		}
		Vector2D aCenter = getCenter();
		Vector2D bCenter = other.getCenter();

		double ax = aCenter.getX();
		double ay = aCenter.getY();

		double rgt = bCenter.getX() + other.getWidth() / 2;
		double lft = bCenter.getX() - other.getWidth() / 2;

		double top = bCenter.getY() + other.getHeight() / 2;
		double bot = bCenter.getY() - other.getHeight() / 2;

		// Find the closest point to the circle within the rectangle
		double closestX = Math.min(Math.max(ax, lft), rgt);
		double closestY = Math.min(Math.max(ay, bot), top);
		Vector2D closest = new Vector2D(closestX, closestY);

		Vector2D difference = aCenter.subtract(closest);

		double distance = difference.magnitude();
		double overlap = radius - distance;

		Vector2D penetration = difference.normalize().scalar(overlap);

		double compensation = other.isStatic ? 1 : 0.5;
		getParent().move(penetration.scalar(compensation));
	}
	
}
