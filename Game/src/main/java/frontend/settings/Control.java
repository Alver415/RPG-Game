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
	
	SPRINT(KeyCode.SPACE),
	SNEAK(KeyCode.SHIFT),

	SPAWN(KeyCode.N),
	SHOOT(KeyCode.M);

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

	private static Map<Object, Control> MAP = new HashMap<Object, Control>();

	public static Control get(Object key) {
		return MAP.get(key);
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
		Map<Object, Control> newMap = new HashMap<Object, Control>();
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
