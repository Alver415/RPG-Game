package game.engine;

import java.util.Set;

import frontend.settings.Control;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.attributes.Value;
import game.engine.components.behaviors.Behavior;
import game.engine.components.colliders.Collider;
import game.engine.components.rendering.render.BasicShapeRender;
import game.engine.components.rigidbody.Force;
import game.engine.components.rigidbody.RigidBody;
import game.engine.geometry.Circle;
import game.engine.systems.RenderingSystem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PlayerControlledBehavior extends Behavior {

	private double speed;
	private boolean jump;
	private double dx;

	private void processInput() {
		InputHandler inputHandler = GameWorld.INSTANCE.getInputHandler();
		RenderingSystem renderSystem = GameWorld.INSTANCE.getRenderingSystem();
		
		Value speedAttr = parent.getAttributeMap().get(AttributeType.SPEED);
		double baseSpeed = speedAttr.getVal();
		double sneakSpeed = speedAttr.getMin();

		dx = 0;
		speed = baseSpeed;
		jump = false;
		
		Set<KeyCode> pressedKeys = inputHandler.getPressedKeys();
		for (KeyCode code : pressedKeys) {
			Control control = Control.get(code);
			if (control != null) {
				switch (control) {
				case LEFT:
					dx--;
					break;
				case RIGHT:
					dx++;
					break;
				case JUMP:
					jump = true;
					break;
				case SNEAK:
					speed = sneakSpeed;
					break;
				case ZOOM_IN:
					GameWorld.INSTANCE.zoomIn();
					break;
				case ZOOM_OUT:
					GameWorld.INSTANCE.zoomOut();
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
	}

	private void shoot(Vector2D target) {
		GameObject thisParent = this.getParentGameObject();
		Vector2D parentPosition = thisParent.getPosition();
		Vector2D velocity = target.subtract(parentPosition).normalize().scalar(10);

		GameObject bullet = new GameObject();
		bullet.setPosition(parentPosition);
		Circle circle = new Circle(0.1);
		RigidBody rigidBody = new RigidBody(0.1);
		rigidBody.setVelocity(velocity);
		rigidBody.setDamping(0);
		bullet.addChild(rigidBody);
		bullet.addChild(new BasicShapeRender(circle, Color.BLACK));
		bullet.addChild(new Collider(circle) {
			@Override
			public void onCollision(Collider other) {
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
		
		Value speedAttr = parent.getAttributeMap().get(AttributeType.SPEED);
		double speed = speedAttr.getVal();
		
		RigidBody rigidBody = parent.getComponent(RigidBody.class);
		if (rigidBody != null) {
			Vector2D currVelocity = rigidBody.getVelocity();
			if (Math.abs(currVelocity.getX()) < speed) {
				rigidBody.setVelocity(new Vector2D(dx * speed, currVelocity.getY()));
			}
			if (jump && rigidBody.isGrounded()) {
				Force jumpForce = new Force(Vector2D.UP.scalar(10));
				rigidBody.addForce(jumpForce);
				rigidBody.setVelocity(currVelocity.subtract(0d, currVelocity.getY()));
			}
		}

	}

}
