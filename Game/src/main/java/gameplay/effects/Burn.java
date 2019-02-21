package gameplay.effects;

import gameplay.entity.Character;

public class Burn extends Effect {

	public Burn(Character source, Character target) {
		super(source, target);
	}

	@Override
	public void apply() {
		super.apply();
	}

	@Override
	public void tick() {
		damage();
	}

	private void damage() {
	}

}
