package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Entity;
import game.engine.components.Collider;
import gameplay.Vector2D;

public class PhysicsSystem extends GameSystem {

	private final Set<Collider> colliders = new HashSet<Collider>();
	
	@Override
	public void process(Set<Entity> entities, double dt) {
		colliders.clear();
		
		for (Entity entity : entities) {
			if (entity.hasCollider()) {
				colliders.add(entity.getCollider());
			}
		}
		
		for (Collider collider : colliders) {
			collider.setCollided(false);
		}

		Set<Collision> potentialCollisions = broadPhaseCollision(colliders);
		Set<Collision> actualCollisions = narrowPhaseCollisions(potentialCollisions);

		for (Collision collision : actualCollisions) {
			collision.handle();
		}

	}

	private Set<Collision> broadPhaseCollision(Set<Collider> colliders) {
		Set<Collision> collisions = new HashSet<Collision>();

		for (Collider a : colliders) {
			for (Collider b : colliders) {
				if (a == b) {
					continue;
				}
				if (isCollision(a, b)) {
					collisions.add(new Collision(a, b));
				} 

			}
		}
		return collisions;
	}

	private Set<Collision> narrowPhaseCollisions(Set<Collision> potentialCollisions) {
		return potentialCollisions;
	}

	private boolean isCollision(Collider a, Collider b) {
		Vector2D ap = a.getPosition().asVector();
		Vector2D bp = b.getPosition().asVector();
		double sumRadius = a.getRadius() + b.getRadius();
		double distance = ap.distance(bp);
		
		return distance < sumRadius;
	}


	private class Collision {
		private final Collider a;
		private final Collider b;

		public Collision(Collider a, Collider b) {
			this.a = a;
			this.b = b;
			a.setCollided(true);
			b.setCollided(true);
		}

		public void handle() {
			
		}

	}
}
