package game.engine.components.controllers;

import game.engine.components.Component;

public abstract class Behavior extends Component {
	
	protected Behavior() {
		super(Component.Type.CONTROLLER);
	}
	public abstract void tick(double dt);

}
