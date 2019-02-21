package game.engine.components;

import gameplay.entity.Position;
import javafx.scene.paint.Color;

public class Render extends Component{

	private Color color = randomColor();
	
	public Color getColor() {
		return color;
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

}
