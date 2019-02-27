package game.engine.components.rendering.render;

import game.engine.components.rendering.Render;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class BasicRender extends Render{

	private Color color;
	
	public BasicRender(Color color) {
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
