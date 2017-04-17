package hu.csega.games.adapters.opengl.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jogamp.opengl.GLAutoDrawable;

import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectType;

public class OpenGLModelStoreImpl implements OpenGLModelStore {

	private boolean dirty = false;
	private boolean disposeAllWhenPossible = false;
	private Map<String, GameObjectHandler> handlers = new HashMap<>();
	private Map<String, GameObjectHandler> toDispose = new HashMap<>();
	private Set<GameObjectHandler> toInitialize = new HashSet<>();
	private Map<GameObjectHandler, OpenGLObjectContainer> containers = new HashMap<>();
	private long counter = 1;

	@Override
	public void setupScreen(GLAutoDrawable glAutodrawable, int width, int height) {
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		// TODO Auto-generated method stub
	}

	@Override
	public void reset(GLAutoDrawable glAutodrawable) {
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		for(OpenGLObjectContainer container : containers.values()) {
			if(container.isInitialized())
				container.dispose(glAutodrawable);
			container.initialize(glAutodrawable);
		}

		dirty = false;
	}

	@Override
	public boolean needsInitialization() {
		return dirty || disposeAllWhenPossible;
	}

	@Override
	public void initializeModels(GLAutoDrawable glAutodrawable) {
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		for(GameObjectHandler handler : toInitialize) {
			OpenGLObjectContainer container = containers.get(handler);
			container.initialize(glAutodrawable);
		}

		toInitialize.clear();
		dirty = false;
	}

	@Override
	public void disposeUnderlyingObjects(GLAutoDrawable glAutodrawable) {
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		for(Entry<GameObjectHandler, OpenGLObjectContainer> entry : containers.entrySet()) {
			GameObjectHandler handler = entry.getKey();
			OpenGLObjectContainer container = entry.getValue();
			if(container.isInitialized())
				container.dispose(glAutodrawable);
			toInitialize.add(handler);
		}

		dirty = true;
	}

	@Override
	public GameObjectHandler loadTexture(String filename) {
		GameObjectHandler handler = nextHandler(GameObjectType.TEXTURE);
		handlers.put(filename, handler);

		OpenGLObjectContainer container = new OpenGLTextureContainer(filename);
		containers.put(handler, container);

		toInitialize.add(handler);
		dirty = true;
		return handler;
	}

	@Override
	public GameObjectHandler buildModel(GameModelBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObjectHandler loadModel(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObjectHandler loadAnimation(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose(GameObjectHandler handler) {
		OpenGLObjectContainer container = containers.get(handler);
		String filename = container.filename();
		toDispose.put(filename, handler);
	}

	@Override
	public void disposeAll() {
		disposeAllWhenPossible = true;
	}

	private boolean disposeEnqueued() {
		return disposeAllWhenPossible || !toDispose.isEmpty();
	}

	private void disposeEnqueuedObjects(GLAutoDrawable glAutodrawable) {
		if(disposeAllWhenPossible) {

			for(OpenGLObjectContainer container : containers.values()) {
				container.dispose(glAutodrawable);
			}

			toInitialize.clear();
			containers.clear();
			handlers.clear();
			disposeAllWhenPossible = false;
		} else {
			for(Entry<String, GameObjectHandler> entry : toDispose.entrySet()) {
				String filename = entry.getKey();
				GameObjectHandler handler = entry.getValue();
				OpenGLObjectContainer container = containers.get(handler);

				container.dispose(glAutodrawable);

				toInitialize.remove(handler);
				containers.remove(handler);
				handlers.remove(filename);
			}

			toDispose.clear();
		}
	}

	private GameObjectHandler nextHandler(GameObjectType type) {
		return new GameObjectHandler(type, counter++);
	}
}
