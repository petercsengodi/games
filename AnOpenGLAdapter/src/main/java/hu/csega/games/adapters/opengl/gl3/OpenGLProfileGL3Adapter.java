package hu.csega.games.adapters.opengl.gl3;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.consts.OpenGLAttribute;
import hu.csega.games.adapters.opengl.consts.OpenGLFragment;
import hu.csega.games.adapters.opengl.consts.OpenGLSampler;
import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.models.OpenGLTextureContainer;
import hu.csega.games.adapters.opengl.utils.BufferUtils;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;
import hu.csega.games.adapters.opengl.utils.OpenGLLogStream;
import hu.csega.games.adapters.opengl.utils.OpenGLProgramLogger;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectRotation;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProfileGL3Adapter implements OpenGLProfileAdapter {

	private int[] programHandlers = new int[2];
	private static final int SAMPLER_INDEX = 0;
	private static final int PROGRAM_INDEX = 1;

	private Matrix4f perspectiveMatrix = new Matrix4f().identity();
	private Matrix4f cameraMatrix = new Matrix4f().identity();
	private Matrix4f modelMatrix = new Matrix4f().identity();
	private Matrix4f calculatedMatrix = new Matrix4f().identity();
	private float[] matrixBuffer = new float[16]; // 4 * 4
	private int modelToClipMatrixUL;

	private GL3 gl3 = null;

	@Override
	public void viewPort(GLAutoDrawable glAutoDrawable, int width, int height) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		gl3.glViewport(0, 0, width, height);
		gl3 = null;

		float viewAngle = (float) Math.toRadians(90);
		float aspect = (float) width / height;
		float zNear = 0.1f;
		float zFar = 10000.0f;
		perspectiveMatrix.setPerspectiveLH(viewAngle, aspect, zNear, zFar);
	}

	@Override
	public void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		gl3.glGenSamplers(1, programHandlers, SAMPLER_INDEX);
		int samplerHandler = programHandlers[SAMPLER_INDEX];
		gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_NEAREST);
		gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_NEAREST);
		gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
		gl3.glSamplerParameteri(samplerHandler, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE);

		ShaderCode vertShader = ShaderCode.create(gl3, GL_VERTEX_SHADER, this.getClass(),
				shadersRoot + "/gl3", null, "vs", "glsl", null, true);
		ShaderCode fragShader = ShaderCode.create(gl3, GL_FRAGMENT_SHADER, this.getClass(),
				shadersRoot + "/gl3", null, "fs", "glsl", null, true);

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

		vertShader.destroy(gl3);
		fragShader.destroy(gl3);

		gl3.glEnable(GL3.GL_DEPTH_TEST);

		OpenGLErrorUtil.checkError(gl3, "ensureOpenGLProgramIsInitialized");
	}

	@Override
	public void disposeProgram(GLAutoDrawable glAutoDrawable) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		gl3.glDeleteProgram(programHandlers[PROGRAM_INDEX]);
		OpenGLErrorUtil.checkError(gl3, "disposeOpenGLProgram");
		gl3 = null;
	}

	@Override
	public void startFrame(GLAutoDrawable glAutoDrawable) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		gl3.glClearColor(0f, 0f, 0f, 1f);
		gl3.glClearDepthf(100000f); // 1f
		gl3.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

		gl3.glUseProgram(programHandlers[PROGRAM_INDEX]);




		//		 glMatrixMode(GL_PROJECTION);
		//		 glLoadIdentity();
		//		 gluPerspective(50.0, 1.0, 3.0, 7.0);
		//		 glMatrixMode(GL_MODELVIEW);
		//		 glLoadIdentity();
		//		 gluLookAt(0.0, 0.0, 5.0, // eye position
		//		           0.0, 0.0, 0.0, // reference position / center
		//		           0.0, 1.0, 0.0  // up-vector
		//		       );


		OpenGLErrorUtil.checkError(gl3, "startFrame");
	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		gl3.glBindSampler(OpenGLSampler.DIFFUSE, 0);
		gl3.glUseProgram(0);
		OpenGLErrorUtil.checkError(gl3, "endFrame");

		gl3 = null;
	}

	@Override
	public int getModelToClipMatrixUL() {
		return modelToClipMatrixUL;
	}

	@Override
	public void loadTexture(GLAutoDrawable glAutodrawable, String filename, OpenGLTextureContainer container) {
		if(gl3 == null)
			gl3 = glAutodrawable.getGL().getGL3();

//		try {
//			GL3 gl3 = glAutodrawable.getGL().getGL3();
//			File textureFile = new File(filename);
//
//			TextureData textureData = TextureIO.newTextureData(gl3.getGLProfile(), textureFile, false, TextureIO.PNG);
//			int level = 0;
//
//			int[] generatedTextureNames = container.getTextureHandlerArray();
//
//			gl3.glGenTextures(1, generatedTextureNames, 0);
//
//			gl3.glBindTexture(GL3.GL_TEXTURE_2D, generatedTextureNames[0]);
//
//			gl3.glTexImage2D(GL3.GL_TEXTURE_2D, level, textureData.getInternalFormat(),
//					textureData.getWidth(), textureData.getHeight(), textureData.getBorder(),
//					textureData.getPixelFormat(), textureData.getPixelType(), textureData.getBuffer());
//
//			gl3.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_BASE_LEVEL, 0);
//			gl3.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAX_LEVEL, level);
//
//			int[] swizzle = new int[]{GL3.GL_RED, GL3.GL_GREEN, GL3.GL_BLUE, GL3.GL_ONE};
//			gl3.glTexParameterIiv(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_SWIZZLE_RGBA, swizzle, 0);
//
//			gl3.glBindTexture(GL3.GL_TEXTURE_2D, 0);
//
//		} catch (IOException ex) {
//			logger.error("IOException in texture initialization: " + filename, ex);
//		}

	}

	@Override
	public void disposeTexture(GLAutoDrawable glAutoDrawable, OpenGLTextureContainer container) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

