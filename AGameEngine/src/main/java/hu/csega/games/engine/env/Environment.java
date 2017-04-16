package hu.csega.games.engine.env;

import hu.csega.units.DefaultImplementation;
import hu.csega.units.Unit;

@Unit
@DefaultImplementation(EnvironmentImpl.class)
public interface Environment {

	void registerForDisposing(Disposable disposable);

	void notifyExitting();

}
