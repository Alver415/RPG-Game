package game.engine.systems;

import game.engine.components.controllers.Behavior;

public class BehaviorSystem extends GameSystem<Behavior> {

	public static final BehaviorSystem INSTANCE = new BehaviorSystem();

	@Override
	public void tick(double dt) {
		for (Behavior controller : components) {
			controller.tick(dt);
		}
	}

}
