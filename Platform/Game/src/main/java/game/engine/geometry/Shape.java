package game.engine.geometry;

import game.engine.Vector2D;

public abstract class Shape {

	private Vector2D center;

	public Shape(Vector2D center) {
		this.center = center;
	}
	
	public Vector2D getCenter() {
		return center;
	}
	
	public void setCenter(Vector2D center) {
		this.center = center;
	}
	
	public abstract AABB getAABB();

	public double getWidth() {
		return getAABB().getWidth();
	}

	public double getHeight() {
		return getAABB().getHeight();
	}
}

