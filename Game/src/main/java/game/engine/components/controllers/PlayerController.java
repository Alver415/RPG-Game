package game.engine.components.controllers;

import java.util.Set;

import frontend.settings.Control;
import game.engine.Entity;
import game.engine.Vector2D;
import game.engine.Viewport;
import game.engine.components.transforms.Position;
import game.engine.components.transforms.Velocity;

public class PlayerController extends Controller {

	private Vector2D	delta;
	private Viewport  	viewport;
	
	public PlayerController(Viewport viewport) {
		this.viewport = viewport;
	}
	
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
			case ZOOM_IN:
				viewport.zoomIn();
				break;
			case ZOOM_OUT:
				viewport.zoomOut();
				break;
			default:
				break;
			}
		}
		
		delta = Vector2D.normalize(dx, dy).scalar(speed);
		speed = 25;
	}

	@Override
	public void tick(double dt) {
		Position pos = entity.getPosition();
		pos.add(delta.scalar(dt));
	}

}
