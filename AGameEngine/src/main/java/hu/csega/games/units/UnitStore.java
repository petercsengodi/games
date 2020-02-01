package hu.csega.games.units;

import java.util.HashMap;
import java.util.Map;

public class UnitStore {

	public static <T> T instance(Class<T> unitClass) {
		return createOrGetUnit(null, unitClass);
	}

	public static void registerProvider(Class<?> interfaceClass, UnitProvider unitProvider) {
		synchronized (unitStoreMap) {
			providerMap.put(interfaceClass, unitProvider);
		}
	}

	public static void registerInstance(Class<?> interfaceClass, Object instance) {
		synchronized (unitStoreMap) {
			unitStoreMap.put(interfaceClass, instance);
		}
	}

	public static void registerDefaultImplementation(Class<?> interfaceClass, Class<?> defaultImplementation) {
		synchronized (unitStoreMap) {
			providerMap.put(interfaceClass, new UnitDefaultImplementationProvider(defaultImplementation));
		}
	}

	@SuppressWarnings("unchecked")
	static <T> T createOrGetUnit(Object parent, Class<T> unitClass) {
		synchronized (unitStoreMap) {
			try {

				AlwaysNew alwaysNewAnnotation = unitClass.getAnnotation(AlwaysNew.class);
				boolean alwaysNew = (alwaysNewAnnotation != null);
				if(alwaysNew)
					return createNewObjectWE(parent, unitClass);

				T object = (T)unitStoreMap.get(unitClass);

				if(object == null) {
					object = createNewObjectWE(parent, unitClass);

					unitStoreMap.put(unitClass, object);
				}

				return object;
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	static <T> T createNewObject(Object parent, Class<T> unitClass) {
		try {
			T object = createNewObjectWE(parent, unitClass);
			return object;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T createNewObjectWE(Object parent, Class<T> unitClass) throws InstantiationException, IllegalAccessException {
		UnitProvider provider = providerMap.get(unitClass);
		if(provider != null) {
			return (T)provider.createNewObject(unitClass);
		}

		Class<?> implementation;

		DefaultImplementation implementorAnnotation = unitClass.getAnnotation(DefaultImplementation.class);
		if(implementorAnnotation != null && implementorAnnotation.value() != null) {
			implementation = implementorAnnotation.value();
		} else {
			implementation = unitClass;
		}

		T object = (T)implementation.newInstance();
		return object;
	}

	private static Map<Class<?>, Object> unitStoreMap = new HashMap<>();
	private static Map<Class<?>, UnitProvider> providerMap = new HashMap<>();
}
