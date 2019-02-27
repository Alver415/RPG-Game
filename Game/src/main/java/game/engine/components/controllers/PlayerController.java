package game.engine.components.controllers;

import java.util.Set;

import frontend.settings.Control;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeType;
import game.engine.components.rendering.Viewport;
import game.engine.systems.RenderingSystem;

public class PlayerController extends Behavior {

	private Vector2D	delta;
	
	public PlayerController() {
	}
	
	public void processInput(Set<Control> controls) {
		Viewport viewport = RenderingSystem.INSTANCE.getViewport();
		double dx = 0;
		double dy = 0;
		
		double speed = entity.getAttributeMap().get(AttributeType.SPEED).getVal();
		
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
			case SNEAK:
				speed = entity.getAttributeMap().get(AttributeType.SPEED).getMin();
				break;
			case SPRINT:
				speed = entity.getAttributeMap().get(AttributeType.SPEED).getMax();
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
	}

	@Override
	public void tick(double dt) {
		Vector2D pos = entity.getPosition();
		entity.getTransform().setPosition(pos.add(delta.scalar(dt)));
	}

}
