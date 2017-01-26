package hu.csega.superstition.env;

public interface Environment {

	void registerForDisposing(Disposable disposable);

	void notifyExitting();

}
