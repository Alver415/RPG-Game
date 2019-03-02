package game.engine.systems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.engine.Vector2D;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.rendering.Render;
import javafx.scene.paint.Color;

public class CollisionSystem extends GameSystem<Collider> {

	public static final CollisionSystem INSTANCE = new CollisionSystem();
	
	private CollisionSystem() {
	}
	
	@Override
	public void tick(double dt) {
		Set<Collision> potentialCollisions = broadPhaseCollision(components);
		Set<Collision> actualCollisions = narrowPhaseCollisions(potentialCollisions);
		for (Collision collision : actualCollisions) {
			collision.handle();
		}
	}

	private Set<Collision> broadPhaseCollision(Set<Collider> colliders) {
   		Set<Collision> collisions = new HashSet<Collision>();
		List<Collider> asList = new ArrayList<>(colliders);
		int size = asList.size();
		for (int a = 0; a < size; a++) {
			Render render = asList.get(a).getGameObject().getRender();
			if (render != null) {
				render.setBorderColor(Color.rgb(0, 0, 0, 0));
			}
			for (int b = a + 1; b < size; b++) {
				collisions.add(new Collision(asList.get(a), asList.get(b)));
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
		

		return !(axMax <= bxMin || ayMax <= byMin || axMin >= bxMax ||
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

		double rgt = ax + a.getWidth() / 2;
		double lft = ax - a.getWidth() / 2;
		double top = ay + a.getHeight() / 2;
		double bot = ay - a.getHeight() / 2;

		// Find the closest point to the circle within the rectangle
		double closestX = Math.min(Math.max(bx, lft), rgt);
		double closestY = Math.min(Math.max(by, bot), top);

		// Calculate the distance between the circle's center and this closest point
		double distanceX = bx - closestX;
		double distanceY = by - closestY;

		// If the distance is less than the circle's radius, an intersection occurs
		double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

		boolean collision = distanceSquared < (br * br);
		return collision;
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
			Render aRender = a.getGameObject().getRender();
			if (aRender != null) {
				aRender.setBorderColor(Color.RED);
			}
			Render bRender = b.getGameObject().getRender();
			if (bRender != null) {
				bRender.setBorderColor(Color.RED);
			}
			
			a.handleCollision(b);
			b.handleCollision(a);
		}
	}
	
}
