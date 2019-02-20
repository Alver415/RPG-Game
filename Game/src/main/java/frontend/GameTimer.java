package frontend;

import javafx.animation.AnimationTimer;

public abstract class GameTimer extends AnimationTimer {

	private static final double NANO_TIME_CONVERSION = 1000000000;

	private long lastTick;
	private double delta;
	
	public GameTimer() {
		this.lastTick = 0;
		this.delta = 0;
		this.start();
	}
	
	@Override
	public void handle(long nanoTime) {
		this.delta = NANO_TIME_CONVERSION / (nanoTime - lastTick);
		this.lastTick = nanoTime;
		
		this.tick(delta);
	}
	
	public abstract void tick(double dt);
	
}
