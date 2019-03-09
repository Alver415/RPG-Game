package game.engine;

import java.util.Set;

import game.engine.components.colliders.Collider;
import game.engine.components.rendering.render.BasicShapeRender;
import game.engine.geometry.Circle;
import javafx.scene.paint.Color;

public class GameUtils {

	public static void spawnEnemy() {
		GameObject enemy = new GameObject();
		double randX = 20 * (Math.random() - 0.5);
		double randY = 18 * (Math.random() - 0.5);
		enemy.move(randX, randY);
		Circle circle = new Circle(1);
		enemy.addComponent(new BasicShapeRender(circle, Color.GREEN));
		enemy.addComponent(new Collider(circle));
		GameWorld.INSTANCE.addChild(enemy);
	}
	
	public static GameObject findPlayer() {
		Set<GameObject> entities = GameWorld.INSTANCE.getGameObjects();
		for (GameObject gameObject : entities) {
			if (gameObject.getBehavior() instanceof PlayerControlledBehavior) {
				return gameObject;
			}
		}
		return null;
	}

	public static double clamp(double min, double val, double max) {
		return Math.max(Math.min(val, max), min);
	}
}
