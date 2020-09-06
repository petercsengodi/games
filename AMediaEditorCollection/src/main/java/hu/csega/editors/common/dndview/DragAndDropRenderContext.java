package hu.csega.editors.common.dndview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DragAndDropRenderContext {

	private Graphics2D graphics;
	private boolean selected;

	protected DragAndDropRenderContext() {
		// ...
	}

	public boolean isSelected() {
		return selected;
	}

	protected void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	protected void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public void drawImage(String imageFilename, double x, double y, double width, double height) {
		BufferedImage image = DragAndDropImageLibrary.resolve(imageFilename);
		graphics.drawImage(image, 10, 10, 64, 64, 0, 0, image.getWidth(), image.getHeight(), null);
	}

	public void drawLine(Color color, double x1, double y1, double x2, double y2) {
		graphics.setColor(color);
		graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
}
