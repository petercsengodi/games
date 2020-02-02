package hu.csega.games.common;

/**
 * Abstraction for those components, which provide some kind of data.
 * May have a state, as this object needs to provide data even if no event is triggered.
 */
public interface CommonSource<D> {

	D provide();

}
