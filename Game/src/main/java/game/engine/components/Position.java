package game.engine.components;

import game.engine.Vector2D;

public class Position extends Component{

	private Vector2D vector;

	public Position() {
		this.vector = new Vector2D();
	}

	public void add(Vector2D vector) {
		this.vector = this.vector.add(vector);
	}

	public void set(Vector2D vector) {
		this.vector = vector;
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
