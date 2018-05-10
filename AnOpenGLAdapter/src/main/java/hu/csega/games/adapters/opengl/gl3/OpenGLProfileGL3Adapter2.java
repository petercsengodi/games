package hu.csega.games.adapters.opengl.gl3;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.consts.OpenGLAttribute;
import hu.csega.games.adapters.opengl.consts.OpenGLFragment;
import hu.csega.games.adapters.opengl.consts.OpenGLSampler;
import hu.csega.games.adapters.opengl.models.OpenGLModelBuilder;
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

public class OpenGLProfileGL3Adapter2 implements OpenGLProfileAdapter {

	private int[] programHandlers = new int[2];
	private static final int SAMPLER_INDEX = 0;
	private static final int PROGRAM_INDEX = 1;

	private Matrix4f perspectiveMatrix = new Matrix4f().identity();
	private Matrix4f cameraMatrix = new Matrix4f().identity();
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

		float viewAngle = (float) Math.toRadians(45);
		float aspect = (float) width / height;
		float zNear = 0.1f;
		float zFar = 10000.0f;
		perspectiveMatrix.identity().setPerspective(viewAngle, aspect, zNear, zFar);
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
		gl3.glClearDepthf(1f); // 1f
		gl3.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

		gl3.glUseProgram(programHandlers[PROGRAM_INDEX]);

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

		try {
			GLProfile profile = gl3.getGLProfile();
			File textureFile = new File(filename);
			String format = (filename.endsWith("png") ? TextureIO.PNG : TextureIO.JPG);
			TextureData textureData = TextureIO.newTextureData(profile, textureFile, false, format);
			Texture texture = TextureIO.newTexture(textureData);
			texture.enable(gl3);
			container.setTexture(texture);
		} catch (IOException ex) {
			logger.error("IOException in texture initialization: " + filename, ex);
		}


