package hu.csega.pixel.editor.original;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import hu.csega.pixel.Palette;
import hu.csega.pixel.Pixel;
import hu.csega.pixel.PixelSheet;

public class PixelCanvas extends JPanel implements MouseListener, MouseMotionListener {

	public PixelCanvas(PixelEditor pixelEditor) {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);

		this.pixelEditor = pixelEditor;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		PixelSheet currentSheet = pixelEditor.getCurrentSheet();

		for(int x = 0; x < PixelSheet.WIDTH; x++) {
			for(int y = 0; y < PixelSheet.HEIGHT; y++) {
				Pixel pixel = currentSheet.pixels[x][y];
				drawPixel(g, x, y, pixel);
			}
		}

		int colorsInOneColumn = Palette.NUMBER_OF_COLORS / 2;
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < colorsInOneColumn; y++) {
				Pixel pixel = Palette.PIXELS[x * colorsInOneColumn + y];
				drawPixel(g, PixelSheet.WIDTH + x, y, pixel);
			}
		}
	}

	private void drawPixel(Graphics g, int x, int y, Pixel pixel) {
		if(pixel.alpha == 0) {
			g.setColor(Color.magenta);
			g.fillRect(x * PIXEL_WIDTH + 1, y * PIXEL_HEIGHT + 1, PIXEL_WIDTH - 2, PIXEL_HEIGHT - 2);
//			g.setColor(Color.gray);
//			g.drawRect(x * PIXEL_WIDTH + 2, y * PIXEL_HEIGHT + 2, PIXEL_WIDTH - 4, PIXEL_HEIGHT - 4);
		} else {
			g.setColor(pixel.convertToColor());
			g.fillRect(x * PIXEL_WIDTH, y * PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
		}
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

		if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
			int px = x / PIXEL_WIDTH;
			int py = y / PIXEL_HEIGHT;
			PixelSheet sheet = pixelEditor.getCurrentSheet();

			if(arg0.getButton() == MouseEvent.BUTTON1) {
				pixel.copyValuesInto(sheet.pixels[px][py]);
				pixelEditor.setUsedForCurrentSheet();
				leftButtonPressed = true;
			} else {
				sheet.pixels[px][py].copyValuesInto(pixel);
			}
		}

		int colorsInOneColumn = Palette.NUMBER_OF_COLORS / 2;
		if(x >= WIDTH && x < WINDOW_WIDTH && y > 0 && y < colorsInOneColumn * PIXEL_HEIGHT) {
			int index = ((x - WIDTH) / PIXEL_WIDTH) * colorsInOneColumn + (y / PIXEL_HEIGHT);
			Palette.PIXELS[index].copyValuesInto(pixel);
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
			if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
				int px = x / PIXEL_WIDTH;
				int py = y / PIXEL_HEIGHT;
				PixelSheet sheet = pixelEditor.getCurrentSheet();
				pixel.copyValuesInto(sheet.pixels[px][py]);
				pixelEditor.setUsedForCurrentSheet();
			}

			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(leftButtonPressed) {
			int x = arg0.getX();
			int y = arg0.getY();
			if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
				int px = x / PIXEL_WIDTH;
				int py = y / PIXEL_HEIGHT;
				PixelSheet sheet = pixelEditor.getCurrentSheet();
				pixel.copyValuesInto(sheet.pixels[px][py]);
				pixelEditor.setUsedForCurrentSheet();
			}

			repaint();
		}
	}

	private boolean leftButtonPressed;
	private Pixel pixel = new Pixel();
	private PixelEditor pixelEditor;

	public static final int PIXEL_WIDTH = 10;
	public static final int PIXEL_HEIGHT = 10;
	public static final int WIDTH = PIXEL_WIDTH * PixelSheet.WIDTH;
	public static final int HEIGHT = PIXEL_WIDTH * PixelSheet.HEIGHT;

	public static final int WINDOW_WIDTH = WIDTH + 2 * PIXEL_WIDTH;
	public static final int WINDOW_HEIGHT = HEIGHT;

	private static final long serialVersionUID = 1L;
}
