package hu.csega.games.units;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractUnit {

	protected AbstractUnit() {
	}

	void injectComponents(Object parent) {
		Method[] methods = this.getClass().getMethods();
		for(Method method : methods) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			int length = (parameterTypes == null ? 0 : parameterTypes.length);
			if(length == 0)
				continue;

			Parent parentAnnotation = method.getAnnotation(Parent.class);
			if(parentAnnotation != null) {
				Object[] objects = new Object[length];

				for(int i = 0; i < length; i++)
					objects[i] = parent;

				try {
					method.invoke(this, objects);
				} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}

				// !!!
				continue;
			}

			New newAnnotation = method.getAnnotation(New.class);
			if(newAnnotation != null) {
				Object[] objects = new Object[length];

				for(int i = 0; i < length; i++)
					objects[i] = UnitStore.createNewObject(this, parameterTypes[i]);

				try {
					method.invoke(this, objects);
				} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}

				// !!!
				continue;
			}

			Unit unitAnnotation = method.getAnnotation(Unit.class);
			if(unitAnnotation != null) {
				Object[] objects = new Object[length];
				Class<?>[] parameters = method.getParameterTypes();

				for(int i = 0; i < length; i++) {
					if(parameters[i].getAnnotation(Parent.class) != null)
						objects[i] = parent;
					else if(parameters[i].getAnnotation(New.class) != null)
						objects[i] = UnitStore.createNewObject(this, parameterTypes[i]);
					else
						objects[i] = UnitStore.createOrGetUnit(this, parameterTypes[i]);
				}

				try {
					method.invoke(this, objects);
				} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
