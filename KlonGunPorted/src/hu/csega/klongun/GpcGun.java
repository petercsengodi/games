package hu.csega.klongun;

import java.io.File;

import hu.csega.klongun.imported.FileUtil;
import hu.csega.klongun.screen.CustomCharset;
import hu.csega.klongun.screen.TPal;
import hu.csega.klongun.screen.TVscr;

public class GpcGun {

	public TVscr vscr = new TVscr();
	public TVscr pix = vscr;
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
    	// thing to do really in java
    }

    public void getPalette(){

    }

    public void setPalette(){

    }

    public void clrVscr(int col){

    }

    public void clrGscr(int col){

    }

    public void setScr(){

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
        					vscr.set(y0 + j, x0 + (i-1)*9 + k, color0);
        			}
        		}
    		}
    	}
    } // end of writeXY


    public void setCounter(){
    	counter = System.currentTimeMillis() / 10;
    }


    public void readMouse(){

    }

    public void setPos(){

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

/*


PROCEDURE ClrVscr;
 BEGIN
  FillChar(Vscr^,64000,Col); {*} {Feltolti a virtualis kepernyot 0-s karakterekkkel}
 END;

PROCEDURE ClrGscr;
 BEGIN
  FillChar(Pix,64000,Col); {*} {Feltolti a video-memoriat 0-s karakterekkel}
 END;

PROCEDURE SetScr;
 BEGIN
  Move(Vscr^,Pix,64000); {*} {Atelyezi a virtualis kepernyorol az egesz memoria-adatot a kepernyo-memoria fole}
 END;


PROCEDURE ReadMouse; ASSEMBLER;
 ASM
  Mov ax, 3          {*} {3-as egerparancs}
  Int $33            {*} {Egerhez tartozo megszakitas}
  Mov M_CurX, Cx     {*} {M_CurX -be (eger X pozicioja) * 2}
  Mov M_CurY, Dx     {*} {M_CurY -ba eger Y pozicioja}
  Mov M_Button, Bx   {*} {M_Button -ba egergomb allapota}
 END;

PROCEDURE SetPos; ASSEMBLER;
 ASM
  Mov ax, 4          {*} {4-as egerparancs : Kurzor beallitasa}
  Mov cx, 320        {*} {Pozicio}
  Mov dx, 100        {*} {kozepre allitasa}
  Int $33            {*} {Egerhez tartozo megszakitas}
 END;

PROCEDURE SetCounter;
 BEGIN
  GetTime(h,min,s,s100); {*} {Aktualis ido lekerdezese: h - ora, min - perc, s : masodperc, s100 : szazadmasodperc}
  Counter := ((h*60+min)*60+s)*100+s100; {Szamlalo beallitasa szazadmasodpercekben}
 END;

PROCEDURE WaitFor;
 VAR gp5 : LongInt;
 BEGIN
  Repeat
   GetTime(h,min,s,s100); {*} {Ido lekerdezese}
   gp5 := ((h*60+min)*60+s)*100+s100; {Szazadmasodpercekke iras}
   gp5 := gp5 - Counter;
   If gp5 < 0 then gp5 := gp5 + 24*360000; {Ejfelkori idovaltas miatt kell ez a sor}
  Until gp5 >= gp4;
 END;

END.

*/
