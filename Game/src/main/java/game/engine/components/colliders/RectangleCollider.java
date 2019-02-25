package game.engine.components.colliders;

public class RectangleCollider extends Collider{

	private double width;
	private double height;
	
	public RectangleCollider(double width, double height) {
		this.width = width;
		this.height = height;
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
		// TODO Auto-generated method stub
	}

	
}
