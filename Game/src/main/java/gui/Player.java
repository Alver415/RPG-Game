package gui;

import gameplay.entity.Entity;
import gui.settings.Control;
import javafx.scene.input.KeyCode;

public class Player {

	private final Entity entity;

	public Player(Entity entity) {
		this.entity = entity;
	}

	public void process(KeyCode keyCode) {
		Control control = Control.get(keyCode);
		if (control == null) {
			return;
		}
		switch (control) {
		case UP:
			entity.getPosition().move(0, -1);
			break;
		case DOWN:
			entity.getPosition().move(0, 1);
			break;
		case LEFT:
			entity.getPosition().move(-1, 0);
			break;
		case RIGHT:
			entity.getPosition().move(1, 0);
			break;
		default:
			break;

		}
	}

}
