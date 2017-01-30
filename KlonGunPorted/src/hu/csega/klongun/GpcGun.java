package hu.csega.klongun;

import java.io.File;

import hu.csega.klongun.imported.FileUtil;
import hu.csega.klongun.screen.CustomCharset;
import hu.csega.klongun.screen.TPal;
import hu.csega.klongun.screen.TVscr;

public class GpcGun {

	public TVscr vscr = new TVscr();
	public TPal palette = new TPal();
	public TPal originalPalette = new TPal();
	public CustomCharset charset = new CustomCharset();

    public char key;
    public char status;

    public int mButton, mCurX, mCurY;
    public long counter;
    public int h, min, s, s100;

    public void init(){
    	fillPalette();
    	fillCharset();
    }

	public void finish(){
    	// nothing to do really in java
    }

    public void clrVscr(int col){
    	for(int i = 0; i < vscr.content.length; i++)
    		vscr.content[i] = col;
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
        					vscr.set(x0 + (i-1)*9 + k, y0 + j, color0);
        			}
        		}
    		}
    	}
    } // end of writeXY


    public void setCounter(){
    	counter = System.currentTimeMillis() / 10;
    }

    private void fillCharset() {
		String root = FileUtil.workspaceRootOrTmp();
		String charsetFileName = root + File.separator + "KlonGunPorted" + File.separator +
				"res" + File.separator + "other" + File.separator + "charset.dat";
		byte[] charsetBytes = FileUtil.readAllBytes(charsetFileName);

		int cursor = 0;
		for(int c = 0; c < CustomCharset.CHARACTERS; c++) {
			for(int y = 0; y < CustomCharset.HEIGHT; y++) {
				for(int x = 0; x < CustomCharset.WIDTH; x++) {
					charset.content[c][y][x] = charsetBytes[cursor++];
				}
			}
		}
	} // end of fillCharset

	private void fillPalette() {
		String root = FileUtil.workspaceRootOrTmp();
		String paletteFileName = root + File.separator + "KlonGunPorted" + File.separator +
				"res" + File.separator + "other" + File.separator + "palette.dat";
		byte[] paletteBytes = FileUtil.readAllBytes(paletteFileName);

		int cursor = 0;
		for(int c = 0; c < TPal.COLORS; c++) {
			for(int rgb = 0; rgb < TPal.COMPONENTS; rgb++) {
				originalPalette.set(c, rgb, paletteBytes[cursor++]);
			}
		}

		originalPalette.copyTo(palette);
	} // end of fillPalette

}
