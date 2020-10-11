package hu.csega.games.adapters.opengl.gl2;

import java.io.File;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector4f;

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
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameSelectionLine;
import hu.csega.games.engine.g3d.GameTransformation;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProfileGL2TriangleAdapter implements OpenGLProfileAdapter {

	private GL2 gl2 = null;
	private GLU glu = null;
	private GLUT glut = null;

	private float viewAngle = 45f;
	private float aspect = 1.5f;
	private float zNear = 0.1f;
	private float zFar = 10000.0f;

	private GameObjectPosition cameraEye = new GameObjectPosition();
	private GameObjectPosition cameraCenter = new GameObjectPosition();
	private GameObjectDirection cameraUp = new GameObjectDirection();

	private Vector4f tmpEye = new Vector4f();
	private Vector4f tmpCenter = new Vector4f();
	private Vector4f tmpUp = new Vector4f();
	private Matrix4f basicLookAt = new Matrix4f();
	private Matrix4f inverseLookAt = new Matrix4f();

	private float[] tmpMatrix = new float[16];

	private int width;
	private int height;
	private Matrix4f inverseCameraMatrix = new Matrix4f();
	private Matrix4f inversePerspectiveMatrix = new Matrix4f();

	private Matrix4f perspectiveMatrix = new Matrix4f();
	private Matrix4f cameraMatrix = new Matrix4f();

	@Override
	public void viewPort(GLAutoDrawable glAutoDrawable, int width, int height) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);

		this.width = width;
		this.height = height;

		OpenGLErrorUtil.checkError(gl2, "viewPort");

		viewAngle = 45f;
		aspect = (float) width / height;
		zNear = 0.1f;
		zFar = 10000.0f;

		float viewAngle = (float) Math.toRadians(45);
		float aspect = (float) width / height;
		float zNear = 0.1f;
		float zFar = 10000.0f;
		perspectiveMatrix.identity().setPerspective(viewAngle, aspect, zNear, zFar);

		perspectiveMatrix.invert(inversePerspectiveMatrix);
	}

	@Override
	public void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot) {
		// Nothing to do currently
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		OpenGLErrorUtil.checkError(gl2, "init");
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

		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);
		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_AUTO_NORMAL); // ?

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(viewAngle, aspect, zNear, zFar);

		// float[] ambientLight = { 1f, 1f, 1f, 0f };
		float[] ambientLight = { 0.3f, 0.3f, 0.3f, 0f };
		float[] diffuseLight = { 10000f, 10000f, 10000f, 0f };
		float[] specular = { 1f, 1f, 1f, 0f};
		float[] lightPosition = { 100f, 100f, 100f, 0f }; // w = 0f for directed light, w = 1 for spotlight
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0); // transformed as well

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

		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		OpenGLErrorUtil.checkError(gl2, filename);
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

		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		OpenGLErrorUtil.checkError(gl2, filename);
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameObjectPlacement placement, OpenGLModelStoreImpl store) {
		gl2.glPushMatrix();

		placement.calculateBasicLookAt(basicLookAt);
		placement.calculateInverseLookAt(basicLookAt, tmpEye, tmpCenter, tmpUp, inverseLookAt);
		inverseLookAt.get(tmpMatrix);
		gl2.glMultMatrixf(tmpMatrix, 0);

		gl2.glScalef(placement.scale.x, placement.scale.y, placement.scale.z);

		drawModel(glAutoDrawable, model, store);
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameTransformation transformation, OpenGLModelStoreImpl store) {
		gl2.glPushMatrix();

		gl2.glMultMatrixf(transformation.getFloats(), 0);

		drawModel(glAutoDrawable, model, store);
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
	public void placeCamera(GLAutoDrawable glAutoDrawable, GameObjectPlacement cameraPlacement) {
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		cameraPlacement.calculateEye(cameraEye);
		cameraPlacement.calculateCenter(cameraCenter);
		cameraPlacement.calculateUp(cameraUp);

		glu.gluLookAt(
				cameraEye.x, cameraEye.y, cameraEye.z,
				cameraCenter.x, cameraCenter.y, cameraCenter.z,
				cameraUp.x, cameraUp.y, cameraUp.z);

		OpenGLErrorUtil.checkError(gl2, "cameraPlacement");

		cameraPlacement.calculateBasicLookAt(cameraMatrix);
		cameraMatrix.invert(inverseCameraMatrix);
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

	private void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store) {
		int stride = 3 + 3 + 2;
		float vx, vy, vz, nx, ny, nz, tx, ty;
		int offset;

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
		OpenGLErrorUtil.checkError(gl2, "draw");
	}

	@Override
	public void setBaseMatricesAndViewPort(GameSelectionLine selectionLine) {
		selectionLine.setBaseMatricesAndViewPort(inversePerspectiveMatrix, inverseCameraMatrix, width, height);
	}

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL2TriangleAdapter.class);
}
