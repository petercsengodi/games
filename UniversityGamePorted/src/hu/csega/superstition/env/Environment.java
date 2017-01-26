package hu.csega.superstition.env;

import java.io.Closeable;

public interface Environment {

	void registerForDisposing(Closeable closeable);

}
