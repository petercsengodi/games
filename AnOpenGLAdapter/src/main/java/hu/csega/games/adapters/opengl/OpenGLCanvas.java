package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.intf.GameCanvas;

public class OpenGLCanvas implements GameCanvas {

	public OpenGLCanvas(GLCanvas realCanvas) {
		this.realCanvas = realCanvas;
	}

	public GLCanvas getRealCanvas() {
		return realCanvas;
	}

	@Override
	public void repaint() {
		realCanvas.repaint();
	}

	@Override
	public void dispose() {
		realCanvas.destroy();
	}

	private GLCanvas realCanvas;
}
