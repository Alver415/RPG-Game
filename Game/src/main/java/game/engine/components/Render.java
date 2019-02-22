package game.engine.components;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Render extends Component {

	private Image	sprite;
	private Color	color;

	public Render(Image sprite) {
		this.sprite = sprite;
	}

	public Image getSprite() {
		return sprite;
	}

	public Color getColor() {
		if (entity.hasCollider()) {
			boolean collision = entity.getCollider().isCollided();
			return collision ? Color.RED : color;
		}
		return color;
	}

	public void setColor(Color baseColor) {
		this.color = baseColor;
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
