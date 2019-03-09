package game.engine.geometry;

import game.engine.Vector2D;

public class Rectangle extends Shape{

	private double width;
	private double height;

	public Rectangle(double size) {
		this(size, size);
	}
	public Rectangle(double width, double height) {
		super(Vector2D.ZERO);
		this.width = width;
		this.height = height;
	}

	@Override
	public final AABB getAABB() {
		double w = width / 2;
		double h = height / 2;
		double x = getCenter().getX();
		double y = getCenter().getY();
		return new AABB(x - w, x + w, y - h, y - h);
	}

	
}
