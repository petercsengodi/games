package hu.csega.superstition.engines.connector;

import hu.csega.games.engine.env.Disposable;
import hu.csega.games.engine.env.Environment;

public interface Connector extends Disposable {

	void run(Environment env);

}
