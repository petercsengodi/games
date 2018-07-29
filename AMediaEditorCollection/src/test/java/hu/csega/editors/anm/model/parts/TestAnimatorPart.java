package hu.csega.editors.anm.model.parts;

import org.json.JSONException;

public class TestAnimatorPart {

	public static void main(String[] args) throws JSONException {
		AnimatorPart root = new AnimatorPart();
		AnimatorJoint joint = new AnimatorJoint();
		root.joints.add(joint);

		String string = root.toJSONString();
		System.out.println(string);

		new AnimatorPart(string);
	}

}