package game.engine.components.rigidbody;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.engine.GameUtils;
import game.engine.Vector2D;
import game.engine.components.Component;
import game.engine.systems.PhysicsSystem;

public class RigidBody extends Component{

	private boolean isGrounded;
	private double damping;
	
	private double mass;
	private Vector2D velocity;
	private final Set<Force> forces;
	
	public RigidBody() {
		this(1);
	}
	
	public RigidBody(double mass) {
		this.mass = mass;
		this.velocity = Vector2D.ZERO;
		this.forces = ConcurrentHashMap.newKeySet();
		this.forces.add(PhysicsSystem.FORCE_OF_GRAVITY);
		this.isGrounded = true;
		this.damping = 0.5;
	}

	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public boolean isGrounded() {
		return velocity.getY() < 0;
	}
	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}
	public double getDamping() {
		return GameUtils.clamp(0, damping, 1);
	}
	public void setDamping(double damping) {
		this.damping = damping;
	}
	public Vector2D getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}
	
	public Set<Force> getForces() {
		return forces;
	}
	
	public void addForce(Force force) {
		forces.add(force);
	}

}
