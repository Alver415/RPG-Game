package game.engine.components.rendering.render;

import game.engine.components.rendering.Render;
import javafx.scene.paint.Color;

public abstract class BasicShapeRender extends Render{

	private Color color;
	
	public BasicShapeRender(Color color) {
		super();
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
