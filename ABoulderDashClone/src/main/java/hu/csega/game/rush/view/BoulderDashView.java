package hu.csega.game.rush.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import hu.csega.toolshed.framework.ToolModel;
import hu.csega.toolshed.framework.ToolView;

public class BoulderDashView implements ToolView {

	private ToolModel model;

	@Override
	public ToolModel getModel() {
		return model;
	}

	@Override
	public void setModel(ToolModel model) {
		this.model = model;
	}

	@Override
	public void paint(Graphics2D g, Dimension size) {
		g.translate(size.width / 2, size.height / 2);
		g.drawLine(-10, 0, 10, 0);
		g.drawLine(0, -10, 0, 10);
		g.translate(-size.width / 2, -size.height / 2);
	}

}
