package game.engine.components.attributes;

public enum UnitOfMeasurement {

	PERCENT("%,.2f%%"),
	MONEY("$%,.2f"),
	TIME("%,.2f seconds"),
	DISTANCE("%,.2f meters"),
	WEIGHT("%,.2f kilograms"),
	AMOUNT("%,.2f");

	private final String formatString;

	private UnitOfMeasurement(String formatString) {
		this.formatString = formatString;
	}

	public static String format(UnitOfMeasurement unit, Number value) {
		return String.format(unit.formatString, value);
	}

	public String format(Number value) {
		return format(this, value);
	}

}