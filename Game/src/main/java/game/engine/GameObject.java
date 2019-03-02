package game.engine;

import java.util.Set;

import game.engine.components.Component;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.Behavior;
import game.engine.components.rendering.Render;
import game.engine.components.transforms.Transform;

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
		GameWorld.INSTANCE.register(component);
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
	public void setTransform(Transform transform) {
		transform.setParent(this);
		addComponent(transform);
	}

	public void terminate() {
		GameWorld.INSTANCE.remove(this);
	}

	@Override
	public String toString() {
		return id + " - " + (getRender() == null ? "" : getRender().toString());
	}

	
}
