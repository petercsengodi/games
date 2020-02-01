package hu.csega.games.common;

/**
 * Abstraction for those components, which accept some kind of data, and immediately transform
 * it into an other kind of data.
 */
public interface CommonDataTransformer<IN, OUT> {

	OUT transform(IN data);

}
