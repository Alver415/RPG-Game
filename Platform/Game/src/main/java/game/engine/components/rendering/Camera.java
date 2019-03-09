package game.engine.components.rendering;

import game.engine.GameUtils;
import game.engine.Vector2D;
import game.engine.components.Component;

public class Camera extends Component {

	private static final double	zoomMax	= 500;
	private static final double	zoomMin	= 10;

	private double	zoom	= 50;

	public Vector2D getPosition() {
		return parent.getPosition();
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void setZoom(double zoom) {
		this.zoom = GameUtils.clamp(zoomMin, zoom, zoomMax);
	}

	public void zoomIn() {
		setZoom(zoom * 1.05);
		
	}
	public void zoomOut() {
		setZoom(zoom * 0.95);
	}

}
