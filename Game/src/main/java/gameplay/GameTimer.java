package gameplay;


import javafx.animation.AnimationTimer;

public abstract class GameTimer extends AnimationTimer {

	private static final double NANO_CONVERSION = 1000000000d;

	private long	lastTick	= 0;
	private double	dt			= 1;

	private Thread gameThread;

	@Override
	public void handle(long nanoTime) {
		this.dt = (nanoTime - lastTick) / NANO_CONVERSION;
		this.lastTick = nanoTime;
		if (gameThread == null) {
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