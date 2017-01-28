package hu.csega.klongun;

public class KlonGun {
	// Array sizes
	public static final int MaxShips = 1;
	public static final int MaxFires = 3;
	public static final int MaxStars = 20;
	public static final int MaxEnemy1 = 10;
	public static final int MaxEnemy2 = MaxEnemy1+4;
	public static final int MaxLesers = 12;
	public static final int MaxDeaths = 4;
	public static final int MaxItem = 7;

	// Object speeds
	public static final int Speed1 = 1;
	public static final int Speed2 = 2;
	public static final int Speed3 = 3;
	public static final int Speed_M = 3;
	public static final int Speed = 1;

	public static final int[] Speed_F = new int[] {7,4,2}; // ship turbins
	public static final int[] Speed_E = new int[] {3,1,5,0,7,3,3,0,15,0,7,3,2,0}; // enemies
	public static final int[] Life_E = new int[] {40,64,15,1250,50,10,15,80,20,5000,20,15,250,15}; // enemy HPs
	public static final int[] Dmg_L = new int[] {5,7,10,10,13,20,2,7,20,25,40,70}; // bullet power
	public static final int[] Late_L = new int[] {2,2,2,5,5,5,3,3,3,8,8,8}; // delay between shootings
	public static final int[] Speed_L = new int[] {8,8,8,15,15,15,6,6,6,20,20,20}; // bullet speeds


	// ship position(s)
	public static final int[] ShipX = new int[] {23};
	public static final int[] ShipY = new int[] {10};

	// map enemy -> PIC
	public static final int[] Abra = new int[] {1,2,3,4,5,6,7,8,9,10,6,5,5,6};

	// menu
    public static final int MaxMenu = 5;
    public static final String[] MenuSzov = new String[] {
    		"Let us play an other game!",
            "Difficulity",
    		"Credits are fun!",
    		"Greatest heroes ...",
            "I do not need those scores!",
            "I want to quit, man !!"
    };

    // credits
    public static final int MaxCredit = 5;
    public static final String[] CreditSzov = {
		"Well, this is embarassing.",
		"When I wrote this game,",
		"I thought this will be",
		"an awesome game.",
		"No, it's a beautiful memory."
     };

}

