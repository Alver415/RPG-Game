package game.engine.components.transforms;

import game.engine.Vector2D;
import game.engine.components.Component;

public class Transform extends Component{

	private Vector2D position;
	private Vector2D velocity;
	private Vector2D acceleration;
	private double rotation;

	public Transform() {
		super(Component.Type.TRANSFORM);
		this.position = Vector2D.ZERO;
		this.velocity = Vector2D.ZERO;
		this.acceleration = Vector2D.ZERO;
		this.setRotation(0);
	}

	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	public Vector2D getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}
	public Vector2D getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	public double getRotation() {
		return rotation;
	}

	public void move(Vector2D delta) {
		position = position.add(delta);
	}

}
