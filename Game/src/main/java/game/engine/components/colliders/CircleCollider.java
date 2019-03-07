package game.engine.components.colliders;

import game.engine.Vector2D;

public class CircleCollider extends Collider{

	private double diameter;
	
	public CircleCollider(double diameter) {
		this.diameter = diameter;
	}

	public CircleCollider(double diameter, boolean isStatic) {
		this(diameter);
		this.isStatic = isStatic;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getRadius() {
		return diameter / 2;
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
	
		double aDiameter = getDiameter();
		double bDiameter = getDiameter();
		
		double distance = aCenter.distance(bCenter);
		double overlap = aDiameter + bDiameter - distance;
		
		double compensation = other.isStatic ? overlap : overlap * 0.5;
		getParentGameObject().move(difference.normalize().scalar(compensation));
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
		double overlap = diameter - distance;

		Vector2D penetration = difference.normalize().scalar(overlap);

		double compensation = other.isStatic ? 1 : 0.5;
		getParentGameObject().move(penetration.scalar(compensation));
	}

	
}
