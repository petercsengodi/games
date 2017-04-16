package hu.csega.games.adapters.opengl;

import java.awt.Component;

import javax.swing.JFrame;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GameEngine;

public class OpenGLGameAdapter implements GameAdapter {

	@Override
	public GameControl createWindow(GameEngine engine) {
		OpenGLFrame frame = new OpenGLFrame(engine.getDescriptor().getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Component canvas = createCanvas(engine);
		frame.getContentPane().add(canvas);

		frame.pack();
		frame.setVisible(true);

		frame.start(engine, (GLCanvas)canvas);

		return frame.openGLControl;
	}

	@Override
	public Component createCanvas(GameEngine engine) {
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

        return glCanvas;
	}

}
