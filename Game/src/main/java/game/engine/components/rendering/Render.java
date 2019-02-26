package game.engine.components.rendering;

import game.engine.Vector2D;
import game.engine.components.Component;
import javafx.scene.paint.Color;

public class Render extends Component {

	private Sprite sprite;
	private Color fill;
	private Color border;
	
	private double width;
	private double height;

	private double zIndex;
	
	private Render() {
		super(Component.Type.RENDER);
	}
	
	public Render(Sprite sprite) {
		this();
		this.sprite = sprite;
		this.fill = null;
		this.width = sprite.getImage().getWidth();
		this.height = sprite.getImage().getWidth();
	}

	public Render(Sprite sprite, double width, double height) {
		this();
		this.sprite = sprite;
		this.fill = null;
		this.width = width;
		this.height = height;
	}

	public Render(Color color, double width, double height) {
		this();
		this.sprite = null;
		this.fill = color;
		this.width = width;
		this.height = height;
	}

	public Render(Sprite sprite, double zIndex) {
		this(sprite);
		this.zIndex = zIndex;
	}

	public Vector2D getCenter() {
		return entity.getPosition();
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}
	
	public Color getBorder() {
		return border;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getzIndex() {
		return zIndex;
	}

	public void setzIndex(double zIndex) {
		this.zIndex = zIndex;
	}

	private static Color randomColor() {
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		return Color.rgb(r, g, b);
	}

}
