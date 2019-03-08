package game.engine.components.attributes;

import java.util.HashMap;
import java.util.Map;

import game.engine.components.Component;

public class AttributeMap extends Component{

	private final Map<AttributeType, Value> attributeMap = new HashMap<>();
	
	public AttributeMap() {
		for (AttributeType type : AttributeType.values()) {
			attributeMap.put(type, new Value(100));
		}

		Value speed = attributeMap.get(AttributeType.SPEED);
		speed.setMin(1);
		speed.setMax(5);
		speed.setVal(3);
	}
	
	public Value get(AttributeType type) {
		return attributeMap.get(type);
	}
}
