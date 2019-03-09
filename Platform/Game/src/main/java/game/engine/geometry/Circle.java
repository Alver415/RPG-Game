package game.engine.geometry;

import game.engine.Vector2D;

public class Circle extends Shape{

	private double diameter;
	
	public Circle(double diameter) {
		super(Vector2D.ZERO);
		this.diameter = diameter;
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

	public void setRadius(double radius) {
		this.diameter = radius * 2;
	}

	@Override
	public final AABB getAABB() {
		double r = getRadius();
		double x = getCenter().getX();
		double y = getCenter().getY();
		return new AABB(x - r, x + r, y - r,  y + r);
	}

	
}
