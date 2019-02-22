package game.engine.components.controllers;

import game.engine.components.Component;
import gameplay.Vector2D;

public abstract class Controller extends Component {

	protected abstract void move(Vector2D delta);
	public abstract void tick(double dt);

}
