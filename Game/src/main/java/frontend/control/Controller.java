package frontend.control;

import gameplay.entity.Entity;

public abstract class Controller {

	protected final Entity entity;

	public Controller(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public abstract void tick();

}
