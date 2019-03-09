package gameplay.items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

	double					maxSize;
	protected List<Item>	items;

	public Inventory() {
		maxSize = 50;
		items = new ArrayList<Item>();
	}

	public void addItem(Item item) throws Exception {
		if (items.size() >= maxSize) {
			throw new Exception("No more room in inventory.");
		}
		items.add(item);
	}

	public void removeItem(Item item) {
		items.remove(item);
	}
}
