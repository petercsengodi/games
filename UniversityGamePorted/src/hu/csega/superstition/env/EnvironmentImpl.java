package hu.csega.superstition.env;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnvironmentImpl implements Environment {

	@Override
	public void registerForDisposing(Disposable closeable) {
		disposables.add(closeable);
	}

	@Override
	public void notifyExitting() {
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * Only reachable by the game main frame.
	 * @throws IOException
	 */
	public void waitForExitting() {
		try {

			synchronized (this) {
				this.wait();
			}

		} catch(InterruptedException ex) {
			throw new GameException("Interruption when waiting.", ex)
			.description("Main running class was waiting for the game to finish while an "
					+ "InterruptedException occurred.");
		}
	}

	/**
	 * Only reachable by the game main frame.
	 * @throws IOException
	 */
	public void finish() {
		for(Disposable disposable : disposables)
			disposable.dispose();
	}

	private Set<Disposable> disposables = new HashSet<>();
}
