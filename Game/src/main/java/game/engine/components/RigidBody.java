package game.engine.components;

public class RigidBody extends Component{

	private double mass = 1;
	
	public RigidBody(double mass) {
		this.mass = mass;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
}
