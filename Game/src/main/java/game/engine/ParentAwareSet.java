package game.engine;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParentAwareSet<T extends Child> implements Set<T> {

	private final GameObject	parent;
	private final Set<T>	wrapped;

	public ParentAwareSet(GameObject parent) {
		this.wrapped = ConcurrentHashMap.newKeySet();
		this.parent = parent;
	}

	@Override
	public int size() {
		return wrapped.size();
	}

	@Override
	public boolean isEmpty() {
		return wrapped.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return wrapped.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return wrapped.iterator();
	}

	@Override
	public Object[] toArray() {
		return wrapped.toArray();
	}

	@Override
	public <_T> _T[] toArray(_T[] a) {
		return wrapped.toArray(a);
	}

	@Override
	public boolean add(T e) {
		e.setParent(this.parent);
		return wrapped.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return wrapped.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return wrapped.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return wrapped.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return wrapped.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return wrapped.removeAll(c);
	}

	@Override
	public void clear() {
		wrapped.clear();
	}

}