		OpenGLErrorUtil.checkError(gl3, filename);
	}

	@Override
	public void disposeTexture(GLAutoDrawable glAutoDrawable, OpenGLTextureContainer container) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		Texture texture = container.getTexture();
		texture.disable(gl3);
		texture.destroy(gl3);
	}

	@Override
	public void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {
		if(gl3 == null)
			gl3 = glAutoDrawable.getGL().getGL3();

		int[] handlers = model.getOpenGLHandlers();
		int numberOfShapes = model.getNumberOfShapes();
		int offsetOfShapesInHandlerArray = model.getOffsetOfVertexArrays();
		int offsetVerticesInHandleArray = model.getOffsetOfVertexBuffers();
		int offsetOfIndicesInHandlerArray = model.getOffsetOfIndexBuffers();

		try {

			// generate IDs in advance
			gl3.glGenVertexArrays(numberOfShapes, handlers, offsetOfShapesInHandlerArray);
			gl3.glGenBuffers(numberOfShapes, handlers, offsetVerticesInHandleArray);
			gl3.glGenBuffers(numberOfShapes, handlers, offsetOfIndicesInHandlerArray);

			for(int i = 0; i < numberOfShapes; i++) {
				int shapeID = handlers[offsetOfShapesInHandlerArray + i];
				gl3.glBindVertexArray(shapeID);

				// Add indices to the shape

				int indicesID = handlers[offsetOfIndicesInHandlerArray + i];
				gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, indicesID);

				short[] indexData = model.builder().indexData(i);
				int sizeOfIndices = indexData.length * Short.BYTES;

				ShortBuffer indexBuffer = GLBuffers.newDirectShortBuffer(indexData);
				gl3.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, sizeOfIndices, indexBuffer, GL3.GL_STATIC_DRAW);
				BufferUtils.destroyDirectBuffer(indexBuffer);

				// Add vertices to the shape

				int verticesID = handlers[offsetVerticesInHandleArray + i];
				gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, verticesID);

				float[] vertexData = model.builder().vertexData(i);
				int sizeOfVertices = vertexData.length * Float.BYTES;

				FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(vertexData);
				gl3.glBufferData(GL3.GL_ARRAY_BUFFER, sizeOfVertices, vertexBuffer, GL3.GL_STATIC_DRAW);
				BufferUtils.destroyDirectBuffer(vertexBuffer);


				// Assign variables in shader

				int stride = (3 + 3 + 2) * Float.BYTES;

				gl3.glEnableVertexAttribArray(OpenGLAttribute.TEXCOORD);
				gl3.glEnableVertexAttribArray(OpenGLAttribute.NORMAL);
				gl3.glEnableVertexAttribArray(OpenGLAttribute.POSITION);

				gl3.glVertexAttribPointer(OpenGLAttribute.TEXCOORD, 2, GL3.GL_FLOAT, false, stride, 6 * Float.BYTES);
				gl3.glVertexAttribPointer(OpenGLAttribute.NORMAL, 3, GL3.GL_FLOAT, false, stride, 3 * Float.BYTES);
				gl3.glVertexAttribPointer(OpenGLAttribute.POSITION, 3, GL3.GL_FLOAT, false, stride, 0 * Float.BYTES);


				// Unbind
				gl3.glBindVertexArray(0);

				gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0); // must be left open according to example code
				gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0); // may be closed after attributes are added according to example code
			}

			OpenGLErrorUtil.checkError(gl3, "loadModel");
		} catch (Exception ex) {
			logger.error("Exception in model initialization: " + filename, ex);
		} catch (Throwable t) {
			throw new RuntimeException("initialization", t);
		}
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameObjectLocation location, OpenGLModelStoreImpl store) {
		try {

			calculatedMatrix.set(perspectiveMatrix);
			calculatedMatrix.mul(cameraMatrix);

			GameObjectPosition p = location.position;
			GameObjectRotation r = location.rotation;
			calculatedMatrix.translate(p.x, p.y, p.z);
			calculatedMatrix.rotate(r.z, 0f, 0f, 1f);
			calculatedMatrix.rotate(r.y, 1f, 0f, 0f);
			calculatedMatrix.rotate(r.x, 0f, 1f, 0f);

			calculatedMatrix.get(matrixBuffer);

			FloatBuffer buffer = GLBuffers.newDirectFloatBuffer(matrixBuffer);
			gl3.glUniformMatrix4fv(modelToClipMatrixUL, 1, false, buffer);
			BufferUtils.destroyDirectBuffer(buffer);

			int[] handlers = model.getOpenGLHandlers();
			int numberOfShapes = model.getNumberOfShapes();
			int offsetOfShapesInHandlerArray = model.getOffsetOfVertexArrays();

			OpenGLModelBuilder builder = model.builder();

			for(int i = 0; i < numberOfShapes; i++) {
				OpenGLTextureContainer textureContainer = builder.textureContainer(i);
				Texture texture = textureContainer.getTexture();
				texture.bind(gl3);

				// BIND

				int shapeID = handlers[offsetOfShapesInHandlerArray + i];
				gl3.glBindVertexArray(shapeID);


				// Draw

				int numberOfIndices = model.builder().numberOfIndices(i);
				gl3.glDrawElements(GL3.GL_TRIANGLES, numberOfIndices, GL3.GL_UNSIGNED_SHORT, 0);


				// UNBIND

				gl3.glBindVertexArray(0);
				gl3.glBindTexture(GL3.GL_TEXTURE_2D, 0);
			}

			OpenGLErrorUtil.checkError(gl3, "draw");
		} catch (Throwable t) {
			throw new RuntimeException("initialization", t);
		}
	}

	@Override
	public void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
		if(gl3 == null)
			gl3 = glAutodrawable.getGL().getGL3();

		int[] handlers = model.getOpenGLHandlers();
		int numberOfShapes = model.getNumberOfShapes();
		int offsetOfShapesInHandlerArray = model.getOffsetOfVertexArrays();
		int offsetVerticesInHandleArray = model.getOffsetOfVertexBuffers();
		int offsetOfIndicesInHandlerArray = model.getOffsetOfIndexBuffers();

		gl3.glDeleteVertexArrays(numberOfShapes, handlers, offsetOfShapesInHandlerArray);
		gl3.glDeleteBuffers(numberOfShapes, handlers, offsetVerticesInHandleArray);
		gl3.glDeleteBuffers(numberOfShapes, handlers, offsetOfIndicesInHandlerArray);

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

		double f2x = f1x * Math.cos(r.y) - f1z * Math.sin(r.y);
		double f2y = f1y;
		double f2z = f1x * Math.sin(r.y) + f1z * Math.cos(r.y);

		double f3x = f2x;
		double f3y = f2y * Math.cos(r.x) - f2z * Math.sin(r.x);
		double f3z = f2y * Math.sin(r.x) + f2z * Math.cos(r.x);

		float fx = (float)f3x + p.x;
		float fy = (float)f3y + p.y;
		float fz = (float)f3z + p.z;

		double u0x = 0f;
		double u0y = 1f;
		double u0z = 0f;

		double u1x = u0x * Math.cos(r.z) - u0y * Math.sin(r.z);
		double u1y = u0x * Math.sin(r.z) + u0y * Math.cos(r.z);
		double u1z = u0z;

		double u2x = u1x * Math.cos(r.y) - u1z * Math.sin(r.y);
		double u2y = u1y;
		double u2z = u1x * Math.sin(r.y) + u1z * Math.cos(r.y);

		double u3x = u2x;
		double u3y = u2y * Math.cos(r.x) - u2z * Math.sin(r.x);
		double u3z = u2y * Math.sin(r.x) + u2z * Math.cos(r.x);

		float ux = (float)u3x;
		float uy = (float)u3y;
		float uz = (float)u3z;

		cameraMatrix.identity();
		cameraMatrix.lookAt(p.x, p.y, p.z, fx, fy, fz, ux, uy, uz);
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL3Adapter2.class);
}
