package game.engine;

import java.util.HashSet;
import java.util.Set;

import game.engine.components.Component;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.behaviors.Behavior;
import game.engine.components.colliders.Collider;
import game.engine.components.rendering.Render;

public class GameObject implements Parent, Child {

	private Parent					parent;
	private Vector2D				position;
	
	private final Set<GameObject>	gameObjects;
	private final Set<Component>	components;

	public GameObject() {
		this.gameObjects = new ParentAwareSet<GameObject>(this);
		this.components = new ParentAwareSet<Component>(this);
		this.position = Vector2D.ZERO;
	}

	@Override
	public Parent getParent() {
		return parent;
	}

	@Override
	public void setParent(Parent parent) {
		this.parent = parent;
	}

	@Override
	public Set<Child> getChildren() {
		Set<Child> set = new HashSet<>();
		set.addAll(this.gameObjects);
		set.addAll(this.components);
		return set;
	}

	@Override
	public void addChild(Child child) {
		if (child instanceof GameObject) {
			this.addGameObject((GameObject) child);
		} else if (child instanceof Component) {
			this.addComponent((Component) child);
		}
	}

	public void addGameObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
		GameWorld gameWorld = this.getGameWorld();
		if (gameWorld != null) {
			gameWorld.register(gameObject);
		}
	}

	public void addComponent(Component component) {
		this.components.add(component);
		GameWorld gameWorld = this.getGameWorld();
		if (gameWorld != null) {
			gameWorld.register(component);
		}
	}

	private GameWorld getGameWorld() {
		Parent root = getRoot();
		if (root instanceof GameWorld) {
			return (GameWorld) root;
		}
		return null;
	}

	private GameObject getParentGameObject() {
		if (this.parent instanceof GameObject) {
			return (GameObject) getParent();
		}
		return null;
	}

	public Parent getRoot() {
		if (this.parent == null) {
			return this;
		}
		return this.parent.getRoot();
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
			return this.getParentGameObject().getPosition().add(this.getPosition());
		}
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D positon) {
		this.position = positon;
	}

	public void move(double x, double y) {
		this.move(new Vector2D(x, y));
	}

	public void move(Vector2D vector) {
		position = position.add(vector);
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

	public void terminate() {
		GameWorld.INSTANCE.remove(this);
	}

	@Override
	public String toString() {
		return getRender() == null ? "" : getRender().toString();
	}

}
