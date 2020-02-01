package hu.csega.editors.anm.components.stubs;

import java.awt.Color;
import java.util.Arrays;

import hu.csega.editors.anm.components.ComponentWireFrameConverter;
import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.anm.swing.AnimatorWireFrame;
import hu.csega.editors.anm.swing.AnimatorWireFrameLine;
import hu.csega.editors.anm.swing.AnimatorWireFramePoint;

public class StubWireFrameConverter implements ComponentWireFrameConverter {

	@Override
	public AnimatorWireFrame transform(AnimatorModel model) {
		AnimatorWireFramePoint source = new AnimatorWireFramePoint();
		source.setColor(Color.gray);
		source.setX(-100);
		source.setY(-100);
		source.setZ(-100);

		AnimatorWireFramePoint destination = new AnimatorWireFramePoint();
		destination.setColor(Color.gray);
		destination.setX(100);
		destination.setY(100);
		destination.setZ(100);

		AnimatorWireFrameLine line = new AnimatorWireFrameLine();
		line.setColor(Color.gray);
		line.setSource(source);
		line.setDestination(destination);

		AnimatorWireFrame wireFrame = new AnimatorWireFrame();
		wireFrame.setLines(Arrays.asList(line));
		return wireFrame;
	}

}
