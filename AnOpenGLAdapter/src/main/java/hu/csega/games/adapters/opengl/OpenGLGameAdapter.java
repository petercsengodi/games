package hu.csega.games.adapters.opengl;

import java.awt.Dimension;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameThread;
import hu.csega.games.engine.GameWindow;

public class OpenGLGameAdapter implements GameAdapter {

	@Override
	public GameWindow createWindow(GameEngine engine) {
		OpenGLFrame frame = new OpenGLFrame(engine);
		return frame;
	}

	@Override
	public GameCanvas createCanvas(GameEngine engine) {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities( glProfile );
        final GLCanvas glCanvas = new GLCanvas( glCapabilities );

        glCanvas.addGLEventListener( new GLEventListener() {

            @Override
            public void reshape( GLAutoDrawable glAutodrawable, int x, int y, int width, int height ) {
                // OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            }

            @Override
            public void init( GLAutoDrawable glAutodrawable ) {
            }

            @Override
            public void dispose( GLAutoDrawable glAutodrawable ) {
            }

            @Override
            public void display( GLAutoDrawable glAutodrawable ) {
                // OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
            }
        });

        glCanvas.setPreferredSize(new Dimension(640, 480));
        return new OpenGLCanvas(glCanvas);
	}

	@Override
	public GameThread createThread(GameEngine engine) {
		return new OpenGLThread(engine.getPhysics(), engine.getCanvas());
	}

}
