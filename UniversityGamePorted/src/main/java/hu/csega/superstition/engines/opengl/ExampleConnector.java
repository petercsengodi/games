package hu.csega.superstition.engines.opengl;

import hu.csega.games.adapters.opengl.HelloTexture;
import hu.csega.games.engine.env.Environment;
import hu.csega.superstition.engines.connector.Connector;

public class ExampleConnector implements Connector {

	public ExampleConnector() {
		example = new HelloTexture();
	}

	@Override
	public void run(Environment env) {
		example.setEnvironment(env);
		example.run();
	}

	@Override
	public void dispose() {
		example.dispose();
	}

	private HelloTexture example;

}
