package game.engine.components.rendering;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AnimatedSprite extends Sprite {

	public static final AnimatedSprite	DOWN	= new AnimatedSprite("pikachu_spritesheet", 32, 32, 0);
	public static final AnimatedSprite	LEFT	= new AnimatedSprite("pikachu_spritesheet", 32, 32, 1);
	public static final AnimatedSprite	RIGHT	= new AnimatedSprite("pikachu_spritesheet", 32, 32, 2);
	public static final AnimatedSprite	UP		= new AnimatedSprite("pikachu_spritesheet", 32, 32, 3);
	
	protected final int frameWidth;
	protected final int frameHeight;
	
	protected List<Image>	frames;
	protected int index;
	
	protected AnimatedSprite(String fileName, int frameWidth, int frameHeight, int row) {
		super(fileName);
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frames = new ArrayList<>();

		int y = frameHeight * row;
		for (int x = 0; x < image.getWidth() - frameWidth + 1; x += frameWidth) {
			Image frame = new WritableImage(image.getPixelReader(), x, y, frameWidth, frameHeight);
			frames.add(frame);
		}
	}
	
	@Override
	public Image getImage() {
		return frames.get((index++ / 10) % frames.size());
	}
}
