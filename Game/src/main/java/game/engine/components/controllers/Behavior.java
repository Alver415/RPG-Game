package game.engine.components.controllers;

import game.engine.components.Component;

public abstract class Behavior extends Component {
	
	public abstract void tick(double dt);

}
