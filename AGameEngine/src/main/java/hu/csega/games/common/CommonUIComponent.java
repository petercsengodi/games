package hu.csega.games.common;

/**
 * Abstraction for those components, which will take part in creating the User Interface.
 * They provide data set by the user as a source, but as a drain they require data to present.
 */
public interface CommonUIComponent<IN, OUT> extends CommonSource<OUT>, CommonDrain<IN> {

}
