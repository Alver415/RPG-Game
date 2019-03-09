package game.engine.components.rendering.render;

import game.engine.components.rendering.Render;
import game.engine.components.rendering.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteRender extends Render{

	private Sprite sprite;
	private Double width;
	private Double height;

	public SpriteRender(Sprite sprite) {
		this(sprite, null, null);
	}

	public SpriteRender(Sprite sprite, Double size) {
		this(sprite, size, size);
	}

	public SpriteRender(Sprite sprite, Double width, Double height) {
		super();
		this.sprite = sprite;
		this.width = width;
		this.height = height;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public double getWidth() {
		return width == null ? sprite.getImage().getWidth() : width;
	}
	
	public double getHeight() {
		return height == null ? sprite.getImage().getWidth() : height;
	}
	
	@Override
	public void draw(GraphicsContext gc, double x, double y, double w, double h) {
		Image image = sprite.getImage();
		gc.drawImage(image, x, y, w, h);
	}
	
	@Override
	public String toString() {
		return sprite.getName();
	}
}
