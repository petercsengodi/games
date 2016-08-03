package hu.csega.skeleton.body;

import java.util.HashMap;
import java.util.Map;

import hu.csega.skeleton.parts.BodyPart;
import hu.csega.skeleton.parts.Line;
import hu.csega.skeleton.parts.Point;
import hu.csega.skeleton.parts.UsedPart;

public class SkeletonBodyParts {

	public static UsedPart createNewSkeleton() {
		UsedPart head = new UsedPart(BODY_PARTS.get("head"));

		UsedPart leftLowerArm = new UsedPart(BODY_PARTS.get("lowerArm"));
		UsedPart rightLowerArm = new UsedPart(BODY_PARTS.get("lowerArm"));
		UsedPart leftLowerLeg = new UsedPart(BODY_PARTS.get("lowerLeg"));
		UsedPart rightLowerLeg = new UsedPart(BODY_PARTS.get("lowerLeg"));

		UsedPart leftUpperArm = new UsedPart(BODY_PARTS.get("upperArm"), leftLowerArm);
		UsedPart rightUpperArm = new UsedPart(BODY_PARTS.get("upperArm"), rightLowerArm);
		UsedPart leftUpperLeg = new UsedPart(BODY_PARTS.get("upperLeg"), leftLowerLeg);
		UsedPart rightUpperLeg = new UsedPart(BODY_PARTS.get("upperLeg"), rightLowerLeg);

		UsedPart body = new UsedPart(BODY_PARTS.get("body"), head, rightUpperArm, leftUpperArm, rightUpperLeg, leftUpperLeg);
		return body;
	}

	public static final Map<String, BodyPart> BODY_PARTS = new HashMap<>();

	static {

		BodyPart body = new BodyPart();
		body.sourceJoint = new Point(0, 0, 0);
		body.targetJoint = new Point[5];
		body.targetJoint[0] = new Point(0, -175, 0);
		body.targetJoint[1] = new Point(-60, -145, 0);
		body.targetJoint[2] = new Point(60, -145, 0);
		body.targetJoint[3] = new Point(-40, 160, 0);
		body.targetJoint[4] = new Point(40, 160, 0);
		body.lines = new Line[3];
		body.lines[0] = new Line(body.targetJoint[0], new Point(0, 175, 0));
		body.lines[1] = new Line(body.targetJoint[1], body.targetJoint[2]);
		body.lines[2] = new Line(body.targetJoint[3], body.targetJoint[4]);
		BODY_PARTS.put("body", body);

		BodyPart head = new BodyPart();
		head.sourceJoint = new Point(0, 50, 0);
		head.lines = new Line[4];
		head.lines[0] = new Line(new Point(-20, 50, 0), new Point(20, 50, 0));
		head.lines[1] = new Line(new Point(-20, -50, 0), new Point(20, -50, 0));
		head.lines[2] = new Line(new Point(-20, 50, 0), new Point(-20, -50, 0));
		head.lines[3] = new Line(new Point(20, 50, 0), new Point(20, -50, 0));
		BODY_PARTS.put("head", head);

		BodyPart upperArm = new BodyPart();
		upperArm.sourceJoint = new Point(0, -60, 0);
		upperArm.targetJoint = new Point[1];
		upperArm.targetJoint[0] = new Point(0, 60, 0);
		upperArm.lines = new Line[1];
		upperArm.lines[0] = new Line(new Point(0, -60, 0), new Point(0, 60, 0));
		BODY_PARTS.put("upperArm", upperArm);

		BodyPart lowerArm = new BodyPart();
		lowerArm.sourceJoint = new Point(0, -60, 0);
		lowerArm.targetJoint = new Point[1];
		lowerArm.targetJoint[0] = new Point(0, 60, 0);
		lowerArm.lines = new Line[1];
		lowerArm.lines[0] = new Line(new Point(0, -60, 0), new Point(0, 60, 0));
		BODY_PARTS.put("lowerArm", lowerArm);

		BodyPart upperLeg = new BodyPart();
		upperLeg.sourceJoint = new Point(0, -75, 0);
		upperLeg.targetJoint = new Point[1];
		upperLeg.targetJoint[0] = new Point(0, 75, 0);
		upperLeg.lines = new Line[1];
		upperLeg.lines[0] = new Line(new Point(0, -75, 0), new Point(0, 75, 0));
		BODY_PARTS.put("upperLeg", upperLeg);

		BodyPart lowerLeg = new BodyPart();
		lowerLeg.sourceJoint = new Point(0, -75, 0);
		lowerLeg.targetJoint = new Point[1];
		lowerLeg.targetJoint[0] = new Point(0, 75, 0);
		lowerLeg.lines = new Line[1];
		lowerLeg.lines[0] = new Line(new Point(0, -75, 0), new Point(0, 75, 0));
		BODY_PARTS.put("lowerLeg", lowerLeg);
	}
}
