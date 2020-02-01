package hu.csega.games.common;

/**
 * Abstraction of components providing data for other components as a source, and can be modified by manipulators.
 */
public interface CommonDataEntity<D> extends CommonSource<D>, CommonManipulable {

}
