package hu.csega.editors.transformations.view;

import java.awt.Color;
import java.awt.Graphics2D;
import hu.csega.editors.common.EditorPoint;
import hu.csega.editors.transformations.model.TransformationTesterModel;
import hu.csega.editors.transformations.model.TransformationTesterVertex;
import hu.csega.games.engine.GameEngineFacade;

public abstract class TransformationTesterSideView extends TransformationTesterCanvas {

	public TransformationTesterSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void paint2d(Graphics2D g) {
		int widthDiv2 = lastSize.width / 2;
		int heightDiv2 = lastSize.height / 2;
		g.translate(widthDiv2, heightDiv2);

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

		EditorPoint start = transformVertexToPoint(screenTransformation(clickStart));
		EditorPoint end = transformVertexToPoint(screenTransformation(clickEnd));
		drawLine(g, start, end);

		g.translate(-widthDiv2, -heightDiv2);
	}

	private void drawLine(Graphics2D g, EditorPoint end1, EditorPoint end2) {
		g.drawLine((int)end1.getX(), (int)end1.getY(), (int)end2.getX(), (int)end2.getY());
	}

	private static final long serialVersionUID = 1L;
}