/*

TYPE pStars = ^tStars;
     tStars = RECORD {Csillagok recordja}
               Fajta : Char;
               X,Y : Integer;
               Next : pStars;
              END;

     pEnemies = ^tEnemies;
     tEnemies = RECORD {Ellens�gek recordja}
                 X, Y, Speed, YSpeed, Faj,
                 Life, Late, Item, Time : Integer;
                 Next : pEnemies;
                 Prev : pEnemies;
                END;

     pLess = ^tLess;
     tLess = RECORD
              X0, Y0, Speed, YSpeed,
              Faj,Side, Dmg : Integer;
              Next,Prev : pLess;
             END;

     PontRec = RECORD
                PontSzam : Integer;
                Nev : String;
               END;

VAR i,j,k,l,m,n,Xv,Yv : Integer; {Ciklus- �s egy�b v�toz�k} (**)
    Ships : ARRAY[1..MaxShips,0..29,0..49]OF Char;   {Haj�} {K�pek t�helyei}
    Fires : ARRAY[1..3,1..3,0..13,0..16]OF Char;     {L�ngcs�va}
    Enemy1 : ARRAY[1..MaxEnemy1,0..31,0..31]OF Char; {Ellens�gek}
    Leser : ARRAY[1..MaxLesers,0..4,0..15]OF Char;   {L�zerek}
    Death : ARRAY[1..MaxDeaths,0..15,0..15]OF Char;  {Hullad�k a robban�s ut�n}
    Status : ARRAY[0..9,0..119]OF Char;              {Energiam�ter}
    Stat2 : ARRAY[1..4,0..3,0..9,0..9]OF Char;       {Sz�mok 4 sz�nben}
    Items : ARRAY[1..MaxItem,0..15,0..15]OF Char;    {Felvehet� dolgok (stuff)}
    Stars : pStars;      {Csillagok}  {L�ncolt list�k az �llapotokhoz}
    Enemies : pEnemies;  {Ellens�gek}
    Less : pLess;        {L�v�sek}
    Boss : pEnemies; {Ezen a c�men tal�lhat� az aktu�lis f�ellens�g}
    s : String[2];
    Mozgas{p�lya poz�ci�ja}, Boss_Del{ha t�lmegy a p�ly�n, ennyit ugrik vissza} : Integer;
    pX,pY,pShip,pLife,pFire,pL : Integer; {J�t�kos adatai}
    pLogged : Boolean; {Ha False, s j�t�kost m�g nem lehet sz�tl�ni}
    pLeser : ARRAY[0..4]OF Char; {A fegyyverek �llapotai}
    pTime, ActualPalya{aktu�lis p�lya}, SzumLife{�ssz �let} : Integer;
    ch1, ch2 : Char; {Karakterek billenty�zet kezel�s�hez}
    Quit{Kil�p�s enged�se}, Cheat, Cheated{csal�s} : Boolean;
    Pontok : Integer;
    PontSzov, Ponts : String;
    AllPalette : tPal;
    Splash : Integer;
    {}
    fP : File Of PontRec;
    Pontszam : ARRAY[0..9]OF PontRec;
    ch,zh : Char;
    {}
    QuitAll : Boolean;
    Diff : Integer;
    MenuPont : Integer;
    Changed : Boolean;
    pLogTime : Integer;

FUNCTION Spaced (XX : String) : String;
 BEGIN
  For l := 1 to 5-Length(XX) do
   XX := ' ' + XX;
  Spaced := XX+'p';
 END;

PROCEDURE DrawPoints(No : Integer);
 BEGIN
  ClrVscr(#148);
  WriteXY(121,0,#0,'Hiscores');
  WriteXY(120,0,#4,'Hiscores');
  For i := 0 to 9 do
   With PontSzam[i] do
    If Pontszam <> 0 then
     If no <> i then
      Begin
       Str(Pontszam,Ponts);
       WriteXY(21,20+i*15,#0,Nev);
       WriteXY(221,20+i*15,#0,Spaced(Ponts));
       WriteXY(20,20+i*15,#2,Nev);
       WriteXY(220,20+i*15,#2,Spaced(Ponts));
      End Else Begin
       Str(Pontszam,Ponts);
       WriteXY(21,20+i*15,#0,Nev);
       WriteXY(221,20+i*15,#0,Spaced(Ponts));
       WriteXY(20,20+i*15,#1,Nev);
       WriteXY(220,20+i*15,#1,Spaced(Ponts));
      End;
 END;

PROCEDURE Anim3;
 VAR Back : ^tVscr;
     pos : Integer;
 BEGIN
  New(Back);
  Move(Vscr^,Back^,64000);
  {}
  pos := 0;
  Repeat
   ClrVscr(#0);
   pos := pos + 1;
   For i := 0 to 199 do
    For j := 0 to 319 do
     Begin
      k := (i-100)*pos+100;
      l := (j-160)*pos+160;
      If (k >= 0) and (l >= 0) and (k < 200) and (l < 320) then
       Vscr^[k,l] := Back^[i,j];
     End;
   SetScr;
  Until pos > 80;
  {}
  Dispose(Back);
 END;

{PROCEDURE Anim3;
 VAR Back : ^tVscr;
     pos : Real;
 BEGIN
  New(Back);
  Move(Vscr^,Back^,64000);
  pos := 1;
  Repeat
   ClrVscr(#0);
   pos := pos + 0.5;
   For i := 0 to 199 do
    For j := 0 to 319 do
     Begin
      k := Round((i-99.5)*pos+99.5);
      l := Round((j-159.5)*pos+159.5);
      If (k >= 0) and (l >= 0) and (k < 200) and (l < 320) then
       Vscr^[k,l] := Back^[i,j];
     End;
   SetScr;
  Until pos > 160;
  Dispose(Back);
 END;           }

PROCEDURE Anim(typ : Integer);
 VAR Back : ^tVscr;
     pos,i : Integer;
 BEGIN
  New(Back);
  Move(Vscr^,Back^,64000);
  If Typ = 0 then ClrVscr(#0) Else ClrVscr(#148);
  {}
  pos := 320;
  Repeat
   pos := pos - 4;
   For i := 0 to 199 do
    Begin
     If i mod 2 = 0 then
      begin
       Move(Back^[i,Pos],Vscr^[i],320-Pos);
      end else begin
       Move(Back^[i],Vscr^[i,Pos],320-Pos);
      end;
    End;
   SetScr;
  Until pos = 0;
  {}
  Dispose(Back);
 END;

PROCEDURE Anim2;

CONST PalS = 8{8};
      Speed = 2{5};
      Pocs = 48{63, !48 = S�t�tebb!};
      MaxM = 63{63};
      MaxI = 60{63};

VAR Quit : Boolean;
    i,j,k,l,m,n,o : Integer;
    y,z : Real;
    Back : ^tVscr;
    ff : File;
    Sinus : ARRAY[0..125]OF Integer;
    FGGVX : ARRAY[0..319]OF Integer;
    FGGVY : ARRAY[0..199]OF Integer;

FUNCTION F(x : Integer) : Integer;
 BEGIN
  F := Round(y*Sinus[Round(x+503) mod 126]);
 END;

FUNCTION F2(x : Integer) : Integer;
 BEGIN
  F2 := Round(z*Sinus[Round((x+m)*2+503) mod 126]);
 END;

FUNCTION Max(r1,r2 : Integer) : Integer;
 BEGIN
  If r1 > r2 then Max := Round(r1) Else Max := Round(r2);
 END;

BEGIN
 New(Back);
 {}
 For i := 0 to 125 do
  Sinus[i] := Round(sin(i/20)*100);
 {}
 i := 0;
 j := 1;
 m := 0;
 y := 0;
 z := 0;
 o := 0;
 {}
 Move(Vscr^,Back^,64000);
 {}
 REPEAT
  If i mod Speed = 0 then
   BEGIN
    y := i/380;
    z := i / 1500;
    For n := 0 to 319 do
     FGGVX[n] := n-F2(n-160){-o};
    For n := 0 to 199 do
     FGGVY[n] := n-F(n-100)-o;
    ClrVscr(#0);
    For k := 0 to 199 do
     For n := 0 to 319 do
      If (FGGVY[k] >= 0) and (FGGVY[k] < 200) and (FGGVX[n] >= 0) and (FGGVX[n] < 320) then
       Vscr^[k,n] := Back^[FGGVY[k],FGGVX[n]];
    SetScr;
   END;
  i := i + j;
  m := m + 1;
  o := o + 2;
  If m > MaxM then m := m - MaxM;
  If i < -Maxi then j := 1 Else If i > Maxi then j := -1;
 UNTIL o >= 200;
 {}
 Dispose(Back);
END;

PROCEDURE Screen(No : Integer);
 VAR i,j : Integer;
 BEGIN
  For i := 0 to 255 do
   For j := 0 to 2 do
    Palette[i,j] := Chr(Round(Ord(AllPalette[i,j])*No/63));
  SetPalette;
 END;

PROCEDURE Load; {File-ok bet�lt�se} {*}
 VAR i,j : Integer;     {BlockRead = file-b�l blokkot beolvas}
     s,t : String[10];  {Reset(f,##) : ## = 1 blokk m�rete}
     f : File;          {Str = sz�mot sz�vegg� alak�t}
                        {Seek = Fileban poz�ci�ra ugrik}
 BEGIN
  Assign(f,'palette.dat'); ReSet(f,768);
  BlockRead(f,Palette,1); Close(f);
  SetPalette;
  For i := 1 to MaxShips do Begin
   Str(i,s); Assign(f,'DATA\ship'+s+'.pic');
   ReSet(f,1); Seek(f,4); {Minden k�p a 4.Byte ut�n kezd�dik}
   BlockRead(f,Ships[i],SizeOf(Ships[i])); Close(f);
  End;
  For i := 1 to MaxItem do Begin
   Str(i,s); Assign(f,'DATA\i_'+s+'.pic');
   ReSet(f,1); Seek(f,4);
   BlockRead(f,Items[i],SizeOf(Items[i])); Close(f);
  End;
  For i := 1 to MaxDeaths do Begin
   Str(i,s); Assign(f,'DATA\ruin'+s+'.pic');
   ReSet(f,1); Seek(f,4);
   BlockRead(f,Death[i],SizeOf(Death[i])); Close(f);
  End;
  For i := 1 to MaxLesers do Begin
   Str(i,s); Assign(f,'DATA\l'+s+'.pic');
   ReSet(f,1); Seek(f,4);
   BlockRead(f,Leser[i],SizeOf(Leser[i])); Close(f);
  End;
  For i := 1 to MaxEnemy1 do Begin
   Str(i,s); Assign(f,'DATA\a'+s+'.pic');
   ReSet(f,1); Seek(f,4);
   BlockRead(f,Enemy1[i],SizeOf(Enemy1[i])); Close(f);
  End;
  Assign(f,'DATA\status1.pic');
  ReSet(f,1); Seek(f,4);
  BlockRead(f,Status,SizeOf(Status)); Close(f);
  For i := 1 to 4 do
   For j := 0 to 3 do Begin
    Str(i,s); Str(j,t);
    Assign(f,'DATA\'+s+t+'.pic');
    ReSet(f,1); Seek(f,4);
    BlockRead(f,Stat2[i,j],SizeOf(Stat2[i,j])); Close(f);
   End;
  For i := 1 to MaxFires do
   For j := 1 to 3 do Begin
    Str(i,s); Str(j,t);
    Assign(f,'DATA\s_fire'+s+t+'.pic');
    ReSet(f,1); Seek(f,4);
    BlockRead(f,Fires[i,j],SizeOf(Fires[i,j])); Close(f);
   End;
 END;

PROCEDURE AddStars(var xStars : pStars); {L�ncolt lista elk�sz�t�se}
 VAR StarPos : pStars;
     i,j : Integer;
 BEGIN
  xStars := nil;
  For i := 0 to 1 do
   For j := 0 to MaxStars do
    Begin
     New(StarPos);
     StarPos^.Next := xStars;
     xStars := StarPos;
     With StarPos^ do
      Begin {Felt�lt�s}
       Fajta := Chr(i); {Igy csak 1 Byte-ot foglal}
       X := Random(340);{Kezdo koordinata beallitasa}
       Y := Random(200);{Xnek 0 es 339, Ynak 0 es 199 koyott kell lennie}
      End;
    End;
 END;

PROCEDURE DoStars(var xStars : pStars); {Csillagok mozg�sa}
 VAR StarPos : pStars;
 BEGIN
  StarPos := xStars;
  WHILE StarPos <> nil DO
   BEGIN
    With StarPos^ do
     Begin
      X := X - (Ord(Fajta) +1)*Speed1;
      If X < -10 then
       Begin {Uj csillag k�sz�t�se}
        X := 320+Random(20);
        Y := Random(200);
       End;
      If (X >= 0) and (X < 320) then Begin {Kirajzol�s}
       If Fajta = #0 then Vscr^[Y,X] := #148 Else Vscr^[Y,X] := #7; End;
     End;
    StarPos := StarPos^.Next;
   END;
 END;

PROCEDURE RemoveStars(var StarPos : pStars);{Kitorli a csillagok lancolt listajat}
 BEGIN
  If StarPos^.Next <> nil then RemoveStars(StarPos^.Next);
  Dispose(StarPos);
 END;

PROCEDURE InitLeser(var xLess : pLess; X1,Y1,Sp0,Yp0,Fj0,Side0 : Integer);
 VAR LessPos : pLess; {Uj lezer = Uj elem a lancolt listaban}
 BEGIN
  If xLess = nil then {Uj elem felvetele}
   Begin
    New(xLess);
    xLess^.Next := nil;
    xLess^.Prev := nil;
   End
  Else
   Begin
    New(LessPos);
    LessPos^.Next := xLess;
    LessPos^.Prev := xLess^.Prev;
    xLess^.Prev := LessPos;
    xLess := LessPos;
   End;
  With xLess^ do {Az uj elemmel dolgozik}
   Begin
    If Fj0 = 0 then
     Begin {Ha a fajtanak 0-t adunk meg, a gep ket loves kozul valaszt,}
      X0 := 320;{kezdopoziciot ad nekik, sebesseget, mindezt veletlenszam-generatorral}
      Y0 := Random(200); {Koordinatak}
      Faj := Random(2)*5+1; {Tipus meghatarozasa: 1 v. 6 os fajta}
      Speed := -(Speed_L[Faj]+Random(Speed_L[Faj])); {Sebessegek kiszamolasa}
      YSpeed := (Random(3)-1)*(Random(Speed_L[Faj]) div 2);
      Dmg := Dmg_L[Faj]; {A lovedek seritese}
      Side := 0; {Mintha egy ellenseg lotte volna ki}
     End
    Else
     Begin {Ugyanezen elemek feltoltese a megadott parameterek alapjan}
      X0 := X1;
      Y0 := Y1+2;
      Speed := Sp0;
      Faj := Fj0;
      YSpeed := Yp0;
      Dmg := Dmg_L[Faj];
      Side := Side0;
     End;
   End;
 END;

PROCEDURE DeleteLeser(var xLess : pLess); {Egy lovedek eltavolitasa = Memoriabol valo torles}
 VAR LessPos : pLess; {Menete : egymasba lancolja az elotte es az utana levot,}
 BEGIN {majd felszabaditja a tarhelyet.}
  LessPos := xLess;
  If xLess^.Prev = nil then
   Begin
    xLess := xLess^.Next;
    If xLess <> nil then xLess^.Prev := nil;
   End
  Else
   Begin
    xLess := xLess^.Prev;
    xLess^.Next := LessPos^.Next;
    If LessPos^.Next <> nil then
     LessPos^.Next^.Prev := xLess;
   end;
  Dispose(LessPos);
 END;

PROCEDURE NextLevel; {Egy szint ugrasa}
 BEGIN
  ActualPalya := ActualPalya + 1; Mozgas := 0; {K�vetkez� szint eleje}
  If cheat then CASE ActualPalya OF  {Ha van csalas, a jatekos megkapja az addig}
   1 : Begin pFire := 2; pLeser[1] := #3; End; {megszerezheeto fegyvereket}
   2 : Begin pLeser[1] := #4; pLeser[2] := #1; pLeser[4] := #1; End;
  END;
 END;

PROCEDURE InitEnemy(var xEnemies : pEnemies; X0,Y0,Sp0,Yp0,Fj0,It : Integer);
 VAR EnemyPos : pEnemies; {Uj ellenseg letrehozasa = Lancolt listaba beillesztes}
 BEGIN
  If xEnemies = nil then {Elem felvetele}
   Begin
    New(xEnemies);
    xEnemies^.Next := nil;
    xEnemies^.Prev := nil;
   End
  Else
   Begin
    New(EnemyPos);
    EnemyPos^.Next := xEnemies;
    EnemyPos^.Prev := xEnemies^.Prev;
    xEnemies^.Prev := EnemyPos;
    xEnemies := EnemyPos;
   End;
  With xEnemies^ do {Az uj elemmel dolgozik}
   If Fj0 = 0 then {Ha tipusanak 0-t adtunk meg}
    Begin {Veletlen general egy meteort}
     X := 320; {Koordinatak : Jobb szelre}
     Y := Random(150);
     Faj := 1+Random(3); {Meteor tipusanak kiszamolasa}
     Speed := Speed_E[Faj]+Random(Speed_E[Faj]); {Sebessegek generalasa}
     YSpeed := (Random(3)-1)*(Random(Speed_E[Faj]) div 2);
     Life := Life_E[Faj]; {Eletero meghatarozasa}
     Item := It; {Azt, hogy a szetlovesevel a jatekos milyen plusszt kap,}
    End {A parameterlistarol olvassa.}
   Else {Ha nem a veletlenre kell bizni az ellenfel megallapitasat:}
    Begin {Az adatokat a parameterlistabol olvassa be.}
     X := X0;
     Y := Y0;
     Speed := Sp0;
     Faj := Fj0;
     YSpeed := Yp0;
     Life := Life_E[Faj];
     Late := 0;
     If (Faj = 4) or (Faj = 10) or (Faj = MaxEnemy1+3) then Boss := xEnemies;
     {Ha a fajtaja 4es, 10esm v. MaxEnemy+3, egy foellenserol van szo, amit a mutato erteke jelol meg}
     Item := It;
    End;
 END;

PROCEDURE DeleteEnemy(var xEnemies : pEnemies);
 VAR EnemyPos : pEnemies; {Ellenseg memoriabol valo felszabaditasa.}
 BEGIN
  EnemyPos := xEnemies;
  If xEnemies^.Prev = nil then
   Begin
    xEnemies := xEnemies^.Next;
    If xEnemies <> nil then xEnemies^.Prev := nil;
   End
  Else
   Begin
    xEnemies := xEnemies^.Prev;
    xEnemies^.Next := EnemyPos^.Next;
    If EnemyPos^.Next <> nil then
     EnemyPos^.Next^.Prev := xEnemies;
   end;
  Dispose(EnemyPos);
 END;

PROCEDURE DoEnemy1(var xEnemies : pEnemies);
 VAR EnemyPos : pEnemies; {Ellenseg cselekedeteinek megallapitasa.}
     Die : Boolean;
 BEGIN
  EnemyPos := xEnemies; {Lepkedes a listan}
  WHILE EnemyPos <> nil DO
   BEGIN
     With EnemyPos^ do
      Begin
       Die := False;
       X := X - Speed; {Mozgatas a sebessegnek megfeleloen}
       Y := Y - YSpeed;
       If (Y < -31) or (Y > 231) then Die := True; {Ha kilep a keprol, meghal}
       If (X < -31) then Die := True;
       If Late > 0 then Late := Late - 1 Else {Ha van beallitva a lovesehez kesleltetes, visszaszamol.}
        If (Life > 0) and (Faj > 0) then
         CASE Faj OF {Ha meg el az ellenfel.}
          4 : BEGIN {A negyes tipus programja.}
               If ((X < 0) and (Speed > 0)) or ((X > 294)
                and (Mozgas > 2100) and (Speed < 0)) then Speed := -Speed;
               If ((Y < 0) and (YSpeed > 0)) or ((Y > 167) and (YSpeed < 0))
                then YSpeed := -YSpeed; {Ne lepjen ki a palyarol}
               CASE ((Mozgas - 2000) mod 600) OF {Lovesek, varatlan sebessegvaltozasok}
                0 : Begin YSpeed := 0; Speed := 3; End;
                10,20,30,40,50,60,70,80,90,100 : Begin
                  InitLeser(Less,X+3,Y+4,-8,0,1,0);
                  InitLeser(Less,X,Y+11,-8,0,6,0);
                  InitLeser(Less,X+3,Y+18,-8,0,1,0);
                 End;
                120 : YSpeed := 5;
                121..131,151..161,181..191: If (Mozgas mod 3) = 0 then Begin
                  InitLeser(Less,X+3,Y+4,-12,0,1,0);
                  InitLeser(Less,X+3,Y+18,-12,0,1,0);
                 End;
                200 : Begin YSpeed := 2; Speed := 5; End;
                201..309:  If (Mozgas mod 8) = 0 then Begin
                  InitLeser(Less,X,Y+11,-8,0,6,0);
                 End;
                310 : Begin YSpeed := 1; Speed := 1; End;
                320 : Begin
                  InitLeser(Less,X+16,Y+16,6,0,12,0);
                  InitLeser(Less,X+16,Y+16,-6,0,12,0);
                  InitLeser(Less,X+16,Y+16,0,6,12,0);
                  InitLeser(Less,X+16,Y+16,0,-6,12,0);
                 End;
                330 : Begin
                  InitLeser(Less,X+16,Y+16,-6,-6,12,0);
                  InitLeser(Less,X+16,Y+16,6,-6,12,0);
                  InitLeser(Less,X+16,Y+16,-6,6,12,0);
                  InitLeser(Less,X+16,Y+16,6,6,12,0);
                 End;
                340 : Begin YSpeed := 2+Random(1); Speed := 2+Random(1); End;
                350,360,370,410,420,430,460,470,480 : Begin
                  InitLeser(Less,X+16,Y+16,6,0,7,0);
                  InitLeser(Less,X+16,Y+16,-6,0,7,0);
                  InitLeser(Less,X+16,Y+16,0,6,7,0);
                  InitLeser(Less,X+16,Y+16,0,-6,7,0);
                  InitLeser(Less,X+16,Y+16,-4,-4,7,0);
                  InitLeser(Less,X+16,Y+16,4,-4,7,0);
                  InitLeser(Less,X+16,Y+16,-4,4,7,0);
                  InitLeser(Less,X+16,Y+16,4,4,7,0);
                 End;
                510,520,530,540,550,560,570,580,590 : Begin
                 InitLeser(Less,X+3,Y+4,-8,0,1,0);
                 InitLeser(Less,X,Y+11,-8,0,6,0);
                 InitLeser(Less,X+3,Y+18,-8,0,1,0);
                End;
               END;
               If (Life < 150) and (Y >= 20) and (Y < 180) then
                Begin {Ha keves az energiaja, meghal es leadja a halalos loveseket.}
                 InitLeser(Less,X+3,Y+4,-16,0,12,0);
                 InitLeser(Less,X,Y+11,-16,0,12,0);
                 InitLeser(Less,X+3,Y+18,-16,0,12,0);
                 InitLeser(Less,X+3,Y+4,16,0,12,0);
                 InitLeser(Less,X,Y+11,16,0,12,0);
                 InitLeser(Less,X+3,Y+18,16,0,12,0);
                 Die := True; Boss := nil; {Nincs foellenseg.}
                 NextLevel; {Palyavaltas}
                End;
              END;
          5 : BEGIN {5os tipusu ellenseg lovese}
               If Mozgas mod 15 = 0 then
                Begin
                 InitLeser(Less,X+16,Y+16,6,0,8,0);
                 InitLeser(Less,X+16,Y+16,-6,0,8,0);
                 InitLeser(Less,X+16,Y+16,0,6,8,0);
                 InitLeser(Less,X+16,Y+16,0,-6,8,0);
                 InitLeser(Less,X+16,Y+16,-4,-4,8,0);
                 InitLeser(Less,X+16,Y+16,4,-4,8,0);
                 InitLeser(Less,X+16,Y+16,-4,4,8,0);
                 InitLeser(Less,X+16,Y+16,4,4,8,0);
                End;
              END;
          6 : BEGIN {Hatos tipusu ellenseg lovese}
               If Mozgas mod 15 = 0 then
                Begin
                 InitLeser(Less,X+6,Y+16,-6,0,7,0);
                End;
              END;
          8 : BEGIN {8as ellenseg ne menjen ki a palyarol Y iranyban, + loves}
               If (Mozgas+X-15) mod 30 = 0 then
                Begin
                 InitLeser(Less,X+6,Y+16,-10,0,11,0);
                End;
               If (Y > 180) and (YSpeed < 0) then YSpeed := -YSpeed;
               If (Y < 0) and (YSpeed > 0) then YSpeed := -YSpeed;
               If X < 100 then Speed := 10;
              END;
          9 : BEGIN {A 9es ellensegnek nincs lovese, de ha a kepernyo szele fele er,}
               If X = 200 then {Megvaltozik az Y sebessege}
                Begin
                 YSpeed := Random(2)*2-1;
                End
               Else If (X = 110) or (X = 40) then
                Begin YSpeed := YSpeed*2; Speed := Speed - 4; End;
              END;
          10 : BEGIN {10es ellenseg: Lovesek, uj ellensegek berakasa : }
                If Mozgas mod 10 = 0 then {Eletet ad a palya kozben mas ellenfeleknek}
                 Begin
                  InitLeser(Less,X+6,Y+10,-10,0,12,0);
                  InitLeser(Less,X+6,Y+22,-10,0,12,0);
                 End;
                If Mozgas mod 87 = 0 then
                 Begin
                  InitEnemy(Enemies,X+16,Y+20,2,-4,MaxEnemy1+4,0);
                 End;
                If Mozgas mod 125 = 0 then
                 Begin
                  InitEnemy(Enemies,X+16,Y+20,15,0,9,0);
                 End;
                If Mozgas mod 210 = 0 then
                 Begin
                  InitEnemy(Enemies,X+16,Y+20,2,-4,8,1+Random(6));
                 End;{Mozgas valtoztatasa -> nehezebb legyen megolni}
                If (Y > 180) and (YSpeed < 0) then YSpeed := -YSpeed;
                If (Y < 0) and (YSpeed > 0) then YSpeed := -YSpeed;
                If Mozgas mod 75 = 0 then YSpeed := YSpeed * 4;
                If Mozgas mod 75 = 32 then YSpeed := YSpeed div 4;
                If X < 200 then Speed := -10;
                If X > 320 then Speed := 3;
               END;
          MaxEnemy1+1 : BEGIN {11es : valtozo mozgas, loves}
                         If (Y < (((Mozgas + X) div 20) mod 4)* 25) then YSpeed := -2;
                         If (Y > 125+(((Mozgas + X) div 20) mod 4)* 25) then YSpeed := 2;
                         If Mozgas mod 20 = 0 then InitLeser(Less,X+6,Y+16,-6,0,8,0);
                        END;
          MaxEnemy1+2 : If Mozgas mod 15 = 0 then InitLeser(Less,X+16,Y+16,-Speed_L[4],0,4,0);
                        {12: csak loves}
          MaxEnemy1+3 : BEGIN {13: Lovesek, ne menjen ki a kepernyorol, foellenseg}
                         If Mozgas mod 25 = 0 then
                          Begin
                           InitLeser(Less,X+16,Y+16,6,0,8,0);
                           InitLeser(Less,X+16,Y+16,-6,0,8,0);
                           InitLeser(Less,X+16,Y+16,0,6,8,0);
                           InitLeser(Less,X+16,Y+16,0,-6,8,0);
                           InitLeser(Less,X+16,Y+16,-4,-4,8,0);
                           InitLeser(Less,X+16,Y+16,4,-4,8,0);
                           InitLeser(Less,X+16,Y+16,-4,4,8,0);
                           InitLeser(Less,X+16,Y+16,4,4,8,0);
                          End;
                         If (Y < 0) and (YSpeed > 0) then YSpeed := -YSpeed;
                         If (Y > 190) and (YSpeed < 0) then YSpeed := -YSpeed;
                         If (X < 0) and (Speed > 0) then Speed := -Speed;
                         If (X > 310) and (Speed < 0) then Speed := -Speed;
                         CASE Mozgas mod 200 OF
                          30 : YSpeed := YSpeed*2;
                          100 : YSpeed := YSpeed div 2;
                          70 : Speed := Speed * 3;
                          170 : Speed := Speed div 3;
                         END;
                        END;
          MaxEnemy1+4 : BEGIN {14: Y irnyban nem lep ki a kepernyorol, loves}
                         If Mozgas mod 15 = 0 then InitLeser(Less,X+6,Y+16,-10,0,5,0);
                         If (Y > 190) and (YSpeed < 0) then YSpeed := -YSpeed;
                         If (Y < 0) and (YSpeed > 0) then YSpeed := -YSpeed;
                         If X < 100 then Speed := 15;
                        END;
         END;
      End;
    If Die then {Ha meghal az ellenseg}
     Begin {Torlesek}
      If EnemyPos^.Prev = nil then
       Begin
        DeleteEnemy(EnemyPos);
        xEnemies := EnemyPos;
       End
      Else
       Begin
        DeleteEnemy(EnemyPos);
        EnemyPos := EnemyPos^.Next;
       End;
     End Else
      EnemyPos := EnemyPos^.Next;
   END;
 END;

PROCEDURE KillAllThings(var dEnemies : pEnemies; var dLess : pLess);
 BEGIN {Teljes memoria felszabaditasa, kiveve csillagok}
  WHILE dEnemies <> nil DO DeleteEnemy(dEnemies);
  WHILE dLess <> nil DO DeleteLeser(dLess);
 END;

PROCEDURE PutPic(X1,Y1,X2,Y2 : Integer; var Tar);
 TYPE tTar2 = ARRAY[0..63999]OF Char;
 VAR Tar2 : ^tTar2;
 BEGIN
  Tar2 := @Tar; {*} {A "Tar2" a "Tar"-ra mutasson}
  For i := 0 to Y2-1 do
   For j := 0 to X2-1 do
    If Tar2^[i*X2+j] < #255 then {A #255 az �tl�tsz� sz�n}
     Begin
      Xv := X1+j;
      Yv := Y1+i;
      If (Xv >= 0) and (Xv < 320) and (Yv >= 0) and (Yv < 200) then
       Vscr^[Yv,Xv] := Tar2^[i*X2+j]; {Keppont a virutalis kepnyore kirakasa}
     End;
 END;

PROCEDURE PutEnemies(var xEnemies : pEnemies); {Ellenfelek kirajzolasa}
 VAR EnemyPos : pEnemies;
     i,j : Integer;
     Die : Boolean;
 BEGIN
  EnemyPos := xEnemies;
  WHILE EnemyPos <> nil DO
   BEGIN
    With EnemyPos^ do
     Begin
      Die := False;
      If Life > 0 then
       Begin {Ha meg el}
        PutPic(X,Y,32,32,Enemy1[Abra[Faj]]); {rajzolas}
        If not cheat then {ha nincs csalas, utkozes vizsgalata a fohossel}
         For i := 0 to 31 do {keppontonkenti hasonlitas}
          For j := 0 to 31 do
           If Enemy1[Abra[Faj],i,j] < #255 then
            Begin
             Xv := X+j-pX;
             Yv := Y+i-pY;
             If (Xv >= 0) and (Xv < 50) and (Yv >= 0) and (Yv < 30)
              and (Ships[pShip,Yv,Xv] < #255) and (pLife > 0) then
              Begin pLife := 0; pTime := 50; End; {Ha utkozik, a fohos meghal}
            End;
       End
      Else
       Begin
        If Time > 0 then
         Begin {Ha az ellenfel eppen robban szet}
          PutPic(X+6-(200-Time*4),Y+6-(50-Time),16,16,Death[1]); {roncsok kirajzolasa}
          PutPic(X+16+(200-Time*4),Y+6-(50-Time),16,16,Death[2]);
          PutPic(X+6-(200-Time*4),Y+16+(50-Time),16,16,Death[3]);
          PutPic(X+16+(200-Time*4),Y+16+(50-Time),16,16,Death[4]);
          If not Cheated then
           Begin
            Str((2+Diff)*Life_E[Faj] div 10,PontSzov);
            PontSzov := PontSzov+'p';
           End Else Pontszov := '0p';
          WriteXY(X+16-(Length(PontSzov)*13) div 2,Y+8-(300-Time*6),#5,PontSzov);
          If (Time > 30) and (Item > 0) then PutPic(X+8,Y+8,16,16,Items[Item]); {felveheto targy kirajzolasa}
          Time := Time - 1; {robbanas haladasa}
         End Else Die := True; {ha mar szetrobbant, ki kell majd venni a memoriabol}
       End;
     End;
    If Die then
     Begin {memoriabol kiveves}
      If EnemyPos^.Prev = nil then
       Begin
        DeleteEnemy(EnemyPos);
        xEnemies := EnemyPos;
       End
      Else
       Begin
        DeleteEnemy(EnemyPos);
        EnemyPos := EnemyPos^.Next;
       End;
     End Else
      EnemyPos := EnemyPos^.Next;
   END;
 END;

PROCEDURE PutLesers(var xLess : pLess); {Lezer kirajzolasa}
 VAR X1,Y1 : Integer;
     EnemyPos : pEnemies;
     LessPos : pLess;
     Talalat : Boolean;
 BEGIN
  LessPos := xLess;
  WHILE LessPos <> nil DO
   BEGIN {Lepkedes a lancon}
    With LessPos^ do
     Begin
      Talalat := False;
      PutPic(X0,Y0,16,5,Leser[Faj]); {Kirajzolas}
      For i := 0 to 4 do {Utkozes megviszgalasa}
       For j := 0 to 15 do
        If (Leser[Faj,i,j] < #255) and (not Talalat) then
         Begin
          Xv := X0+j;
          Yv := Y0+i;
          If (Xv >= 0) and (Xv < 320) and (Yv >= 0) and (Yv < 200) then
           Begin {Utkozes ellenfelekkel: keppontonkent}
            If Side > 0 then {Jatekos lotte ki}
             Begin
              EnemyPos := Enemies;
              WHILE EnemyPos <> nil DO
               Begin
                If EnemyPos^.Life > 0 then
                 Begin {Ha az ellenfel meg el}
                  X1 := Xv-EnemyPos^.X;
                  Y1 := Yv-EnemyPos^.Y;
                  If (X1 >= 0) and (X1 < 32) and (Y1 >= 0) and (Y1 < 32)
                   and (Enemy1[Abra[EnemyPos^.Faj],Y1,X1] < #255) then
                  Begin
                   Talalat := True;
                    EnemyPos^.Life := EnemyPos^.Life-(Dmg-Diff*Dmg div 2);
                    If EnemyPos^.Life <= 0 then
                     Begin {Ha most fog meghalni}
                      EnemyPos^.Time := 50;
                      If not Cheated then
                       Pontok := Pontok + (2+Diff)*Life_E[EnemyPos^.Faj] div 10;
                      If EnemyPos = Boss then Boss := nil;
                      If EnemyPos^.Item > 0 then
                      CASE EnemyPos^.Item OF
                        1 : Begin {Ha volt az ellenfelben felveheto targy}
                            If pLife > 0 then pLife := pLife + 20;
                            If pLife > 100 then pLife := 100;
                           End;
                        2 : If pLeser[1] < #5 then pLeser[1] := Chr(Ord(pLeser[1]) + 1);
                        3..5 : If pLeser[EnemyPos^.Item-1] < #3 then
                                pLeser[EnemyPos^.Item-1] := Chr(Ord(pLeser[EnemyPos^.Item-1]) + 1);
                        6 : If pFire > 2 then pFire := 2;
                        7 : If pFire > 1 then pFire := 1;
                       END;
                     End;
                   End;
                 End;
                EnemyPos := EnemyPos^.Next
               End;
             End
            Else
             If (pLife > 0) and (not cheat) then {ellenseg lotte ki}
              Begin {Ha jatekossal utkozna}
               Xv := Xv-pX;
               Yv := Yv-pY; {Keppontonkenti hasonlitas}
               If (Xv >= 0) and (Xv < 50) and (Yv >= 0) and (Yv < 30)
                and (Ships[pShip,Yv,Xv] < #255) then
                 Begin pLife := pLife - (Dmg+Diff*Dmg div 2); Talalat := True;
                  If pLife <= 0 then pTime := 50; {Halal eset}
                  If pLife < 0 then pLife := 0; End;
              End;
           End;
         End;
     End;
    If Talalat then
     Begin {Ha van talalat, lezert ki kell venni a memoriabol}
      If LessPos^.Prev <> nil then
       Begin
        DeleteLeser(LessPos);
        LessPos := LessPos^.Next;
       End
      Else
       Begin
        DeleteLeser(LessPos);
        xLess := LessPos;
       End;
     End Else LessPos := LessPos^.Next;
   END;
 END;

PROCEDURE Palya;
 BEGIN {Palya programozasa: ellensegek, kontenerek, elszorodo lovedekek megjelenitese}
  If Mozgas > 100 then
  CASE ActualPalya OF
   0 : BEGIN {1. szint}
        If (Mozgas < 500) and ((Mozgas mod 25) = 0) then
          InitEnemy(Enemies,320,70+((Mozgas div 25) mod 2)*60,Speed_E[6],((Mozgas div 25) mod 2)*2-1,6,0);
        If Mozgas = 500 then InitEnemy(Enemies,320,Random(20)+90,4,0,7,1);
        If (Mozgas > 500) and (Mozgas < 600) and (Mozgas mod 20 = 0) then
          InitEnemy(Enemies,250,-10,1,-2,MaxEnemy1+2,0);
        If (Mozgas > 600) and (Mozgas < 700) and (Mozgas mod 20 = 0) then
          InitEnemy(Enemies,250,210,1,2,MaxEnemy1+2,0);
        If Mozgas = 700 then InitEnemy(Enemies,250,210,1,2,7,2);
        If Mozgas = 800 then InitEnemy(Enemies,320,Random(20)+90,2,0,7,1);
        If Mozgas = 990 then InitEnemy(Enemies,320,Random(20)+90,2,0,7,6);
        If (Mozgas > 750) and (Mozgas < 1040) and (Mozgas mod 25 = 0) then
          InitEnemy(Enemies,320,60+((Mozgas div 20) mod 4)* 25,1,2,MaxEnemy1+1,0);
        If Mozgas = 1100 then {foellensegnel addig nincs vege a palyanak, amig meg nem hal}
         Begin
          InitEnemy(Enemies,320,100,3,3,MaxEnemy1+3,2);
          Boss_Del := 200;
          InitEnemy(Enemies,320,Random(20)+90,2,0,7,1);
         End;
        If Mozgas > 2200 then Mozgas := Mozgas- Boss_Del;
        If (Mozgas > 1100) and (Boss = nil) then NextLevel; {foellenseg halala -> palya vege}
       END;
   1 : BEGIN {2. szint}
        If (Mozgas < 2000) and ((Mozgas mod 19) = 0) then
         Begin
          InitEnemy(Enemies,0,0,0,0,0,0);
         End;
        If (Mozgas < 2000) and ((Mozgas mod 32) = 0) then
         Begin
          InitLeser(Less,0,0,0,0,0,0);
         End;
        If (Mozgas < 2000) and (Mozgas mod 115 = 0) then
         CASE (Mozgas div 115) OF
          2 : InitEnemy(Enemies,320,Random(160)+20,4,0,7,1);
          4 : InitEnemy(Enemies,320,Random(160)+20,4,0,7,6);
          6 : InitEnemy(Enemies,320,Random(160)+20,4,0,7,3);
         END;
        If Mozgas = 1000 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,1);
        If Mozgas = 1850 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,2);
        If Mozgas = 1250 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,5);
        If Mozgas = 1700 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,1);
        If Mozgas = 2000 then
         Begin
          InitEnemy(Enemies,320,88,3,0,4,0);
          Boss_Del := 3600;
         End;
        If Mozgas > 10000 then Mozgas := Mozgas- Boss_Del;
       END;
   2 : BEGIN {3. szint}
        If (Mozgas < 500) and (Mozgas mod 20 = 0) then
         Begin
          InitEnemy(Enemies,320,88,Speed_E[5],(Speed_E[5] div 3)*(((Mozgas div 20) mod 2)*2-1),5,0);
         End;
        If Mozgas = 180 then InitEnemy(Enemies,320,Random(20)+90,4,0,7,1);
        If Mozgas = 360 then InitEnemy(Enemies,320,Random(20)+90,4,0,7,1);
        If (Mozgas > 530) and (Mozgas < 1000) and (Mozgas mod 3 = 0) then
         InitEnemy(Enemies,320,Random(190),15,0,9,0);
        If Mozgas = 620 then InitEnemy(Enemies,320,68+Random(40),3,0,7,1);
        If Mozgas = 800 then InitEnemy(Enemies,320,68+Random(40),3,0,7,3);
        If Mozgas = 950 then InitEnemy(Enemies,320,68+Random(40),3,0,7,7);
        If (Mozgas > 1000) and (Mozgas < 1300) and (Mozgas mod 25 = 0) then
         Begin
          InitEnemy(Enemies,310,199,2,5,8,0);
          InitEnemy(Enemies,270,-20,2,-5,8,0);
         End;
        If Mozgas = 1300 then
         Begin
          InitEnemy(Enemies,310,199,2,5,8,4);
          InitEnemy(Enemies,270,-20,2,-5,8,3);
         End;
        If (Mozgas > 1000) and (Mozgas < 1300) and (Mozgas mod 85 = 40) then
         InitEnemy(Enemies,320,Random(180),4,0,7,1);
        If Mozgas = 1400 then
         Begin
          InitEnemy(Enemies,320,0,1,0,7,1);
          InitEnemy(Enemies,320,40,1,0,7,4);
          InitEnemy(Enemies,320,120,1,0,7,3);
          InitEnemy(Enemies,320,160,1,0,7,1);
          InitEnemy(Enemies,320,88,1,2,10,1);
          Boss_Del := 4200;
         End;
        If Mozgas > 10000 then Mozgas := Mozgas- Boss_Del;
        If (Mozgas > 1400) and (Boss = nil) then NextLevel;
       END;
  END;
 END;

PROCEDURE DoLeser(var xLess : pLess);
 VAR LessPos : pLess; {Leserek cselekedetei}
     Eltunt : Boolean;
 BEGIN
  LessPos := xLess;
  WHILE LessPos <> nil DO
   Begin
    With LessPos^ do
     Begin {Mozgas}
      Eltunt := False;
      X0 := X0 + Speed;
      Y0 := Y0 - YSpeed;
      If (Y0 < -31) or (Y0 > 231) then Eltunt := True;
      If (X0 < -31) or (X0 > 320) then Eltunt := True;
     End;
    If Eltunt then
     Begin {Ha kilepett a kernyorol, felszabaditja a hozza tartozo memoriat}
      If LessPos^.Prev <> nil then
       Begin
        DeleteLeser(LessPos);
        LessPos := LessPos^.Next;
       End
      Else
       Begin
        DeleteLeser(LessPos);
        xLess := LessPos;
       End;
     End Else LessPos := LessPos^.Next;
   End;
 END;

PROCEDURE Temp; {Kezd� be�ll�t�sok}
 BEGIN
  Pontok := 0;
  pFire := 3; {Hajt�mu a leggyeng�bb}
  pShip := 1; {Ebben a verzi�ban csak egy haj� van}
  pLife := 100; {�let be�ll�t�sa}
  pY := 75; pX := -40; {Haj� kezd�poz�ci�ja}
  SzumLife := 4; {4 �let}
  pLogged := False; {m�g s�rthetlen}
  pLogTime := 0;
  pL := 0; {Fegyverek alap�llapotba}
  pLeser[0] := #1; pLeser[1] := #1;
  pLeser[2] := #0; pLeser[3] := #0;
  pLeser[4] := #0;
  ActualPalya := 0; {0. P�ly�n�l kezd}
  Mozgas := 0; {P�lya eleje}
  Boss := nil; {M�g nincs f�ellens�g}
  Enemies := nil; Less := nil; {L�ncolt list�k t�rl�se}
  Randomize; {V�letlengener�tor alap�llapotba}
  Cheat := False; {Csal�s kikapcsol�sa}
  PontSzov := '1p';
  Splash := 1;
  {}
  Assign(fP,'hiscore.dat');
  {$I-}
  ReSet(fP);
  If IOResult <> 0 then
   Begin
    For i := 0 to 9 do
     Pontszam[i].Pontszam := 0;
    ReWrite(fP);
    For i := 0 to 9 do
     Write(fP,Pontszam[i]);
    Close(fP);
    ReSet(fP);
   End;
  {$I+}
  For i := 0 to 9 do
   Read(fP,Pontszam[i]);
  Close(fP);
  Quit := False;
  Cheated := False;
 END;

PROCEDURE SaveScore;
 BEGIN
  Assign(fP,'hiscore.dat');
  ReWrite(fP);
  For i := 0 to 9 do
   Write(fP,Pontszam[i]);
  Close(fP);
 END;

PROCEDURE GAME;
 BEGIN
  Temp;
  AddStars(Stars);
  {}
  REPEAT
   If Splash < 63 then
    Begin
     Splash := Splash + 2;
     Screen(Splash);
    End;
   SetCounter; Palya; {Id�z�t� be�ll�t�sa, p�lya alakul�sa}
   DoEnemy1(Enemies); DoLeser(Less); {l�ved�kek �s ellens�gek mozg�sa}
   If KeyPressed then {*} {Ha le van �tve billenty�}
    Begin ch1 := ReadKey; {*} {Billentyu kiolvas�sa}
     CASE ch1 OF
      #0 : ch2 := ReadKey; {*} {Ha speci�lis billenty�, kiolvassa a scan-k�dot}
      #27 : Quit := True; {ESC: Kil�p�s}
      '1' : pLeser[0] := #1; {Fegyverek}
      '2' : If (pLeser[2] > #0) then pLeser[0] := #2;
      '3' : If (pLeser[3] > #0) then pLeser[0] := #3;
      '4' : If (pLeser[4] > #0) then pLeser[0] := #4;
      'c','C' : Begin Cheat := Cheat xor True; Cheated := True; End;
     END; End;
   ReadMouse; {Eg�rpoz�ci� kiolvas�sa}
   If pLife > 0 then Begin {Ha a j�t�kos m�g �l}
    If pL > 0 then pL := pL - 1 Else
    If ((M_Button and 1) = 1) and (pL = 0) then
     Begin {Ha a bal eg�rgomb le van nyomva �s m�r nincs t�bb k�s�s}
      CASE pLeser[0] OF {A megfelel� l�v�sek programja}
       #1 : CASE pLeser[1] OF {K�k l�zer}
            #1 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,1,1);
            #2 : Begin
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,1,1);
                End;
            #3 : Begin
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,2,1);
                End;
            #4 : Begin
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],3,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-3,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,3,1);
                End;
            #5 : Begin
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],3,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-3,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,3,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,3,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,3,1);
                End;
           END;
       #2 : CASE pLeser[2] OF {Z�ld}
            #1 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[4],0,4,1);
            #2 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[5],0,5,1);
            #3 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[6],0,6,1);
           END;
       #3 : CASE pLeser[3] OF {K�rben l�v�}
            #1 : BEGIN
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],6,0,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,6,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-6,0,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,-6,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,4,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,-4,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,4,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,-4,7,1);
                END;
            #2 : BEGIN
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],6,0,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,6,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-6,0,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,-6,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,4,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,-4,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,4,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,-4,8,1);
                END;
            #3 : BEGIN
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],6,0,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,6,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-6,0,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,-6,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,4,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,-4,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,4,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,-4,9,1);
                END;
           END;
       #4 : CASE pLeser[4] OF {S�rga}
            #1 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[10],0,10,1);
            #2 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[11],0,11,1);
            #3 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[12],0,12,1);
           END;
      END;
      pL := Late_L[Ord(pLeser[0])*3+Ord(pLeser[Ord(pLeser[0])])]; {K�s�st be�ll�tja}
     End;
    If M_CurY < 99 then pY := pY - Speed_F[pFire]; {Eg�r elmozdul�sa szerinti ir�ny�t�s}
    If M_CurY > 101 then pY := pY + Speed_F[pFire];
    If pLogged or (pX >= 5) then
      Begin
       If M_CurX < 319 then pX := pX - Speed_F[pFire];
       If M_CurX > 321 then pX := pX + Speed_F[pFire];
      End;
     If pX < 5 then Begin If pLogged then pX := 5
       else pX := pX + Speed_M End;
     If not pLogged then
      Begin
       pLogTime := pLogTime +1;
       If pLogTime >= 50 then pLogged := True;
      End;
   End;
   If pX > 274 then pX := 274;
   If pY < -10 then pY := -10;
   If pY > 180 then pY := 180;
   Mozgas := Mozgas+1;
   {}
   ClrVscr(#0); DoStars(Stars); {Virtu�lis k�perny� t�rl�se, csillagmozg�s}
   PutLesers(Less); PutEnemies(Enemies); {Ellens�gek, l�zerek kirajzol�sa, s�r�t�se}
   If pLife > 0 then Begin {Ha a j�t�kos �l, kirajzol�s}
    PutPic(pX,pY,50,30,Ships[pShip]); {Haj� kirajzol�sa}
    PutPic(pX-17,pY+9,17,14,Fires[pFire,(Mozgas mod 3)+1]); {Hajt�mu}
   End Else If pShip > 0 then Begin
    PutPic(pX+6-(200-pTime*4),pY+6-(50-pTime),16,16,Death[1]);  {Hullad�kok }
    PutPic(pX+16+(200-pTime*4),pY+6-(50-pTime),16,16,Death[2]); { kirajzol�sa}
    PutPic(pX+6-(200-pTime*4),pY+16+(50-pTime),16,16,Death[3]);
    PutPic(pX+16+(200-pTime*4),pY+16+(50-pTime),16,16,Death[4]);
    If pTime > 0 then pTime := pTime - 1 Else {Ha pTime > 0, csak darabokat rajzol ki}
     If SzumLife > 1 then Begin {Hal�l eset�n �let cs�kken�se, alapbe�ll�t�sok}
      SzumLife := SzumLife - 1; pLife := 100;
      pX := -40; pY := 75; pLogged := False;
      pLogTime := 0;
     End Else Begin pShip := 0; SzumLife := 0; End; {Teljes hal�l}
   End;
   {}
   PutPic(0,190,120,10,Status); {�leter�vonal kirajzol�sa}
   If SzumLife > 0 then PutPic(120,190,10,10,Stat2[SzumLife,3]);
   For i := 1 to Round((pLife/100)*115) do
    For j := 3 to 6 do Vscr^[190+j,2+i] := #2;
   For m := 1 to 4 do {Fegyverkijelz�}
    If (pLeser[m] = #0) then PutPic(270+m*10,190,10,10,Stat2[m,0]) Else
     If (pLeser[m] > #0) and (pLeser[0] <> Chr(m)) then PutPic(270+m*10,190,10,10,Stat2[m,1]) Else
      If (pLeser[m] > #0) and (pLeser[0] = Chr(m)) then PutPic(270+m*10,190,10,10,Stat2[m,2]);
   If Boss <> nil then Begin
    PutPic(0,0,120,10,Status); WriteXY(125,0,#4,'BOSS'); {F�ellens�g ereje}
    For i := 1 to Round((Boss^.Life/Life_E[Boss^.Faj])*115) do
     For j := 3 to 6 do Vscr^[j,2+i] := #4; End;
   {Sz�vegek ki�r�sa}
   If SzumLife <= 0 then WriteXY(127,117,#5,'GAME OVER');
   If Cheat then WriteXY(0,0,#7,'cheat on');
   If ActualPalya = 3 then WriteXY(127,117,#5,'YOU WON !') Else
   If (Mozgas > 25) and (Mozgas < 200) then
    Begin
     CASE Mozgas OF
      25..124 : Begin
                Str(ActualPalya+1,s);
                WriteXY(137,Round(90-((125-Mozgas))*(1-sqr(sin((Mozgas-25)/10)))),#5,'LEVEL '+s);
               End;
      125..149 : WriteXY(137,90,#5,'LEVEL '+s);
      150..199 : Begin
                  PutPic(154-(Mozgas-150)*4,84-Mozgas+150,16,16,Death[1]); {roncsok kirajzolasa}
                  PutPic(166+(Mozgas-150)*4,84-Mozgas+150,16,16,Death[2]);
                  PutPic(154-(Mozgas-150)*4,96+Mozgas-150,16,16,Death[3]);
                  PutPic(166+(Mozgas-150)*4,96+Mozgas-150,16,16,Death[4]);
                 End;

     END;
    End;
   {}
   Str(Pontok,Ponts);
   Ponts := 'Pontszam ' + ' ' + Spaced(Ponts);
   WriteXY(165,0,#2,Ponts);
   {}
   SetScr; {Virtu�lis k�perny� �tm�sol�sa = eddigiek kirajzol�sa}
   SetPos; {Eg�rpoz�ci� k�z�pre �ll�t�sa}
   WaitFor(Speed); {K�sleltet�s a SetCounter-hez k�pest Speed id�vel}
  UNTIL Quit; {Ameddig nincs kil�p�s}
  {}
  Anim2; Splash := 63; Screen(Splash);
  DrawPoints(10);
  If Pontok > Pontszam[9].Pontszam then
   Begin
    Str(Pontok,Ponts);
    Ponts := '    You have reached '+Spaced(Ponts)+' !!';
    WriteXY(1,170,#0,Ponts);
    WriteXY(0,170,#3,Ponts);
    WriteXY(1,182,#0,'Enter Name');
    WriteXY(0,182,#4,'Enter Name');
   End;
  Anim(0);
  SetScr;
  {}
  IF Pontok > Pontszam[i].Pontszam THEN BEGIN
   PontSzov := ''; k := 0;
   Quit := False;
   Repeat
    zh := ReadKey;
    CASE zh OF
     #0 : ch := ReadKey;
     #8 : If Length(Pontszov) > 0 then PontSzov[0] := Chr(Ord(Pontszov[0])-1);
     'a'..'z','A'..'Z',' ' : If Length(PontSzov) < 20 then
      PontSzov := PontSzov + zh;
     #27, #13 : Begin
                 If PontSzov = '' then Pontszov := ' ';
                 Quit := True;
                End;
    END;
    DrawPoints(10);
    WriteXY(1,182,#0,'Enter Name');
    WriteXY(0,182,#4,'Enter Name');
    WriteXY(121,182,#0,PontSzov);
    WriteXY(120,182,#1,PontSzov);
    Str(Pontok,Ponts);
    Ponts := '    You have reached '+Spaced(Ponts)+' !!';
    WriteXY(1,170,#0,Ponts);
    WriteXY(0,170,#3,Ponts);
    SetScr;
   Until Quit;
   Pontszam[9].Pontszam := Pontok;
   Pontszam[9].Nev := Pontszov;
   k := 9; l := 10;
   Repeat
    If Pontok > Pontszam[k-1].Pontszam then
     Begin
      Pontszam[k] := Pontszam[k-1];
      Pontszam[k-1].Pontszam := Pontok;
      Pontszam[k-1].Nev := Pontszov;
      l := k-1;
     End;
    k := k - 1;
   Until (k = 0);
   ClrVscr(#148);
   DrawPoints(l);
   Anim(1);
   SetScr;
  END;
  {}
  Repeat Until KeyPressed;
  While KeyPressed do ch := ReadKey;
  REPEAT
   SetCounter;
   Splash := Splash -2;
   Screen(Splash);
   WaitFor(Speed);
  UNTIL Splash <= 1;
  {}
  RemoveStars(Stars); {Mem�riafelszabad�t�s}
  KillAllthings(Enemies,Less);
  SaveScore;
 END;

FUNCTION MENU : Integer;
 VAR MenuQuit : Boolean;

 PROCEDURE DrawMenu;
  VAR MP2 : Integer;
      DStr : String;
  BEGIN
   CASE Diff OF
    -1 : DStr := 'Easy';
    0 : DStr := 'Medium';
    1 : DStr := 'Hard';
   END;
   ClrVscr(#1);
   WriteXY(116,5,#5,'KlonGun');
   WriteXY(115,6,#5,'KlonGun');
   WriteXY(114,5,#5,'KlonGun');
   WriteXY(115,4,#5,'KlonGun');
   WriteXY(115,5,#148,'KlonGun');
   For MP2 := 0 to MaxMenu do
    Begin
     WriteXY(70,71+20*MP2,#0,MenuSzov[MP2]);
     WriteXY(71,71+20*MP2,#0,MenuSzov[MP2]);
     WriteXY(70,70+20*MP2,#6,MenuSzov[MP2]);
     If MP2 = 1 then
      BEGIN
       WriteXY(200,71+20*MP2,#0,DStr);
       WriteXY(201,71+20*MP2,#0,DStr);
       WriteXY(200,70+20*MP2,#4,DStr);
      END;
    End;
   PutPic(5,60+20*MenuPont,50,30,Ships[1]);
  END;

 BEGIN
  If Changed then
   Begin
    ClrVscr(#0);
    SetScr;
    Splash := 63;
    Screen(Splash);
    MenuQuit := False;
    DrawMenu;
    Anim(0);
   End;
  REPEAT
   DrawMenu;
   SetScr;
   CASE ReadKey OF
    #0 : CASE ReadKey OF
          #72 : If MenuPont > 0 then MenuPont := MenuPont -1;
          #80 : If MenuPont < MaxMenu then MenuPont := MenuPont +1;
         END;
    #27 : Begin MenuQuit := True; MENU := MaxMenu; End;
    #13 : Begin MenuQuit := True; MENU := MenuPont; End;
   END;
  UNTIL MenuQuit;
  WHILE KeyPressed DO ch := ReadKey;
 END;

PROCEDURE CREDITS;
 VAR pos,p : Integer;
 BEGIN
  ClrVscr(#0);
  SetScr;
  Splash := 1;
  Screen(Splash);
  ClrVscr(#148);
  SetScr;
  pos := 0;
  REPEAT
   SetCounter;
   Splash := Splash + 2;
   Screen(Splash);
   WaitFor(Speed);
  UNTIL Splash >= 62;
  Splash := 63;
  Screen(Splash);
  REPEAT
   ClrVscr(#148);
   pos := pos + 1;
   For p := 0 to MaxCredit do
    Begin
     WriteXY(161-Length(CreditSzov[p])*9 div 2,200+p*20-pos,#0,CreditSzov[p]);
     WriteXY(160-Length(CreditSzov[p])*9 div 2,201+p*20-pos,#0,CreditSzov[p]);
     WriteXY(160-Length(CreditSzov[p])*9 div 2,200+p*20-pos,#2,CreditSzov[p]);
    End;
   SetScr;
  UNTIL (pos >= 400+(MaxCredit+1)*20) or KeyPressed;
  WHILE KeyPressed DO ch := ReadKey;
 END;

BEGIN
 Init; {320x200x256os vide�m�d}
 Load;
 Move(Palette,AllPalette,768);
 Diff := 0;
 {}
 QuitAll := False;
 Changed := True;
 REPEAT
  CASE MENU OF
   0 : Begin
        REPEAT
         SetCounter;
         Splash := Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        GAME;
        Changed := True;
       End;
   1 : Begin
        If Diff = 0 then Diff := 1 Else
         If Diff = 1 then Diff := -1 Else
          Diff := 0;
        Changed := False;
       End;
   2 : Begin
        REPEAT
         SetCounter;
         Splash := Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        CREDITS;
        REPEAT
         SetCounter;
         Splash := Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        Changed := True;
       End;
   3 : BEGIN
        Assign(fP,'hiscore.dat');
        {$I-}
        ReSet(fP);
        If IOResult <> 0 then
         Begin
          For i := 0 to 9 do
           Pontszam[i].Pontszam := 0;
          ReWrite(fP);
          For i := 0 to 9 do
           Write(fP,Pontszam[i]);
          Close(fP);
          ReSet(fP);
         End;
        {$I+}
        For i := 0 to 9 do
         Read(fP,Pontszam[i]);
        Close(fP);
        REPEAT
         SetCounter;
         Splash := Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        ClrVscr(#0);
        SetScr;
        Splash := 63;
        Screen(Splash);
        ClrVscr(#148);
        DrawPoints(l);
        Anim(0);
        SetScr;
        Repeat Until KeyPressed;
        While KeyPressed do ch := ReadKey;
        REPEAT
         SetCounter;
         Splash := Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        Changed := True;
       END;
   4 : BEGIN
        For i := 0 to 9 do
         Begin
          Pontszam[i].Pontszam := 0;
          Pontszam[i].Nev := '';
         End;
        Changed := True;
        SaveScore;
       END;
   MaxMenu : QuitAll := True;
  END;
 UNTIL QuitAll;
 {}
 Anim3;
 Finish; {Vide�m�d lez�r�sa}
 {Credits}
 WriteLn;
 WriteLn('Created by : Csengodi Peter (11.tk) JOT2FY');
 WriteLn('All rights reserved.');
 WriteLn;
END.

*/
