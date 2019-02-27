package game.engine;

import java.util.HashSet;
import java.util.Set;

import frontend.settings.Control;
import game.engine.systems.RenderingSystem;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<InputEvent>{
	
	private final Set<Control> buttonsDown;
	
	public InputHandler() {
		this.buttonsDown = new HashSet<>();
	}
	
	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			KeyCode code = keyEvent.getCode();
			if (isPressed(keyEvent)) {
				switch(code) {
				case ESCAPE:
					GameWorld.INSTANCE.getGameTimer().pause();
					return;
				case F:
					if (((KeyEvent) event).isControlDown()) {
						RenderingSystem.INSTANCE.toggleTime();
					}
					return;
				default:
					break;
				}
			}
			
			Control input = Control.get(code);
			if (input != null && isPressed(keyEvent)) {
				buttonsDown.add(input);
			} else {
				buttonsDown.remove(input);
			}
		}
	}

	private static boolean isPressed(KeyEvent event) {
		return KeyEvent.KEY_PRESSED.equals(event.getEventType());
	}
	
	public Set<Control> getControls(){
		return buttonsDown;
	}

}
