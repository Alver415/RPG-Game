package game.engine;

import java.util.Set;

import frontend.settings.Control;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.behaviors.Behavior;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.rendering.AnimatedSprite;
import game.engine.components.rendering.render.CircleRender;
import game.engine.components.rendering.render.SpriteRender;
import game.engine.systems.RenderingSystem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PlayerControlledBehavior extends Behavior {

	private Vector2D delta;

	private void processInput() {
		InputHandler inputHandler = GameWorld.INSTANCE.getInputHandler();
		RenderingSystem renderSystem = GameWorld.INSTANCE.getRenderingSystem();
		
		double dx = 0;
		double dy = 0;

		double baseSpeed = parent.getAttributeMap().get(AttributeType.SPEED).getVal();
		double sneakSpeed = parent.getAttributeMap().get(AttributeType.SPEED).getMin();
		double sprintSpeed = parent.getAttributeMap().get(AttributeType.SPEED).getMax();

		double speed = baseSpeed;
		
		Set<KeyCode> pressedKeys = inputHandler.getPressedKeys();
		for (KeyCode code : pressedKeys) {
			Control control = Control.get(code);
			if (control != null) {
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
				case ZOOM_IN:
					GameWorld.INSTANCE.zoomIn();
					break;
				case ZOOM_OUT:
					GameWorld.INSTANCE.zoomOut();
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
				default:
					break;
				}
			}
		}

		Set<MouseEvent> mouseEvents = inputHandler.getMouseEvents();
		for (MouseEvent mouseEvent : mouseEvents) {
			Control control = Control.get(mouseEvent.getButton());
			if (control != null) {
				switch (control) {
				case SHOOT:
					double x = mouseEvent.getSceneX();
					double y = mouseEvent.getSceneY();
					Vector2D world = renderSystem.canvasToWorld(new Vector2D(x, y));
					shoot(world);
					break;
				default:
					break;
				}
			}
		}

		delta = Vector2D.normalize(dx, dy).scalar(speed);
	}

	private void shoot(Vector2D target) {
		GameObject thisParent = this.getParentGameObject();
		Vector2D parentPosition = thisParent.getPosition();
		Vector2D velocity = target.subtract(parentPosition).normalize().scalar(10);

		
		GameObject bullet = new GameObject();
		bullet.getTransform().setPosition(parentPosition);
		bullet.getTransform().setVelocity(velocity);
		bullet.addChild(new CircleRender(Color.BLACK, 0.2));
		bullet.addChild(new CircleCollider(0.2, true) {
			@Override
			public void handleCollision(Collider other) {
				GameObject otherParent = other.getParentGameObject();
				if (!otherParent.equals(thisParent)) {
					AttributeMap otherAttributes = otherParent.getAttributeMap();
					if (otherAttributes != null) {
						otherAttributes.get(AttributeType.HEALTH).subtract(10);
						if (otherAttributes.get(AttributeType.HEALTH).getVal() <= 0) {
							otherParent.terminate();
						}
					}
					bullet.terminate();
				}
			}
		});
		GameWorld.INSTANCE.addChild(bullet);
	}

	@Override
	public void tick(double dt) {
		this.processInput();
		
		parent.getTransform().setVelocity(delta);

		if (delta.equals(Vector2D.ZERO)) {
			return;
		}
		SpriteRender spriteRender = parent.getComponent(SpriteRender.class);
		if (spriteRender != null) {
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
	
			spriteRender.setSprite(animation);
		}
		
	}

}
