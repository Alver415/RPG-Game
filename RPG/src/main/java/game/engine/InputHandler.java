package game.engine;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputHandler implements EventHandler<InputEvent> {
	
	private final Set<KeyCode> pressedKeys;
	private final Set<MouseEvent> mouseEvents;
	
	public InputHandler() {
		this.pressedKeys = ConcurrentHashMap.newKeySet();
		this.mouseEvents = ConcurrentHashMap.newKeySet();
	}
	
	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent){
			KeyEvent keyEvent = (KeyEvent) event;
			if (isPressed(keyEvent)){
				this.pressedKeys.add(keyEvent.getCode());	
			} else if (isReleased(keyEvent)){
				this.pressedKeys.remove(keyEvent.getCode());
			}
		}

		else if (event instanceof MouseEvent){
			this.mouseEvents.add((MouseEvent)event);	
		}
	}


	private boolean isPressed(KeyEvent keyEvent) {
		return KeyEvent.KEY_PRESSED.equals(keyEvent.getEventType());
	}
	private boolean isReleased(KeyEvent keyEvent) {
		return KeyEvent.KEY_RELEASED.equals(keyEvent.getEventType());
	}

	public Set<KeyCode> getPressedKeys() {
		return new HashSet<>(pressedKeys);
	}

	public Set<MouseEvent> getMouseEvents() {
		return new HashSet<>(mouseEvents);
	}

	public void tick() {
		this.mouseEvents.clear();
	}

}
