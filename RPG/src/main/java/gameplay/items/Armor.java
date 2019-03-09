package gameplay.items;

public abstract class Armor extends Equipment {

	public enum Type {
		HEAD,
		BODY,
		ARMS,
		LEGS,
		FEET,
		SHIELD;
	}

	protected final Type type;

	protected Armor(String name, Type type) {
		super(name);
		this.type = type;
	}

}
