package game.engine.components.rendering.render;

import game.engine.components.attributes.AttributeMap;
import game.engine.components.attributes.AttributeType;
import game.engine.components.attributes.Value;
import game.engine.components.rendering.Render;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthBarRender extends Render{

	public HealthBarRender() {
		super();
	}

	@Override
	public void draw(GraphicsContext gc, double x, double y, double w, double h) {
		AttributeMap attributeMap = this.getParentGameObject().getAttributeMap();
		if (attributeMap == null) {
			return;
		}
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y - 5, w, 5);
		
		Value health = attributeMap.get(AttributeType.HEALTH);
		double ratio = health.getVal() / health.getMax();
		gc.setFill(Color.GREEN);
		gc.fillRect(x, y - 5, ratio * w, 5);
	}

	@Override
	public double getWidth() {
		return 1;
	}

	@Override
	public double getHeight() {
		return 0.2;
	}
	
	
}
