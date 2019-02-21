package game.engine.components;

import game.engine.Entity;

public abstract class Component {

	protected Entity entity;

	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}
