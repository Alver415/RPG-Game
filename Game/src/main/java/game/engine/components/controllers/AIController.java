package game.engine.components.controllers;

import java.util.Set;

import game.engine.Entity;
import game.engine.GameWorld;
import game.engine.Vector2D;
import game.engine.components.transforms.Position;

public class AIController extends Controller {

	@Override
	public void tick(double dt) {
		Vector2D direction;
		Vector2D position = entity.getPosition().asVector();

		Entity player = findPlayer();
		if (player != null) {
			Vector2D target = player.getPosition().asVector();
			direction = target.subtract(position).normalize();
		} else {
			direction = Vector2D.ZERO.subtract(position).normalize();
		}
		Vector2D delta = direction.scalar(speed * dt);
		Position pos = entity.getPosition();
		pos.add(delta.normalize().scalar(speed * dt));
	}

	public Entity findPlayer() {
		Set<Entity> entities = GameWorld.get().getEntities();
		for (Entity entity : entities) {
			if (entity.getController() instanceof PlayerController) {
				return entity;
			}
		}

		return null;
	}

}
