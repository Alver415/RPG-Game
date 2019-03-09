package gameplay.gameObject;

import static gameplay.gameObject.AttributeType.ATTACK;
import static gameplay.gameObject.AttributeType.HEALTH;
import static gameplay.gameObject.AttributeType.MAGIC;
import static gameplay.gameObject.Modifier.Type.ADDITIVE;
import static gameplay.gameObject.Modifier.Type.MULTIPLICATIVE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import game.engine.Vector2D;
import gameplay.effects.Effect;
import gameplay.items.Equipment;
import gameplay.items.Inventory;
import gameplay.items.consumables.Consumable;

public class Character {

	protected final String		name;
	protected final Vector2D	position;

	protected final Map<AttributeType, Value>	attributes;
	protected final Map<String, Ability>	abilities;
	protected final Set<Effect>				effects;

	protected final Inventory		inventory;
	protected final Set<Equipment>	equipment;

	public Character(String name) {
		this.name = name;
		this.position = new Vector2D();
		this.equipment = new HashSet<Equipment>();

		attributes = new HashMap<AttributeType, Value>();
		for (AttributeType attr : AttributeType.values()) {
			attributes.put(attr, new Value(100));
		}
		abilities = new HashMap<String, Ability>();

		abilities.put("Attack", new Ability(this, null) {
			@Override
			public void execute() {
				double targetHealth = target.getAttribute(HEALTH).getVal();
				double sourceDamage = source.getAttribute(ATTACK).getVal();
				target.getAttribute(HEALTH).setVal(targetHealth - sourceDamage);

				System.out.println(source.getName() + " attacks " + target.getName() + " for " + sourceDamage
						+ " damage, leaving him at " + target.getAttribute(HEALTH).getVal() + "/"
						+ target.getAttribute(HEALTH).getMax() + "hp");
			}
		});

		abilities.put("Heal", new Ability(this, this) {
			@Override
			public void execute() {
				Value targetHealth = target.getAttribute(HEALTH);
				Value sourceMagic = source.getAttribute(MAGIC);
				double initialHealth = targetHealth.getVal();
				targetHealth.add(sourceMagic.getVal());
				double amountHealed = targetHealth.getVal() - initialHealth;
				System.out.println(source.getName() + " just healed for " + amountHealed + "hp");
			}
		});

		abilities.put("Fireball", new Ability(this, null) {
			@Override
			public void execute() {
				Value targetHealth = target.getAttribute(HEALTH);
				Value sourceMagic = source.getAttribute(MAGIC);
				targetHealth.subtract(sourceMagic.getVal());

				System.out.println(source.getName() + " cast Fireball at " + target.getName() + " for " + sourceMagic
						+ " damage, leaving him at " + target.getAttribute(HEALTH).getVal() + "/"
						+ target.getAttribute(HEALTH).getMax() + "hp");

				if (Math.random() < 0.5) {
					new Effect(source, target) {
						int		turns	= 3;
						double	damage	= sourceMagic.getVal() / 10;

						@Override
						public void tick() {
							target.getAttribute(HEALTH).subtract(damage);
							System.out.println(target.getName() + " takes " + damage
									+ " damage from burn, leaving him at " + target.getAttribute(HEALTH).getVal() + "/"
									+ target.getAttribute(HEALTH).getMax() + "hp");
							if (turns-- <= 0) {
								this.remove();
							}
						}
					};

					System.out.println(target.getName() + " is burned!");
				}

			}
		});

		effects = new HashSet<Effect>();
		inventory = new Inventory();
	}

	public Set<Effect> getEffects() {
		return effects;
	}

	public Value getAttribute(AttributeType key) {
		return attributes.get(key);
	}

	public Value getAttributeAfterModifiers(AttributeType key) {
		Value value = attributes.get(key).copy();

		double product = 1;
		double sum = 0;

		// For each effect, filter out all the modifiers for each type and sum them.
		for (Effect effect : effects) {
			Stream<Modifier> stream = effect.getModifiers().get(key).stream();

			product += stream.filter(modifier -> modifier.getType().equals(MULTIPLICATIVE))
					.mapToDouble(modifier -> modifier.getAmount()).sum();

			sum += stream.filter(modifier -> modifier.getType().equals(ADDITIVE))
					.mapToDouble(modifier -> modifier.getAmount()).sum();
		}

		value.setVal(value.getVal() * product);
		value.setVal(value.getVal() + sum);

		return value;

	}

	public String getName() {
		return name;
	}

	public Vector2D getPosition() {
		return position;
	}

	public boolean isDead() {
		return getAttributeAfterModifiers(AttributeType.HEALTH).getVal() <= 0;
	}

	public Map<String, Ability> getAbilities() {
		return abilities;
	}

	public void consume(Consumable consumable) {
		consumable.setTarget(this);
		consumable.consume();
	}

	public void tick(double dt) {
//		double speed = getAttributeAfterModifiers(Attribute.SPEED).getVal();
//		Vector2D velocity = this.getVelocity().asVector().scalar(speed * dt);
//		position.add(velocity);
	}

}
