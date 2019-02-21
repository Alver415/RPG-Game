package game.engine.components;

import java.util.Set;

import frontend.settings.Control;
import game.engine.Entity;
import gameplay.Vector2D;

public class AIController extends Controller{

	private static final double speed = 1;
	
	public void process(Set<Control> controls) {
		double dx = Math.random() - 0.5;
		double dy = Math.random() - 0.5;
		
		Vector2D delta= Vector2D.normalize(dx, dy).scalar(speed);
		
		move(entity, delta);
	}

	private void move(Entity entity, Vector2D delta) {
		entity.getPosition().add(delta);
	}

}
