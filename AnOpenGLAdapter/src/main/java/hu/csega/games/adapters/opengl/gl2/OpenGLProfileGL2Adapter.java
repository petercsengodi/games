package hu.csega.games.adapters.opengl.gl2;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.consts.OpenGLAttribute;
import hu.csega.games.adapters.opengl.consts.OpenGLFragment;
import hu.csega.games.adapters.opengl.consts.OpenGLSampler;
import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.models.OpenGLTextureContainer;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;
import hu.csega.games.adapters.opengl.utils.OpenGLLogStream;
import hu.csega.games.adapters.opengl.utils.OpenGLProgramLogger;

public class OpenGLProfileGL2Adapter implements OpenGLProfileAdapter {

	private int[] programHandlers = new int[1];
	private static final int PROGRAM_INDEX = 0;

	private int modelToClipMatrixUL;

	@Override
	public void viewPort(GLAutoDrawable glAutoDrawable, int width, int height) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);
	}

	@Override
	public void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();

		ShaderCode vertShader = ShaderCode.create(gl2, GL_VERTEX_SHADER, this.getClass(),
				shadersRoot + "/gl2", null, "vs", "glsl", null, true);
		ShaderCode fragShader = ShaderCode.create(gl2, GL_FRAGMENT_SHADER, this.getClass(),
				shadersRoot + "/gl2", null, "fs", "glsl", null, true);

		ShaderProgram shaderProgram = new ShaderProgram();
		shaderProgram.add(vertShader);
		shaderProgram.add(fragShader);

		shaderProgram.init(gl2);

		int program = shaderProgram.program();
		programHandlers[PROGRAM_INDEX] = program;

		gl2.glBindAttribLocation(program, OpenGLAttribute.POSITION, "position");
		gl2.glBindAttribLocation(program, OpenGLAttribute.NORMAL, "normalVector");
		gl2.glBindAttribLocation(program, OpenGLAttribute.TEXCOORD, "texCoord");
		gl2.glBindFragDataLocation(program, OpenGLFragment.COLOR, "outputColor");

		shaderProgram.link(gl2, new OpenGLProgramLogger(new OpenGLLogStream()));

		OpenGLErrorUtil.checkError(gl2, "ensureOpenGLProgramIsInitialized");
		modelToClipMatrixUL = gl2.glGetUniformLocation(program, "modelToClipMatrix");
		OpenGLErrorUtil.checkError(gl2, "ensureOpenGLProgramIsInitialized");

		// TODO: why is texture here
		int texture0UL = gl2.glGetUniformLocation(program, "texture0");

		vertShader.destroy(gl2);
		fragShader.destroy(gl2);

		gl2.glUseProgram(program);
		gl2.glUniform1i(texture0UL, OpenGLSampler.DIFFUSE);
		gl2.glUseProgram(0);

		gl2.glEnable(GL2.GL_DEPTH_TEST);
		OpenGLErrorUtil.checkError(gl2, "ensureOpenGLProgramIsInitialized");
	}

	@Override
	public void disposeProgram(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		gl2.glDeleteProgram(programHandlers[PROGRAM_INDEX]);
		OpenGLErrorUtil.checkError(gl2, "disposeOpenGLProgram");
	}

	@Override
	public void startFrame(GLAutoDrawable glAutoDrawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getModelToClipMatrixUL() {
		return modelToClipMatrixUL;
	}

	@Override
	public void loadTexture(GLAutoDrawable glAutodrawable, String filename, OpenGLTextureContainer container) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disposeTexture(GLAutoDrawable glAutoDrawable, OpenGLTextureContainer container) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
		// TODO Auto-generated method stub

	}

}
