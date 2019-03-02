package game.engine.components.transforms;

import game.engine.Vector2D;
import game.engine.components.Component;

public class Transform extends Component {

	private Vector2D position;
	private Vector2D velocity;

	public Transform() {
		this.position = Vector2D.ZERO;
		this.velocity = Vector2D.ZERO;
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

	public void move(double x, double y) {
		this.move(new Vector2D(x, y));
	}
	public void move(Vector2D delta) {
		position = position.add(delta);
	}

	public void tick(double dt) {
		setPosition(position.add(velocity.scalar(dt)));
	}

}
