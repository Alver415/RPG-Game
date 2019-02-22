package frontend.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.scene.input.KeyCode;

public enum Control {

	UP(KeyCode.W),
	DOWN(KeyCode.S),
	LEFT(KeyCode.A),
	RIGHT(KeyCode.D),
	
	ZOOM_IN(KeyCode.E),
	ZOOM_OUT(KeyCode.Q),
	
	SPACE(KeyCode.SPACE);

	private final KeyCode	defaultKeyCode;
	private KeyCode			currentKeyCode;

	Control(KeyCode defaultKeyCode) {
		this.defaultKeyCode = defaultKeyCode;
		this.currentKeyCode = defaultKeyCode;
	}

	public KeyCode getDefault() {
		return defaultKeyCode;
	}

	public KeyCode get() {
		return currentKeyCode;
	}

	// Static fields and methods

	private static Map<KeyCode, Control> MAP = new HashMap<KeyCode, Control>();

	public static Control get(KeyCode keyCode) {
		return MAP.get(keyCode);
	}

	private static final String		SETTINGS_LOCATION	= "src/main/resources/Settings/controls.txt";
	private static final Properties	PROPERTIES			= new Properties();

	static {
		read();
	}

	public static void read() {
		try (FileInputStream in = new FileInputStream(SETTINGS_LOCATION);) {
			PROPERTIES.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<KeyCode, Control> newMap = new HashMap<KeyCode, Control>();
		for (Control control : values()) {
			String stringVal = PROPERTIES.getProperty(control.name());
			if (stringVal == null) {
				control.currentKeyCode = control.defaultKeyCode;
			} else {
				control.currentKeyCode = KeyCode.valueOf(stringVal);
			}
			newMap.put(control.get(), control);
		}
		MAP = newMap;
	}

}
