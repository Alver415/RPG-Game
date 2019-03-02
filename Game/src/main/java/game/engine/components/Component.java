package game.engine.components;

import game.engine.GameObject;
import game.engine.components.attributes.AttributeMap;
import game.engine.components.colliders.Collider;
import game.engine.components.controllers.Behavior;
import game.engine.components.rendering.Render;
import game.engine.components.transforms.Transform;

public abstract class Component {

	public enum Type{
		RENDER(Render.class),
		COLLIDER(Collider.class),
		CONTROLLER(Behavior.class),
		ATTRIBUTES(AttributeMap.class),
		TRANSFORM(Transform.class);
		
		private final Class<? extends Component> baseClass;

		Type(Class<? extends Component> baseClass){
			this.baseClass = baseClass;
		}
		public Class<? extends Component> getBaseClass(){
			return baseClass;
		}
	}
	
	protected final Type type;
	protected GameObject gameObject;

	protected Component(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	
}
