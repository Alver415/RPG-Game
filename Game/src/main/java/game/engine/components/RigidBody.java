package game.engine.components;

import gameplay.entity.Position;

public class RigidBody extends Component{

	private double mass = 1;
	
	public RigidBody(double mass) {
		this.mass = mass;
	}

	public Position getPosition() {
		return entity.getPosition();
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
}
