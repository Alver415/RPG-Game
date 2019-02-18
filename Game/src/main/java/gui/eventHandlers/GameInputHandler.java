package gui.eventHandlers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameInputHandler implements EventHandler<InputEvent> {

	private final Set<InputProcessor>	inputProcessors;
	private final Set<KeyCode>			pressedKeys;
	private final Queue<MouseEvent>		mouseEvents;

	public GameInputHandler() {
		this.inputProcessors = new HashSet<>();
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

	public void addInputProcessor(InputProcessor inputProcessor) {
		this.inputProcessors.add(inputProcessor);
	}

	public void process() {
		for (InputProcessor inputProcessor : inputProcessors) {
			inputProcessor.process(pressedKeys, mouseEvents);
		}
		mouseEvents.clear();
	}
}
