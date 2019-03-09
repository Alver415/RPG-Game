package game.engine.geometry;

public class AABB {
	public final double xMin;
	public final double xMax;
	public final double yMin;
	public final double yMax;
	
	public AABB(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public double getWidth() {
		return xMax - xMin;
	}

	public double getHeight() {
		return yMax - yMin;
	}
}

