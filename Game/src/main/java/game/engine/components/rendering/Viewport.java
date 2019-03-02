package game.engine.components.rendering;

import game.engine.GameObject;
import game.engine.Vector2D;

public class Viewport {

	private static final double	zoomMax	= 500;
	private static final double	zoomMin	= 10;

	private GameObject target;
	private double	zoom	= 50;

	public Viewport() {
		this.target = null;
	}
	public Viewport(GameObject target) {
		this.target = target;
	}
	
	public void setTarget(GameObject target) {
		this.target = target;
	}
	
	public Vector2D getVector2D() {
		return target == null ? Vector2D.ZERO : target.getPosition();
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void setZoom(double zoom) {
		this.zoom = Math.max(Math.min(zoom, zoomMax), zoomMin);
	}

	public GameObject getTarget() {
		return target;
	}

	public void zoomIn() {
		setZoom(zoom * 1.05);
		
	}
	public void zoomOut() {
		setZoom(zoom * 0.95);
	}
	
}
