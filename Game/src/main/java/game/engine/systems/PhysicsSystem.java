package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Entity;
import game.engine.components.RigidBody;

public class PhysicsSystem extends GameSystem {

	@Override
	public void process(Set<Entity> entities, double dt) {
		Set<RigidBody> rigidBodies = new HashSet<RigidBody>();
		
		for (Entity entity : entities) {
			if (entity.hasComponent(RigidBody.class)) {
				rigidBodies.add(entity.getComponent(RigidBody.class));
			}
		}
		
		for (RigidBody a : rigidBodies) {
			for (RigidBody b : rigidBodies) {
				if (a == b) {
					continue;
				}
				if (isCollision(a,b)) {
					handleCollision(a, b);
				}
			}
		}
	}

	private boolean isCollision(RigidBody a, RigidBody b) {
		return false;
	}
	
	private void handleCollision(RigidBody a, RigidBody b) {
		
	}

}
