package hu.csega.games.common;

/**
 * Abstraction for those components, which accept some kind of data.
 */
public interface CommonDrain<D> {

	void accept(D data);

}
