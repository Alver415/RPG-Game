package game.engine.components.controllers;

import game.engine.GameObject;
import game.engine.GameUtils;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeType;
import game.engine.components.transforms.Transform;

public class FollowPlayerBehavior extends Behavior {

	private GameObject target = GameUtils.findPlayer();
	
	@Override
	public void tick(double dt) {
		Vector2D direction;
		Vector2D position = parent.getPosition();

		GameObject player = target;
		if (player != null) {
			double distance = player.getPosition().distance(position);
			if (distance > 100) {
				return;
			}
			Vector2D target = player.getPosition();
			direction = target.subtract(position).normalize();
		} else {
			direction = Vector2D.ZERO.subtract(position).normalize();
		}
		double speed = parent.getAttributeMap().get(AttributeType.SPEED).getVal();
		
		Vector2D delta = direction.scalar(speed * dt);
		Transform transform = parent.getTransform();
		transform.move(delta.normalize().scalar(speed * dt));
	}


}
