package game.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.engine.components.Component;
import game.engine.components.Render;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.Controller;
import game.engine.components.transforms.Acceleration;
import game.engine.components.transforms.Position;
import game.engine.components.transforms.Velocity;

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
	public Velocity getVelocity() {
		return getComponent(Velocity.class);
	}
	public Acceleration getAcceleration() {
		return getComponent(Acceleration.class);
	}
	public Controller getController() {
		return getComponent(Controller.class);
	}
	public Collider getCollider() {
		return getComponent(Collider.class);
	}
	public Render getRender() {
		return getComponent(Render.class);
	}
	public AttributeMap getAttributeMap() {
		return getComponent(AttributeMap.class);
	}
	
	/*
	 * Utility methods to set various components.
	 */

	public void addPosition(Position position) {
		addComponent(Position.class, position);
	}
	public void addVelocity(Velocity velocity) {
		addComponent(Velocity.class, velocity);
	}
	public void addAcceleration(Acceleration acceleration) {
		addComponent(Acceleration.class, acceleration);
	}
	public void addController(Controller controller) {
		addComponent(Controller.class, controller);
	}
	public void addCollider(Collider collider) {
		addComponent(Collider.class, collider);
	}
	public void addRender(Render render) {
		addComponent(Render.class, render);
	}
	public void addAttributeMap(AttributeMap attributeMap) {
		addComponent(AttributeMap.class, attributeMap);
	}
	
	/*
	 * Utility methods to get whether a component exists
	 */

	public boolean hasPosition() {
		return getPosition() != null;
	}
	public boolean hasVelocity() {
		return getVelocity() != null;
	}
	public boolean hasAcceleration() {
		return getAcceleration() != null;
	}
	public boolean hasController() {
		return getController() != null;
	}
	public boolean hasCollider() {
		return getCollider() != null;
	}
	public boolean hasRender() {
		return getRender() != null;
	}
	public boolean hasAttributeMap() {
		return getAttributeMap() != null;
	}
}
