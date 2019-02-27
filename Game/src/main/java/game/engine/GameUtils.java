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
		Entity enemy = new Entity();
		double randX = 200 * (Math.random() - 0.5);
		double randY = 200 * (Math.random() - 0.5);
		enemy.move(randX, randY);
		enemy.setRender(new CircleRender(Color.GREEN, 10));
		enemy.setCollider(new CircleCollider(5, false));
		GameWorld.INSTANCE.addEntity(enemy);
	}
	
	public static void shoot() {
		Entity player = findPlayer();
		Entity bullet = new Entity();
		bullet.move(0, 0);
		bullet.setRender(new CircleRender(Color.RED, 2));
		bullet.setCollider(new CircleCollider(1, true) {
			@Override
			public void handleCollision(Collider other) {
				if (other.getEntity().equals(player)) {
					entity.terminate();
				}
			}
		});
		bullet.setBehavior(new TargetedProjectileBehavior(player));
		GameWorld.INSTANCE.addEntity(bullet);
	}
	
	public static Entity findPlayer() {
		Set<Entity> entities = GameWorld.INSTANCE.getEntities();
		for (Entity entity : entities) {
			if (entity.getBehavior() instanceof PlayerController) {
				return entity;
			}
		}
		return null;
	}
}
