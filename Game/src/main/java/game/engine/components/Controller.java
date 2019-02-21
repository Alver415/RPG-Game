package game.engine.components;

import java.util.Set;

import frontend.settings.Control;
import game.engine.Entity;
import gameplay.Vector2D;

public class Controller extends Component{

	private static final double speed = 1;
	
	public void process(Set<Control> controls) {
		double dx = 0;
		double dy = 0;
		
		for (Control control : controls) {
			switch (control) {
			case UP:
				dy++;
				break;
			case DOWN:
				dy--;
				break;
			case LEFT:
				dx--;
				break;
			case RIGHT:
				dx++;
				break;
			case SPACE:
				break;
			default:
				break;
			
			}
		}
		Vector2D delta= Vector2D.normalize(dx, dy).scalar(speed);
		
		move(entity, delta);
	}

	private void move(Entity entity, Vector2D delta) {
		entity.getPosition().add(delta);
	}

}
