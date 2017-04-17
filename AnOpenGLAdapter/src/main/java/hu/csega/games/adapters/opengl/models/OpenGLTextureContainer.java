package hu.csega.games.adapters.opengl.models;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLTextureContainer implements OpenGLObjectContainer {

	private String filename;
	private boolean initialized = false;
	private int[] generatedTextureNames = new int[1];

	public OpenGLTextureContainer(String filename) {
		this.filename = filename;
	}

	public int getTextureHandler() {
		if(initialized)
			return generatedTextureNames[0];
		return 0;
	}

	@Override
	public String filename() {
		return filename;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void initialize(GLAutoDrawable glAutodrawable) {
		logger.trace("Initializing texture: " + filename);

		try {
			GL3 gl3 = glAutodrawable.getGL().getGL3();
			File textureFile = new File(filename);

			TextureData textureData = TextureIO.newTextureData(gl3.getGLProfile(), textureFile, false, TextureIO.PNG);
			int level = 0;

			gl3.glGenTextures(1, generatedTextureNames, 0);

			gl3.glBindTexture(GL3.GL_TEXTURE_2D, generatedTextureNames[0]);

			gl3.glTexImage2D(GL3.GL_TEXTURE_2D, level, textureData.getInternalFormat(),
						textureData.getWidth(), textureData.getHeight(), textureData.getBorder(),
						textureData.getPixelFormat(), textureData.getPixelType(), textureData.getBuffer());

			gl3.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_BASE_LEVEL, 0);
			gl3.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAX_LEVEL, level);

			int[] swizzle = new int[]{GL3.GL_RED, GL3.GL_GREEN, GL3.GL_BLUE, GL3.GL_ONE};
			gl3.glTexParameterIiv(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_SWIZZLE_RGBA, swizzle, 0);

			gl3.glBindTexture(GL3.GL_TEXTURE_2D, 0);

		} catch (IOException ex) {
			logger.error("IOException in texture initialization: " + filename, ex);
		}

		initialized = true;
		logger.trace("Initialized texture: " + filename);
	}

	@Override
	public void dispose(GLAutoDrawable glAutodrawable) {
		logger.trace("Disposing texture: " + filename);
		GL3 gl3 = glAutodrawable.getGL().getGL3();
		gl3.glDeleteTextures(1, generatedTextureNames, 0);
		initialized = false;
		logger.trace("Disposed texture: " + filename);
	}


	private static final Logger logger = LoggerFactory.createLogger(OpenGLTextureContainer.class);
}
