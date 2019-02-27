package game.engine.systems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.attributes.AttributeType;
import game.engine.components.attributes.Value;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.RectangleCollider;
import game.engine.components.rendering.Render;
import game.engine.components.rendering.Sprite;
import game.engine.components.rendering.Viewport;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RenderingSystem extends GameSystem<Render> {

	public static final RenderingSystem INSTANCE = new RenderingSystem();

	private static final Font FONT = new Font(12);
	
	private final Viewport viewport;

	private GraphicsContext graphicsContext;
	private double cw;
	private double ch;
	
	private RenderingSystem() {
		super(new HashSet<Render>());
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
				sorted.sort(new Comparator<Render>() {
					@Override
					public int compare(Render o1, Render o2) {
						return Double.compare(o1.getzIndex(), o2.getzIndex());
					}
				});
				for (Render render : sorted) {
					render(render);
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
		double inc = 10;

		graphicsContext.setStroke(Color.GRAY);

		Vector2D worldMin = canvasToWorld(new Vector2D(0, 0));
		Vector2D worldMax = canvasToWorld(new Vector2D(cw, ch));

		double xMin = Math.ceil(worldMin.getX() / inc) * inc;
		double yMin = Math.ceil(worldMin.getY() / inc) * inc;

		double xMax = Math.floor(worldMax.getX() / inc) * inc;
		double yMax = Math.floor(worldMax.getY() / inc) * inc;

		for (double x = xMin; x <= xMax; x += inc) {
			for (double y = yMin; y <= yMax; y += inc) {
				Vector2D worldToCanvas = worldToCanvas(new Vector2D(x, y));
				graphicsContext.strokeRect(worldToCanvas.getX(), worldToCanvas.getY(), 1, 1);
			}
		}
	}

	private Vector2D canvasToWorld(Vector2D canvas) {
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

	private Vector2D worldToCanvas(Vector2D world) {
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

	protected void render(Render render) {
		Sprite sprite = render.getSprite();
		Vector2D world = render.getCenter();
		Vector2D canvasPoint = worldToCanvas(world);

		double z = viewport.getZoom(); // view zoom
		double w = render.getWidth() * z; // relative width
		double h = render.getHeight() * z; // relative height
		double x = canvasPoint.getX() - w / 2;
		double y = canvasPoint.getY() - h / 2;

		graphicsContext.setStroke(render.getBorder());
		graphicsContext.setFill(render.getFill());
		if (sprite != null) {
			Image image = sprite.getImage();
			graphicsContext.drawImage(image, x, y, w, h);
			if (render.getEntity().getCollider() instanceof CircleCollider) {
				graphicsContext.strokeOval(x, y, w, h);
			}
			if (render.getEntity().getCollider() instanceof RectangleCollider) {
				graphicsContext.strokeRect(x, y, w, h);
			}
		} else {
			graphicsContext.fillOval(x, y, w, h);
			graphicsContext.strokeOval(x, y, w, h);
		}
		
		if (render.getEntity().getAttributeMap() != null) {
			Value value = render.getEntity().getAttributeMap().get(AttributeType.HEALTH);
			double ratio = value.getVal() / value.getMax();
			Color color = getColor(ratio);

			graphicsContext.setFill(Color.GRAY);
			graphicsContext.fillRect(x, y - 10, w, 5);
			graphicsContext.setFill(color);
			graphicsContext.fillRect(x, y - 10, w * ratio, 5);
			graphicsContext.strokeRect(x, y - 10, w, 5);
		}
	}
	
	private Color getColor(double ratio) {
		double r = clamp((-2 * ratio) + 2);
		double g = clamp(( 2 * ratio) + 0);
		return Color.rgb((int) (r * 255), (int) (g * 255), 0);
	}

	private double clamp(double r) {
		return Math.min(Math.max(r, 0), 1);
	}

	private boolean showTime;

	public void toggleTime() {
		showTime = !showTime;
	}

}
