package game.engine.components.rendering;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AnimatedSprite extends Sprite {

	public static final AnimatedSprite TEST = new AnimatedSprite("pallet_town", 10, 10);
	
	protected final int frameWidth;
	protected final int frameHeight;
	
	protected List<Image> frames;
	protected int index;
	
	protected AnimatedSprite(String fileName, int frameWidth, int frameHeight) {
		super(fileName);
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frames = new ArrayList<>();
		
		for (int x = 0; x < image.getWidth() - frameWidth; x += frameWidth) {
			for (int y = 0; y < image.getHeight() - frameHeight; y += frameHeight) {
				Image frame = new WritableImage(image.getPixelReader(), x, y, frameWidth, frameHeight);
				frames.add(frame);
			}
		}
	}
	
	@Override
	public Image getImage() {
		return frames.get((index++ / 10) % frames.size());
	}
}
