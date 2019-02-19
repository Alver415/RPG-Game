package frontend.fxml;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import frontend.control.Controller;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameInputHandler implements EventHandler<InputEvent> {

	private final Set<Controller>	controllers;
	private final Set<KeyCode>		pressedKeys;
	private final Queue<MouseEvent>	mouseEvents;

	public GameInputHandler() {
		this.controllers = new HashSet<>();
		this.pressedKeys = new HashSet<>();
		this.mouseEvents = new LinkedList<>();
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyCode code = ((KeyEvent) event).getCode();
			if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
				pressedKeys.add(code);
			} else {
				pressedKeys.remove(code);
			}
		} else if (event instanceof MouseEvent) {
			mouseEvents.add((MouseEvent) event);
		}
	}

	public void addController(Controller inputProcessor) {
		this.controllers.add(inputProcessor);
	}

	public void process() {
		for (Controller controller : controllers) {
			controller.tick();
		}
		mouseEvents.clear();
	}

	public Set<KeyCode> getPressedKeys() {
		return new HashSet<>(pressedKeys);
	}
}
