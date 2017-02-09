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
		if(cursor != 0 && cursor < length)
			throw new IllegalStateException("Should not be tampered while iterating!");

		return (T)array[length++];
	}

	@SuppressWarnings("unchecked")
	public void free(T obj) {
		if(cursor != 0 && cursor < length)
			throw new IllegalStateException("Should not be tampered while iterating!");

		thisIndex = obj.getIndex();
		otherIndex = --length;

		swapObject = (T)array[otherIndex];
		array[thisIndex] = swapObject;
		swapObject.setIndex(thisIndex);

		array[otherIndex] = obj;
		obj.setIndex(otherIndex);
	}

	@Override
	public Iterator<T> iterator() {
		cursor = 0;
		return this;
	}

	@Override
	public boolean hasNext() {
		return cursor < length;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T next() {
		if(!hasNext())
			throw new IllegalStateException("Already over!");

		return (T)array[cursor++];
	}

	private int thisIndex;
	private int otherIndex;
	private T swapObject;
	private int cursor = 0;
	private int length = 0;
	private final int capacity;
	private final Object[] array;
}
