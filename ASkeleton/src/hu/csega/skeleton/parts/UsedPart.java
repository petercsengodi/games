package hu.csega.skeleton.parts;

import static hu.csega.skeleton.parts.SkeletonConstants.DIMENSIONS;

public class UsedPart {

	public UsedPart(BodyPart bodyPart, UsedPart... targetParts) {
		this.angles = new double[DIMENSIONS];
		this.mirrored = new boolean[DIMENSIONS];
		this.bodyPart = bodyPart;
		this.targetParts = targetParts;

		for(UsedPart part: targetParts)
			part.sourcePart = this;
	}

	public BodyPart bodyPart;
	public UsedPart sourcePart;
	public UsedPart[] targetParts;
	public double[] angles;
	public boolean[] mirrored;
}
