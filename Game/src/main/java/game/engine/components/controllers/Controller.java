package game.engine.components.controllers;

import game.engine.components.Component;

public abstract class Controller extends Component {
	
	protected double speed = 25;

	public double getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public abstract void tick(double dt);

}
