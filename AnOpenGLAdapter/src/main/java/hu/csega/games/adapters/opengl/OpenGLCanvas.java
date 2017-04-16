package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.GameCanvas;

public class OpenGLCanvas implements GameCanvas {

	public OpenGLCanvas(GLCanvas realCanvas) {
		this.realCanvas = realCanvas;
	}

	public GLCanvas getRealCanvas() {
		return realCanvas;
	}

	public void repaint() {
		realCanvas.repaint();
	}

	private GLCanvas realCanvas;
}
