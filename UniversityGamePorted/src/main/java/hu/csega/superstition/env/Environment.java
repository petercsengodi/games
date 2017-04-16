package hu.csega.superstition.env;

import hu.csega.superstition.common.Disposable;

public interface Environment {

	void registerForDisposing(Disposable disposable);

	void notifyExitting();

}
