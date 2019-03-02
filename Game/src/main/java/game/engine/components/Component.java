package game.engine.components;

import game.engine.GameObject;

public abstract class Component implements IComponent {

	protected GameObject	parent;

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	
	
}
