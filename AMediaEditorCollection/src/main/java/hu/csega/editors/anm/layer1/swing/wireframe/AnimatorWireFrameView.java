package hu.csega.editors.anm.layer1.swing.wireframe;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

import javax.swing.JPanel;

import hu.csega.editors.anm.components.ComponentWireFrameRenderer;
import hu.csega.editors.anm.components.ComponentWireFrameTransformer;
import hu.csega.games.units.UnitStore;

public class AnimatorWireFrameView extends JPanel implements ComponentWireFrameRenderer {

	private ComponentWireFrameTransformer source;
	private AnimatorWireFrame wireFrame;

	public AnimatorWireFrameView() {
		this.source = UnitStore.instance(ComponentWireFrameTransformer.class);
	}

	@Override
	public void paint(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();

		g.setColor(Color.darkGray);
		g.fillRect(0, 0, width, height);

		if(wireFrame == null) {
			wireFrame = source.transform(null);
		}

		if(wireFrame != null) {
			Collection<AnimatorWireFrameLine> lines = wireFrame.getLines();
			if(lines != null) {
				g.translate(width / 2, height / 2);
				for(AnimatorWireFrameLine line : lines) {
					AnimatorWireFramePoint source = line.getSource();
					int x1 = (int)source.getX();
					int y1 = (int)source.getY();

					AnimatorWireFramePoint destination = line.getDestination();
					int x2 = (int)destination.getX();
					int y2 = (int)destination.getY();

					g.setColor(line.getColor());
					g.drawLine(x1, y1, x2, y2);

					g.setColor(source.getColor());
					g.drawRect(x1 - 2, y1 - 2, 5, 5);

					g.setColor(destination.getColor());
					g.drawRect(x2 - 2, y2 - 2, 5, 5);
				}
				g.translate(-width / 2, -height / 2);
			}
		}
	}

	@Override
	public Void provide() {
		return null;
	}

	@Override
	public void accept(AnimatorWireFrame wireFrame) {
		this.wireFrame = wireFrame;
		this.repaint();
	}

	private static final long serialVersionUID = 1L;
}
