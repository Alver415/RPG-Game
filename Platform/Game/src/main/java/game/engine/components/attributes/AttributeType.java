package game.engine.components.attributes;

public enum AttributeType {
	HEALTH("Health"),
	SPEED("Speed"),
	ATTACK("Attack"),
	DEFENSE("Defense"),
	MAGIC("Magic");

	private String displayName;

	AttributeType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}