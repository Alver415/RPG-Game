package game.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.engine.components.Component;
import gameplay.entity.Position;

public class Entity {

	private static long next_id = 0;
	private final long id;
	
	private final ComponentMap components;
	private final Position position;
	
	public Entity() {
		this.id = next_id++;
		this.position = new Position();
		this.components = new ComponentMap();
	}
	
	public long getId() {
		return id;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public <T extends Component> void addComponent(T component) {
		component.setEntity(this);
		this.components.add(component);
	}

	public <T extends Component> T getComponent(Class<T> clazz) {
		return this.components.get(clazz);
	}

	public <T extends Component> boolean hasComponent(Class<T>... clazz) {
		return this.components.contains(new HashSet<Class<T>>(Arrays.asList(clazz)));
	}
	
	private class ComponentMap {
		private Map<Class<?>, Component> components = new HashMap<Class<?>, Component>();

		public <T extends Component> T add(T component) {
			Class<T> clazz = (Class<T>) component.getClass();
			return  clazz.cast(components.put(clazz, component));
		}

		public <T extends Component> T get(Class<T> clazz) {
			return clazz.cast(components.get(clazz));
		}

		public <T extends Component> boolean contains(Set<Class<T>> clazzes) {
			for (Class<T> clazz : clazzes) {
				if (!components.containsKey(clazz)) {
					return false;
				}
			}
			return true;
		}
	}
}
