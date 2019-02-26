package game.engine.systems;

import java.util.Set;

import game.engine.components.Component;

public abstract class GameSystem<T extends Component>{

	protected final Set<T> components;
	
	protected GameSystem(Set<T> components) {
		this.components = components;
	}
	
	public abstract void tick(double dt);

	public void addComponent(T component) {
		this.components.add(component);
	}
}
