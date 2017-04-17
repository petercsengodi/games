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
        logger.trace("Initialing texture: " + filename);

        try {
        	GL3 gl3 = glAutodrawable.getGL().getGL3();
            File textureFile = new File(filename);

            /**
             * Texture data is an object containing all the relevant information
             * about texture.
             */
            TextureData textureData = TextureIO.newTextureData(gl3.getGLProfile(), textureFile, false, TextureIO.PNG);
            /**
             * We don't use multiple levels (mipmaps) here, then our maximum
             * level is zero.
             */
            int level = 0;

            gl3.glGenTextures(1, generatedTextureNames, 0);

            gl3.glBindTexture(GL3.GL_TEXTURE_2D, generatedTextureNames[0]);
            {
                /**
                 * In this example internal format is GL_RGB8, dimensions are
                 * 512 x 512, border should always be zero, pixelFormat GL_RGB,
                 * pixelType GL_UNSIGNED_BYTE.
                 */
                gl3.glTexImage2D(GL3.GL_TEXTURE_2D, level, textureData.getInternalFormat(),
                        textureData.getWidth(), textureData.getHeight(), textureData.getBorder(),
                        textureData.getPixelFormat(), textureData.getPixelType(), textureData.getBuffer());
                /**
                 * We set the base and max level.
                 */
                gl3.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_BASE_LEVEL, 0);
                gl3.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAX_LEVEL, level);
                /**
                 * We set the swizzling. Since it is an RGB texture, we can
                 * choose to make the missing component alpha equal to one.
                 */
                int[] swizzle = new int[]{GL3.GL_RED, GL3.GL_GREEN, GL3.GL_BLUE, GL3.GL_ONE};
                gl3.glTexParameterIiv(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_SWIZZLE_RGBA, swizzle, 0);
            }

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
