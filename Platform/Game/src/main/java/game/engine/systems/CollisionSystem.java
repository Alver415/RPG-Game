package game.engine.systems;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.engine.Collision;
import game.engine.CollisionHandler;
import game.engine.components.colliders.Collider;
import game.engine.components.rendering.render.ColliderRender;

public class CollisionSystem extends GameSystem<Collider> {
	
	private final Set<Collision> collisions = ConcurrentHashMap.newKeySet();
	
	public CollisionSystem() {
		super(Collider.class);
	}

	@Override
	public void tick(double dt) {
		this.collisions.clear();
		this.collisions.addAll(CollisionHandler.findCollisions(components));

		for (Collision collision : collisions) {
			handle(collision);
		}
	}
	
	public Set<Collision> getCollisions(){
		return collisions;
	}
	
	public void handle(Collision collision) {
		Collider a = collision.getA();
		Collider b = collision.getB();
		
		ColliderRender aRender = a.getParentGameObject().getComponent(ColliderRender.class);
		if (aRender != null) aRender.setCollision(true);

		ColliderRender bRender = b.getParentGameObject().getComponent(ColliderRender.class);
		if (bRender != null) bRender.setCollision(true);
		
		a.onCollision(b);
		b.onCollision(a);
	}
}
