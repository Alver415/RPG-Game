package game.engine.components;

import game.engine.Child;
import game.engine.GameObject;
import game.engine.Parent;

public abstract class Component implements Child {

	protected GameObject parent;

	public GameObject getParentGameObject() {
		return (GameObject) parent;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = (GameObject) parent;
	}
	
	public void tick(double dt) {
	}
	
}
