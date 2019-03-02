package game.engine.components.controllers;

import java.util.Set;

import frontend.settings.Control;
import game.engine.GameUtils;
import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeType;
import game.engine.components.rendering.AnimatedSprite;
import game.engine.components.rendering.Viewport;
import game.engine.components.rendering.render.SpriteRender;
import game.engine.systems.RenderingSystem;

public class PlayerController extends Behavior {

	private Vector2D delta;

	public PlayerController() {
	}

	private void processInput() {
		Viewport viewport = RenderingSystem.INSTANCE.getViewport();
		double dx = 0;
		double dy = 0;

		double baseSpeed = gameObject.getAttributeMap().get(AttributeType.SPEED).getVal();
		double sneakSpeed = gameObject.getAttributeMap().get(AttributeType.SPEED).getMin();
		double sprintSpeed = gameObject.getAttributeMap().get(AttributeType.SPEED).getMax();

		double speed = baseSpeed;
		Set<Control> controls = GameWorld.INSTANCE.getControls();
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
				speed = sneakSpeed;
				break;
			case SPRINT:
				speed = sprintSpeed;
				break;
			case SPAWN:
				GameUtils.spawn();
				break;
			case SHOOT:
				GameUtils.shoot();
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
		processInput();
		gameObject.getTransform().setVelocity(delta);

		AnimatedSprite animation;
		if (delta.getY() > Math.abs(delta.getX())) {
			animation = AnimatedSprite.UP;
		} else if (delta.getX() > 0) {
			animation = AnimatedSprite.RIGHT;
		} else if (delta.getX() < 0) {
			animation = AnimatedSprite.LEFT;
		} else {
			animation = AnimatedSprite.DOWN;
		}

		((SpriteRender) gameObject.getRender()).setSprite(animation);
	}

}
