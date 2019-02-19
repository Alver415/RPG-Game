package frontend.fxml.components;

import java.util.ArrayList;
import java.util.List;

import frontend.fxml.components.Message.Type;
import javafx.animation.FadeTransition;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MessagePane extends BorderPane {

	private static final List<Message>	MESSAGES	= new ArrayList<Message>();
	public static final String			ID			= "#messagePane";
	public static final String			COUNTER_ID	= "#counter";

	public void addMessage(String text) {
		addMessage(null, text);
	}

	public void addMessage(String title, String text) {
		addMessage(null, text, Type.NORMAL);
	}

	public void addMessage(String title, String text, Type type) {
		Message newMessage = new Message(title, text, type);
		addMessage(newMessage);
	}

	public void addMessage(Message message) {
		MESSAGES.add(message);
		setBottom(message);
		animateIn(message);

	}

	public void removeMessage(Message message) {
		animateOut(message);
		MESSAGES.remove(message);
		if (getBottom().equals(message)) {
			if (MESSAGES.size() > 0) {
				final Message next = MESSAGES.get(MESSAGES.size() - 1);
				setBottom(next);
				animateIn(next);
			} else {
				setBottom(null);
			}
		}

	}

	private void animateIn(Message message) {
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.seconds(1));
		fade.setFromValue(0);
		fade.setToValue(0);
	}

	private void animateOut(Message message) {
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.seconds(1));
		fade.setFromValue(0);
		fade.setToValue(0);
	}
}
