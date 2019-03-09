package gameplay.gameObject;

public class Value {

	private final UnitOfMeasurement	unit;
	private double					max;
	private double					min;
	private double					val;

	public Value(double max, double min, double val, UnitOfMeasurement unit) {
		this.unit = unit;
		this.max = max;
		this.min = min;
		this.val = val;
	}

	public Value(double max, double min, UnitOfMeasurement unit) {
		this(max, min, max, unit);
	}

	public Value(double max, UnitOfMeasurement unit) {
		this(max, 0, max, unit);
	}

	public Value(double max, double min, double val) {
		this(max, min, val, UnitOfMeasurement.AMOUNT);
	}

	public Value(double max, double min) {
		this(max, min, max, UnitOfMeasurement.AMOUNT);
	}

	public Value(double max) {
		this(max, 0, max, UnitOfMeasurement.AMOUNT);
	}

	public UnitOfMeasurement getUnit() {
		return unit;
	}

	public double getMax() {
		return max;
	}

	/**
	 * Sets the max value. <br>
	 * If the current val is more than the new max, then set it to the new min.
	 * 
	 * @throws IllegalArgumentException
	 *             if max is less than min.
	 */
	public void setMax(double max) {
		if (max < min) {
			throw new IllegalArgumentException("max cannot be less than min");
		}
		this.max = max;

		if (val > max) {
			val = max;
		}
	}

	public double getMin() {
		return min;
	}

	/**
	 * Sets the min value. <br>
	 * If the current val is less than the new min, then set it to the new min.
	 * 
	 * @param max
	 * @throws IllegalArgumentException
	 *             if min is more than max.
	 */
	public void setMin(double min) {
		if (min > max) {
			throw new IllegalArgumentException("min cannot be more than max");
		}
		this.min = min;

		if (val < min) {
			val = min;
		}
	}

	public double getVal() {
		return val;
	}

	/**
	 * Sets the val after clamping between min and max.
	 * 
	 * @param amount
	 */
	public void setVal(double amount) {
		this.val = Math.max(min, Math.min(max, amount));
	}

	public void add(double amount) {
		setVal(this.val + amount);
	}

	public void subtract(double amount) {
		setVal(this.val - amount);
	}

	public Value copy() {
		return new Value(this.max, this.min, this.val, this.unit);
	}

	/* OVERRIDES */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(max);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(val);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Value other = (Value) obj;
		if (Double.doubleToLongBits(max) != Double.doubleToLongBits(other.max))
			return false;
		if (Double.doubleToLongBits(min) != Double.doubleToLongBits(other.min))
			return false;
		if (Double.doubleToLongBits(val) != Double.doubleToLongBits(other.val))
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return unit.format(val);
	}
}
