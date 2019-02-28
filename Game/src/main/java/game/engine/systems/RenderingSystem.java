package game.engine.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.rendering.Render;
import game.engine.components.rendering.Viewport;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RenderingSystem extends GameSystem<Render> {

	public static final RenderingSystem INSTANCE = new RenderingSystem();

	private static final Font FONT = new Font(12);

	private final Viewport viewport;

	private GraphicsContext	graphicsContext;
	private double			cw;
	private double			ch;

	private RenderingSystem() {
		this.viewport = new Viewport();
	}

	public void setCanvas(Canvas canvas) {
		this.graphicsContext = canvas.getGraphicsContext2D();
	}

	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public void tick(double dt) {
		if (graphicsContext == null) {
			return;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				cw = graphicsContext.getCanvas().getWidth();
				ch = graphicsContext.getCanvas().getHeight();
				graphicsContext.clearRect(0, 0, cw, ch);

				renderGrid();

				List<Render> sorted = new ArrayList<>(components);

				// Sort objects first by zIndex, then by distance to the viewport.
				// Things closer to the center will be drawn last (on top of things further from
				// center)
				sorted.sort(new Comparator<Render>() {
					@Override
					public int compare(Render o1, Render o2) {
						int zIndex = Double.compare(o1.getzIndex(), o2.getzIndex());
						if (zIndex == 0) {
							double d1 = o1.getEntity().getPosition().distance(viewport.getVector2D());
							double d2 = o2.getEntity().getPosition().distance(viewport.getVector2D());
							int distance = Double.compare(d2, d1);
							return distance;
						}
						return zIndex;
					}
				});
				for (Render render : sorted) {
					render.draw(graphicsContext, viewport);
					;
				}

				renderTime();
			}
		});
	}

	private void renderTime() {
		if (!showTime) {
			return;
		}
		graphicsContext.setFont(FONT);
		double totalSecs = GameWorld.INSTANCE.getGameTime();
		int hours = (int) (totalSecs / 3600);
		int minutes = (int) ((totalSecs % 3600) / 60);
		int seconds = (int) (totalSecs % 60);

		String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.strokeText(timeString, 2, FONT.getSize());

		double fps = GameWorld.INSTANCE.getFPS();
		String fpsString = Integer.toString((int) Math.floor(fps));
		graphicsContext.strokeText(fpsString, cw - 15, FONT.getSize());
	}

	private void renderGrid() {
		double inc = 1;

		graphicsContext.setStroke(Color.GRAY);

		Vector2D worldMin = canvasToWorld(new Vector2D(0, 0));
		Vector2D worldMax = canvasToWorld(new Vector2D(cw, ch));

		double xMin = Math.floor(worldMin.getX() / inc) * inc;
		double yMin = Math.floor(worldMin.getY() / inc) * inc;

		double xMax = Math.ceil(worldMax.getX() / inc) * inc;
		double yMax = Math.ceil(worldMax.getY() / inc) * inc;

		for (double x = xMin; x <= xMax; x += inc) {
			Vector2D start = worldToCanvas(new Vector2D(x, yMin));
			Vector2D end = worldToCanvas(new Vector2D(x, yMax));
			graphicsContext.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
		}
		for (double y = yMin; y <= yMax; y += inc) {
			Vector2D start = worldToCanvas(new Vector2D(xMin, y));
			Vector2D end = worldToCanvas(new Vector2D(xMax, y));
			graphicsContext.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
		}
	}

	public Vector2D canvasToWorld(Vector2D canvas) {
		// World position of the render
		double cx = canvas.getX(); // world x
		double cy = canvas.getY(); // world y

		// World position relative to the viewport
		double vx = viewport.getVector2D().getX(); // view x
		double vy = viewport.getVector2D().getY(); // view y
		double vz = viewport.getZoom(); // view zoom

		// Canvas position
		double wx = (cx - cw / 2) / vz + vx;
		double wy = (cy - ch / 2) / vz + vy;

		return new Vector2D(wx, wy);
	}

	public Vector2D worldToCanvas(Vector2D world) {
		// World position of the render
		double wx = world.getX(); // world x
		double wy = world.getY(); // world y

		// World position relative to the viewport
		double vx = viewport.getVector2D().getX(); // view x
		double vy = viewport.getVector2D().getY(); // view y
		double vz = viewport.getZoom(); // view zoom

		// Canvas position
		double cx = ((wx - vx) * vz) + cw / 2; // canvas x
		double cy = (ch - (wy - vy) * vz) - ch / 2; // canvas y

		return new Vector2D(cx, cy);
	}

	private boolean showTime;

	public void toggleTime() {
		showTime = !showTime;
	}

}
