package hu.csega.games.library.collection;

public class PoolItem<T> {

	public T getObject() {
		if(!allocated)
			throw new IllegalStateException("Pool item is released!");

		return object;
	}

	public void release() {
		if(!allocated)
			throw new IllegalStateException("Pool item is released!");

		pool.release(this);
	}

	int index;
	boolean allocated;
	T object;
	Pool<T> pool;
}
