package game.engine.components.controllers;

import game.engine.Vector2D;
import game.engine.components.Component;

public abstract class Controller extends Component {

	protected abstract void move(Vector2D delta);
	public abstract void tick(double dt);

}
