package game.engine.components.behaviors;

import game.engine.components.Component;

public abstract class Behavior extends Component {

	public abstract void tick(double dt);

}
