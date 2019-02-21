package game.engine.systems;

import java.util.Set;

import game.engine.Entity;

public abstract class GameSystem {

	public abstract void process(Set<Entity> entities, double dt);

}
