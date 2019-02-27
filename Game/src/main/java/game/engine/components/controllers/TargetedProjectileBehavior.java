package game.engine.components.controllers;

import game.engine.Entity;
import game.engine.Vector2D;

public class TargetedProjectileBehavior extends Behavior{

	private final Entity target;
	private final double speed = 100;
	
	public TargetedProjectileBehavior(Entity target) {
		this.target = target;
	}

	@Override
	public void tick(double dt) {
		Vector2D targetPos = target.getPosition();
		Vector2D thisPos = entity.getPosition();
		
		Vector2D direction = targetPos.subtract(thisPos).normalize();
		
		entity.move(direction.scalar(speed * dt));
	}
	
}
