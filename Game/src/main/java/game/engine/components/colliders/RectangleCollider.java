package game.engine.components.colliders;

import game.engine.Vector2D;

public class RectangleCollider extends Collider{

	private double width;
	private double height;
	
	public RectangleCollider(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public RectangleCollider(double width, double height, boolean isStatic) {
		this(width, height);
		this.isStatic = isStatic;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public void handleCollision(Collider other) {
		if (other instanceof CircleCollider) {
			handleCollision((CircleCollider) other);
		} else if (other instanceof RectangleCollider) {
			handleCollision((RectangleCollider) other);
		}
	}

	public void handleCollision(CircleCollider other) {
		if (this.isStatic) {
			return;
		}
		Vector2D aCenter = getCenter();
		Vector2D bCenter = other.getCenter();

		double bx = bCenter.getX();
		double by = bCenter.getY();

		double rgt = aCenter.getX() + getWidth() / 2;
		double lft = aCenter.getX() - getWidth() / 2;

		double top = aCenter.getY() + getHeight() / 2;
		double bot = aCenter.getY() - getHeight() / 2;

		// Find the closest point to the circle within the rectangle
		double closestX = Math.min(Math.max(bx, lft), rgt);
		double closestY = Math.min(Math.max(by, bot), top);
		Vector2D closest = new Vector2D(closestX, closestY);

		Vector2D difference = bCenter.subtract(closest);

		double distance = difference.magnitude();

		double overlap = distance - other.getRadius();
		Vector2D penetration = difference.normalize().scalar(overlap);

		double compensation = other.isStatic ? 1 : 0.5;
		getParent().move(penetration.scalar(compensation));
	}

	public void handleCollision(RectangleCollider other) {
		if (this.isStatic) {
			return;
		}

		Vector2D aCenter = getCenter();
		Vector2D bCenter = other.getCenter();

		double aRgt = aCenter.getX() + getWidth() / 2;
		double aLft = aCenter.getX() - getWidth() / 2;

		double aTop = aCenter.getY() + getHeight() / 2;
		double aBot = aCenter.getY() - getHeight() / 2;

		double bRgt = bCenter.getX() + other.getWidth() / 2;
		double bLft = bCenter.getX() - other.getWidth() / 2;

		double bTop = bCenter.getY() + other.getHeight() / 2;
		double bBot = bCenter.getY() - other.getHeight() / 2;

		double x1Overlap = aRgt - bLft;
		double x2Overlap = aLft - bRgt;
		double y1Overlap = aTop - bBot;
		double y2Overlap = aBot - bTop;

		double xOverlap = Math.abs(x1Overlap) <= Math.abs(x2Overlap) ? x1Overlap : x2Overlap;
		double yOverlap = Math.abs(y1Overlap) <= Math.abs(y2Overlap) ? y1Overlap : y2Overlap;

		Vector2D overlap = Math.abs(xOverlap) <= Math.abs(yOverlap) ? new Vector2D(-xOverlap, 0)
				: new Vector2D(0, -yOverlap);


		double compensation = other.isStatic ? 1 : 0.5;
		getParent().move(overlap.scalar(compensation));

	}
	
}
