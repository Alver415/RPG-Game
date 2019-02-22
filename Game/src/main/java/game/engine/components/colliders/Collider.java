package game.engine.components;

public class Collider extends Component{

	private boolean isCollided;
	private double radius = 10;
	
	public boolean isCollided() {
		return isCollided;
	}

	public void setCollided(boolean collided) {
		this.isCollided = collided;
	}

	public Position getPosition() {
		return entity.getPosition();
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	
	
}
