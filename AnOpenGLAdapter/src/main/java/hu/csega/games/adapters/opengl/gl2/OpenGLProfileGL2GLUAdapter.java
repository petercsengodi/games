package hu.csega.games.adapters.opengl.gl2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;

public class OpenGLProfileGL2GLUAdapter implements OpenGLProfileAdapter {

	@Override
	public void viewPort(GLAutoDrawable glAutoDrawable, int width, int height) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);
	}

	@Override
	public void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl2);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(45.0f, 1, 0.1f,100.0f);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		gl2.glEnable(GL2.GL_DEPTH_TEST);

		OpenGLErrorUtil.checkError(gl2, "ensureOpenGLProgramIsInitialized");
	}

	@Override
	public void disposeProgram(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		OpenGLErrorUtil.checkError(gl2, "disposeOpenGLProgram");
	}

	@Override
	public void startFrame(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl2);

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		glu.gluLookAt(0,  0, 10,
				0,  0,  0,
				0,  1,  1);

		gl2.glPushMatrix();

		gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		gl2.glLoadIdentity();

		//	    gl2.glTranslatef(x, y, z);
		//	    gl2.glBegin( GL2.GL_QUADS );
		//	        gl2.glColor3f( 1, 0, 0 );
		//
		//	        //24 glVertex3f calls & some colour changes go here.
		//	        gl2.glVertex3f(...)
	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();

		gl2.glEnd();
		gl2.glPopMatrix();

		gl2.glFlush();
	}

	@Override
	public int getModelToClipMatrixUL() {
		return -1;
	}

	@Override
	public void loadTexture(GLAutoDrawable glAutodrawable, String filename, int[] generatedTextureNames) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disposeTexture(GLAutoDrawable glAutoDrawable, int[] generatedTextureNames) {
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
