package gameplay.effects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gameplay.entity.Attribute;
import gameplay.entity.Entity;
import gameplay.entity.Modifier;

public abstract class Effect {

	protected Entity	source;
	protected Entity	target;

	protected Map<Attribute, Set<Modifier>> modifiers;

	public Effect(Entity source, Entity target) {
		this.modifiers = new HashMap<Attribute, Set<Modifier>>();
		this.source = source;
		this.target = target;
		apply();
	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public void apply() {
		target.getEffects().add(this);
	}

	public void remove() {
		target.getEffects().remove(this);
	}

	public abstract void tick();

	public Map<Attribute, Set<Modifier>> getModifiers() {
		return modifiers;
	}

	public void addModifier(Attribute attribute, Modifier modifier) {
		modifiers.get(attribute).add(modifier);
	}
}
