package hu.csega.editors.anm.model;

import java.util.List;

public class AnimationPart {

	private int meshIndex;
	private int textureIndex;
	private String name;
	private AnimationTransformation basicTransformation;
	private List<AnimationPartJoint> joints;

	public int getMeshIndex() {
		return meshIndex;
	}

	public void setMeshIndex(int meshIndex) {
		this.meshIndex = meshIndex;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
