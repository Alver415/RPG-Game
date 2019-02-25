package game.engine;

public class Viewport {

	private static final double zoomMax = 5;
	private static final double zoomMin = 0.5;

	private Entity target;
	private double zoom = 1;

	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public Vector2D getVector2D() {
		return target == null ? Vector2D.ZERO : target.getPosition().asVector();
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void setZoom(double zoom) {
		this.zoom = Math.max(Math.min(zoom, zoomMax), zoomMin);
	}

	public Entity getTarget() {
		return target;
	}

	public void zoomIn() {
		setZoom(zoom * 1.05);
		
	}
	public void zoomOut() {
		setZoom(zoom * 0.95);
	}
	
}
