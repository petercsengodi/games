package hu.csega.games.adapters.opengl.gl2;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.models.OpenGLModelBuilder;
import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.models.OpenGLTextureContainer;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProfileGL2TriangleAdapter implements OpenGLProfileAdapter {

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
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl2);

		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_TEXTURE_2D);

		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_AUTO_NORMAL);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		float[] ambientLight = { 1f, 1f, 1f, 0f };
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

		float[] diffuseLight = { 1f, 2f, 1f, 0f };
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(45.0f, 1f, 0.1f, 10000.0f);

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glMatrixMode(GL.GL_TEXTURE);

		OpenGLErrorUtil.checkError(gl2, "startFrame");
	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();

		gl2.glFlush();

		OpenGLErrorUtil.checkError(gl2, "endFrame");
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

		GL2 gl2 = glAutoDrawable.getGL().getGL2();

		// gl2.glPushMatrix();

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw init");

		OpenGLModelBuilder builder = model.builder();
		for(int part = 0; part < builder.numberOfVertexArrays(); part++) {
			OpenGLTextureContainer textureContainer = builder.textureContainer(part);
			Texture texture = textureContainer.getTexture();
			texture.enable(gl2);
			texture.bind(gl2);

			gl2.glBegin(GL2.GL_TRIANGLES); // GL_TRIANGLE_STRIP

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

		// gl2.glPopMatrix();

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
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl2);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		OpenGLErrorUtil.checkError(gl2, "modelViewMatrix");

		GameObjectPosition p = cameraSettings.position;
		GameObjectDirection d = cameraSettings.forward;
		GameObjectDirection u = cameraSettings.up;

		glu.gluLookAt(
				p.x, p.y, p.z,
				d.x, d.y, d.z,
				u.x, u.y, u.z
				);

		OpenGLErrorUtil.checkError(gl2, "cameraPlacement");
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL2TriangleAdapter.class);
}
