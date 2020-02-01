package hu.csega.games.common;

/**
 * Abstraction for components, which are capable of manipulating other components.
 * They behave as drains towards others.
 */
public interface CommonDataManipulator<D> extends CommonDrain<D> {

	void addObjectToManipulate(CommonManipulable manipulable);
}
