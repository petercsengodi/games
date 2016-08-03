package hu.csega.skeleton.show;

import static hu.csega.skeleton.parts.SkeletonConstants.DIMENSIONS;
import static java.lang.Math.min;

import java.awt.Graphics;

import javax.swing.JPanel;

import hu.csega.skeleton.parts.Line;
import hu.csega.skeleton.parts.Point;
import hu.csega.skeleton.parts.UsedPart;
import hu.csega.skeleton.rendering.SkeletonRendering;

public class SkeletonCanvas extends JPanel {

	public SkeletonCanvas(UsedPart skeleton) {
		this.skeleton = skeleton;
	}

	@Override
	public void paint(Graphics g) {
		Point source = new Point();
		double[] angles = new double[DIMENSIONS];

		int widthPerTwo = this.getWidth() / 2;
		int heightPerTwo = this.getHeight() / 2;
		g.translate(widthPerTwo, heightPerTwo);

		drawSkeleton(g, skeleton, source, angles);

		g.translate(-widthPerTwo, -heightPerTwo);
	}

	private void drawSkeleton(Graphics g, UsedPart part, Point sourcePoint, double[] sourceAngles) {
		Point accumulatedPoint = SkeletonRendering.add(sourcePoint, SkeletonRendering.invert(part.bodyPart.sourceJoint));

		g.drawLine((int)accumulatedPoint.position[0] - 3, (int)accumulatedPoint.position[1], (int)accumulatedPoint.position[0] + 3, (int)accumulatedPoint.position[1]);
		g.drawLine((int)accumulatedPoint.position[0], (int)accumulatedPoint.position[1] - 3, (int)accumulatedPoint.position[0], (int)accumulatedPoint.position[1] + 3);

		// calculate current part's exact position

		double[] accumulatedAngles = new double[DIMENSIONS];

		for(int d = 0; d < DIMENSIONS; d++) {
			accumulatedAngles[d] = sourceAngles[d] + part.angles[d];
		}


		// draw lines

		Line[] lines = part.bodyPart.lines;
		for(int i = 0; i < lines.length; i++) {
			Line line = lines[i];
			Point start = SkeletonRendering.add(accumulatedPoint, SkeletonRendering.rotate(line.start, accumulatedAngles));
			Point end = SkeletonRendering.add(accumulatedPoint, SkeletonRendering.rotate(line.end, accumulatedAngles));
			g.drawLine((int)start.position[0], (int)start.position[1], (int)end.position[0], (int)end.position[1]);
		}


		if(part.bodyPart.targetJoint == null || part.targetParts == null) {
			return;
		}

		// draw sub parts on joints

		Point targetPoint2 = new Point();
		int numberOfTargets = min(part.bodyPart.targetJoint.length, part.targetParts.length);
		for(int i = 0; i < numberOfTargets; i++) {

			Point targetJoint = part.bodyPart.targetJoint[i];
			Point targetPoint1 = SkeletonRendering.rotate(targetJoint, accumulatedAngles);
			for(int d = 0; d < DIMENSIONS; d++) {
				targetPoint2.position[d] = sourcePoint.position[d] + targetPoint1.position[d];
			}

			drawSkeleton(g, part.targetParts[i], targetPoint2, accumulatedAngles);

		}
	}

	private UsedPart skeleton;

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
