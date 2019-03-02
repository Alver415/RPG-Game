package game.engine.systems;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.engine.components.Component;

public abstract class GameSystem<T extends Component> {

	protected final Class<T> relevantType;
	protected final Set<T> components;
	
	protected GameSystem(Class<T> clazz) {
		this.relevantType = clazz;
		this.components = ConcurrentHashMap.newKeySet();
	}
	
	public abstract void tick(double dt);

	public void register(Component component) {
		if (relevantType.isInstance(component)) {
			this.components.add(relevantType.cast(component));
		}
	}

	public void remove(T component) {
		if (component != null && this.components.contains(component)) {
			this.components.remove(component);
		}
	}
}
