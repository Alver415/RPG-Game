package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import com.sun.prism.paint.Color;

import game.engine.Entity;
import game.engine.components.Render;
import game.engine.components.RigidBody;
import gameplay.Vector2D;

public class PhysicsSystem extends GameSystem {

	@Override
	public void process(Set<Entity> entities, double dt) {
		Set<RigidBody> rigidBodies = new HashSet<RigidBody>();
		
		for (Entity entity : entities) {
			if (entity.hasComponent(RigidBody.class)) {
				rigidBodies.add(entity.getComponent(RigidBody.class));
			}
		}
		
		Set<Collision> collisions;

		collisions = broadPhaseCollision();
		collisions = narrowPhaseCollisions();

		for (RigidBody a : rigidBodies) {
			for (RigidBody b : rigidBodies) {
				if (a == b) {
					continue;
				}
				if (isCollision(a,b)) {
					handleCollision(a, b);
				}
				else {
					Render ra = a.getEntity().getComponent(Render.class);
					Render rb = b.getEntity().getComponent(Render.class);

					ra.setCollosion(false);
					rb.setCollosion(false);
				}
			}
		}
	}

	private Set<Collision> broadPhaseCollision() {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<Collision> narrowPhaseCollisions() {
		return null;
	}

	private boolean isCollision(RigidBody a, RigidBody b) {
		Vector2D ap = a.getPosition().asVector();
		Vector2D bp = b.getPosition().asVector();
		return ap.distance(bp) < 25;
	}
	
	private void handleCollision(RigidBody a, RigidBody b) {
		Render ra = a.getEntity().getComponent(Render.class);
		Render rb = b.getEntity().getComponent(Render.class);

		ra.setCollosion(true);
		rb.setCollosion(true);
	}

	private class Collision {
		private final RigidBody	a;
		private final RigidBody	b;

		public Collision(RigidBody a, RigidBody b) {
			this.a = a;
			this.b = b;
		}
	}
}
