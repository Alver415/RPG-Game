package game.engine;

import game.engine.components.attributes.AttributeMap;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.Behavior;
import game.engine.components.rendering.Render;
import game.engine.components.transforms.Transform;
import game.engine.systems.AttributeSystem;
import game.engine.systems.BehaviorSystem;
import game.engine.systems.CollisionSystem;
import game.engine.systems.MovementSystem;
import game.engine.systems.RenderingSystem;

public class Entity {

	private static long next_id = 0;
	private final long id;

	private Transform transform;
	private AttributeMap attributeMap;
	private Behavior behavior;
	private Collider collider;
	private Render render;

	public Entity() {
		this.id = next_id++;
		setTransform(new Transform());
	}

	public long getId() {
		return id;
	}

	public Transform getTransform() {
		return transform;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public AttributeMap getAttributeMap() {
		return attributeMap;
	}

	public Collider getCollider() {
		return collider;
	}

	public Render getRender() {
		return render;
	}

	public Vector2D getPosition() {
		return transform.getPosition();
	}

	public void move(double x, double y) {
		this.move(new Vector2D(x, y));
	}

	public void move(Vector2D vector) {
		this.transform.move(vector);
	}
	
	public void setTransform(Transform transform) {
		transform.setEntity(this);
		this.transform = transform;
		MovementSystem.INSTANCE.add(transform);
	}
	
	public void setAttributeMap(AttributeMap attributeMap) {
		attributeMap.setEntity(this);
		this.attributeMap = attributeMap;
		AttributeSystem.INSTANCE.add(attributeMap);
	}

	public void setBehavior(Behavior behavior) {
		behavior.setEntity(this);
		this.behavior = behavior;
		BehaviorSystem.INSTANCE.add(behavior);
	}

	public void setCollider(Collider collider) {
		collider.setEntity(this);
		this.collider = collider;
		CollisionSystem.INSTANCE.add(collider);
	}

	public void setRender(Render render) {
		render.setEntity(this);
		this.render = render;
		RenderingSystem.INSTANCE.add(render);
	}
	
	public void terminate() {
		GameWorld.INSTANCE.remove(this);
		BehaviorSystem.INSTANCE.remove(this.getBehavior());
		RenderingSystem.INSTANCE.remove(this.getRender());
		MovementSystem.INSTANCE.remove(this.getTransform());
		CollisionSystem.INSTANCE.remove(this.getCollider());
		AttributeSystem.INSTANCE.remove(this.getAttributeMap());
	}

	@Override
	public String toString() {
		return id + " - " + (render == null ? "" : render.toString());
	}
	
}
