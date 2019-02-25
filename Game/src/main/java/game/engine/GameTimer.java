package game.engine;


import javafx.animation.AnimationTimer;

public abstract class GameTimer extends AnimationTimer {

	private static final double NANO_CONVERSION = 1000000000d;

	private long	lastTick	= System.nanoTime();
	private double	dt			= 1;

	private Thread gameThread;

	@Override
	public void handle(long nanoTime) {
		if (gameThread == null) {
			this.dt = (nanoTime - lastTick) / NANO_CONVERSION;
			this.lastTick = nanoTime;
			gameThread = new Thread(new Runnable() {
				@Override
				public void run() {
					tick(dt);
					gameThread = null;
				}
			}, "Game Thread");
			gameThread.start();
		}
	}

	public abstract void tick(double dt);

}