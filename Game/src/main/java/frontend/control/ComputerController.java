package frontend.control;

import gameplay.Vector2D;
import gameplay.entity.Entity;

public class ComputerController extends Controller {

	private Vector2D delta;

	public ComputerController(Entity entity) {
		super(entity);
		this.delta = Vector2D.ZERO;
	}

	@Override
	public void tick() {
		delta = delta.add(Math.random() - 0.5, Math.random() - 0.5).normalize();

		entity.getVelocity().set(delta);
	}

}
