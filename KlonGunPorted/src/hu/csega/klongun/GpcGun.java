package hu.csega.klongun;

import java.io.File;
import hu.csega.klongun.data.CustomCharset;
import hu.csega.klongun.data.TPal;
import hu.csega.klongun.data.TVscr;
import hu.csega.klongun.imported.FileUtil;

public class GpcGun {

	public TVscr vscr = new TVscr();
	public TVscr pix = vscr;
	public TPal palette = new TPal();
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

    public void clrVscr(char col){

    }

    public void clrGscr(char col){

    }

    public void setScr(){

    }

    public void writeXY(int x0, int y0, char color0, String szov){

    }


    public void setCounter(){

    }


    public void waitFor(long gp4){

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
				palette.content[c][rgb] = paletteBytes[cursor++];
			}
		}
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

PROCEDURE WriteXY; {A karakterek kis kepek, amiket a megfelelo szinnel kirajzol}
 VAR i0,j0,k0 : Integer;
 BEGIN
  For i0 := 1 to Ord(Szov[0]) do
   For j0 := 0 to 12 do
    For k0 := 0 to 8 do
     If (Y0+j0 >= 0) and (Y0+j0 < 200) and (X0+(i0-1)*9+k0 >= 0) and (X0+(i0-1)*9+k0 < 320) then
      If All[Ord(Szov[i0]),j0,k0] = #1 then
       Vscr^[Y0+j0,X0+(i0-1)*9+k0] := Color0;
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
