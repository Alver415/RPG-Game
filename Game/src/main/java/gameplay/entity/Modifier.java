package gameplay.entity;

public class Modifier {

	public enum Type {
		ADDITIVE,
		MULTIPLICATIVE;
	}

	private double	amount;
	private Type	type;

	private Modifier(double amount) {
		this(amount, Type.ADDITIVE);
	}

	private Modifier(double amount, Type type) {
		this.setAmount(amount);
		this.setType(type);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
