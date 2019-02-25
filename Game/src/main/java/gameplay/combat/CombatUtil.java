package gameplay.combat;

import gameplay.entity.AttributeType;
import gameplay.entity.Character;

public final class CombatUtil {

	/**
	 * 50 Defense => 33% damage reduction. 100 Defense => 50% damage reduction. 150
	 * Defense => 60% damage reduction. 200 Defense => 66% damage reduction.
	 */
	private static final double DEFENSE_CONSTANT = 100d;

	public static void attack(Character source, Character target) {

		double sourceAtk = source.getAttributeAfterModifiers(AttributeType.ATTACK).getVal();
		double targetDef = source.getAttributeAfterModifiers(AttributeType.DEFENSE).getVal();

		double percentReduction = targetDef / (DEFENSE_CONSTANT + targetDef);
		double damage = sourceAtk * percentReduction;

		source.getAttribute(AttributeType.HEALTH).subtract(damage);
		
	}

}
