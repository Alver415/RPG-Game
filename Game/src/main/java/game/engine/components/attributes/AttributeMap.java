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
		
		attributeMap.get(AttributeType.SPEED).setVal(10);
	}
	
	public Value get(AttributeType type) {
		return attributeMap.get(type);
	}
}
