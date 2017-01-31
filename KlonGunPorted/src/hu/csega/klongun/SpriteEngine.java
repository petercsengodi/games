package hu.csega.klongun;

import java.io.File;

import hu.csega.klongun.imported.FileUtil;
import hu.csega.klongun.screen.CustomCharset;
import hu.csega.klongun.screen.Palette;
import hu.csega.klongun.screen.VirtualScreen;

public class SpriteEngine {

    public void init(){
    	fillPalette();
    	fillCharset();
    }

    public Palette getPalette() {
    	return palette;
    }

    public VirtualScreen getBackBuffer() {
    	return backBuffer;
    }

    public void writeXY(int x0, int y0, int color0, String szov){
    	for(int i = 0; i < szov.length(); i++) {
    		int c = (int)szov.charAt(i);
    		if(c >= 256)
    			continue;

			int[][] js = charset.content[c];

    		for(int j = 0; j < CustomCharset.HEIGHT; j++) {
        		for(int k = 0; k < CustomCharset.WIDTH; k++) {
        			if(y0 + j >= 0 && y0 + j < 200 && x0 + (i-1)*9 + k >= 0 && x0 + (i-1)*9 + k < 320) {
        				if(js[j][k] == 1)
        					backBuffer.set(x0 + (i-1)*9 + k, y0 + j, color0);
        			}
        		}
    		}
    	}
    } // end of writeXY

    private void fillCharset() {
		String root = FileUtil.workspaceRootOrTmp();
		String charsetFileName = root + File.separator + "KlonGunPorted" + File.separator +
				"res" + File.separator + "other" + File.separator + "charset.dat";
		byte[] charsetBytes = FileUtil.readAllBytes(charsetFileName);
		charset = new CustomCharset(charsetBytes);
	} // end of fillCharset

	private void fillPalette() {
		String root = FileUtil.workspaceRootOrTmp();
		String paletteFileName = root + File.separator + "KlonGunPorted" + File.separator +
				"res" + File.separator + "other" + File.separator + "palette.dat";
		byte[] paletteBytes = FileUtil.readAllBytes(paletteFileName);
		originalPalette = new Palette(paletteBytes);
		originalPalette.copyTo(palette);
	} // end of fillPalette

	protected char key;
	protected char status;

	protected int mButton, mCurX, mCurY;
	protected long counter;
	protected int h, min, s, s100;

	protected VirtualScreen backBuffer = new VirtualScreen();
	protected Palette palette = new Palette();
	protected Palette originalPalette = new Palette();
	protected CustomCharset charset = null;
}
