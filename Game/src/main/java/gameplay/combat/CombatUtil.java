package gameplay.combat;

import gameplay.entity.Attribute;
import gameplay.entity.Entity;

public final class CombatUtil {

	/**
	 * 50 Defense => 33% damage reduction. 100 Defense => 50% damage reduction. 150
	 * Defense => 60% damage reduction. 200 Defense => 66% damage reduction.
	 */
	private static final double DEFENSE_CONSTANT = 100d;

	public static void attack(Entity source, Entity target) {

		double sourceAtk = source.getAttributeAfterModifiers(Attribute.ATTACK).getVal();
		double targetDef = source.getAttributeAfterModifiers(Attribute.DEFENSE).getVal();

		double percentReduction = targetDef / (DEFENSE_CONSTANT + targetDef);
		double damage = sourceAtk * percentReduction;

		source.getAttribute(Attribute.HEALTH).subtract(damage);
		
	}

}
