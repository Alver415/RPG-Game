package game.engine.components.behaviors;

import game.engine.GameObject;
import game.engine.GameUtils;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeType;

public class FollowPlayerBehavior extends Behavior {

	@Override
	public void tick(double dt) {
		Vector2D direction;
		Vector2D position = parent.getPosition();

		GameObject player = GameUtils.findPlayer();
		if (player != null) {
			Vector2D target = player.getPosition();
			direction = target.subtract(position).normalize();
		} else {
			Vector2D target = Vector2D.ZERO;
			direction = target;
		}
		
		double speed = parent.getAttributeMap().get(AttributeType.SPEED).getVal();
		parent.move(direction.scalar(speed * dt));
	}


}
