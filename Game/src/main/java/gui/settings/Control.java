package gui.settings;

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
	RIGHT(KeyCode.D);

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

	private static final Map<KeyCode, Control> MAP = new HashMap<KeyCode, Control>();

	public static Control get(KeyCode keyCode) {
		return MAP.get(keyCode);
	}

	private static final String		SETTINGS_LOCATION	= "src/main/resources/Settings/controls.txt";
	private static final Properties	PROPERTIES			= new Properties();

	static {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					while (true) {
						read();
						try {
							wait(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public static void read() {
		try (FileInputStream in = new FileInputStream(SETTINGS_LOCATION);) {
			PROPERTIES.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		MAP.clear();
		for (Control control : values()) {
			String stringVal = PROPERTIES.getProperty(control.name());
			if (stringVal == null) {
				continue;
			}
			control.currentKeyCode = KeyCode.valueOf(stringVal);
			MAP.put(control.get(), control);
		}
	}

}
