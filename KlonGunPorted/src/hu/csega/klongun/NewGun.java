PROCEDURE Anim;

CONST PalS = 8{8};
      Speed = 2{5};
      Pocs = 48{63, !48 = S”t‚tebb!};
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
     FGGVX[n] := (n-F2(n-160)-o+1920) mod 320;
    For n := 0 to 199 do
     FGGVY[n] := (n-F(n-100)-o+1800) mod 200;
    ClrVscr(0);
    For k := 0 to 199 do
     For n := 0 to 319 do
      Vscr^[k,n] := Back^[FGGVY[k],FGGVX[n]];
    SetScr;
   END;
  i := i + j;
  m := m + 1;
  o := o + 1;
  If o > 1600 then o := o - 1600;
  If m > MaxM then m := m - MaxM;
  If i < -Maxi then j := 1 Else If i > Maxi then j := -1;
 UNTIL KeyPressed;
 WHILE KeyPressed DO ReadKey;
 {}
 Finish;
 Dispose(Back);
END.