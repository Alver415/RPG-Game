package gui.eventHandlers;

import java.util.Collection;
import java.util.Queue;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public interface InputProcessor {

	void process(Collection<KeyCode> pressedKeys, Queue<MouseEvent> mouseEvents);

}
