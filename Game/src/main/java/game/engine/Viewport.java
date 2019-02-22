package game.engine;

public class Viewport {

	private Entity target;
	public static double zoom = 0.5;

	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public Vector2D getVector2D() {
		return target == null ? Vector2D.ZERO : target.getPosition().asVector();
	}
	
	public double getZoom() {
		return zoom;
	}

	public Entity getTarget() {
		return target;
	}
	
}
