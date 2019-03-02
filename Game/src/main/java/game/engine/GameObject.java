package game.engine;

import java.util.Set;

import game.engine.components.Component;
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

public class GameObject implements Child {

	private static long next_id = 0;
	private final long id;

	private GameObject				parent;
	private final Set<GameObject>	children;
	private final Set<Component>	components;

	public GameObject() {
		this.id = next_id++;
		this.children = new ParentAwareSet<GameObject>(this);
		this.components = new ParentAwareSet<Component>(this);
		setTransform(new Transform());
	}

	public long getId() {
		return id;
	}

	@Override
	public GameObject getParent() {
		return parent;
	}

	@Override
	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public Set<GameObject> getChildren() {
		return children;
	}

	public void addChild(GameObject child) {
		this.children.add(child);
	}

	public Set<Component> getComponents() {
		return components;
	}

	public void addComponent(Component component) {
		this.components.add(component);
	}

	public Vector2D getWorldPosition() {
		if (this.parent == null) {
			return getPosition();
		} else {
			return this.parent.getPosition().add(getPosition());
		}
	}

	public Vector2D getWorldVelocity() {
		if (this.parent == null) {
			return getVelocity();
		} else {
			return this.parent.getVelocity().add(getVelocity());
		}
	}


	public Vector2D getPosition() {
		return getTransform().getPosition();
	}

	public Vector2D getVelocity() {
		return getTransform().getVelocity();
	}

	public void move(double x, double y) {
		this.move(new Vector2D(x, y));
	}

	public void move(Vector2D vector) {
		getTransform().move(vector);
	}

	public Transform getTransform() {
		return getComponent(Transform.class);
	}

	public Behavior getBehavior() {
		return getComponent(Behavior.class);
	}

	public AttributeMap getAttributeMap() {
		return getComponent(AttributeMap.class);
	}

	public Collider getCollider() {
		return getComponent(Collider.class);
	}

	public Render getRender() {
		return getComponent(Render.class);
	}

	public <T extends Component> T getComponent(Class<T> clazz) {
		T c = null;
		for (Component component : components) {
			if (clazz.isAssignableFrom(component.getClass())) {
				c = clazz.cast(component);
			}
		}
		return c;
	}

	public void setTransform(Transform transform) {
		transform.setParent(this);
		addComponent(transform);
		MovementSystem.INSTANCE.add(transform);
	}

	public void setAttributeMap(AttributeMap attributeMap) {
		attributeMap.setParent(this);
		addComponent(attributeMap);
		AttributeSystem.INSTANCE.add(attributeMap);
	}

	public void setBehavior(Behavior behavior) {
		behavior.setParent(this);
		addComponent(behavior);
		BehaviorSystem.INSTANCE.add(behavior);
	}

	public void setCollider(Collider collider) {
		collider.setParent(this);
		addComponent(collider);
		CollisionSystem.INSTANCE.add(collider);
	}

	public void setRender(Render render) {
		render.setParent(this);
		addComponent(render);
		RenderingSystem.INSTANCE.add(render);
	}
	
	public void terminate() {
		GameWorld.INSTANCE.remove(this);
		BehaviorSystem.INSTANCE.remove(this.getBehavior());
		RenderingSystem.INSTANCE.remove(this.getRender());
		CollisionSystem.INSTANCE.remove(this.getCollider());
		AttributeSystem.INSTANCE.remove(this.getAttributeMap());
	}

	@Override
	public String toString() {
		return id + " - " + (getRender() == null ? "" : getRender().toString());
	}

	
}
