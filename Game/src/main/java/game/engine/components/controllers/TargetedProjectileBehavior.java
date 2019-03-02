package game.engine.components.controllers;

import game.engine.GameObject;
import game.engine.Vector2D;

public class TargetedProjectileBehavior extends Behavior{

	private final GameObject target;
	private final double speed = 100;
	
	public TargetedProjectileBehavior(GameObject target) {
		this.target = target;
	}

	@Override
	public void tick(double dt) {
		Vector2D targetPos = target.getPosition();
		Vector2D thisPos = parent.getPosition();
		
		Vector2D direction = targetPos.subtract(thisPos).normalize();
		
		parent.move(direction.scalar(speed * dt));
	}
	
}
