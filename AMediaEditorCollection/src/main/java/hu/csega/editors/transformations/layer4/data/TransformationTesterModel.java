package hu.csega.editors.transformations.layer4.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class TransformationTesterModel implements Serializable {

	private double canvasXYTranslateX;
	private double canvasXYTranslateY;
	private double canvasXYZoom = 1.0;

	private double canvasXZTranslateX;
	private double canvasXZTranslateY;
	private double canvasXZZoom = 1.0;

	private double canvasZYTranslateX;
	private double canvasZYTranslateY;
	private double canvasZYZoom = 1.0;

	private double customViewTranslateX;
	private double customViewTranslateY;
	private double customViewZoom = 1.0;

	private double openGLTranslateX;
	private double openGLTranslateY;
	private double openGLAlpha;
	private double openGLBeta;
	private double openGLZoom = 1.0;

	private GameObjectPlacement camera = new GameObjectPlacement();

	private TransformationTesterVertex clickStart = new TransformationTesterVertex(0, 0, 0);
	private TransformationTesterVertex clickEnd = new TransformationTesterVertex(0, 0, 0);

	private List<TransformationTesterVertex> vertices;

	public TransformationTesterModel() {
		resetCamera();

		this.vertices = new ArrayList<>();
		double ZERO = 0.0;
		double ONE = 1.0 / 3.0;
		double TWO = 2.0 / 3.0;
		double THREE = 1.0;


		// 1. side: z = -100 // FRONT
		this.vertices.add(new TransformationTesterVertex(100, 100, -100, TWO, ONE));
		this.vertices.add(new TransformationTesterVertex(100, -100, -100, TWO, TWO));
		this.vertices.add(new TransformationTesterVertex(-100, 100, -100, ONE, ONE));

		this.vertices.add(new TransformationTesterVertex(100, -100, -100, TWO, TWO));
		this.vertices.add(new TransformationTesterVertex(-100, 100, -100, ONE, ONE));
		this.vertices.add(new TransformationTesterVertex(-100, -100, -100, ONE, TWO));

		// 2. side: z = 100 // BACK
		this.vertices.add(new TransformationTesterVertex(100, 100, 100, TWO, ZERO));
		this.vertices.add(new TransformationTesterVertex(100, -100, 100, TWO, ONE));
		this.vertices.add(new TransformationTesterVertex(-100, 100, 100, THREE, ZERO));

		this.vertices.add(new TransformationTesterVertex(100, -100, 100, TWO, ONE));
		this.vertices.add(new TransformationTesterVertex(-100, 100, 100, THREE, ZERO));
		this.vertices.add(new TransformationTesterVertex(-100, -100, 100, THREE, ONE));

		// 3. side: x = -100 // LEFT
		this.vertices.add(new TransformationTesterVertex(-100, 100, 100, ONE, ZERO));
		this.vertices.add(new TransformationTesterVertex(-100, 100, -100, TWO, ZERO));
		this.vertices.add(new TransformationTesterVertex(-100, -100, 100, ONE, ONE));

		this.vertices.add(new TransformationTesterVertex(-100, 100, -100, TWO, ZERO));
		this.vertices.add(new TransformationTesterVertex(-100, -100, 100, ONE, ONE));
		this.vertices.add(new TransformationTesterVertex(-100, -100, -100, TWO, ONE));

		// 4. side: x = 100 // RIGHT
		this.vertices.add(new TransformationTesterVertex(100, 100, 100, THREE, ONE));
		this.vertices.add(new TransformationTesterVertex(100, 100, -100, TWO, ONE));
		this.vertices.add(new TransformationTesterVertex(100, -100, 100, THREE, TWO));

		this.vertices.add(new TransformationTesterVertex(100, 100, -100, TWO, ONE));
		this.vertices.add(new TransformationTesterVertex(100, -100, 100, THREE, TWO));
		this.vertices.add(new TransformationTesterVertex(100, -100, -100, TWO, TWO));

		// 5. side: y = -100 // TOP
		this.vertices.add(new TransformationTesterVertex(100, -100, 100, ONE, ONE));
		this.vertices.add(new TransformationTesterVertex(100, -100, -100, ONE, ZERO));
		this.vertices.add(new TransformationTesterVertex(-100, -100, 100, ZERO, ONE));

		this.vertices.add(new TransformationTesterVertex(100, -100, -100, ONE, ZERO));
		this.vertices.add(new TransformationTesterVertex(-100, -100, 100, ZERO, ONE));
		this.vertices.add(new TransformationTesterVertex(-100, -100, -100, ZERO, ZERO));

		// 6. side: y = 100 // BOTTOM
		this.vertices.add(new TransformationTesterVertex(100, 100, 100, ONE, ONE));
		this.vertices.add(new TransformationTesterVertex(100, 100, -100, ONE, TWO));
		this.vertices.add(new TransformationTesterVertex(-100, 100, 100, ZERO, ONE));

		this.vertices.add(new TransformationTesterVertex(100, 100, -100, ONE, TWO));
		this.vertices.add(new TransformationTesterVertex(-100, 100, 100, ZERO, ONE));
		this.vertices.add(new TransformationTesterVertex(-100, 100, -100, ZERO, TWO));
	}

	public List<TransformationTesterVertex> getVertices() {
		return vertices;
	}

	public void setClick(TransformationTesterVertex clickStart, TransformationTesterVertex clickEnd) {
		this.clickStart.copyFrom(clickStart);
		this.clickEnd.copyFrom(clickEnd);
	}

	public double getCanvasXYTranslateX() {
		return canvasXYTranslateX;
	}

	public void setCanvasXYTranslateX(double canvasXYTranslateX) {
		this.canvasXYTranslateX = canvasXYTranslateX;
	}

	public void addCanvasXYTranslateX(double canvasXYTranslateX) {
		this.canvasXYTranslateX += canvasXYTranslateX;
	}

	public double getCanvasXYTranslateY() {
		return canvasXYTranslateY;
	}

	public void setCanvasXYTranslateY(double canvasXYTranslateY) {
		this.canvasXYTranslateY = canvasXYTranslateY;
	}

	public void addCanvasXYTranslateY(double canvasXYTranslateY) {
		this.canvasXYTranslateY += canvasXYTranslateY;
	}

	public double getCanvasXYZoom() {
		return canvasXYZoom;
	}

	public void setCanvasXYZoom(double canvasXYZoom) {
		this.canvasXYZoom = canvasXYZoom;
	}

	public void addCanvasXYZoom(double canvasXYZoom) {
		this.canvasXYZoom += canvasXYZoom;
	}

	public double getCanvasXZTranslateX() {
		return canvasXZTranslateX;
	}

	public void setCanvasXZTranslateX(double canvasXZTranslateX) {
		this.canvasXZTranslateX = canvasXZTranslateX;
	}

	public void addCanvasXZTranslateX(double canvasXZTranslateX) {
		this.canvasXZTranslateX += canvasXZTranslateX;
	}

	public double getCanvasXZTranslateY() {
		return canvasXZTranslateY;
	}

	public void setCanvasXZTranslateY(double canvasXZTranslateY) {
		this.canvasXZTranslateY = canvasXZTranslateY;
	}

	public void addCanvasXZTranslateY(double canvasXZTranslateY) {
		this.canvasXZTranslateY += canvasXZTranslateY;
	}

	public double getCanvasXZZoom() {
		return canvasXZZoom;
	}

	public void setCanvasXZZoom(double canvasXZZoom) {
		this.canvasXZZoom = canvasXZZoom;
	}

	public void addCanvasXZZoom(double canvasXZZoom) {
		this.canvasXZZoom += canvasXZZoom;
	}

	public double getCanvasZYTranslateX() {
		return canvasZYTranslateX;
	}

	public void setCanvasZYTranslateX(double canvasZYTranslateX) {
		this.canvasZYTranslateX = canvasZYTranslateX;
	}

	public void addCanvasZYTranslateX(double canvasZYTranslateX) {
		this.canvasZYTranslateX += canvasZYTranslateX;
	}

	public double getCanvasZYTranslateY() {
		return canvasZYTranslateY;
	}

	public void setCanvasZYTranslateY(double canvasZYTranslateY) {
		this.canvasZYTranslateY = canvasZYTranslateY;
	}

	public void addCanvasZYTranslateY(double canvasZYTranslateY) {
		this.canvasZYTranslateY += canvasZYTranslateY;
	}

	public double getCanvasZYZoom() {
		return canvasZYZoom;
	}

	public void setCanvasZYZoom(double canvasZYZoom) {
		this.canvasZYZoom = canvasZYZoom;
	}

	public void addCanvasZYZoom(double canvasZYZoom) {
		this.canvasZYZoom += canvasZYZoom;
	}

	public double getCustomViewTranslateX() {
		return customViewTranslateX;
	}

	public void setCustomViewTranslateX(double customViewTranslateX) {
		this.customViewTranslateX = customViewTranslateX;
	}

	public void addCustomViewTranslateX(double customViewTranslateX) {
		this.customViewTranslateX += customViewTranslateX;
	}

	public double getCustomViewTranslateY() {
		return customViewTranslateY;
	}

	public void setCustomViewTranslateY(double customViewTranslateY) {
		this.customViewTranslateY = customViewTranslateY;
	}

	public void addCustomViewTranslateY(double customViewTranslateY) {
		this.customViewTranslateY += customViewTranslateY;
	}

	public double getCustomViewZoom() {
		return customViewZoom;
	}

	public void setCustomViewZoom(double customViewZoom) {
		this.customViewZoom = customViewZoom;
	}

	public void addCustomViewZoom(double customViewZoom) {
		this.customViewZoom += customViewZoom;
	}

	public double getOpenGLTranslateX() {
		return openGLTranslateX;
	}

	public void setOpenGLTranslateX(double openGLTranslateX) {
		this.openGLTranslateX = openGLTranslateX;
	}

	public void addOpenGLTranslateX(double openGLTranslateX) {
		this.openGLTranslateX += openGLTranslateX;
	}

	public double getOpenGLTranslateY() {
		return openGLTranslateY;
	}

	public void setOpenGLTranslateY(double openGLTranslateY) {
		this.openGLTranslateY = openGLTranslateY;
	}

	public void addOpenGLTranslateY(double openGLTranslateY) {
		this.openGLTranslateY += openGLTranslateY;
	}

	public double getOpenGLAlpha() {
		return openGLAlpha;
	}

	public void setOpenGLAlpha(double openGLAlpha) {
		this.openGLAlpha = openGLAlpha;
	}

	public double getOpenGLBeta() {
		return openGLBeta;
	}

	public void setOpenGLBeta(double openGLBeta) {
		this.openGLBeta = openGLBeta;
	}

	public double getOpenGLZoom() {
		return openGLZoom;
	}

	public void setOpenGLZoom(double openGLZoom) {
		this.openGLZoom = openGLZoom;
	}

	public void addOpenGLZoom(double openGLZoom) {
		this.openGLZoom += openGLZoom;
	}

	public TransformationTesterVertex getClickStart() {
		return clickStart;
	}

	public void setClickStart(TransformationTesterVertex clickStart) {
		this.clickStart = clickStart;
		logger.info("SET LINE START: (" + clickStart.getPX() + "; " + clickStart.getPY() + "; " + clickEnd.getPZ() + ')');
	}

	public TransformationTesterVertex getClickEnd() {
		return clickEnd;
	}

	public void setClickEnd(TransformationTesterVertex clickEnd) {
		this.clickEnd = clickEnd;
		logger.info("SET LINE END: (" + clickEnd.getPX() + "; " + clickEnd.getPY() + "; " + clickEnd.getPZ() + ')');
	}

	public GameObjectPlacement getCamera() {
		return camera;
	}

	public void resetCamera() {
		camera.position.set(0f,  0f,  0f);
		camera.target.set(0f,  0f,  1f);
		camera.up.set(0f,  -1f,  0f);
		camera.scale.set(1f,  1f,  1f);
	}

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.createLogger(TransformationTesterModel.class);
}
