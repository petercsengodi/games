package hu.csega.games.common;

/**
 * Abstraction for those components, which provide some kind of data.
 */
public interface CommonSource<D> {

	D provide();

}
