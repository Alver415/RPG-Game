package gameplay.combat;

public enum Element {

	FIRE("Fire"),
	EARTH("Earth"),
	WATER("Water"),
	AIR("Air");

	private final String displayName;

	Element(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
