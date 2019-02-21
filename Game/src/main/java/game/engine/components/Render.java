package game.engine.components;

import gameplay.entity.Position;
import javafx.scene.paint.Color;

public class Render extends Component{

	private Color	baseColor		= randomColor();
	private boolean	collision	= false;

	public Color getColor() {
		return collision ? Color.RED : baseColor;
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

	private static Color randomColor() {
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		return Color.rgb(r, g, b);
	}

	public void setCollosion(boolean collision) {
		this.collision = collision;
	}

}
