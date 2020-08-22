package hu.csega.pixel.editor.p32x32;

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

public class P32x32Canvas extends JPanel implements MouseListener, MouseMotionListener {

	public P32x32Canvas(P32x32Editor pixelEditor) {
		this.pixelEditor = pixelEditor;
		setPreferredSize(new Dimension(getCanvasWidth(), getCanvasHeight()));
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		PixelSheet currentSheet = pixelEditor.getCurrentSheet();

		for(int x = 0; x < pixelEditor.getSpWidth(); x++) {
			for(int y = 0; y < pixelEditor.getSpHeigth(); y++) {
				Pixel pixel = currentSheet.pixels[x][y];
				drawPixel(g, x, y, pixel);
			}
		}

		// Draw the available colors
		for(int cr = 0; cr < Palette.TONES; cr++) {
			int startX = (cr % 3) * Palette.TONES;
			int startY = (cr / 3) * Palette.TONES;

			for(int cg = 0; cg < Palette.TONES; cg++) {
				for(int cb = 0; cb < Palette.TONES; cb++) {
					int index = Palette.pixelIndexOf(cr, cg, cb);
					Pixel p = Palette.PIXELS[index];
					drawPixel(g, pixelEditor.getSpWidth() + startX + cb, startY + cg, p);
				}
			}
		}

		// Draw the grey colors
		int startX = 0;
		int startY = 3 * Palette.TONES;
		for(int cx = 0; cx < Palette.TONES; cx++) {
			int tone = Palette.INT_TO_COLOR_TONE[cx];
			Pixel p = Palette.get(tone, tone, tone, 255);
			drawPixel(g, pixelEditor.getSpWidth() + startX + cx, startY, p);
		}

		// Draw the transparent colors
		startY += 1;
		for(int cx = 0; cx < BACKGROUND_COLORS.length; cx++) {
			Color color = BACKGROUND_COLORS[cx];
			drawBackgroundPixel(g, pixelEditor.getSpWidth() + startX + cx, startY, color);
		}


		// Draw the image with the pixel size of eight
		startX = (3 * Palette.TONES + pixelEditor.getSpWidth()) * PIXEL_WIDTH + 10;
		startY = 10;
		for(int cx = 0; cx < pixelEditor.getSpWidth(); cx++) {
			for(int cy = 0; cy < pixelEditor.getSpHeigth(); cy++) {
				Pixel pixel = currentSheet.pixels[cx][cy];
				drawPixelSize(g, startX, startY, cx, cy, pixel, 8);
			}
		}

		// Draw the image with the pixel size of four
		startY += pixelEditor.getSpHeigth() * 8 + 10;
		for(int cx = 0; cx < pixelEditor.getSpWidth(); cx++) {
			for(int cy = 0; cy < pixelEditor.getSpHeigth(); cy++) {
				Pixel pixel = currentSheet.pixels[cx][cy];
				drawPixelSize(g, startX, startY, cx, cy, pixel, 4);
			}
		}

		// Draw the image with the pixel size of two
		startX += pixelEditor.getSpWidth() * 4 + 10;
		for(int cx = 0; cx < pixelEditor.getSpWidth(); cx++) {
			for(int cy = 0; cy < pixelEditor.getSpHeigth(); cy++) {
				Pixel pixel = currentSheet.pixels[cx][cy];
				drawPixelSize(g, startX, startY, cx, cy, pixel, 2);
			}
		}

		// Draw the image with the pixel size of one
		startX += pixelEditor.getSpWidth() * 2 + 10;
		for(int cx = 0; cx < pixelEditor.getSpWidth(); cx++) {
			for(int cy = 0; cy < pixelEditor.getSpHeigth(); cy++) {
				Pixel pixel = currentSheet.pixels[cx][cy];
				drawPixelSize(g, startX, startY, cx, cy, pixel, 1);
			}
		}

	}

	private void drawPixel(Graphics g, int x, int y, Pixel pixel) {
		if(pixel.alpha == 0) {
			g.setColor(backgroundColor == Color.BLACK ? Color.DARK_GRAY : Color.black);
			g.fillRect(x * PIXEL_WIDTH, y * PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
			g.setColor(backgroundColor);
			g.fillRect(x * PIXEL_WIDTH + 1, y * PIXEL_HEIGHT + 1, PIXEL_WIDTH - 2, PIXEL_HEIGHT - 2);
			//			g.setColor(Color.gray);
			//			g.drawRect(x * PIXEL_WIDTH + 2, y * PIXEL_HEIGHT + 2, PIXEL_WIDTH - 4, PIXEL_HEIGHT - 4);
		} else {
			g.setColor(pixel.convertToColor());
			g.fillRect(x * PIXEL_WIDTH, y * PIXEL_HEIGHT, PIXEL_WIDTH, PIXEL_HEIGHT);
		}
	}

	private void drawBackgroundPixel(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x * PIXEL_WIDTH + 1, y * PIXEL_HEIGHT + 1, PIXEL_WIDTH - 2, PIXEL_HEIGHT - 2);
	}

