package game.engine;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import frontend.settings.Control;
import game.engine.components.controllers.PlayerController;
import game.engine.systems.GameSystem;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputHandler extends GameSystem<PlayerController> implements EventHandler<InputEvent> {
	
	private final Set<InputEvent> events;
	
	public InputHandler() {
		super(PlayerController.class);
		this.events = ConcurrentHashMap.newKeySet();
	}
	
	@Override
	public void handle(InputEvent event) {
		this.events.add(event);
	}

	public Set<InputEvent> getInputEvents() {
		return new HashSet<>(events);
	}
	
	public void consume(InputEvent inputEvent) {
		this.events.remove(inputEvent);
	}

	@Override
	public void tick(double dt) {
		for (PlayerController controller : components) {
			controller.processInput(events);
		}
	}

}
