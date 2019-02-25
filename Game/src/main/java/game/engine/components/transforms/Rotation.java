package game.engine.components.transforms;

public class Rotation {

	public static final Rotation N = new Rotation(0);
	public static final Rotation NE = new Rotation(1 * 2 * Math.PI / 8);
	public static final Rotation E = new Rotation(2 * 2 * Math.PI / 8);
	public static final Rotation SE = new Rotation(3 * 2 * Math.PI / 8);
	public static final Rotation S = new Rotation(4 * 2 * Math.PI / 8);
	public static final Rotation SW = new Rotation(5 * 2 * Math.PI / 8);
	public static final Rotation W = new Rotation(6 * 2 * Math.PI / 8);
	public static final Rotation NW = new Rotation(7 * 2 * Math.PI / 8);
	
	private double theta;
	
	public Rotation() {
		this.theta = 0;
	}
	
	public Rotation(double theta) {
		set(theta);
	}
	
	public void set(double theta) {
		this.theta = clamp(theta, 0, 2 * Math.PI);
	}
	
	public double get() {
		return this.theta;
	}
	
	private static final double clamp(double val, double min, double max) {
		return Math.max(Math.min(val, max), min);
	}
		
	
}
