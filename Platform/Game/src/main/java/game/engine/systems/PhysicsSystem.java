package game.engine.systems;

import java.util.Set;

import game.engine.Vector2D;
import game.engine.components.rigidbody.Force;
import game.engine.components.rigidbody.RigidBody;

public class PhysicsSystem extends GameSystem<RigidBody> {
	
	public static final Force FORCE_OF_GRAVITY = new Force(new Vector2D(0, -9.8), false);


	public PhysicsSystem() {
		super(RigidBody.class);
	}

	@Override
	public void tick(double dt) {
		
		for (RigidBody rigidBody : components) {
			Vector2D sumForces = Vector2D.ZERO;
			
			Set<Force> forces = rigidBody.getForces();
			for (Force force : forces) {
				if (force.isContinuous()) {
					sumForces = sumForces.add(force.getVector().scalar(dt));
				} else if (force.isImpact()) {
					sumForces = sumForces.add(force.getVector());
				}
			}
			
			forces.removeIf(i -> i.isImpact());
			
			double inverseMass = 1 / rigidBody.getMass();
			sumForces = sumForces.scalar(inverseMass);

			Vector2D newVel = rigidBody.getVelocity().add(sumForces);
			double damping = 1 - (rigidBody.getDamping());
			newVel = newVel.scalar(Math.pow(Math.E, -dt * Math.log(1 / damping)));
			
			rigidBody.setVelocity(newVel);
			rigidBody.getParentGameObject().move(newVel.scalar(dt));

		}
	}
}
