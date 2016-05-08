package hu.csega.alpoc.frame;

import hu.csega.alpoc.terrain.ScreenConversion;
import hu.csega.alpoc.terrain.TerrainEquation;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AlpocTerrainCanvas extends Canvas {

	public static final Dimension PREFERRED_SIZE = new Dimension(800, 600);
	private static final long serialVersionUID = 1L;


	public AlpocTerrainCanvas() {
		setPreferredSize(PREFERRED_SIZE);
		prepareSlides();
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		long start = System.currentTimeMillis();

		// clear before draw terrain
		System.arraycopy(background, 0, terrain, 0, allSize);

		paintTerrain();

//        WritableRaster raster = (WritableRaster) buffer.getData();
//        raster.setPixels(0,0,PREFERRED_SIZE.width, PREFERRED_SIZE.height,terrain);

		buffer.setRGB(0, 0, PREFERRED_SIZE.width, PREFERRED_SIZE.height, terrain, 0, PREFERRED_SIZE.width);

		g.drawImage(buffer, 0, 0, null);

		System.out.println(System.currentTimeMillis() - start);
	}

	private void paintTerrain() {
		int lastY=0, y, c;
		boolean first;
		int startSY = PREFERRED_SIZE.height - 1;
		int endSY = PREFERRED_SIZE.height / 2 + 1;
		ScreenConversion SC = ScreenConversion.SC;

		// TODO: remove from here
		ScreenConversion.C.alfa += 0.1;
		ScreenConversion.C.recalculateMatrix();

		for(SC.sx = 0; SC.sx < PREFERRED_SIZE.width; SC.sx++){
			first = true;

			for(SC.sy = startSY; SC.sy > endSY; SC.sy--){
				SC.calculateSee();
				SC.ty = TerrainEquation.y(SC.seeposx, SC.seeposz);
				SC.calculateResult();

				c = TerrainEquation.colorFromY(SC.ty) | ALPHA;
				y = (int)Math.max(0, Math.min(PREFERRED_SIZE.height - 1, SC.screeny));

				if(first) {
					lastY = y;
					first = false;
				} else
				while(lastY > y) {
					lastY--;
					terrain[SC.sx+lastY*PREFERRED_SIZE.width] = c;
				}

				if(lastY <= 0)
					break;
			}
		}
	}

	public void prepareSlides() {
		allSize = PREFERRED_SIZE.width * PREFERRED_SIZE.height;
		background = new int[allSize];
		for(int i = 0; i < allSize; i++)
			background[i] = ALPHA | (255*GREEN + 255*BLUE);

		terrain = new int[allSize];
	}

	private int allSize;
	private BufferedImage buffer = new BufferedImage(PREFERRED_SIZE.width, PREFERRED_SIZE.height, BufferedImage.TYPE_INT_ARGB);
	private int[] background;
	private int[] terrain;
	private int[] controlBuffer; // if you click, what is selected?

	private static int BLUE = 1;
	private static int GREEN = 256;
	private static int RED = 256*256;

	private static int ALPHA = ~0 ^ (255*RED + 255*BLUE + 255*GREEN);
}
