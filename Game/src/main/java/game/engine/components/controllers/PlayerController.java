package game.engine.components.controllers;

import java.util.Set;

import frontend.settings.Control;
import game.engine.Entity;
import gameplay.Vector2D;

public class PlayerController extends Controller {

	private double		speed	= 25;
	private Vector2D	direction;
	
	public void processInput(Set<Control> controls) {
		double dx = 0;
		double dy = 0;
		
		for (Control control : controls) {
			switch (control) {
			case UP:
				dy++;
				break;
			case DOWN:
				dy--;
				break;
			case LEFT:
				dx--;
				break;
			case RIGHT:
				dx++;
				break;
			case SPACE:
				speed = 50;
				break;
			default:
				break;
			}
		}
		
		this.direction = Vector2D.normalize(dx, dy);

	}

	@Override
	protected void move(Vector2D delta) {
		entity.getPosition().add(delta);
	}

	@Override
	public void tick(double dt) {
		move(direction.scalar(speed * dt));
		speed = 25;
	}

}
