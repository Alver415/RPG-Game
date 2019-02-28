package game.engine;


import javafx.animation.AnimationTimer;

public abstract class GameTimer extends AnimationTimer {

	public static final double NANO_CONVERSION = 1000000000d;

	private long	lastTick	= System.nanoTime();

	private double	deltaTime	= 1;
	private double	gameTime	= 0;

	private double	fpsSmoothing	= 0.95;
	private double	avgFps			= 60;

	private boolean	isRunning;
	private Thread gameThread;

	public GameTimer() {
		super();
		super.start();
	}

	@Override
	public void start() {
		isRunning = true;
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	public void pause() {
		isRunning = !isRunning;
	}

	public double getGameTime() {
		return gameTime;
	}

	public double getDeltaTime() {
		return deltaTime;
	}

	public double getFPS() {
		return avgFps;
	}

	@Override
	public final void handle(long nanoTime) {
		if (gameThread == null) {
			this.deltaTime = (nanoTime - lastTick) / NANO_CONVERSION;
			this.lastTick = nanoTime;
			
			if (!isRunning) {
				return;
			}
			this.gameTime += deltaTime;
			this.avgFps = (avgFps * fpsSmoothing) + ((1 / deltaTime) * (1 - fpsSmoothing));

			gameThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						tick(deltaTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
					gameThread = null;
				}
			}, "Game Thread");
			gameThread.start();
		}
	}

	public abstract void tick(double dt);



}