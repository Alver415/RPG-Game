package gameplay.items;

import java.util.HashMap;

import gameplay.entity.AttributeType;
import gameplay.entity.Value;

public class Equipment extends Item {

	protected HashMap<AttributeType, Value> attributes;

	public Equipment(String name) {
		super(name);

		attributes = new HashMap<AttributeType, Value>();
		for (AttributeType attr : AttributeType.values()) {
			attributes.put(attr, new Value(0));
		}
	}

}
