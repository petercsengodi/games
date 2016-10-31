package hu.csega.cube.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import hu.csega.cube.editor.CubeEditor;
import hu.csega.cube.editor.model.CubePiece;
import hu.csega.cube.editor.model.CubeSheet;
import hu.csega.pixel.Pixel;

public class CubeCanvas extends JPanel implements MouseListener, MouseMotionListener {

	public CubeCanvas(CubeEditor cubeEditor) {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);

		this.cubeEditor = cubeEditor;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.translate(this.getWidth() / 2, this.getHeight() / 2);

		CubeSheet currentSheet = cubeEditor.getCurrentSheet();

		for(CubePiece piece : currentSheet.cubePieces) {
			drawCubePiece(g, piece);
		}

		g.translate(-this.getWidth() / 2, -this.getHeight() / 2);
	}

	private void drawCubePiece(Graphics g, CubePiece piece) {
		g.setColor(Color.gray);

		for(CubeEdge c : CubeEdge.values()) {
			drawCubeEdge(g,
					(piece.x + c.x1) * CUBE_SIZE_PER_2, (piece.y + c.y1) * CUBE_SIZE_PER_2, (piece.z + c.z1) * CUBE_SIZE_PER_2,
					(piece.x + c.x2) * CUBE_SIZE_PER_2, (piece.y + c.y2) * CUBE_SIZE_PER_2, (piece.z + c.z2) * CUBE_SIZE_PER_2);
		}
	}

	private void drawCubeEdge(Graphics g, int x11, int y11, int z11, int x21, int y21, int z21) {
		double x12 = x11 * Math.cos(verticalAngle) - y11 * Math.sin(verticalAngle);
		double y12 = x11 * Math.sin(verticalAngle) + y11 * Math.cos(verticalAngle);
		double z12 = z11;
		double x13 = x12 * Math.cos(horizontalAngle) - z12 * Math.sin(horizontalAngle);
		double y13 = y12;
		double z13 = x12 * Math.sin(horizontalAngle) + z12 * Math.cos(horizontalAngle);

		double x22 = x21 * Math.cos(verticalAngle) - y21 * Math.sin(verticalAngle);
		double y22 = x21 * Math.sin(verticalAngle) + y21 * Math.cos(verticalAngle);
		double z22 = z21;
		double x23 = x22 * Math.cos(horizontalAngle) - z22 * Math.sin(horizontalAngle);
		double y23 = y22;
		double z23 = x22 * Math.sin(horizontalAngle) + z22 * Math.cos(horizontalAngle);

		g.drawLine((int)x13, (int)y13, (int)x23, (int)y23);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();

		if(arg0.getButton() == MouseEvent.BUTTON1) {
			leftButtonPressed = true;

			lastX = x;
			lastY = y;
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
			leftButtonPressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(leftButtonPressed) {
			int x = arg0.getX();
			int y = arg0.getY();

			horizontalAngle += (x - lastX) * PIXEL_TO_ANGLE;
			verticalAngle += (y - lastY) * PIXEL_TO_ANGLE;

			lastX = x;
			lastY = y;

			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	private boolean leftButtonPressed;
	private Pixel pixel = new Pixel();
	private CubeEditor cubeEditor;

	private int lastX;
	private int lastY;

	private double horizontalAngle = 0;
	private double verticalAngle = 0;

	public static final int CUBE_SIZE_PER_2 = 100;

	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;

	public static final double PIXEL_TO_ANGLE = 0.1;

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
