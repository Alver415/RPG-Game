package game.engine.systems;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import antlr.debug.Event;
import frontend.settings.Control;
import game.engine.Entity;
import game.engine.components.AIController;
import game.engine.components.Controller;
import game.engine.components.RigidBody;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputSystem extends GameSystem {

	private final EventHandler<InputEvent> inputHandler;
	private final Set<Control>		buttonsDown;

	public InputSystem() {
		this.buttonsDown = new HashSet<>();
		this.inputHandler = new EventHandler<InputEvent>() {
			@Override
			public void handle(InputEvent event) {
				if (event instanceof KeyEvent) {
					KeyCode code = ((KeyEvent) event).getCode();
					Control input = Control.get(code);
					if (input != null && KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
						buttonsDown.add(input);
					} else {
						buttonsDown.remove(input);
					}
				}
			}
		};
	}

	public EventHandler<InputEvent> getInputHandler(){
		return inputHandler;
	}

	@Override
	public void process(Set<Entity> entities, double dt) {
		Set<Controller> controllers = new HashSet<Controller>();
		
		for (Entity entity : entities) {
			if (entity.hasComponent(Controller.class)) {
				controllers.add(entity.getComponent(Controller.class));
			}
			if (entity.hasComponent(AIController.class)) {
				controllers.add(entity.getComponent(AIController.class));
			}
		}
		
		for (Controller controller : controllers) {
			controller.process(buttonsDown);
		}
	}
	
}
