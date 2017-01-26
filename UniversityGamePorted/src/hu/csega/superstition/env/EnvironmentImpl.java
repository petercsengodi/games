package hu.csega.superstition.env;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnvironmentImpl implements Environment {

	@Override
	public void registerForDisposing(Closeable closeable) {
		disposables.add(closeable);
	}

	/**
	 * Only reachable by the game main frame.
	 * @throws IOException
	 */
	public void finish() {
		try {
			for(Closeable disposable : disposables)
				disposable.close();
		} catch(IOException ex) {
			throw new GameException("Exception occurred when finishing program.", ex);
		}
	}

	private Set<Closeable> disposables = new HashSet<>();
}
