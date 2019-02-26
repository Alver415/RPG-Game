package game.engine.components;

import game.engine.Entity;
import game.engine.components.attributes.*;
import game.engine.components.colliders.*;
import game.engine.components.controllers.*;
import game.engine.components.rendering.*;
import game.engine.components.transforms.*;

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
	protected Entity entity;

	protected Component(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	
}
