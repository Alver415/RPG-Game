package game.engine.components;

import javafx.scene.paint.Color;

public class Render extends Component {

	private Color baseColor = randomColor();

	public Color getColor() {
		if (entity.hasCollider()) {
			boolean collision = entity.getCollider().isCollided();
			return collision ? Color.RED : baseColor;
		}
		return baseColor;
	}

	public Color getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(Color baseColor) {
		this.baseColor = baseColor;
	}

	public Position getPosition() {
		return entity.getPosition();
	}
	
	public double getRadius() {
		if (entity.hasCollider()) {
			return entity.getCollider().getRadius();
		}
		else {
			return 50;
		}
	}

	private static Color randomColor() {
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		return Color.rgb(r, g, b);
	}

}
