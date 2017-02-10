package hu.csega.klongun.model;

import java.util.Iterator;

public class Pool<T extends PoolItem> implements Iterable<T>, Iterator<T> {

	public Pool(Class<T> objectClass, int capacity) {
		this.capacity = capacity;
		this.array = new Object[capacity];

		try {
			for(int i = 0; i < capacity; i++) {
				T obj = objectClass.newInstance();
				obj.setIndex(i);
				this.array[i] = obj;
			}
		} catch(Exception ex) {
			throw new RuntimeException("Error during constructor!", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public T allocate() {
		if(length >= capacity)
			throw new IllegalStateException("Full!");

		T ret = (T)array[length++];
		ret.clear();

		if(length > maxLength)
			maxLength = length;

		return ret;
	}

	public void free(T obj) {
		if(cursor > 0)
			throw new IllegalStateException("Should not be tampered while iterating!");

		thisIndex = obj.getIndex();
		delete(obj);
	}

	@Override
	public Iterator<T> iterator() {
		cursor = length;
		onRealElement = false;
		return this;
	}

	@Override
	public boolean hasNext() {
		return cursor > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T next() {
		if(!hasNext())
			throw new IllegalStateException("Already over!");

		onRealElement = true;
		return (T)array[--cursor];
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove() {
		if(!onRealElement)
			throw new IllegalStateException("Not on real element, yet!");
		thisIndex = cursor;
		delete((T)array[thisIndex]);
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void clear() {
		cursor = length = 0;
		onRealElement = false;
	}

	@SuppressWarnings("unchecked")
	private void delete(/* thisIndex, */ T obj) {
		otherIndex = --length;

		swapObject = (T)array[otherIndex];
		array[thisIndex] = swapObject;
		swapObject.setIndex(thisIndex);

		array[otherIndex] = obj;
		obj.setIndex(otherIndex);
	}

	private int thisIndex;
	private int otherIndex;
	private T swapObject;
	private int cursor = 0;
	private int length = 0;
	private final int capacity;
	private final Object[] array;
	private boolean onRealElement = false;
	private int maxLength;
}
