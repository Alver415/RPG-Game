package frontend.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public enum Control {

	LEFT(KeyCode.A),
	RIGHT(KeyCode.D),
	JUMP(KeyCode.SPACE),
	SNEAK(KeyCode.SHIFT),
	
	ZOOM_IN(KeyCode.E),
	ZOOM_OUT(KeyCode.Q),
	
	
	SPAWN(KeyCode.N),
	SHOOT(MouseButton.PRIMARY);

	private final Object	defaultInput;
	private Object			currentInput;

	Control(Object defaultInput) {
		this.defaultInput = defaultInput;
		this.currentInput = defaultInput;
	}

	public Object getDefault() {
		return defaultInput;
	}

	public Object get() {
		return currentInput;
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
				control.currentInput = control.defaultInput;
			} else {
				control.currentInput = coerceInput(stringVal);
			}
			newMap.put(control.get(), control);
		}
		MAP = newMap;
	}

	private static Object coerceInput(String stringVal) {
		try {
			return KeyCode.valueOf(stringVal);
		} catch (Exception e) {
			
		}

		try {
			return MouseButton.valueOf(stringVal);
		} catch (Exception e) {
			
		}
		return null;
	}

}