//		int[] generatedTextureNames = container.getTextureHandlerArray();
//		gl3.glDeleteTextures(1, generatedTextureNames , 0);
	}

	@Override
	public void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		try {

			createVertexArrays(model);
			// createVertexBuffers(model);
			// createIndexBuffers(model);

		} catch (Exception ex) {
			logger.error("Exception in model initialization: " + filename, ex);
		}
	}

	private void createVertexBuffers(OpenGLModelContainer model) {
		for(int i = 0; i < model.getNumberOfVertexBuffers(); i++) {
			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
		}

		OpenGLErrorUtil.checkError(gl3, "OpenGLModelContainer.createVertexBuffers");
	}

	private void createIndexBuffers(OpenGLModelContainer model) {
		gl3.glGenBuffers(model.getNumberOfIndexBuffers(), model.getOpenGLHandlers(), model.getOffsetOfIndexBuffers());
		for(int i = 0; i < model.getNumberOfIndexBuffers(); i++) {
			gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfIndexBuffers() + i]);

			short[] indexData = model.builder().indexData(i);
			int size = indexData.length * Short.BYTES;

			ShortBuffer indexBuffer = GLBuffers.newDirectShortBuffer(indexData);
			gl3.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, size, indexBuffer, GL3.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(indexBuffer);

			gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);
		}

		OpenGLErrorUtil.checkError(gl3, "OpenGLModelContainer.createIndexBuffers");
	}

	private void createVertexArrays(OpenGLModelContainer model) {
		int[] handlers = model.getOpenGLHandlers();
		int numberOfVertexArrays = model.getNumberOfVertexArrays();
		int offsetOfVerticesInHandlerArray = model.getOffsetOfVertexArrays();
		int offsetOfBuffersInHandleArray = model.getOffsetOfVertexBuffers();

		// generate IDs in advance
		gl3.glGenVertexArrays(numberOfVertexArrays, handlers, offsetOfVerticesInHandlerArray);
		gl3.glGenBuffers(numberOfVertexArrays, handlers, offsetOfBuffersInHandleArray);

		for(int i = 0; i < numberOfVertexArrays; i++) {
			int vertexArrayID = handlers[offsetOfVerticesInHandlerArray + i];
			gl3.glBindVertexArray(vertexArrayID);

			int bufferID = handlers[offsetOfBuffersInHandleArray + i];
			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, bufferID);

			float[] vertexData = model.builder().vertexData(i);
			int size = vertexData.length * Float.BYTES;

			FloatBuffer buffer = GLBuffers.newDirectFloatBuffer(vertexData);
			gl3.glBufferData(GL3.GL_ARRAY_BUFFER, size, buffer, GL3.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(buffer);

			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
			gl3.glBindVertexArray(0);
		}

		OpenGLErrorUtil.checkError(gl3, "OpenGLModelContainer.createVertexArrays");
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameObjectLocation location, OpenGLModelStoreImpl store) {
		calculatedMatrix.set(perspectiveMatrix).mul(cameraMatrix);
		calculatedMatrix.get(matrixBuffer);

		FloatBuffer buffer = GLBuffers.newDirectFloatBuffer(matrixBuffer);
		gl3.glUniformMatrix4fv(modelToClipMatrixUL, 1, false, buffer);
		BufferUtils.destroyDirectBuffer(buffer);

		int[] handlers = model.getOpenGLHandlers();
		int numberOfVertexArrays = model.getNumberOfVertexArrays();
		int offsetOfVerticesInHandlerArray = model.getOffsetOfVertexArrays();
		int offsetOfBuffersInHandleArray = model.getOffsetOfVertexBuffers();

		int stride = (3 + 3 + 2) * Float.BYTES;

		for(int i = 0; i < numberOfVertexArrays; i++) {
			int vertexArrayID = handlers[offsetOfVerticesInHandlerArray + i];
			gl3.glBindVertexArray(vertexArrayID);

			int bufferID = handlers[offsetOfBuffersInHandleArray + i];
			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, bufferID);

			gl3.glEnableVertexAttribArray(OpenGLAttribute.POSITION);

			gl3.glVertexAttribPointer(OpenGLAttribute.POSITION, 3, GL3.GL_FLOAT,
					false, stride, 0 * Float.BYTES);

			gl3.glEnableVertexAttribArray(OpenGLAttribute.NORMAL);
			gl3.glVertexAttribPointer(OpenGLAttribute.NORMAL, 3, GL3.GL_FLOAT,
					false, stride, 3 * Float.BYTES);

			gl3.glEnableVertexAttribArray(OpenGLAttribute.TEXCOORD);
			gl3.glVertexAttribPointer(OpenGLAttribute.TEXCOORD, 2, GL3.GL_FLOAT,
					false, stride, 6 * Float.BYTES);

			int numberOfVertices = model.builder().numberOfVertices(i);
			gl3.glDrawArrays(GL3.GL_TRIANGLES, 0, numberOfVertices);

			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
			gl3.glBindVertexArray(0);
		}

