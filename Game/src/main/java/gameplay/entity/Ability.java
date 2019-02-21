package gameplay.entity;

public abstract class Ability {

	protected Character	source;
	protected Character	target;

	public Ability(Character source, Character target) {
		this.source = source;
		this.target = target;

	}

	public Character getSource() {
		return source;
	}

	public void setSource(Character source) {
		this.source = source;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public abstract void execute();
}
