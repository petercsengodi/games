package hu.csega.games.adapters.opengl.gl2;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.models.OpenGLModelBuilder;
import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.models.OpenGLTextureContainer;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectRotation;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProfileGL2TriangleAdapter implements OpenGLProfileAdapter {

	private static final float RAD = (float)(180.0 / Math.PI);

	private GL2 gl2 = null;
	private GLU glu = null;
	private GLUT glut = null;

	@Override
	public void viewPort(GLAutoDrawable glAutoDrawable, int width, int height) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);

		OpenGLErrorUtil.checkError(gl2, "viewPort");
	}

	@Override
	public void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot) {
		// Nothing to do currently
	}

	@Override
	public void disposeProgram(GLAutoDrawable glAutoDrawable) {
		// Nothing to do currently
	}

	@Override
	public void startFrame(GLAutoDrawable glAutoDrawable) {
		gl2 = glAutoDrawable.getGL().getGL2();
		glu = GLU.createGLU(gl2);
		glut = new GLUT();

		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_TEXTURE_2D);

		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_AUTO_NORMAL);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		float[] ambientLight = { 10f, 10f, 10f, 0f };
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

		//		float[] diffuseLight = { 1f, 2f, 1f, 0f };
		//		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(45.0f, 2f, 0.1f, 10000.0f);

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glMatrixMode(GL.GL_TEXTURE);

		OpenGLErrorUtil.checkError(gl2, "startFrame");
	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		gl2 = glAutoDrawable.getGL().getGL2();

		// printHello(gl2);

		gl2.glFlush();

		OpenGLErrorUtil.checkError(gl2, "endFrame");

		glut = null;
		glu = null;
		gl2 = null;
	}

	@Override
	public int getModelToClipMatrixUL() {
		return -1;
	}

	@Override
	public void loadTexture(GLAutoDrawable glAutoDrawable, String filename, OpenGLTextureContainer container) {
		try {
			Texture texture = TextureIO.newTexture(new File(filename), true);
			container.setTexture(texture);
		} catch (IOException ex) {
			throw new RuntimeException("Exception occurred while loading texture!", ex);
		}
	}

	@Override
	public void disposeTexture(GLAutoDrawable glAutoDrawable, OpenGLTextureContainer container) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		Texture texture = container.getTexture();
		texture.destroy(gl2);
		OpenGLErrorUtil.checkError(gl2, "disposeTexture");
	}

	@Override
	public void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {
		// this implementation doesn't create buffers on hardware side,
		// only draws triangle strips
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameObjectLocation location, OpenGLModelStoreImpl store) {
		int stride = 3 + 3 + 2;
		float vx, vy, vz, nx, ny, nz, tx, ty;
		int offset;

		gl2.glPushMatrix();

		GameObjectPosition lp = location.position;
		gl2.glTranslatef(lp.x, lp.y, lp.z);
		gl2.glRotatef(location.rotation.z * RAD, 0f, 0f, 1f);
		gl2.glRotatef(location.rotation.y * RAD, 1f, 0f, 0f);
		gl2.glRotatef(location.rotation.x * RAD, 0f, 1f, 0f);

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw init");

		OpenGLModelBuilder builder = model.builder();
		int numberOfShapes = builder.numberOfShapes();

		for(int part = 0; part < numberOfShapes; part++) {
			OpenGLTextureContainer textureContainer = builder.textureContainer(part);
			Texture texture = textureContainer.getTexture();
			texture.enable(gl2);
			texture.bind(gl2);

			gl2.glBegin(GL2.GL_TRIANGLES);

			float[] verticies = builder.vertexData(part);

			int numberOfindicies = builder.indexLength(part);
			short[] indicies = builder.indexData(part);
			for(int index = 0; index < numberOfindicies; index++) {
				offset = indicies[index] * stride;

				vx = verticies[offset++];
				vy = verticies[offset++];
				vz = verticies[offset++];

				nx = verticies[offset++];
				ny = verticies[offset++];
				nz = verticies[offset++];

				tx = verticies[offset++];
				ty = verticies[offset++];

				gl2.glNormal3f(nx, ny, nz);
				gl2.glTexCoord2f(tx, ty);
				gl2.glVertex3f(vx, vy, vz);
			}

			gl2.glEnd();

			texture.disable(gl2);
		}

		gl2.glPopMatrix();

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw finish");
	}

	@Override
	public void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
		// nothing to do in this implementation
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
	public void placeCamera(GLAutoDrawable glAutoDrawable, GameObjectLocation cameraSettings) {
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		OpenGLErrorUtil.checkError(gl2, "modelViewMatrix");

		GameObjectPosition p = cameraSettings.position;
		GameObjectRotation r = cameraSettings.rotation;

		double f0x = 0f;
		double f0y = 0f;
		double f0z = 1f;

		double f1x = f0x * Math.cos(r.z) - f0y * Math.sin(r.z);
		double f1y = f0x * Math.sin(r.z) + f0y * Math.cos(r.z);
		double f1z = f0z;

		double f2x = f1x;
		double f2y = f1y * Math.cos(r.y) - f1z * Math.sin(r.y);
		double f2z = f1y * Math.sin(r.y) + f1z * Math.cos(r.y);

		double f3x = f2x * Math.cos(r.x) - f2z * Math.sin(r.x);
		double f3y = f2y;
		double f3z = f2x * Math.sin(r.x) + f2z * Math.cos(r.x);

		float fx = (float)f3x + p.x;
		float fy = (float)f3y + p.y;
		float fz = (float)f3z + p.z;

		double u0x = 0f;
		double u0y = 1f;
		double u0z = 0f;

		double u1x = u0x * Math.cos(r.z) - u0y * Math.sin(r.z);
		double u1y = u0x * Math.sin(r.z) + u0y * Math.cos(r.z);
		double u1z = u0z;

		double u2x = u1x;
		double u2y = u1y * Math.cos(r.y) - u1z * Math.sin(r.y);
		double u2z = u1y * Math.sin(r.y) + u1z * Math.cos(r.y);

		double u3x = u2x * Math.cos(r.x) - u2z * Math.sin(r.x);
		double u3y = u2y;
		double u3z = u2x * Math.sin(r.x) + u2z * Math.cos(r.x);

		float ux = (float)u3x;
		float uy = (float)u3y;
		float uz = (float)u3z;

		glu.gluLookAt(
				p.x, p.y, p.z,
				fx, fy, fz,
				ux, uy, uz
				);

		OpenGLErrorUtil.checkError(gl2, "cameraPlacement");
	}

	@SuppressWarnings("unused")
	private void printHello(GL2 gl2) {
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		OpenGLErrorUtil.checkError(gl2, "resetTransform");

		String score = "Hello!";
		gl2.glColor4f( 255, 0, 0, 1);
		gl2.glRasterPos2f(0.0f, 0.0f);

		for (int i = 0; i < score.length(); i++) {
			glut.glutBitmapCharacter(GLUT.BITMAP_TIMES_ROMAN_24, score.charAt(i));
		}

		OpenGLErrorUtil.checkError(gl2, "textOut");
	}

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL2TriangleAdapter.class);
}
