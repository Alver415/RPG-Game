package gui.eventHandlers;

import java.util.Collection;
import java.util.Queue;

import gui.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class PlayerInputProcessor implements InputProcessor {

	private final Player player;

	public PlayerInputProcessor(Player player) {
		this.player = player;
	}

	@Override
	public void process(Collection<KeyCode> pressedKeys, Queue<MouseEvent> mouseEvents) {
		for (KeyCode code : pressedKeys) {
			process(code);
		}
		for (MouseEvent event : mouseEvents) {
			process(event);
		}
	}

	private void process(KeyCode code) {
		player.process(code);
	}

	private void process(MouseEvent event) {
		switch (event.getButton()) {
		case MIDDLE:
			break;
		case NONE:
			break;
		case PRIMARY:
			break;
		case SECONDARY:
			break;
		default:
			break;
		}
	}

}
