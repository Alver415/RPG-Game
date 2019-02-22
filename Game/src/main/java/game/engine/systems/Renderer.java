package game.engine.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.engine.Entity;
import game.engine.Sprite;
import game.engine.Vector2D;
import game.engine.Viewport;
import game.engine.components.Render;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Renderer extends GameSystem{

	private final Canvas canvas;
	private GraphicsContext graphicsContext;
	
	private double cw;
	private double ch;
	
	private Viewport viewport;
	
	public Renderer(Canvas canvas) {
		this.canvas = canvas;
		this.graphicsContext = canvas.getGraphicsContext2D();
		this.viewport = new Viewport();
	}
	
	public void setTarget(Entity target) {
		this.viewport.setTarget(target);
	}

	@Override
	public void process(Set<Entity> entities, double dt) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				cw = graphicsContext.getCanvas().getWidth();
				ch = graphicsContext.getCanvas().getHeight();
				graphicsContext.clearRect(0, 0, cw, ch);
				
				renderGrid();
				
				List<Render> renders = new ArrayList<Render>();
				for (Entity entity : entities) {
					if (entity.hasRender()) {
						renders.add(entity.getRender());
					}
				}
				
				Collections.sort(renders, new Comparator<Render>() {
					@Override
					public int compare(Render o1, Render o2) {
						return Double.compare(o1.getzIndex(), o2.getzIndex()) ;
					}
				});
				
				for (Render render : renders) {
					render(render);
				}
			}
		});
	}
	
	private void renderGrid() {
		graphicsContext.setStroke(Color.GRAY);
		for (int x = -100; x <= 100; x += 5) {
			for (int y = -100; y <= 100; y += 5) {
				Vector2D canvasPoint = worldToCanvas(new Vector2D(x, y));
				graphicsContext.strokeRect(canvasPoint.getX(), canvasPoint.getY(), 1, 1);
			}	
		}
	}

	private Vector2D worldToCanvas(Vector2D vector2d) {
		// World position of the render
		double wx = vector2d.getX();						// world x
		double wy = vector2d.getY();						// world y
		
		// World position relative to the viewport
		double vx = wx - viewport.getVector2D().getX();		// view x
		double vy = wy - viewport.getVector2D().getY();		// view y
		double vz = viewport.getZoom();						// view zoom
		
		// Canvas position
		double cx = ((     vx * vz) + cw / 2);				// canvas x
		double cy = ((ch - vy * vz) - ch / 2);				// canvas y
		
		return new Vector2D(cx, cy);
	}

	protected void render(Render render) {
		Sprite sprite = render.getSprite();
		Vector2D world = render.getCenter();
		Vector2D canvas = worldToCanvas(world);

		double z = viewport.getZoom();						// view zoom
		double w = render.getWidth() * z;					// relative width
		double h = render.getHeight() * z;					// relative height		
		double x = canvas.getX();
		double y = canvas.getY();


		graphicsContext.setStroke(render.getBorder());
		graphicsContext.setFill(render.getFill());
		if (sprite != null) {
			Image image = sprite.getImage();
			graphicsContext.drawImage(image, x, y, w, h);
			graphicsContext.strokeRect(x, y, w, h);
		} else {
			graphicsContext.fillOval(x, y, w, h);
			graphicsContext.strokeOval(x, y, w, h);
		}
	}
}
