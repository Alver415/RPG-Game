package game.engine.systems;

import game.engine.components.behaviors.Behavior;

public class BehaviorSystem extends GameSystem<Behavior> {

	public BehaviorSystem() {
		super(Behavior.class);
	}

	@Override
	public void tick(double dt) {
		for (Behavior controller : components) {
			controller.tick(dt);
		}
	}

}
