package game.engine;

import game.engine.components.colliders.Collider;

public class Collision {
	private final Collider a;
	private final Collider b;

	public Collision(Collider a, Collider b) {
		this.a = a;
		this.b = b;
	}
	
	public Collider getA() {
		return a;
	}

	public Collider getB() {
		return b;
	}
}