package frontend.control;

import java.util.Collection;

import frontend.Facade;
import frontend.fxml.GameInputHandler;
import frontend.settings.Control;
import gameplay.Vector2D;
import gameplay.entity.Entity;
import javafx.scene.input.KeyCode;

public class PlayerController extends Controller {

	private final GameInputHandler	inputHandler;

	private int dx, dy;

	public PlayerController(GameInputHandler inputHandler, Entity entity) {
		super(entity);
		this.inputHandler = inputHandler;
	}

	@Override
	public void tick() {
		Collection<KeyCode> pressedKeys = inputHandler.getPressedKeys();
		dx = 0;
		dy = 0;
		for (KeyCode code : pressedKeys) {
			process(code);
		}

		entity.getVelocity().set(Vector2D.normalize(dx, dy));
	}

	private void process(KeyCode keyCode) {
		Control control = Control.get(keyCode);
		if (control == null) {
			return;
		}
		process(control);
	}

	public void process(Control control) {
		switch (control) {
		case UP:
			dy++;
			break;
		case DOWN:
			dy--;
			break;
		case LEFT:
			dx--;
			break;
		case RIGHT:
			dx++;
			break;
		case SPACE:
			Facade.addComputer();
			break;
		default:
			break;
		}
	}
}