//		GL3 gl3 = glAutoDrawable.getGL().getGL3();
//
//		for(int i = 0; i < model.getNumberOfVertexArrays(); i++) {
//			gl3.glBindVertexArray(model.getOpenGLHandlers()[model.getOffsetOfVertexArrays() + i]);
//
//			int textureIndex = model.builder().textureContainer(i).getTextureHandler();
//			int indexLength = model.builder().indexLength(i);
//
//			gl3.glUniformMatrix4fv(store.modelToClipMatrix(), 1, false, zRotation, 0);
//			// gl3.glBindSampler(OpenGLSampler.DIFFUSE, store.samplerIndex()); // TODO csega: why is this a problem?
//			gl3.glActiveTexture(GL3.GL_TEXTURE0 + OpenGLSampler.DIFFUSE);
//			gl3.glBindTexture(GL3.GL_TEXTURE_2D, textureIndex);
//
//			gl3.glDrawElements(GL3.GL_TRIANGLES, indexLength, GL3.GL_UNSIGNED_SHORT, 0);
//
//			gl3.glBindTexture(GL3.GL_TEXTURE_2D, 0);
//			gl3.glBindVertexArray(0);
//		}

		OpenGLErrorUtil.checkError(gl3, "OpenGLModelContainer.draw");
	}

	@Override
	public void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
		if(gl3 == null)
			gl3 = glAutodrawable.getGL().getGL3();

		int[] handlers = model.getOpenGLHandlers();
		gl3.glDeleteVertexArrays(model.getNumberOfVertexArrays(), handlers, model.getOffsetOfVertexArrays());
		gl3.glDeleteBuffers(model.getNumberOfVertexArrays(), handlers, model.getOffsetOfVertexBuffers());
		// gl3.glDeleteBuffers(model.getNumberOfIndexBuffers(), handlers, model.getOffsetOfIndexBuffers());

		OpenGLErrorUtil.checkError(gl3, "OpenGLModelContainer.dispose");
	}

	@Override
	public void loadAnimation(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {
	}

	@Override
	public void drawAnimation(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store) {
	}

	@Override
	public void disposeAnimation(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
	}

	@Override
	public void placeCamera(GLAutoDrawable glAutodrawable, GameObjectLocation cameraSettings) {
		if(gl3 == null)
			gl3 = glAutodrawable.getGL().getGL3();

		GameObjectPosition p = cameraSettings.position;
		GameObjectRotation r = cameraSettings.rotation;

		double f0x = 0f;
		double f0y = 0f;
		double f0z = 1f;

		double f1x = f0x * Math.cos(r.z) - f0y * Math.sin(r.z);
		double f1y = f0x * Math.sin(r.z) + f0y * Math.cos(r.z);
		double f1z = f0z;

		double f2x = f1x;
		double f2y = f1y * Math.cos(r.y) - f1z * Math.sin(r.y);
		double f2z = f1y * Math.sin(r.y) + f1z * Math.cos(r.y);

		double f3x = f2x * Math.cos(r.x) - f2z * Math.sin(r.x);
		double f3y = f2y;
		double f3z = f2x * Math.sin(r.x) + f2z * Math.cos(r.x);

		float fx = (float)f3x + p.x;
		float fy = (float)f3y + p.y;
		float fz = (float)f3z + p.z;

		double u0x = 0f;
		double u0y = 1f;
		double u0z = 0f;

		double u1x = u0x * Math.cos(r.z) - u0y * Math.sin(r.z);
		double u1y = u0x * Math.sin(r.z) + u0y * Math.cos(r.z);
		double u1z = u0z;

		double u2x = u1x;
		double u2y = u1y * Math.cos(r.y) - u1z * Math.sin(r.y);
		double u2z = u1y * Math.sin(r.y) + u1z * Math.cos(r.y);

		double u3x = u2x * Math.cos(r.x) - u2z * Math.sin(r.x);
		double u3y = u2y;
		double u3z = u2x * Math.sin(r.x) + u2z * Math.cos(r.x);

		float ux = (float)u3x;
		float uy = (float)u3y;
		float uz = (float)u3z;

		cameraMatrix.lookAtLH(p.x, p.y, p.z, fx, fy, fz, ux, uy, uz);
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL3Adapter.class);
}
