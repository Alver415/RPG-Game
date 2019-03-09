package game.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.engine.components.colliders.Collider;
import game.engine.components.rendering.render.ColliderRender;
import game.engine.geometry.AABB;
import game.engine.geometry.Circle;
import game.engine.geometry.Rectangle;
import game.engine.geometry.Shape;

public class CollisionHandler {
	
	public static Set<Collision> findCollisions(Set<Collider> colliders){
		Set<Collision> potentialCollisions = broadPhaseCollisionDetection(colliders);
		Set<Collision> actualCollisions = narrowPhaseCollisionDetection(potentialCollisions);
		return actualCollisions;
	}
	
	public static Set<Collision> broadPhaseCollisionDetection(Set<Collider> colliders) {
   		Set<Collision> collisions = new HashSet<Collision>();
		List<Collider> asList = new ArrayList<>(colliders);
		int size = asList.size();
		for (int a = 0; a < size; a++) {
			Collider aCollider = asList.get(a);
			ColliderRender render = aCollider.getParentGameObject().getComponent(ColliderRender.class);
			if (render != null) {
				render.setCollision(false);
			}
			for (int b = a + 1; b < size; b++) {
				Collider bCollider = asList.get(b);
				AABB aBox = aCollider.getAABB();
				AABB bBox = bCollider.getAABB();
				
				if (intersect(aBox, bBox)) {
					collisions.add(new Collision(aCollider, bCollider));
				}
				
			}
		}
		return collisions;
	}
	

	public static boolean intersect(AABB aBox, AABB bBox) {		
		return !(aBox.xMax <= bBox.xMin ||
				 aBox.yMax <= bBox.yMin || 
				 aBox.xMin >= bBox.xMax ||
				 aBox.yMin >= bBox.yMax);
	}


	public static boolean isCollision(Collider a, Collider b) {
		Shape bShape = b.getShape();
		Shape aShape = a.getShape();
		
		if (aShape instanceof Rectangle && bShape instanceof Rectangle) {
			return true;
		}
		else if (aShape instanceof Circle && bShape instanceof Rectangle) {
			return isCollision((Circle)aShape, (Rectangle)bShape);
		}
		else if (aShape instanceof Rectangle && bShape instanceof Circle) {
			return isCollision((Circle)bShape, (Rectangle)aShape);
		}
		else if (aShape instanceof Circle && bShape instanceof Circle) {
			return isCollision((Circle)bShape, (Circle)aShape);
		}
		throw new RuntimeException("Collision between " + a.getClass().getName() + " and " + b.getClass().getName() + " not handled.");
	}

	public static boolean isCollision(Circle circle, Rectangle rect) {
		Vector2D circleCenter = circle.getCenter();
		double radius = circle.getRadius();
		double circleX = circleCenter.getX();
		double circleY = circleCenter.getY();
		
		double xMax = rect.getAABB().xMax;
		double xMin = rect.getAABB().xMin;
		double yMax = rect.getAABB().yMax;
		double yMin = rect.getAABB().yMin;

		// Find the closest point to the circle within the rectangle
		double closestX = GameUtils.clamp(xMin, circleX, xMax);
		double closestY = GameUtils.clamp(yMin, circleY, yMax);
		Vector2D rectClosestPoint = new Vector2D(closestX, closestY);
		
		// Calculate the distance between the circle's center and this closest point
		double distanceSqr = circleCenter.distanceSquared(rectClosestPoint);

		// If the distance is less than the circle's radius, an intersection occurs
		return distanceSqr < (radius * radius);
	}
	

	public static boolean isCollision(Circle a, Circle b) {
		Vector2D centerA = a.getCenter();
		Vector2D centerB = b.getCenter();
		
		double sumRadius = a.getRadius() + b.getRadius();
		double distanceSqr = centerA.distanceSquared(centerB);
		
		return distanceSqr < (sumRadius * sumRadius);
	}

	public static Set<Collision> narrowPhaseCollisionDetection(Set<Collision> potentialCollisions) {
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
}
