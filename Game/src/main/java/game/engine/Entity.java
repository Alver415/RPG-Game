package game.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.engine.components.Component;
import game.engine.components.Position;
import game.engine.components.Render;
import game.engine.components.RigidBody;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.Controller;

public class Entity {

	private static long next_id = 0;
	private final long id;
	
	private Map<Class<?>, Component> components;
	
	public Entity() {
		this.id = next_id++;
		this.components = new HashMap<Class<?>, Component>();
	}
	
	public long getId() {
		return id;
	}
	
	private <T extends Component> T addComponent(Class<T> clazz, T component) {
		component.setEntity(this);
		return  clazz.cast(components.put(clazz, component));
	}

	private <T extends Component> T getComponent(Class<T> clazz) {
		return clazz.cast(components.get(clazz));
	}
	
	private <T extends Component> boolean contains(Class<T> clazz) {
		return this.contains(new HashSet<Class<T>>(Arrays.asList(clazz)));
	}

	private <T extends Component> boolean contains(Class<T>... clazz) {
		return this.contains(new HashSet<Class<T>>(Arrays.asList(clazz)));
	}
	
	private <T extends Component> boolean contains(Set<Class<T>> clazzes) {
		for (Class<T> clazz : clazzes) {
			if (!components.containsKey(clazz)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Utility methods to get various components.
	 */

	public Position getPosition() {
		return getComponent(Position.class);
	}

	public Controller getController() {
		return getComponent(Controller.class);
	}
	
	public Collider getCollider() {
		return getComponent(Collider.class);
	}

	public RigidBody getRigidBody() {
		return getComponent(RigidBody.class);
	}
	
	public Render getRender() {
		return getComponent(Render.class);
	}
	
	/*
	 * Utility methods to set various components.
	 */

	public void setPosition(Position position) {
		addComponent(Position.class, position);
	}

	public void setController(Controller controller) {
		addComponent(Controller.class, controller);
	}

	public void setCollider(Collider collider) {
		addComponent(Collider.class, collider);
	}

	public void setRigidBody(RigidBody rigidBody) {
		addComponent(RigidBody.class, rigidBody);
	}

	public void setRender(Render render) {
		addComponent(Render.class, render);
	}
	
	/*
	 * Utility methods to get whether a component exists
	 */

	public boolean hasPosition() {
		return getPosition() != null;
	}
	public boolean hasController() {
		return getController() != null;
	}
	public boolean hasCollider() {
		return getCollider() != null;
	}
	public boolean hasRigidBody() {
		return getRigidBody() != null;
	}
	public boolean hasRender() {
		return getRender() != null;
	}

	
}
