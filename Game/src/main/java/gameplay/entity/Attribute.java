package gameplay.entity;

public enum Attribute {
	HEALTH("Health"),
	SPEED("Speed"),
	ATTACK("Attack"),
	DEFENSE("Defense"),
	MAGIC("Magic");

	private String displayName;

	Attribute(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}