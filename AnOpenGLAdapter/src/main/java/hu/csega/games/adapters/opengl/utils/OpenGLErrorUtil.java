package hu.csega.games.adapters.opengl.utils;

import static com.jogamp.opengl.GL.GL_INVALID_ENUM;
import static com.jogamp.opengl.GL.GL_INVALID_FRAMEBUFFER_OPERATION;
import static com.jogamp.opengl.GL.GL_INVALID_OPERATION;
import static com.jogamp.opengl.GL.GL_INVALID_VALUE;
import static com.jogamp.opengl.GL.GL_NO_ERROR;
import static com.jogamp.opengl.GL.GL_OUT_OF_MEMORY;

import com.jogamp.opengl.GL;

import hu.csega.games.engine.env.GameEngineException;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLErrorUtil {

	public static boolean checkError(GL gl, String title) {
		int error = gl.glGetError();
		if (error != GL_NO_ERROR) {
			String errorString;
			switch (error) {
			case GL_INVALID_ENUM:
				errorString = "GL_INVALID_ENUM";
				break;
			case GL_INVALID_VALUE:
				errorString = "GL_INVALID_VALUE";
				break;
			case GL_INVALID_OPERATION:
				errorString = "GL_INVALID_OPERATION";
				break;
			case GL_INVALID_FRAMEBUFFER_OPERATION:
				errorString = "GL_INVALID_FRAMEBUFFER_OPERATION";
				break;
			case GL_OUT_OF_MEMORY:
				errorString = "GL_OUT_OF_MEMORY";
				break;
			default:
				errorString = "UNKNOWN";
				break;
			}

			String msg = "OpenGL Error(" + errorString + "): " + title;
			logger.error(msg);
			throw new GameEngineException(msg);
		}
		return error == GL_NO_ERROR;
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLErrorUtil.class);
}
