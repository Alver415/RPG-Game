package game.engine.systems;

import java.util.HashSet;
import java.util.Set;

import frontend.settings.Control;
import game.engine.Entity;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.AIController;
import game.engine.components.controllers.Behavior;
import game.engine.components.controllers.PlayerController;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputSystem extends GameSystem<Behavior> {

	public static final InputSystem INSTANCE = new InputSystem();

	private final EventHandler<InputEvent> inputHandler;
	private final Set<Control> buttonsDown;
	
	private InputSystem() {
		super(new HashSet<Behavior>());
		
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

	public EventHandler<InputEvent> getInputHandler() {
		return inputHandler;
	}

	@Override
	public void tick(double dt) {
		for (Behavior controller : components) {
			if (controller instanceof PlayerController) {
				((PlayerController) controller).processInput(buttonsDown);
			}
			controller.tick(dt);
		}
	}

}
