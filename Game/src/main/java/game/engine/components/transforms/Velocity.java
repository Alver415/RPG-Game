package game.engine.components.transforms;

import game.engine.Vector2D;
import game.engine.components.Component;

public class Velocity extends Component {

	private Vector2D vector;

	public Velocity() {
		this.vector = Vector2D.ZERO;
	}

	public Velocity(double x, double y) {
		this.vector = new Vector2D(x, y);
	}

	public Velocity(Vector2D vector) {
		this.vector = vector;
	}

	public void add(Vector2D vector) {
		this.vector = this.vector.add(vector);
	}

	public void set(Vector2D vector) {
		this.vector = vector;
	}

	public void setX(double x) {
		this.vector = new Vector2D(x, vector.getY());
	}

	public void setY(double y) {
		this.vector = new Vector2D(vector.getX(), y);
	}

	public double getX() {
		return vector.getX();
	}

	public double getY() {
		return vector.getY();
	}

	public Vector2D asVector() {
		return vector;
	}

	@Override
	public String toString() {
		return "[" + getX() + ", " + getY() + "]";
	}
}
