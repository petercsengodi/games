package hu.csega.editors.anm.components.stubs;

import java.awt.Color;
import java.util.Arrays;

import hu.csega.editors.anm.components.ComponentWireFrameTransformer;
import hu.csega.editors.anm.layer1.swing.wireframe.AnimatorWireFrame;
import hu.csega.editors.anm.layer1.swing.wireframe.AnimatorWireFrameLine;
import hu.csega.editors.anm.layer1.swing.wireframe.AnimatorWireFramePoint;

public class StubWireFrameTransformer implements ComponentWireFrameTransformer {

	@Override
	public AnimatorWireFrame transform(AnimatorWireFrame data) {
		AnimatorWireFramePoint source = new AnimatorWireFramePoint();
		source.setColor(Color.red);
		source.setX(-100);
		source.setY(-100);
		source.setZ(-100);

		AnimatorWireFramePoint destination = new AnimatorWireFramePoint();
		destination.setColor(Color.green);
		destination.setX(100);
		destination.setY(100);
		destination.setZ(100);

		AnimatorWireFrameLine line = new AnimatorWireFrameLine();
		line.setColor(Color.black);
		line.setSource(source);
		line.setDestination(destination);

		AnimatorWireFrame wireFrame = new AnimatorWireFrame();
		wireFrame.setLines(Arrays.asList(line));
		return wireFrame;
	}

}
