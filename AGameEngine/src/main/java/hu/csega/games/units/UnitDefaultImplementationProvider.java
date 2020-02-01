package hu.csega.games.units;

public class UnitDefaultImplementationProvider implements UnitProvider {

	private Class<?> defaultImplementation;

	public UnitDefaultImplementationProvider(Class<?> defaultImplementation) {
		this.defaultImplementation = defaultImplementation;
	}

	@Override
	public Object createNewObject(Class<?> interfaceClass) {
		try {
			return defaultImplementation.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
