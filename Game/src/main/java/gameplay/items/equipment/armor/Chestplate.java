package gameplay.items.equipment.armor;

import static gameplay.entity.AttributeType.DEFENSE;

import gameplay.items.Armor;

public class Chestplate extends Armor {

	public Chestplate() {
		super("Chestplate", Type.BODY);

		attributes.get(DEFENSE).setVal(100);
	}

}
