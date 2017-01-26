package hu.csega.superstition.engines.connector;

import hu.csega.superstition.env.Disposable;
import hu.csega.superstition.env.Environment;

public interface Connector extends Disposable {

	void run(Environment env);

}
