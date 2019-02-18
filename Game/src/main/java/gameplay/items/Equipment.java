package gameplay.items;

import java.util.HashMap;

import gameplay.entity.Attribute;
import gameplay.entity.Value;

public class Equipment extends Item {

	protected HashMap<Attribute, Value> attributes;

	public Equipment(String name) {
		super(name);

		attributes = new HashMap<Attribute, Value>();
		for (Attribute attr : Attribute.values()) {
			attributes.put(attr, new Value(0));
		}
	}

}
