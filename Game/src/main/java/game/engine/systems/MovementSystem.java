package game.engine.systems;

import java.util.Set;

import game.engine.Entity;
import game.engine.components.transforms.Acceleration;
import game.engine.components.transforms.Position;
import game.engine.components.transforms.Velocity;

public class MovementSystem extends GameSystem {

	@Override
	public void process(Set<Entity> entities, double dt) {
		for (Entity entity : entities) {
			if (entity.hasPosition()) {
				Position pos = entity.getPosition();
				if (entity.hasVelocity()) {
					Velocity vel = entity.getVelocity();
					if (entity.hasAcceleration()) {
						Acceleration acc = entity.getAcceleration();
						vel.add(acc.asVector().scalar(dt));
					}
					pos.add(vel.asVector().scalar(dt));
				}
			}
		}
	}
}
