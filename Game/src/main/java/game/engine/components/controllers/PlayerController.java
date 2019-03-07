package game.engine.components.controllers;

import java.util.Set;

import com.sun.javafx.tk.Toolkit;

import frontend.settings.Control;
import game.engine.GameObject;
import game.engine.GameUtils;
import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeType;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.rendering.AnimatedSprite;
import game.engine.components.rendering.render.SpriteRender;
import javafx.scene.input.InputEvent;

public class PlayerController extends Controller {

	private Vector2D delta;

	public PlayerController() {
	}

	private void processInput() {
		double dx = 0;
		double dy = 0;

		double baseSpeed = parent.getAttributeMap().get(AttributeType.SPEED).getVal();
		double sneakSpeed = parent.getAttributeMap().get(AttributeType.SPEED).getMin();
		double sprintSpeed = parent.getAttributeMap().get(AttributeType.SPEED).getMax();

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
				GameUtils.spawnEnemy();
				break;
			case SHOOT:
				shoot();
				break;
			default:
				break;
			}
		}

		delta = Vector2D.normalize(dx, dy).scalar(speed);
	}

	private void shoot() {
		GameObject bullet = new GameObject();
		bullet.getTransform().setVelocity(velocity);
		bullet.addChild(new CircleCollider(0.1, true));
	}

	public void processInput(Set<InputEvent> events) {
		processInput();
		parent.getTransform().setVelocity(delta);

		if (delta.equals(Vector2D.ZERO)) {
			return;
		}
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

		((SpriteRender) parent.getRender()).setSprite(animation);
	}

	@Override
	public void tick(double dt) {
		// TODO Auto-generated method stub

	}

}
