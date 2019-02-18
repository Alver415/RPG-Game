package gameplay.effects;

import gameplay.entity.Entity;

public class Burn extends Effect {

	public Burn(Entity source, Entity target) {
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
