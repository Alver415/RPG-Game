package game.engine;

import java.util.HashSet;
import java.util.Set;

import game.engine.components.attributes.AttributeMap;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.Behavior;
import game.engine.components.rendering.Render;
import game.engine.components.transforms.Transform;
import game.engine.systems.AttributeSystem;
import game.engine.systems.CollisionSystem;
import game.engine.systems.InputSystem;
import game.engine.systems.RenderingSystem;

public class Entity {

	private static long next_id = 0;
	private final long id;

	private final Transform transform;
	private AttributeMap attributeMap;
	private Behavior behavior;
	private Collider collider;
	private Render render;

	public Entity() {
		this.id = next_id++;
		this.transform = new Transform();
	}

	public long getId() {
		return id;
	}

	public Transform getTransform() {
		return transform;
	}

	public Vector2D getPosition() {
		return transform.getPosition();
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
	
	public void setAttributeMap(AttributeMap attributeMap) {
		attributeMap.setEntity(this);
		this.attributeMap = attributeMap;
		AttributeSystem.INSTANCE.addComponent(attributeMap);
	}

	public void setBehavior(Behavior behavior) {
		behavior.setEntity(this);
		this.behavior = behavior;
		InputSystem.INSTANCE.addComponent(behavior);
	}

	public void setCollider(Collider collider) {
		collider.setEntity(this);
		this.collider = collider;
		CollisionSystem.INSTANCE.addComponent(collider);
	}

	public void setRender(Render render) {
		render.setEntity(this);
		this.render = render;
		RenderingSystem.INSTANCE.addComponent(render);
	}

	public void move(double x, double y) {
		this.move(new Vector2D(x, y));
	}

	public void move(Vector2D vector) {
		this.transform.move(vector);

	}

}
