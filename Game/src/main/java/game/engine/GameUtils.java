package game.engine;

import java.util.Set;

import game.engine.components.colliders.CircleCollider;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.PlayerController;
import game.engine.components.controllers.TargetedProjectileBehavior;
import game.engine.components.rendering.render.CircleRender;
import javafx.scene.paint.Color;

public class GameUtils {

	public static void spawn() {
		GameObject enemy = new GameObject();
		double randX = 20 * (Math.random() - 0.5);
		double randY = 18 * (Math.random() - 0.5);
		enemy.move(randX, randY);
		enemy.addComponent(new CircleRender(Color.GREEN, 0.5));
		enemy.addComponent(new CircleCollider(0.5, false));
		GameWorld.INSTANCE.addGameObject(enemy);
	}
	
	public static void shoot() {
		GameObject player = findPlayer();
		GameObject bullet = new GameObject();
		bullet.move(0, 0);
		bullet.addComponent(new CircleRender(Color.RED, 0.1));
		bullet.addComponent(new CircleCollider(0.1, true) {
			@Override
			public void handleCollision(Collider other) {
				if (other.getParent().equals(player)) {
					parent.terminate();
				}
			}
		});
		bullet.addComponent(new TargetedProjectileBehavior(player));
		GameWorld.INSTANCE.addGameObject(bullet);
	}
	
	public static GameObject findPlayer() {
		Set<GameObject> entities = GameWorld.INSTANCE.getEntities();
		for (GameObject gameObject : entities) {
			if (gameObject.getBehavior() instanceof PlayerController) {
				return gameObject;
			}
		}
		return null;
	}
}
