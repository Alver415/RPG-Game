package frontend.fxml.components;

import java.io.IOException;
import java.net.URL;

import frontend.Facade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Message extends VBox {

	private static final String	FXML_LOCATION	= "/fxml/components/message.fxml";
	private static final URL	FXML			= Message.class.getResource(FXML_LOCATION);

	@FXML
	private Label	titleLabel;
	@FXML
	private Text	messageText;

	public Message() {
		this(null);
	}

	public Message(String message) {
		this(null, message);
	}

	public Message(String title, String message) {
		this(title, message, Type.NORMAL);
	}

	public Message(String title, String message, Type type) {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		titleLabel.setText(title);
		messageText.setText(message);
		getStyleClass().add(type.getStyleClass());
		setFillWidth(true);
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public Text getMessageText() {
		return messageText;
	}

	@FXML
	protected void confirm() {
		Facade.removeMessage(this);
	}

	@FXML
	protected void cancel() {
		Facade.removeMessage(this);
	}

	public enum Type {
		SUCCESS("Success", "success"),
		NORMAL("Normal", "normal");

		private final String	displayName;
		private final String	styleClass;

		Type(String displayName, String styleClass) {
			this.displayName = displayName;
			this.styleClass = styleClass;
		}

		public String getStyleClass() {
			return styleClass;
		}

		public String getDisplayName() {
			return displayName;
		}
	}
}
