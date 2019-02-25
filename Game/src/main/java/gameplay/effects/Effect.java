package gameplay.effects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gameplay.entity.AttributeType;
import gameplay.entity.Character;
import gameplay.entity.Modifier;

public abstract class Effect {

	protected Character	source;
	protected Character	target;

	protected Map<AttributeType, Set<Modifier>> modifiers;

	public Effect(Character source, Character target) {
		this.modifiers = new HashMap<AttributeType, Set<Modifier>>();
		this.source = source;
		this.target = target;
		apply();
	}

	public Character getSource() {
		return source;
	}

	public void setSource(Character source) {
		this.source = source;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public void apply() {
		target.getEffects().add(this);
	}

	public void remove() {
		target.getEffects().remove(this);
	}

	public abstract void tick();

	public Map<AttributeType, Set<Modifier>> getModifiers() {
		return modifiers;
	}

	public void addModifier(AttributeType attribute, Modifier modifier) {
		modifiers.get(attribute).add(modifier);
	}
}
