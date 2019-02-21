package gameplay;

import javafx.animation.AnimationTimer;

public abstract class GameTimer extends AnimationTimer {

	private long lastTick = 0;

	@Override
	public void handle(long nanoTime) {
		double dt = ((nanoTime - lastTick) / 1000000000d);
		lastTick = nanoTime;
		tick(dt);
	}

	public abstract void tick(double dt);

}