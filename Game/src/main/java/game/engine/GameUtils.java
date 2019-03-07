package game.engine;

import java.util.Set;

import game.engine.components.behaviors.TargetedProjectileBehavior;
import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.PlayerController;
import game.engine.components.rendering.render.CircleRender;
import javafx.scene.paint.Color;

public class GameUtils {

	public static void spawnEnemy() {
		GameObject enemy = new GameObject();
		double randX = 20 * (Math.random() - 0.5);
		double randY = 18 * (Math.random() - 0.5);
		enemy.move(randX, randY);
		enemy.addComponent(new CircleRender(Color.GREEN, 1));
		enemy.addComponent(new CircleCollider(1, false));
		GameWorld.INSTANCE.addChild(enemy);
	}
	
	public static GameObject findPlayer() {
		Set<GameObject> entities = GameWorld.INSTANCE.getGameObjects();
		for (GameObject gameObject : entities) {
			if (gameObject.getController() instanceof PlayerController) {
				return gameObject;
			}
		}
		return null;
	}

	public static double clamp(double min, double val, double max) {
		return Math.max(Math.min(val, max), min);
	}
}
