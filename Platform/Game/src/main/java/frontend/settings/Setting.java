package frontend.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum Setting {

	FULL_SCREEN(true, Boolean.class),
	SCREEN(1, Integer.class),
	
	SCREEN_WIDTH(500d, Double.class),
	SCREEN_HEIGHT(500d, Double.class);
	

	private final Object	defaultValue;
	private Object			currentValue;

	<T> Setting(T defaultValue, T clazz) {
		this.defaultValue = defaultValue;
		this.currentValue = defaultValue;
	}

	@SuppressWarnings("unchecked")
	public <T> T getDefault() {
		return (T) defaultValue;
	}

	@SuppressWarnings("unchecked")
	public <T> T get() {
		return (T) currentValue;
	}

	// Static fields and methods

	private static final String		SETTINGS_LOCATION	= "src/main/resources/Settings/settings.txt";
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

		for (Setting setting : values()) {
			String stringVal = PROPERTIES.getProperty(setting.name());
			if (stringVal == null) {
				continue;
			}
			Class<? extends Object> clazz = setting.defaultValue.getClass();
			setting.currentValue = coerce(clazz, stringVal);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T coerce(T clazz, String stringVal) {
		if (clazz.equals(String.class)) {
			return (T) stringVal;
		}
		if (clazz.equals(Integer.class)) {
			return (T) new Integer(stringVal);
		}
		if (clazz.equals(Double.class)) {
			return (T) new Double(stringVal);
		}
		if (clazz.equals(Boolean.class)) {
			return (T) new Boolean(stringVal);
		}
		throw new RuntimeException("Failed to coerce \"" + stringVal + "\" into " + clazz.toString());
	}

}
