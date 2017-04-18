package hu.csega.games.adapters.opengl.models;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import hu.csega.games.adapters.opengl.consts.OpenGLAttribute;
import hu.csega.games.adapters.opengl.consts.OpenGLFragment;
import hu.csega.games.adapters.opengl.consts.OpenGLSampler;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;
import hu.csega.games.adapters.opengl.utils.OpenGLLogStream;
import hu.csega.games.adapters.opengl.utils.OpenGLProgramLogger;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectType;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLModelStoreImpl implements OpenGLModelStore {

	private long identifierCounter = 1;
	private boolean disposeAllWhenPossible = false;

	private Map<String, GameObjectHandler> handlers = new HashMap<>();
	private Map<String, GameObjectHandler> toDispose = new HashMap<>();
	private Set<GameObjectHandler> toInitialize = new HashSet<>();
	private Map<GameObjectHandler, OpenGLObjectContainer> containers = new HashMap<>();

	private boolean programInitialized = false;
	private int[] programHandlers = new int[2];

	private int modelToClipMatrixUL;

	private static final String SHADERS_ROOT = "res/example";

	private static final int SAMPLER_INDEX = 0;
	private static final int PROGRAM_INDEX = 1;

	@Override
	public void setupScreen(GLAutoDrawable glAutodrawable, int width, int height) {
		ensureOpenGLProgramIsInitialized(glAutodrawable);
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		GL3 gl3 = glAutodrawable.getGL().getGL3();
		gl3.glViewport(0, 0, width, height);
	}

	@Override
	public void reset(GLAutoDrawable glAutodrawable) {
		disposeOpenGLProgram(glAutodrawable);
		ensureOpenGLProgramIsInitialized(glAutodrawable);

		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		for(OpenGLObjectContainer container : containers.values()) {
			if(container.isInitialized())
				container.dispose(glAutodrawable);
			container.initialize(glAutodrawable);
		}

		toInitialize.clear();
	}

	@Override
	public boolean needsInitialization() {
		return !toInitialize.isEmpty() || disposeAllWhenPossible;
	}

	@Override
	public void initializeModels(GLAutoDrawable glAutodrawable) {
		ensureOpenGLProgramIsInitialized(glAutodrawable);
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		for(GameObjectHandler handler : toInitialize) {
			OpenGLObjectContainer container = containers.get(handler);
			container.initialize(glAutodrawable);
		}

		toInitialize.clear();
	}

	@Override
	public void disposeUnderlyingObjects(GLAutoDrawable glAutodrawable) {
		disposeOpenGLProgram(glAutodrawable);
		if(disposeEnqueued())
			disposeEnqueuedObjects(glAutodrawable);

		for(Entry<GameObjectHandler, OpenGLObjectContainer> entry : containers.entrySet()) {
			GameObjectHandler handler = entry.getKey();
			OpenGLObjectContainer container = entry.getValue();
			if(container.isInitialized())
				container.dispose(glAutodrawable);
			toInitialize.add(handler);
		}
	}

	@Override
	public GameObjectHandler loadTexture(String filename) {
		GameObjectHandler handler = handlers.get(filename);

		if(handler == null) {
			handler = nextHandler(GameObjectType.TEXTURE);
			handlers.put(filename, handler);

			OpenGLObjectContainer container = containers.get(handler);
			if(container == null) {
				container = new OpenGLTextureContainer(filename);
				containers.put(handler, container);
				toInitialize.add(handler);
			}
		}

		return handler;
	}

	@Override
	public GameObjectHandler buildModel(GameModelBuilder builder) {
		String filename = "__id:" + identifierCounter;
		GameObjectHandler handler = nextHandler(GameObjectType.MODEL);
		handlers.put(filename, handler);

		OpenGLModelBuilder modelBuilder = new OpenGLModelBuilder(builder, this);

		OpenGLObjectContainer container = new OpenGLCustomModelContainer(filename, this, modelBuilder);
		containers.put(handler, container);

		toInitialize.add(handler);
		return handler;
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

	public OpenGLTextureContainer resolveTexture(GameObjectHandler textureReference) {
		OpenGLTextureContainer texture = (OpenGLTextureContainer)containers.get(textureReference);
		return texture;
	}

	public OpenGLModelContainer resolveModel(GameObjectHandler modelReference) {
		OpenGLModelContainer model = (OpenGLModelContainer)containers.get(modelReference);
		return model;
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

	private void ensureOpenGLProgramIsInitialized(GLAutoDrawable glAutodrawable) {
		if(!programInitialized) {
			logger.info("Initializing program.");

			GL3 gl3 = glAutodrawable.getGL().getGL3();

			gl3.glGenSamplers(1, programHandlers, SAMPLER_INDEX);
			int samplerHandler = programHandlers[SAMPLER_INDEX];
			gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_NEAREST);
			gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_NEAREST);
			gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
			gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE);

			ShaderCode vertShader = ShaderCode.create(gl3, GL_VERTEX_SHADER, this.getClass(),
					SHADERS_ROOT, null, "vs", "glsl", null, true);
			ShaderCode fragShader = ShaderCode.create(gl3, GL_FRAGMENT_SHADER, this.getClass(),
					SHADERS_ROOT, null, "fs", "glsl", null, true);

			ShaderProgram shaderProgram = new ShaderProgram();
			shaderProgram.add(vertShader);
			shaderProgram.add(fragShader);

			shaderProgram.init(gl3);

			int program = shaderProgram.program();
			programHandlers[PROGRAM_INDEX] = program;

			gl3.glBindAttribLocation(program, OpenGLAttribute.POSITION, "position");
			gl3.glBindAttribLocation(program, OpenGLAttribute.NORMAL, "normalVector");
			gl3.glBindAttribLocation(program, OpenGLAttribute.TEXCOORD, "texCoord");
			gl3.glBindFragDataLocation(program, OpenGLFragment.COLOR, "outputColor");

			shaderProgram.link(gl3, new OpenGLProgramLogger(new OpenGLLogStream()));
			modelToClipMatrixUL = gl3.glGetUniformLocation(program, "modelToClipMatrix");

			// TODO: why is texture here
			int texture0UL = gl3.glGetUniformLocation(program, "texture0");

			vertShader.destroy(gl3);
			fragShader.destroy(gl3);

			gl3.glUseProgram(program);
			gl3.glUniform1i(texture0UL, OpenGLSampler.DIFFUSE);
			gl3.glUseProgram(0);

			gl3.glEnable(GL3.GL_DEPTH_TEST);

			OpenGLErrorUtil.checkError(gl3, "ensureOpenGLProgramIsInitialized");

			programInitialized = true;
			logger.info("Initialized program.");
		}
	}

	private void disposeOpenGLProgram(GLAutoDrawable glAutodrawable) {
		if(programInitialized) {
			logger.info("Releasing program.");

			GL3 gl3 = glAutodrawable.getGL().getGL3();
			gl3.glDeleteProgram(programHandlers[PROGRAM_INDEX]);
			OpenGLErrorUtil.checkError(gl3, "disposeOpenGLProgram");

			programInitialized = false;
			logger.info("Released program.");
		}
	}

	public void startFrame(GLAutoDrawable glAutodrawable) {
		if(!programInitialized)
			return;

		GL3 gl3 = glAutodrawable.getGL().getGL3();

		gl3.glClearColor(0f, .33f, 0.66f, 1f);
		gl3.glClearDepthf(1f);
		gl3.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

		gl3.glUseProgram(programHandlers[PROGRAM_INDEX]);
		OpenGLErrorUtil.checkError(gl3, "startFrame");
	}

	public void endFrame(GLAutoDrawable glAutodrawable) {
		if(!programInitialized)
			return;

		GL3 gl3 = glAutodrawable.getGL().getGL3();
		gl3.glBindSampler(OpenGLSampler.DIFFUSE, 0);
		gl3.glUseProgram(0);
		OpenGLErrorUtil.checkError(gl3, "endFrame");
	}

	public int samplerIndex() {
		return programHandlers[PROGRAM_INDEX];
	}

	public int modelToClipMatrix() {
		return modelToClipMatrixUL;
	}

	private GameObjectHandler nextHandler(GameObjectType type) {
		return new GameObjectHandler(type, identifierCounter++);
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLModelStoreImpl.class);
}
