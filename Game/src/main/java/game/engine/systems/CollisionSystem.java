package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import game.engine.Entity;
import game.engine.Vector2D;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.transforms.Position;
import javafx.scene.paint.Color;

public class CollisionSystem extends GameSystem {

	private final Set<Collider> colliders = new HashSet<Collider>();
	
	@Override
	public void process(Set<Entity> entities, double dt) {
		colliders.clear();
		
		for (Entity entity : entities) {
			if (entity.hasCollider()) {
				colliders.add(entity.getCollider());
			}
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
				collisions.add(new Collision(a, b));

			}
		}
		return collisions;
	}
	

	public boolean isCollision(Collider a, Collider b) {
		if (a instanceof CircleCollider) {
			CircleCollider ca = (CircleCollider) a;
			if (b instanceof CircleCollider) {
				CircleCollider cb = (CircleCollider) b;
				return isCollision(ca, cb);
			}
			if (b instanceof RectangleCollider) {
				RectangleCollider cb = (RectangleCollider) b;
				return isCollision(ca, cb);
			}
		}
		if (a instanceof RectangleCollider) {
			RectangleCollider ca = (RectangleCollider) a;
			if (b instanceof CircleCollider) {
				CircleCollider cb = (CircleCollider) b;
				return isCollision(ca, cb);
			}
			if (b instanceof RectangleCollider) {
				RectangleCollider cb = (RectangleCollider) b;
				return isCollision(ca, cb);
			}
		}
		return false;
	}

	public boolean isCollision(RectangleCollider a, RectangleCollider b) {
		Vector2D centerA = a.getCenter();
		Vector2D centerB = b.getCenter();

		double ax = centerA.getX();
		double ay = centerA.getY();
		double bx = centerB.getX();
		double by = centerB.getY();

		double aw = a.getWidth() / 2;
		double ah = a.getHeight() / 2;
		double bw = b.getWidth() / 2;
		double bh = b.getHeight() / 2;
		
		double axMax = ax + aw;
		double axMin = ax - aw;
		double ayMax = ay + ah;
		double ayMin = ay - ah;
		
		double bxMax = bx + bw;
		double bxMin = bx - bw;
		double byMax = by + bh;
		double byMin = by - bh;
		

		return !(axMax <= bxMin &&
				 ayMax <= byMin &&
				 axMin >= bxMax &&
				 ayMin >= byMax);
	}

	public boolean isCollision(CircleCollider a, RectangleCollider b) {
		return isCollision(b, a);
	}
	
	public boolean isCollision(RectangleCollider a, CircleCollider b) {

		double br = b.getRadius();
		
		// clamp(value, min, max) - limits value to the range min..max
		double ax = a.getCenter().getX();
		double ay = a.getCenter().getY();

		double bx = b.getCenter().getX();
		double by = b.getCenter().getY();

		double rr = ax + a.getWidth() / 2;
		double rl = ax - a.getWidth() / 2;
		double rt = ay + a.getHeight() / 2;
		double rb = ay - a.getHeight() / 2;

		// Find the closest point to the circle within the rectangle
		double closestX = Math.min(Math.max(bx, rl), rr);
		double closestY = Math.min(Math.max(by, rb), rt);

		// Calculate the distance between the circle's center and this closest point
		double distanceX = bx - closestX;
		double distanceY = by - closestY;

		// If the distance is less than the circle's radius, an intersection occurs
		double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
		return distanceSquared < (br * br);
	}
	
	public boolean isCollision(CircleCollider a, CircleCollider b) {
		Vector2D centerA = a.getCenter();
		Vector2D centerB = b.getCenter();
		
		double sumRadius = a.getRadius() + b.getRadius();
		double distance = centerA.distance(centerB);
		
		return distance < sumRadius;
	}
	
	private Set<Collision> narrowPhaseCollisions(Set<Collision> potentialCollisions) {
		Set<Collision> collisions = new HashSet<Collision>();
		for (Collision potentialCollision : potentialCollisions) {
			Collider a = potentialCollision.getA();
			Collider b = potentialCollision.getB();
			if (isCollision(a, b)) {
				collisions.add(potentialCollision);
				a.getEntity().getRender().setBorder(Color.RED);
				a.getEntity().getRender().setBorder(Color.RED);
			} else {
				a.getEntity().getRender().setBorder(Color.BLACK);
				a.getEntity().getRender().setBorder(Color.BLACK);
			}
		}
		return collisions;
	}

	private class Collision {
		private final Collider a;
		private final Collider b;

		public Collision(Collider a, Collider b) {
			this.a = a;
			this.b = b;
		}
		
		public Collider getA() {
			return a;
		}

		public Collider getB() {
			return b;
		}
		
		public void handle() {
			a.handleCollision(b);
			b.handleCollision(a);
		}
	}
}