	private void drawPixelSize(Graphics g, int startX, int startY, int x, int y, Pixel pixel, int size) {
		int rx = startX + x * size;
		int ry = startY + y * size;
		int sx = size;
		int sy = size;

		if(pixel.alpha == 0) {
			g.setColor(backgroundColor);
		} else {
			g.setColor(pixel.convertToColor());
		}

		g.fillRect(rx, ry, sx, sy);
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

		int px = x / PIXEL_WIDTH;
		int py = y / PIXEL_HEIGHT;

		pixelPressed(arg0, px, py);

		repaint();
	}

	private void pixelPressed(MouseEvent arg0, int px, int py) {
		int spw = pixelEditor.getSpWidth();
		int sph = pixelEditor.getSpHeigth();

		if(px >= 0 && py >= 0 && px < spw && py < sph) {
			PixelSheet sheet = pixelEditor.getCurrentSheet();

			if(arg0.getButton() == MouseEvent.BUTTON1) {
				pixel.copyValuesInto(sheet.pixels[px][py]);
				pixelEditor.setUsedForCurrentSheet();
				leftButtonPressed = true;
			} else {
				sheet.pixels[px][py].copyValuesInto(pixel);
			}

			return;
		}

		if(px >= spw && px < spw + 3 * Palette.TONES && py >= 0 && py < 3 * Palette.TONES) {

			// Check the available colors
			for(int cr = 0; cr < Palette.TONES; cr++) {
				int startX = (cr % 3) * Palette.TONES;
				int startY = (cr / 3) * Palette.TONES;

				for(int cg = 0; cg < Palette.TONES; cg++) {
					for(int cb = 0; cb < Palette.TONES; cb++) {
						if(px == spw + startX + cb && py == startY + cg) {
							int index = Palette.pixelIndexOf(cr, cg, cb);
							Pixel p = Palette.PIXELS[index];
							p.copyValuesInto(pixel);
							return;
						}
					}
				}
			}
		}

		// Check the grey colors
		int startX = 0;
		int startY = 3 * Palette.TONES;
		for(int cx = 0; cx < Palette.TONES; cx++) {
			int tone = Palette.INT_TO_COLOR_TONE[cx];
			if(px == spw + startX + cx && py == startY) {
				Pixel p = Palette.get(tone, tone, tone, 255);
				p.copyValuesInto(pixel);
				return;
			}
		}

		// Check the transparent colors
		startY += 1;
		for(int cx = 0; cx < BACKGROUND_COLORS.length; cx++) {
			Color color = BACKGROUND_COLORS[cx];
			if(px == spw + startX + cx && py == startY) {
				backgroundColor = color;
				Palette.PIXELS[Palette.INDEX_OF_TRANSPARENT].copyValuesInto(pixel);
				return;
			}
		}
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

			int px = x / PIXEL_WIDTH;
			int py = y / PIXEL_HEIGHT;
			if(px >= 0 && py >= 0 && px < pixelEditor.getSpWidth() && py < pixelEditor.getSpHeigth()) {
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

			int px = x / PIXEL_WIDTH;
			int py = y / PIXEL_HEIGHT;
			if(px >= 0 && py >= 0 && px < pixelEditor.getSpWidth() && py < pixelEditor.getSpHeigth()) {
				PixelSheet sheet = pixelEditor.getCurrentSheet();
				pixel.copyValuesInto(sheet.pixels[px][py]);
				pixelEditor.setUsedForCurrentSheet();
			}

			repaint();
		}
	}

	private int getCanvasWidth () {
		return PIXEL_WIDTH * pixelEditor.getSpWidth() + Palette.TONES * 3 * PIXEL_WIDTH +
				pixelEditor.getSpWidth() * 8 + 20;
	}

	private int getCanvasHeight() {
		return Math.max(PIXEL_HEIGHT * pixelEditor.getSpHeigth(), PIXEL_HEIGHT * (Palette.TONES * 3 + 2));
	}

	private boolean leftButtonPressed;
	private Pixel pixel = new Pixel();
	private P32x32Editor pixelEditor;
	private Color backgroundColor = Color.magenta;

	private static final Color[] BACKGROUND_COLORS = new Color[] {
			Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.BLACK,
			Color.MAGENTA, Color.CYAN, Color.BLUE, Color.GREEN, Color.RED
	};

	public static final int PIXEL_WIDTH = 15;
	public static final int PIXEL_HEIGHT = 15;

	private static final long serialVersionUID = 1L;
}
