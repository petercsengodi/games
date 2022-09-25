package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;
import java.util.List;

public class AnimationPart implements Serializable {

	private String name;
	private String mesh;
	private AnimationTransformation basicTransformation;
	private List<AnimationPartJoint> joints;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMesh() {
		return mesh;
	}

	public void setMesh(String mesh) {
		this.mesh = mesh;
	}

	public AnimationTransformation getBasicTransformation() {
		return basicTransformation;
	}

	public void setBasicTransformation(AnimationTransformation basicTransformation) {
		this.basicTransformation = basicTransformation;
	}

	public List<AnimationPartJoint> getJoints() {
		return joints;
	}

	public void setJoints(List<AnimationPartJoint> joints) {
		this.joints = joints;
	}

	private static final long serialVersionUID = 1L;

}
