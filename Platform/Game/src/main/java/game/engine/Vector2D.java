package game.engine;

public class Vector2D {

	public final static Vector2D	ZERO	= new Vector2D(0, 0);
	public final static Vector2D	LEFT	= new Vector2D(-1, 0);
	public final static Vector2D	RIGHT	= new Vector2D(1, 0);
	public final static Vector2D	UP		= new Vector2D(0, 1);
	public final static Vector2D	DOWN	= new Vector2D(0, -1);

	private final double	x;
	private final double	y;

	public Vector2D() {
		this(0, 0);
	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	/*
	 * ======================= INSTANCE OPERATIONS ===============================
	 */

	public Vector2D add(double x, double y) {
		return add(this.x, this.y, x, y);
	}
	
	public Vector2D add(Vector2D v) {
		return add(this, v);
	}


	public Vector2D subtract(double x, double y) {
		return subtract(this.x, this.y, x, y);
	}

	public Vector2D subtract(Vector2D v) {
		return subtract(this, v);
	}

	public Vector2D scalar(double scale) {
		return scalar(this, scale);
	}

	public double dot(Vector2D v) {
		return dot(this, v);
	}

	public Vector2D normalize() {
		return normalize(this);
	}

	public double magnitude() {
		return magnitude(this);
	}

	public double magnitudeSqr() {
		return magnitudeSqr(this);
	}

	public double distance(Vector2D v) {
		return distance(this, v);
	}

	public double distanceSquared(Vector2D v) {
		return distanceSquared(this, v);
	}

	public Vector2D midpoint(Vector2D v) {
		return midpoint(this, v);
	}

	/*
	 * ================ STATIC OPERATIONS ===================
	 */

	public static Vector2D add(double ax, double ay, double bx, double by) {
		return new Vector2D(ax + bx, ay + by);
	}

	public static Vector2D add(Vector2D a, Vector2D b) {
		return new Vector2D(a.x + b.x, a.y + b.y);
	}

	public static Vector2D subtract(double ax, double ay, double bx, double by) {
		return new Vector2D(ax - bx, ay - by);
	}

	public static Vector2D subtract(Vector2D a, Vector2D b) {
		return new Vector2D(a.x - b.x, a.y - b.y);
	}

	public static Vector2D scalar(Vector2D v, double scale) {
		return new Vector2D(v.x * scale, v.y * scale);
	}

	public static double dot(Vector2D a, Vector2D b) {
		return a.x * b.x + a.y * b.y;
	}

	public static Vector2D normalize(double x, double y) {
		double m = magnitude(x, y);
		if (m == 0) {
			return Vector2D.ZERO;
		}
		return new Vector2D(x / m, y / m);
	}

	public static Vector2D normalize(Vector2D v) {
		return normalize(v.x, v.y);
	}

	public static double magnitude(double x, double y) {
		return Math.sqrt(magnitudeSqr(x, y));
	}

	public static double magnitude(Vector2D v) {
		return magnitude(v.x, v.y);
	}

	public static double magnitudeSqr(double x, double y) {
		return x * x + y * y;
	}

	public static double magnitudeSqr(Vector2D v) {
		return magnitudeSqr(v.x, v.y);
	}

	public static double distance(Vector2D a, Vector2D b) {
		return magnitude(subtract(a, b));
	}

	public static double distanceSquared(Vector2D a, Vector2D b) {
		return magnitudeSqr(subtract(a, b));
	}

	public static Vector2D midpoint(Vector2D a, Vector2D b) {
		return scalar(add(a, b), 1 / 2f);
	}
	
	/*
	 * ================== OVERRIDES====================================
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public Vector2D clone() {
		return new Vector2D(x, y);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
}
