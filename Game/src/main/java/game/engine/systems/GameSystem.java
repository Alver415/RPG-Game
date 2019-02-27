package game.engine.systems;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.engine.components.Component;

public abstract class GameSystem<T extends Component>{

	protected final Set<T> components;
	
	protected GameSystem() {
		this.components = ConcurrentHashMap.newKeySet();
	}
	
	public abstract void tick(double dt);

	public void add(T component) {
		this.components.add(component);
	}
	public void remove(T component) {
		if (component != null && this.components.contains(component)) {
			this.components.remove(component);
		}
	}
}
