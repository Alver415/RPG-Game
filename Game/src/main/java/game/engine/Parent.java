package game.engine;

import java.util.Set;

public interface Parent {

	Set<Child> getChildren();
	void addChild(Child child);

	Parent getRoot();

}
