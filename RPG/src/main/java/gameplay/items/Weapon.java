package gameplay.items;

public abstract class Weapon extends Equipment {

	public enum Type {
		SWORD,
		HAMMER,
		AXE,
		BOW;
	}

	protected final Type type;

	public Weapon(String name, Type type) {
		super(name);
		this.type = type;
	}

}
