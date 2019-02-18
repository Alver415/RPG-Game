package gameplay.entity;

public abstract class Ability {

	protected Entity	source;
	protected Entity	target;

	public Ability(Entity source, Entity target) {
		this.source = source;
		this.target = target;

	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public abstract void execute();
}
