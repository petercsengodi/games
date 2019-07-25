package hu.csega.editors.transformations.view;

import java.awt.Color;
import java.awt.Graphics2D;
import hu.csega.editors.common.EditorPoint;
import hu.csega.editors.transformations.model.TransformationTesterModel;
import hu.csega.editors.transformations.model.TransformationTesterVertex;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public abstract class TransformationTesterSideView extends TransformationTesterCanvas {

	public TransformationTesterSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void paint2d(Graphics2D g) {
		TransformationTesterModel model = getModel();

		{
			EditorPoint point, lastPoint = null;
			int count = 0;

			g.setColor(Color.darkGray);
			for(TransformationTesterVertex vertex : model.getVertices()) {
				point = transformVertexToPoint(screenTransformation(vertex));

				if(count % 6 != 0) {
					drawLine(g, lastPoint, point);
				}

				lastPoint = point;
				count++;
			}
		}

		TransformationTesterVertex clickStart = model.getClickStart();
		TransformationTesterVertex clickEnd = model.getClickEnd();

		g.setColor(Color.red);
		EditorPoint start = transformVertexToPoint(screenTransformation(clickStart));
		EditorPoint end = transformVertexToPoint(screenTransformation(clickEnd));
		drawLine(g, start, end);

		g.setColor(Color.cyan);
		GameObjectLocation camera = model.getCamera();
		GameObjectPosition eye = new GameObjectPosition();
		GameObjectPosition center = new GameObjectPosition();
		GameObjectPosition up = new GameObjectPosition();

		camera.calculateEye(eye);
		camera.calculateCenterDirection(center);
		camera.calculateUp(up);

		center.x = center.x * 40 + eye.x;
		center.y = center.y * 40 + eye.y;
		center.z = center.z * 40 + eye.z;

		up.x = up.x * 40 + eye.x;
		up.y = up.y * 40 + eye.y;
		up.z = up.z * 40 + eye.z;

		EditorPoint cameraPosition = transformVertexToPoint(screenTransformation(eye.x, eye.y, eye.z));
		drawCircle(g, cameraPosition, 15);
		drawCircle(g, cameraPosition, 10);

		EditorPoint cameraTarget = transformVertexToPoint(screenTransformation(center.x, center.y, center.z));
		drawCircle(g, cameraTarget, 5);
		drawLine(g, cameraPosition, cameraTarget);

		EditorPoint cameraUpTarget = transformVertexToPoint(screenTransformation(up.x, up.y, up.z));
		drawLine(g, cameraPosition, cameraUpTarget);
	}

	private void drawLine(Graphics2D g, EditorPoint end1, EditorPoint end2) {
		g.drawLine((int)end1.getX(), (int)end1.getY(), (int)end2.getX(), (int)end2.getY());
	}

	private void drawCircle(Graphics2D g, EditorPoint center, double radius) {
		int r2 = (int)(radius * 2.0);
		g.drawOval((int)(center.getX() - radius), (int)(center.getY() - radius), r2, r2);
	}

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.createLogger(TransformationTesterSideView.class);
}
