(*  A {*}-gal megjelolt sorok nem szabvanyos eljarast tartalmaznak.   *)
UNIT gpcgun; {Kirajzolashoz es egerkezeleshez hasznalhato egyseg + idozito eljaras}

INTERFACE

USES Dos;

TYPE tVscr = ARRAY[0..199,0..319]OF Char;
     tPal = ARRAY[0..255,0..2]OF Char;

VAR Vscr : ^tVscr; {Virtualis kepernyo}
    Pix : tVscr ABSOLUTE $A000:0; {*} {Az egyik ilyen tombot a $A000:0 cimre, azaz a video-memoria fole helyezik}
    {Ha ebbe a tombbe irunk, a kepernyo megvaltozik a 19. videomodban}
    Key, Status : Char;
    Palette : tPal;
    M_Button,M_CurX,M_CurY : Word; {Egerhez hasznalhato valtozok}
    Counter : LongInt; {Szamlalo az idozitohoz}
    h,min,s,s100 : Word;
    All : ARRAY[0..255,0..12,0..8]OF Char; {Karakterkeszlet}

{Grafikai utasitasok}
PROCEDURE SetMode(Gm : Word); {Videomo beallitasa}
PROCEDURE Init; {Video elokeszitese teljes felhasznalasra}
PROCEDURE Finish; {Video befejezese, visszaallitas az eredeti allapotba}
PROCEDURE GetPalette; {Paletta lekerdezese}
PROCEDURE SetPalette; {Paletta beallitasa}
PROCEDURE ClrVscr(Col : Char); {Virtualis kepernyo torlese}
PROCEDURE ClrGscr(Col : Char); {Valodi kepernyo torlese}
PROCEDURE SetScr; {Virtualis kep valossa tetele}
PROCEDURE WriteXY(X0,Y0 : Integer; Color0 : Char; Szov : String); {Szoveg kiiratasa}
{Idozites}
PROCEDURE SetCounter; {Idomeres kezdete}
PROCEDURE WaitFor(gp4 : LongInt); {Addig var, mig el nem telik a megadott ido}
{Egerkezeles}
PROCEDURE ReadMouse; {Eger- es gombpoziciok beolvasasa}
PROCEDURE SetPos; {Eger alapbeallitasa}

IMPLEMENTATION

PROCEDURE SetMode; ASSEMBLER; {*} {Gepikodu parancs}
 ASM                          {*}
  Mov ax, Gm                  {*} {Beolvassa az akkumlatorba a megadott videomod kodjat}
  Int $10                     {*} {�j video,odot hiv meg az akkumlator fuggvenyeben}
 END;                         {*}

PROCEDURE Init;
 VAR f0 : File;                 {*}
 BEGIN
  SetMode(19); {19. videomod: 320x200x256 szin}
  New(Vscr);
  GetPalette;
  Assign(f0,'chars.set');       {*} {A karakterkeszletet tarolo file}
  ReSet(f0,1);                  {*}
  BlockRead(f0,All,SizeOf(All));{*} {Beolvassa}
  Close(f0);                    {*}
 END;

PROCEDURE Finish;
 BEGIN
  SetMode(3); {3. videomod: noral karakteres kepernyo}
  Dispose(Vscr);
 END;

PROCEDURE GetPalette;
 VAR gp1,gp2 : Integer;
 BEGIN
  Port[$3C7] := 0; {*} {A paletta kiolvasasat a 0. szintol kezdje}
  For gp1 := 0 to 255 do
   For gp2 := 0 to 2 do
    Palette[gp1,gp2] := Chr(Port[$3C9]); {*} {Paletta RGB komponenseinek beolvasaysa portrol}
 END;

PROCEDURE SetPalette;
 VAR gp1,gp2 : Integer;
 BEGIN
  Port[$3C8] := 0; {*} {A palette atirasat a 0. szintol kezdje}
  For gp1 := 0 to 255 do
   For gp2 := 0 to 2 do
    Port[$3C9] := Ord(Palette[gp1,gp2]); {*} {A paletta szineinek RGB komponenseinek atirasa}
 END;

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
{Unit lenyege:}

{1. Keppont kirajzolasa : tomb elemenek valtoztatasa:}
{    Vscr^[y-koordinata(0..199),x-koordinata(0..319)] := szin}

{2. Kepernyore kirajzolas elott rengeteg mindent me glehet valositani, }
{    mert az addigi valtozasok csak a virtualis kepernyon tortentek. }
{     -> Folyamatos kep}

{3. Gyors egerkezeles}

{4. Idozites -> allando kep/masodperc -> nincs szaggatas vagy tul nagy }
{     sebesseg}

{5. Paletta kezelese gyors, hasznalataval szebb grafika valosithato meg}