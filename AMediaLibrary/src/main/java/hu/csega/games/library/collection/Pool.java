package hu.csega.games.library.collection;

import java.lang.reflect.Constructor;

public class Pool<T> {

	public Pool(Class<T> type, int capacity) {
		this.capacity = capacity;
		this.items = new PoolItem<?>[capacity];

		try {
			Constructor<T> ctr = type.getConstructor();
			for(int i = 0; i < capacity; i++) {
				PoolItem<T> item = new PoolItem<>();
				item.index = i;
				item.object = ctr.newInstance();
				item.allocated = false;
				item.pool = this;
				this.items[i] = item;
			}
		} catch(Exception ex) {
			throw new RuntimeException("Could not create pool!", ex);
		}
	}

	/**
	 * Allocating is simple. We take the object at the cursor, and
	 * increase the cursor by one.
	 */
	@SuppressWarnings("unchecked")
	public synchronized PoolItem<T> allocate() {
		if(allocated >= capacity)
			throw new IllegalStateException("Too many objects are allocated!");

		PoolItem<T> ret = (PoolItem<T>)items[allocated++];
		ret.allocated = true;
		return ret;
	}

	/**
	 * Releasing an item is a bit tricky. We swap it with the last allocated element,
	 * so there won't be any wholes in the items array.
	 */
	public synchronized void release(PoolItem<T> poolItem) {
		swapFrom = poolItem.index;
		swapTo = allocated - 1;

		// swap the elements
		swap = items[swapFrom];
		items[swapFrom] = items[swapTo];
		items[swapTo] = swap;
		items[swapFrom].index = swapFrom;
		items[swapTo].index = swapTo;

		// release the last one
		allocated--;
		items[allocated].allocated = false;

		// clean up
	}

	/*
	 * We iterate through the whole items array, and reset the pool item markers.
	 */
	public synchronized void releaseAll() {
		allocated = 0;
		for(int i = 0; i < capacity; i++)
			items[i].allocated = false;
	}

	public synchronized int getAllocated() {
		return allocated;
	}

	private int capacity;
	private int allocated;
	private PoolItem<?>[] items;

	private PoolItem<?> swap; // has dirty value
	private int swapFrom; // has dirty value
	private int swapTo; // has dirty value
}
