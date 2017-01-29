package hu.csega.klongun;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import hu.csega.klongun.imported.FileUtil;
import hu.csega.klongun.model.Enemy;
import hu.csega.klongun.model.Less;
import hu.csega.klongun.model.Score;
import hu.csega.klongun.model.Star;
import hu.csega.klongun.screen.Picture;
import hu.csega.klongun.screen.TPal;
import hu.csega.klongun.screen.TVscr;
import sun.java2d.Disposer;

public class KlonGun {

	private static final String HISCORES = "Hiscores";
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

    public List<Star> stars = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public List<Enemy> bosses = new ArrayList<>();
    public List<Less> lesses = new ArrayList<>();

    public int i,j,k,l,m,n,Xv,Yv;

    public Picture[] ships = new Picture[MaxShips]; // 30 x 40
    public Picture[][] fires = new Picture[3][3]; // 14 x 17
    public Picture[] enemyShips = new Picture[MaxEnemy1]; // 32 x 32
    public Picture[] lesers = new Picture[MaxLesers]; // 5 x 16
    public Picture[] deaths = new Picture[MaxDeaths]; // 16 x 16
    public Picture[] items = new Picture[MaxItem]; // 16 x 16
    public Picture[][] stat2 = new Picture[4][4]; // 10 x 10
    public Picture status;

    public String[] s = new String[2];

    public int areaScroll; // = Mozgas
    public int bossDel;
    public Enemy boss;

    // Player data
    public int pX;
    public int pY;
    public int pShip;
    public int pLife;
    public int pFire;
    public int pL;
    public boolean pLogged; // if false, player can't be hurt
    public int[] leser = new int[5]; // weapon statuses
    public int pTime;
    public int currentArea;
    public int sumLife;

    public char ch1;
    public char ch2;

    public boolean quit; // if false, quitting is not enabled
    public boolean cheat;
    public boolean cheated;
    public int scores;
    public int scoreText1; // PontSzov
    public int scoreText2; // Ponts
    public int splash;

    public TPal palette = new TPal();

    public File scoresFile;
    public Score[] scoreRecords;

    public char ch;
    public char zh;

    public boolean quitAll;
    public int diff;
    public int menuItem;
    public boolean changed;
    public int pLogTime;

    public GpcGun gun = new GpcGun();

    public static final int sinus[] = new int[126];

    public static final Random RND = new Random(System.currentTimeMillis());


	public static void main(String[] args) throws Exception {
		KlonGun kg = new KlonGun();
		for (int i = 0; i < 126; i++)
			sinus[i] = (int) Math.round(Math.sin(i / 20) * 100);

		//////////////////////
		kg.run();
	}

    public String spaced(String xx) {
    	for(int i = 1; i < 5-xx.length(); i++) {
    		xx = ' ' + xx;
    	}

    	return xx + 'p';
    }


    public void drawPoints(int no) {
    	gun.clrVscr(149);
        gun.writeXY(121,0,0,HISCORES);
        gun.writeXY(120,0,4,HISCORES);

        for(int i = 0; i < 10; i++) {
        	/*
            With PontSzam[i] do
             if Pontszam <> 0 then
              if no <> i then
               {
                Str(Pontszam,Ponts);
                WriteXY(21,20+i*15,#0,Nev);
                WriteXY(221,20+i*15,#0,Spaced(Ponts));
                WriteXY(20,20+i*15,#2,Nev);
                WriteXY(220,20+i*15,#2,Spaced(Ponts));
               } Else {
                Str(Pontszam,Ponts);
                WriteXY(21,20+i*15,#0,Nev);
                WriteXY(221,20+i*15,#0,Spaced(Ponts));
                WriteXY(20,20+i*15,#1,Nev);
                WriteXY(220,20+i*15,#1,Spaced(Ponts));
               }
      */


        }
    } // end drawPoints

    public void Anim3() {
    	TVscr back = new TVscr();
    	int pos = 0;

    	gun.vscr.copyTo(back);

    	do {
    		gun.clrVscr(0);
    		pos++;

    		for(int i = 0; i < TVscr.HEIGHT; i++) {
    			for(int j = 0; j < TVscr.WIDTH; j++) {
    				k = (i - 100) * pos + 100;
    				l = (j - 160) * pos + 160;
    		        if ((k >= 0) && (l >= 0) && (k < 200) && (l < 320))
    		          gun.vscr.set(k, l, back.get(i, j));
    			}
    		}

    		gun.setScr();
    	} while(pos <= 80);
    }

    public void Anim(int type) {
    	TVscr back = new TVscr();
    	int pos = 320, i = 0;

    	gun.vscr.copyTo(back);
        if (type == 0)
        	gun.clrVscr(0);
        else
        	gun.clrVscr(148);

    	do {
    		pos -= 4;
    		for(i = 0; i < 200; i++) {
    			if(i % 2 == 0) {
    				System.arraycopy(back.content, i * TVscr.WIDTH + pos,
    						gun.vscr.content, i * TVscr.WIDTH, 320-pos);
    			} else {
    				System.arraycopy(back.content, i * TVscr.WIDTH,
    						gun.vscr.content, i * TVscr.WIDTH + pos, 320-pos);
    			}
    		}

    		gun.setScr();
    	} while(pos != 0);
    }

    public int f1(int x, double y) {
    	return (int)Math.round(y * sinus[(int)(Math.round(x + 503) % 126)]);
    }

    public int f2(int x, double z) {
    	return (int)Math.round(z * sinus[(int)(Math.round((x+m)*2 + 503) % 126)]);
    }


    public void anim2() {
        final int Speed = 2;
        final int MaxM = 63;
        final int MaxI = 60;

        int i = 0, j = 1, k = 0, m = 0, n = 0, o = 0;
        double y, z;

        TVscr back = new TVscr();


        int[] FGGVX = new int[320];
        int[] FGGVY = new int[200];

        gun.vscr.copyTo(back);

		do {
			if (i % Speed == 0) {
				y = i / 380.0;
				z = i / 1500.0;

				for (n = 0; n < 320; n++)
					FGGVX[n] = n - f2(n - 160, z);
				for (n = 0; n < 200; n++)
					FGGVY[n] = n - f1(n - 100, y) - o;

				gun.clrVscr(0);

				for (k = 0; k < 200; k++) {
					for (n = 0; n < 320; n++) {

					}
				}

				if (FGGVY[k] >= 0 && FGGVY[k] < 200 && FGGVX[n] >= 0 && FGGVX[n] < 320)
					gun.vscr.set(k, n, back.get(FGGVY[k], FGGVX[n]));

				gun.setScr();
			}

			i += j;
			m++;
			o += 2;

			if (m > MaxM)
				m -= MaxM;

			if (i < -MaxI)
				j = 1;
			else if (i > MaxI)
				j = -1;

		} while (o < 200);
    }

	public void screen(int no) {
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 3; j++) {
				int v = (int) Math.round(gun.originalPalette.get(i, j) * no / 63.0);
				gun.palette.set(i, j, v);
			}
		}
	} // end screen


	public void load() {
		int i = 0;
		int j = 0;

		// BlockRead = reads block from file
		// Reset(f,##) : ## = size of one block
		// Str = number to string
		// Seek = goes to position in file

		// Every pictures start after 4 bytes

		for(i = 0; i < MaxShips; i++)
			ships[i] = FileUtil.loadPic("ship" + i, 40, 30);
		for(i = 0; i < MaxItem; i++)
			items[i] = FileUtil.loadPic("i_" + i, 16, 16);
		for(i = 0; i < MaxDeaths; i++)
			deaths[i] = FileUtil.loadPic("ruin" + i, 16, 16);
		for(i = 0; i < MaxLesers; i++)
			lesers[i] = FileUtil.loadPic("l" + i, 16, 5);
		for(i = 0; i < MaxEnemy1; i++)
			enemyShips[i] = FileUtil.loadPic("a" + i, 32, 32);
		status = FileUtil.loadPic("status1", 120, 10);

		for(i = 0; i < 4; i++) {
			for(j = 0; j < 4; j++) {
				String picName = String.valueOf(i) + j;
				stat2[i][j] = FileUtil.loadPic(picName, 10, 10);
			}
		}

		for(i = 0; i < MaxFires; i++) {
			for(j = 0; j < 3; j++) {
				String picName = "s_fire" + String.valueOf(i) + j;
				fires[i][j] = FileUtil.loadPic(picName, 17, 14);
			}
		}

	} // end of load

	public void addStars() {
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < MaxStars; j++) {
				Star s = new Star();
				s.kind = (char)i;
				s.x = RND.nextInt(340);
				s.y = RND.nextInt(200);
				stars.add(s);
			}
		}
	}


	public void doStars() {
		for(Star s : stars) {
			s.x -= (s.kind + 1) * Speed1;
			if(s.x < -10) {
		        s.x = 320+RND.nextInt(20);
		        s.y = RND.nextInt(200);
			}
		    if(s.x >= 0 && s.x < 320) {
		    	if(s.kind == 0) {
		    		gun.vscr.set(s.x, s.y, 148);
		    	} else {
		    		gun.vscr.set(s.x, s.y, 7);
		    	}
		    }
		}
	} // end if doStars

	public void removeStars() {
		stars.clear();
	}

	public void initLeser(int x1, int y1, int sp0, int yp0, int fj0, int side0) {
		Less less = new Less();
		lesses.add(less);

		if (fj0 == 0) {
			less.x = 320;
			less.y = RND.nextInt(200);
			less.kind = RND.nextInt(2) * 5 + 1; // 1 || 6
			less.xSpeed = -(Speed_L[less.kind] + RND.nextInt(Speed_L[less.kind]));
			less.ySpeed = (RND.nextInt(3) - 1) * (RND.nextInt(Speed_L[less.kind]) / 2);
			less.damage = Dmg_L[less.kind];
			less.side = 0;
		} else {
			// load data accoring parameters
			less.x = x1;
			less.y = y1 + 2;
			less.xSpeed = sp0;
			less.kind = fj0;
			less.ySpeed = yp0;
			less.damage = Dmg_L[less.kind];
			less.side = side0;
		}
	} // end if initLeser

	public void doLeser() {
		boolean disappeared = false;

		Iterator<Less> it = lesses.iterator();
		while (it.hasNext()) {
			Less less = it.next();
			disappeared = false;
			less.x -= less.xSpeed;
			less.y -= less.ySpeed;
			if (less.y < -31 || less.y > 231)
				disappeared = true;
			if (less.x < -31 || less.x > 320)
				disappeared = true;

			if (disappeared) {
				it.remove();
			}
		}
	} // end if doLeser

	public void deleteLeser(Less less) {
		lesses.remove(less);
	}

	public void nextLevel() {
		currentArea++;
		areaScroll = 0;

		if (cheat) {
			// if cheat exists, player gets all possible weapon of previous area
			switch (currentArea) {
			case 1:
				pFire = 2;
				leser[0] = 3;
				break;
			case 2:
				leser[0] = 4;
				leser[1] = 1;
				leser[3] = 1;
				break;
			}
		}
	} // end of nextLevel



	public void initEnemy(int X0, int Y0, int Sp0, int Yp0, int Fj0, int It) {
		Enemy enemy = new Enemy();
		enemies.add(enemy);

		if(Fj0 == 0) {
			// random generated meteor
			enemy.x = 320;
			enemy.y = RND.nextInt(150);
			enemy.kind = Fj0 = 1 + RND.nextInt(3);
			enemy.xSpeed = Speed_E[Fj0] + RND.nextInt(Speed_E[Fj0]);
			enemy.ySpeed = (RND.nextInt(3) - 1) * (RND.nextInt(Speed_E[Fj0]) / 2);
			enemy.life = Life_E[Fj0];
			enemy.item = It;
		} else {
			// from parameters
			enemy.kind = Fj0;
		    enemy.x = X0;
			enemy.y = Y0;
			enemy.xSpeed = Sp0;
			enemy.ySpeed = Yp0;
			enemy.life = Life_E[Fj0];
			enemy.late = 0;
			enemy.item = It;

			// bosses
			if(Fj0 == 4 || Fj0 == 10 || Fj0 == MaxEnemy1+3)
				boss = enemy;

		}
	} // end of initEnemy

	public void deleteEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}


	public void doEnemy1() {
	     boolean die = false;

	     Iterator<Enemy> it = enemies.iterator();
	     while(it.hasNext()) {
	    	 Enemy enemy = it.next();
	    	 die = false;
	    	 enemy.x -= enemy.xSpeed;
	    	 enemy.y -= enemy.ySpeed;

	    	 if(enemy.y < -31 || enemy.y > 231)
	    		 die = true;

	    	 if(enemy.x < -31)
	    		 die = true;

	    	 if(enemy.late > 0) {
	    		 enemy.late--;
	    	 } else {

	    		 if(enemy.life > 0 && enemy.kind > 0) {

	    			 switch(enemy.kind) {

	    			 case 4 : {
	               if ((X < 0) && (Speed > 0)) || ((X > 294)
	                && (areaScroll > 2100) && (Speed < 0)) then Speed = -Speed;
	               if ((Y < 0) && (YSpeed > 0)) || ((Y > 167) && (YSpeed < 0))
	                then YSpeed = -YSpeed;
	    			 }

	    			 int areaScroll2 = (areaScroll - 2000) % 600;
	    			 switch(areaScroll2) {
	    			 case 0 : { YSpeed = 0; Speed = 3; }
	    			 case 10:
	    			 case 20:
	    			 case 30:
	    			 case 40:
	    			 case 50:
	    			 case 60:
	    			 case 70:
	    			 case 80:
	    			 case 90:
	    			 case 100 : {
	                  InitLeser(Less,X+3,Y+4,-8,0,1,0);
	                  InitLeser(Less,X,Y+11,-8,0,6,0);
	                  InitLeser(Less,X+3,Y+18,-8,0,1,0);
	                 }
	    			 case 120 : YSpeed = 5;
	                121..131,151..161,181..191: if (Mozgas % 3 == 0) {
	                  InitLeser(Less,X+3,Y+4,-12,0,1,0);
	                  InitLeser(Less,X+3,Y+18,-12,0,1,0);
	                 }
	    			 case 200 : { YSpeed = 2; Speed = 5; }
	                201..309:  if (Mozgas % 8) == 0 then {
	                  InitLeser(Less,X,Y+11,-8,0,6,0);
	                 }
	    			 case 310 : { YSpeed = 1; Speed = 1; }
	    			 case 320 : {
	                  InitLeser(Less,X+16,Y+16,6,0,12,0);
	                  InitLeser(Less,X+16,Y+16,-6,0,12,0);
	                  InitLeser(Less,X+16,Y+16,0,6,12,0);
	                  InitLeser(Less,X+16,Y+16,0,-6,12,0);
	                 }
	    			 case 330 : {
	                  InitLeser(Less,X+16,Y+16,-6,-6,12,0);
	                  InitLeser(Less,X+16,Y+16,6,-6,12,0);
	                  InitLeser(Less,X+16,Y+16,-6,6,12,0);
	                  InitLeser(Less,X+16,Y+16,6,6,12,0);
	                 }
	    			 case 340 : YSpeed = 2+Random(1); Speed = 2+Random(1); break;
	    			 case 350:
	    			 case 360:
	    			 case 370:
	    			 case 410:
	    			 case 420:
	    			 case 430:
	    			 case 460:
	    			 case 470:
	    			 case 480: {
	                  InitLeser(Less,X+16,Y+16,6,0,7,0);
	                  InitLeser(Less,X+16,Y+16,-6,0,7,0);
	                  InitLeser(Less,X+16,Y+16,0,6,7,0);
	                  InitLeser(Less,X+16,Y+16,0,-6,7,0);
	                  InitLeser(Less,X+16,Y+16,-4,-4,7,0);
	                  InitLeser(Less,X+16,Y+16,4,-4,7,0);
	                  InitLeser(Less,X+16,Y+16,-4,4,7,0);
	                  InitLeser(Less,X+16,Y+16,4,4,7,0);
	                 }
	    			 case 510:
	    			 case 520:
	    			 case 530:
	    			 case 540:
	    			 case 550:
	    			 case 560:
	    			 case 570:
	    			 case 580:
	    			 case 590: {
	                 InitLeser(Less,X+3,Y+4,-8,0,1,0);
	                 InitLeser(Less,X,Y+11,-8,0,6,0);
	                 InitLeser(Less,X+3,Y+18,-8,0,1,0);
	                }
	    			 } // end switch areaScroll2

	               if ((Life < 150) && (Y >= 20) && (Y < 180))
	                {
	                 InitLeser(Less,X+3,Y+4,-16,0,12,0);
	                 InitLeser(Less,X,Y+11,-16,0,12,0);
	                 InitLeser(Less,X+3,Y+18,-16,0,12,0);
	                 InitLeser(Less,X+3,Y+4,16,0,12,0);
	                 InitLeser(Less,X,Y+11,16,0,12,0);
	                 InitLeser(Less,X+3,Y+18,16,0,12,0);
	                 Die = True; Boss = nil;
	                 nextLevel();
	                }
	              }
	          5 : {
	               if (Mozgas % 15 == 0)
	                {
	                 InitLeser(Less,X+16,Y+16,6,0,8,0);
	                 InitLeser(Less,X+16,Y+16,-6,0,8,0);
	                 InitLeser(Less,X+16,Y+16,0,6,8,0);
	                 InitLeser(Less,X+16,Y+16,0,-6,8,0);
	                 InitLeser(Less,X+16,Y+16,-4,-4,8,0);
	                 InitLeser(Less,X+16,Y+16,4,-4,8,0);
	                 InitLeser(Less,X+16,Y+16,-4,4,8,0);
	                 InitLeser(Less,X+16,Y+16,4,4,8,0);
	                }
	              }
	          6 : {
	               if (Mozgas % 15 == 0)
	                {
	                 InitLeser(Less,X+6,Y+16,-6,0,7,0);
	                }
	              }
	          8 : {
	               if (Mozgas+X-15) % 30 == 0 then
	                {
	                 InitLeser(Less,X+6,Y+16,-10,0,11,0);
	                }
	               if (Y > 180) && (YSpeed < 0) then YSpeed = -YSpeed;
	               if (Y < 0) && (YSpeed > 0) then YSpeed = -YSpeed;
	               if X < 100 then Speed = 10;
	              }
	          9 : {
	               if X == 200 then
	                {
	                 YSpeed = Random(2)*2-1;
	                }
	               Else if (X == 110) || (X == 40) then
	                { YSpeed = YSpeed*2; Speed = Speed - 4; }
	              }
	          10 : {
	                if Mozgas % 10 == 0 then
	                 {
	                  InitLeser(Less,X+6,Y+10,-10,0,12,0);
	                  InitLeser(Less,X+6,Y+22,-10,0,12,0);
	                 }
	                if Mozgas % 87 == 0 then
	                 {
	                  InitEnemy(Enemies,X+16,Y+20,2,-4,MaxEnemy1+4,0);
	                 }
	                if Mozgas % 125 == 0 then
	                 {
	                  InitEnemy(Enemies,X+16,Y+20,15,0,9,0);
	                 }
	                if Mozgas % 210 == 0 then
	                 {
	                  InitEnemy(Enemies,X+16,Y+20,2,-4,8,1+Random(6));
	                 }
	                if (Y > 180) && (YSpeed < 0) then YSpeed = -YSpeed;
	                if (Y < 0) && (YSpeed > 0) then YSpeed = -YSpeed;
	                if Mozgas % 75 == 0 then YSpeed = YSpeed * 4;
	                if Mozgas % 75 == 32 then YSpeed = YSpeed div 4;
	                if X < 200 then Speed = -10;
	                if X > 320 then Speed = 3;
	               }
	          MaxEnemy1+1 : {
	                         if (Y < (((Mozgas + X) div 20) % 4)* 25) then YSpeed = -2;
	                         if (Y > 125+(((Mozgas + X) div 20) % 4)* 25) then YSpeed = 2;
	                         if Mozgas % 20 == 0 then InitLeser(Less,X+6,Y+16,-6,0,8,0);
	                        }
	          MaxEnemy1+2 : if Mozgas % 15 == 0 then InitLeser(Less,X+16,Y+16,-Speed_L[4],0,4,0);

	          MaxEnemy1+3 : {
	                         if Mozgas % 25 == 0 then
	                          {
	                           InitLeser(Less,X+16,Y+16,6,0,8,0);
	                           InitLeser(Less,X+16,Y+16,-6,0,8,0);
	                           InitLeser(Less,X+16,Y+16,0,6,8,0);
	                           InitLeser(Less,X+16,Y+16,0,-6,8,0);
	                           InitLeser(Less,X+16,Y+16,-4,-4,8,0);
	                           InitLeser(Less,X+16,Y+16,4,-4,8,0);
	                           InitLeser(Less,X+16,Y+16,-4,4,8,0);
	                           InitLeser(Less,X+16,Y+16,4,4,8,0);
	                          }
	                         if (Y < 0) && (YSpeed > 0) then YSpeed = -YSpeed;
	                         if (Y > 190) && (YSpeed < 0) then YSpeed = -YSpeed;
	                         if (X < 0) && (Speed > 0) then Speed = -Speed;
	                         if (X > 310) && (Speed < 0) then Speed = -Speed;
	                         CASE Mozgas % 200 OF
	                          30 : YSpeed = YSpeed*2;
	                          100 : YSpeed = YSpeed div 2;
	                          70 : Speed = Speed * 3;
	                          170 : Speed = Speed div 3;
	                         }
	                        }
	          MaxEnemy1+4 : {
	                         if Mozgas % 15 == 0 then InitLeser(Less,X+6,Y+16,-10,0,5,0);
	                         if (Y > 190) && (YSpeed < 0) then YSpeed = -YSpeed;
	                         if (Y < 0) && (YSpeed > 0) then YSpeed = -YSpeed;
	                         if X < 100 then Speed = 15;
	                        }

	    			 } // end switch kind
	    		 }

	    	 }

	      if(die) {
	    	  it.remove();
	      }
	     }

	} // end of doEnemy1

	public void KillAllThings() {
		enemies.clear();
		lesses.clear();
	}

	public void putPic(int X1, int Y1, int X2, int Y2, Picture pic) {
		int Xv, Yv, c;

		for(int y = 0; y < Y2; y++) {
			for(int x = 0; x < X2; x++) {
				c = pic.get(x, y);
				if(c < 255) {
					Xv = X1 + x;
					Yv = Y1 + y;
					if(Xv >= 0 && Yv >= 0 && Xv < 320 && Yv < 200)
						gun.vscr.set(Xv, Yv, c);
				}
			}
		}
	} // end putPic




public void run() {
 gun.init();
load();
 diff = 0;

 quitAll = false;
 changed = true;

 do {
 switch(menu) {
 case 0 :
        do {
         SetCounter;
         splash -= 2;
         screen(splash);
         WaitFor(Speed);
        while(splash > 1);
        GAME;
        Changed = True;
       break;
 case 1 : {
        if Diff == 0 then Diff = 1 Else
         if Diff == 1 then Diff = -1 Else
          Diff = 0;
        Changed = False;
       }
 case 2 : {
        REPEAT
         SetCounter;
         Splash = Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        CREDITS;
        REPEAT
         SetCounter;
         Splash = Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        Changed = True;
       }
 case 3 : {
        Assign(fP,'hiscore.dat');

        ReSet(fP);
        if IOResult <> 0 then
         {
          For i = 0 to 9 do
           Pontszam[i].Pontszam = 0;
          ReWrite(fP);
          For i = 0 to 9 do
           Write(fP,Pontszam[i]);
          Close(fP);
          ReSet(fP);
         }

        For i = 0 to 9 do
         Read(fP,Pontszam[i]);
        Close(fP);
        REPEAT
         SetCounter;
         Splash = Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        ClrVscr(#0);
        SetScr;
        Splash = 63;
        Screen(Splash);
        ClrVscr(#148);
        DrawPoints(l);
        Anim(0);
        SetScr;
        Repeat Until KeyPressed;
        While KeyPressed do ch = ReadKey;
        REPEAT
         SetCounter;
         Splash = Splash -2;
         Screen(Splash);
         WaitFor(Speed);
        UNTIL Splash <= 1;
        Changed = True;
       }
 case 4 : {
        For i = 0 to 9 do
         {
          Pontszam[i].Pontszam = 0;
          Pontszam[i].Nev = '';
         }
        Changed = True;
        SaveScore;
       }
 case MaxMenu:
	 quitAll == true;
	 break;
 } // end switch
 } while(!quitAll);


 Anim3();
 gun.finish();

 System.out.println("Thanks for checking out!");

}


} // end of KLONGUN

/*

public void PutEnemies(var xEnemies : pEnemies);
 VAR EnemyPos : pEnemies;
     i,j : Integer;
     Die : Boolean;
 {
  EnemyPos = xEnemies;
  WHILE EnemyPos <> nil DO
   {
    With EnemyPos^ do
     {
      Die = False;
      if Life > 0 then
       {
        PutPic(X,Y,32,32,Enemy1[Abra[Faj]]);
        if not cheat then
         For i = 0 to 31 do
          For j = 0 to 31 do
           if Enemy1[Abra[Faj],i,j] < #255 then
            {
             Xv = X+j-pX;
             Yv = Y+i-pY;
             if (Xv >= 0) && (Xv < 50) && (Yv >= 0) && (Yv < 30)
              && (Ships[pShip,Yv,Xv] < #255) && (pLife > 0) then
              { pLife = 0; pTime = 50; }
            }
       }
      Else
       {
        if Time > 0 then
         {
          PutPic(X+6-(200-Time*4),Y+6-(50-Time),16,16,Death[1]);
          PutPic(X+16+(200-Time*4),Y+6-(50-Time),16,16,Death[2]);
          PutPic(X+6-(200-Time*4),Y+16+(50-Time),16,16,Death[3]);
          PutPic(X+16+(200-Time*4),Y+16+(50-Time),16,16,Death[4]);
          if not Cheated then
           {
            Str((2+Diff)*Life_E[Faj] div 10,PontSzov);
            PontSzov = PontSzov+'p';
           } Else Pontszov = '0p';
          WriteXY(X+16-(Length(PontSzov)*13) div 2,Y+8-(300-Time*6),#5,PontSzov);
          if (Time > 30) && (Item > 0) then PutPic(X+8,Y+8,16,16,Items[Item]);
          Time = Time - 1;
         } Else Die = True;
       }
     }
    if Die then
     {
      if EnemyPos^.Prev == nil then
       {
        DeleteEnemy(EnemyPos);
        xEnemies = EnemyPos;
       }
      Else
       {
        DeleteEnemy(EnemyPos);
        EnemyPos = EnemyPos^.Next;
       }
     } Else
      EnemyPos = EnemyPos^.Next;
   }
 }

public void PutLesers(var xLess : pLess);
 VAR X1,Y1 : Integer;
     EnemyPos : pEnemies;
     LessPos : pLess;
     Talalat : Boolean;
 {
  LessPos = xLess;
  WHILE LessPos <> nil DO
   {
    With LessPos^ do
     {
      Talalat = False;
      PutPic(X0,Y0,16,5,Leser[Faj]);
      For i = 0 to 4 do
       For j = 0 to 15 do
        if (Leser[Faj,i,j] < #255) && (not Talalat) then
         {
          Xv = X0+j;
          Yv = Y0+i;
          if (Xv >= 0) && (Xv < 320) && (Yv >= 0) && (Yv < 200) then
           {
            if Side > 0 then
             {
              EnemyPos = Enemies;
              WHILE EnemyPos <> nil DO
               {
                if EnemyPos^.Life > 0 then
                 {
                  X1 = Xv-EnemyPos^.X;
                  Y1 = Yv-EnemyPos^.Y;
                  if (X1 >= 0) && (X1 < 32) && (Y1 >= 0) && (Y1 < 32)
                   && (Enemy1[Abra[EnemyPos^.Faj],Y1,X1] < #255) then
                  {
                   Talalat = True;
                    EnemyPos^.Life = EnemyPos^.Life-(Dmg-Diff*Dmg div 2);
                    if EnemyPos^.Life <= 0 then
                     {
                      EnemyPos^.Time = 50;
                      if not Cheated then
                       Pontok = Pontok + (2+Diff)*Life_E[EnemyPos^.Faj] div 10;
                      if EnemyPos == Boss then Boss = nil;
                      if EnemyPos^.Item > 0 then
                      CASE EnemyPos^.Item OF
                        1 : {
                            if pLife > 0 then pLife = pLife + 20;
                            if pLife > 100 then pLife = 100;
                           }
                        2 : if pLeser[1] < #5 then pLeser[1] = Chr(Ord(pLeser[1]) + 1);
                        3..5 : if pLeser[EnemyPos^.Item-1] < #3 then
                                pLeser[EnemyPos^.Item-1] = Chr(Ord(pLeser[EnemyPos^.Item-1]) + 1);
                        6 : if pFire > 2 then pFire = 2;
                        7 : if pFire > 1 then pFire = 1;
                       }
                     }
                   }
                 }
                EnemyPos = EnemyPos^.Next
               }
             }
            Else
             if (pLife > 0) && (not cheat) then
              {
               Xv = Xv-pX;
               Yv = Yv-pY;
               if (Xv >= 0) && (Xv < 50) && (Yv >= 0) && (Yv < 30)
                && (Ships[pShip,Yv,Xv] < #255) then
                 { pLife = pLife - (Dmg+Diff*Dmg div 2); Talalat = True;
                  if pLife <= 0 then pTime = 50;
                  if pLife < 0 then pLife = 0; }
              }
           }
         }
     }
    if Talalat then
     {
      if LessPos^.Prev <> nil then
       {
        DeleteLeser(LessPos);
        LessPos = LessPos^.Next;
       }
      Else
       {
        DeleteLeser(LessPos);
        xLess = LessPos;
       }
     } Else LessPos = LessPos^.Next;
   }
 }

public void Palya;
 {
  if Mozgas > 100 then
  switch (currentArea) {
  case  0 : {
        if (Mozgas < 500) && ((Mozgas % 25) == 0) then
          InitEnemy(Enemies,320,70+((Mozgas div 25) % 2)*60,Speed_E[6],((Mozgas div 25) % 2)*2-1,6,0);
        if Mozgas == 500 then InitEnemy(Enemies,320,Random(20)+90,4,0,7,1);
        if (Mozgas > 500) && (Mozgas < 600) && (Mozgas % 20 == 0) then
          InitEnemy(Enemies,250,-10,1,-2,MaxEnemy1+2,0);
        if (Mozgas > 600) && (Mozgas < 700) && (Mozgas % 20 == 0) then
          InitEnemy(Enemies,250,210,1,2,MaxEnemy1+2,0);
        if Mozgas == 700 then InitEnemy(Enemies,250,210,1,2,7,2);
        if Mozgas == 800 then InitEnemy(Enemies,320,Random(20)+90,2,0,7,1);
        if Mozgas == 990 then InitEnemy(Enemies,320,Random(20)+90,2,0,7,6);
        if (Mozgas > 750) && (Mozgas < 1040) && (Mozgas % 25 == 0) then
          InitEnemy(Enemies,320,60+((Mozgas div 20) % 4)* 25,1,2,MaxEnemy1+1,0);
        if Mozgas == 1100 then
         {
          InitEnemy(Enemies,320,100,3,3,MaxEnemy1+3,2);
          Boss_Del = 200;
          InitEnemy(Enemies,320,Random(20)+90,2,0,7,1);
         }
        if Mozgas > 2200 then Mozgas = Mozgas- Boss_Del;
        if (Mozgas > 1100) && (Boss == nil) then NextLevel;
       }
   case 1 : {
        if (Mozgas < 2000) && ((Mozgas % 19) == 0) then
         {
          InitEnemy(Enemies,0,0,0,0,0,0);
         }
        if (Mozgas < 2000) && ((Mozgas % 32) == 0) then
         {
          InitLeser(Less,0,0,0,0,0,0);
         }
        if (Mozgas < 2000) && (Mozgas % 115 == 0) then
         CASE (Mozgas div 115) OF
          2 : InitEnemy(Enemies,320,Random(160)+20,4,0,7,1);
          4 : InitEnemy(Enemies,320,Random(160)+20,4,0,7,6);
          6 : InitEnemy(Enemies,320,Random(160)+20,4,0,7,3);
         }
        if Mozgas == 1000 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,1);
        if Mozgas == 1850 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,2);
        if Mozgas == 1250 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,5);
        if Mozgas == 1700 then InitEnemy(Enemies,320,Random(160)+20,4,0,7,1);
        if Mozgas == 2000 then
         {
          InitEnemy(Enemies,320,88,3,0,4,0);
          Boss_Del = 3600;
         }
        if Mozgas > 10000 then Mozgas = Mozgas- Boss_Del;
       }
   case 2 : {
        if (Mozgas < 500) && (Mozgas % 20 == 0) then
         {
          InitEnemy(Enemies,320,88,Speed_E[5],(Speed_E[5] div 3)*(((Mozgas div 20) % 2)*2-1),5,0);
         }
        if Mozgas == 180 then InitEnemy(Enemies,320,Random(20)+90,4,0,7,1);
        if Mozgas == 360 then InitEnemy(Enemies,320,Random(20)+90,4,0,7,1);
        if (Mozgas > 530) && (Mozgas < 1000) && (Mozgas % 3 == 0) then
         InitEnemy(Enemies,320,Random(190),15,0,9,0);
        if Mozgas == 620 then InitEnemy(Enemies,320,68+Random(40),3,0,7,1);
        if Mozgas == 800 then InitEnemy(Enemies,320,68+Random(40),3,0,7,3);
        if Mozgas == 950 then InitEnemy(Enemies,320,68+Random(40),3,0,7,7);
        if (Mozgas > 1000) && (Mozgas < 1300) && (Mozgas % 25 == 0) then
         {
          InitEnemy(Enemies,310,199,2,5,8,0);
          InitEnemy(Enemies,270,-20,2,-5,8,0);
         }
        if Mozgas == 1300 then
         {
          InitEnemy(Enemies,310,199,2,5,8,4);
          InitEnemy(Enemies,270,-20,2,-5,8,3);
         }
        if (Mozgas > 1000) && (Mozgas < 1300) && (Mozgas % 85 == 40) then
         InitEnemy(Enemies,320,Random(180),4,0,7,1);
        if Mozgas == 1400 then
         {
          InitEnemy(Enemies,320,0,1,0,7,1);
          InitEnemy(Enemies,320,40,1,0,7,4);
          InitEnemy(Enemies,320,120,1,0,7,3);
          InitEnemy(Enemies,320,160,1,0,7,1);
          InitEnemy(Enemies,320,88,1,2,10,1);
          Boss_Del = 4200;
         }
        if Mozgas > 10000 then Mozgas = Mozgas- Boss_Del;
        if (Mozgas > 1400) && (Boss == nil) then NextLevel;
       }
       }
  }
 }

public void Temp;
 {
  Pontok = 0;
  pFire = 3;
  pShip = 1;
  pLife = 100;
  pY = 75; pX = -40;
  SzumLife = 4;
  pLogged = False;
  pLogTime = 0;
  pL = 0;
  pLeser[0] = #1; pLeser[1] = #1;
  pLeser[2] = #0; pLeser[3] = #0;
  pLeser[4] = #0;
  ActualPalya = 0;
  Mozgas = 0;
  Boss = nil;
  Enemies = nil; Less = nil;
  Randomize;
  Cheat = False;
  PontSzov = '1p';
  Splash = 1;

  Assign(fP,'hiscore.dat');

  ReSet(fP);
  if IOResult <> 0 then
   {
    For i = 0 to 9 do
     Pontszam[i].Pontszam = 0;
    ReWrite(fP);
    For i = 0 to 9 do
     Write(fP,Pontszam[i]);
    Close(fP);
    ReSet(fP);
   }

  For i = 0 to 9 do
   Read(fP,Pontszam[i]);
  Close(fP);
  Quit = False;
  Cheated = False;
 }

public void saveScore()
 {
  Assign(fP,'hiscore.dat');
  ReWrite(fP);
  For i = 0 to 9 do
   Write(fP,Pontszam[i]);
  Close(fP);
 }

public void game()
 {
  Temp();
  addStars();

  REPEAT
   if Splash < 63 then
    {
     Splash = Splash + 2;
     Screen(Splash);
    }
   SetCounter; Palya;
   DoEnemy1(Enemies); DoLeser(Less);
   if KeyPressed then
    { ch1 = ReadKey;
     CASE ch1 OF
      #0 : ch2 = ReadKey;
      #27 : Quit = True;
      '1' : pLeser[0] = #1;
      '2' : if (pLeser[2] > #0) then pLeser[0] = #2;
      '3' : if (pLeser[3] > #0) then pLeser[0] = #3;
      '4' : if (pLeser[4] > #0) then pLeser[0] = #4;
      'c','C' : { Cheat = Cheat xor True; Cheated = True; }
     } }
   ReadMouse;
   if pLife > 0 then {
    if pL > 0 then pL = pL - 1 Else
    if ((M_Button && 1) == 1) && (pL == 0) then
     {
      CASE pLeser[0] OF
       #1 : CASE pLeser[1] OF
            #1 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,1,1);
            #2 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,1,1);
                }
            #3 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,2,1);
                }
            #4 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],3,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-3,1,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,3,1);
                }
            #5 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],3,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-3,2,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],1,3,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],-1,3,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[1],0,3,1);
                }
           }
       #2 : CASE pLeser[2] OF
            #1 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[4],0,4,1);
            #2 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[5],0,5,1);
            #3 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[6],0,6,1);
           }
       #3 : CASE pLeser[3] OF
            #1 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],6,0,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,6,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-6,0,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,-6,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,4,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,-4,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,4,7,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,-4,7,1);
                }
            #2 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],6,0,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,6,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-6,0,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,-6,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,4,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,-4,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,4,8,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,-4,8,1);
                }
            #3 : {
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],6,0,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,6,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-6,0,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],0,-6,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,4,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],4,-4,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,4,9,1);
                 InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],-4,-4,9,1);
                }
           }
       #4 : CASE pLeser[4] OF
            #1 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[10],0,10,1);
            #2 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[11],0,11,1);
            #3 : InitLeser(Less,pX+ShipX[pShip],pY+ShipY[pShip],Speed_L[12],0,12,1);
           }
      }
      pL = Late_L[Ord(pLeser[0])*3+Ord(pLeser[Ord(pLeser[0])])];
     }
    if M_CurY < 99 then pY = pY - Speed_F[pFire];
    if M_CurY > 101 then pY = pY + Speed_F[pFire];
    if pLogged || (pX >= 5) then
      {
       if M_CurX < 319 then pX = pX - Speed_F[pFire];
       if M_CurX > 321 then pX = pX + Speed_F[pFire];
      }
     if pX < 5 then { if pLogged then pX = 5
       else pX = pX + Speed_M }
     if not pLogged then
      {
       pLogTime = pLogTime +1;
       if pLogTime >= 50 then pLogged = True;
      }
   }
   if pX > 274 then pX = 274;
   if pY < -10 then pY = -10;
   if pY > 180 then pY = 180;
   Mozgas = Mozgas+1;

   ClrVscr(#0); DoStars(Stars);
   PutLesers(Less); PutEnemies(Enemies);
   if pLife > 0 then {
    PutPic(pX,pY,50,30,Ships[pShip]);
    PutPic(pX-17,pY+9,17,14,Fires[pFire,(Mozgas % 3)+1]);
   } Else if pShip > 0 then {
    PutPic(pX+6-(200-pTime*4),pY+6-(50-pTime),16,16,Death[1]);
    PutPic(pX+16+(200-pTime*4),pY+6-(50-pTime),16,16,Death[2]);
    PutPic(pX+6-(200-pTime*4),pY+16+(50-pTime),16,16,Death[3]);
    PutPic(pX+16+(200-pTime*4),pY+16+(50-pTime),16,16,Death[4]);
    if pTime > 0 then pTime = pTime - 1 Else
     if SzumLife > 1 then {
      SzumLife = SzumLife - 1; pLife = 100;
      pX = -40; pY = 75; pLogged = False;
      pLogTime = 0;
     } Else { pShip = 0; SzumLife = 0; }
   }

   PutPic(0,190,120,10,Status);
   if SzumLife > 0 then PutPic(120,190,10,10,Stat2[SzumLife,3]);
   For i = 1 to Round((pLife/100)*115) do
    For j = 3 to 6 do Vscr^[190+j,2+i] = #2;
   For m = 1 to 4 do
    if (pLeser[m] == #0) then PutPic(270+m*10,190,10,10,Stat2[m,0]) Else
     if (pLeser[m] > #0) && (pLeser[0] <> Chr(m)) then PutPic(270+m*10,190,10,10,Stat2[m,1]) Else
      if (pLeser[m] > #0) && (pLeser[0] == Chr(m)) then PutPic(270+m*10,190,10,10,Stat2[m,2]);
   if Boss <> nil then {
    PutPic(0,0,120,10,Status); WriteXY(125,0,#4,'BOSS');
    For i = 1 to Round((Boss^.Life/Life_E[Boss^.Faj])*115) do
     For j = 3 to 6 do Vscr^[j,2+i] = #4; }

   if SzumLife <= 0 then WriteXY(127,117,#5,'GAME OVER');
   if Cheat then WriteXY(0,0,#7,'cheat on');
   if ActualPalya == 3 then WriteXY(127,117,#5,'YOU WON !') Else
   if (Mozgas > 25) && (Mozgas < 200) then
    {
     CASE Mozgas OF
      25..124 : {
                Str(ActualPalya+1,s);
                WriteXY(137,Round(90-((125-Mozgas))*(1-sqr(sin((Mozgas-25)/10)))),#5,'LEVEL '+s);
               }
      125..149 : WriteXY(137,90,#5,'LEVEL '+s);
      150..199 : {
                  PutPic(154-(Mozgas-150)*4,84-Mozgas+150,16,16,Death[1]);
                  PutPic(166+(Mozgas-150)*4,84-Mozgas+150,16,16,Death[2]);
                  PutPic(154-(Mozgas-150)*4,96+Mozgas-150,16,16,Death[3]);
                  PutPic(166+(Mozgas-150)*4,96+Mozgas-150,16,16,Death[4]);
                 }

     }
    }

   Str(Pontok,Ponts);
   Ponts = 'Pontszam ' + ' ' + Spaced(Ponts);
   WriteXY(165,0,#2,Ponts);

   SetScr;
   SetPos;
   WaitFor(Speed);
  UNTIL Quit;

  Anim2; Splash = 63; Screen(Splash);
  DrawPoints(10);
  if Pontok > Pontszam[9].Pontszam then
   {
    Str(Pontok,Ponts);
    Ponts = '    You have reached '+Spaced(Ponts)+' !!';
    WriteXY(1,170,#0,Ponts);
    WriteXY(0,170,#3,Ponts);
    WriteXY(1,182,#0,'Enter Name');
    WriteXY(0,182,#4,'Enter Name');
   }
  Anim(0);
  SetScr;

  IF Pontok > Pontszam[i].Pontszam THEN {
   PontSzov = ''; k = 0;
   Quit = False;
   Repeat
    zh = ReadKey;
    CASE zh OF
     #0 : ch = ReadKey;
     #8 : if Length(Pontszov) > 0 then PontSzov[0] = Chr(Ord(Pontszov[0])-1);
     'a'..'z','A'..'Z',' ' : if Length(PontSzov) < 20 then
      PontSzov = PontSzov + zh;
     #27, #13 : {
                 if PontSzov == '' then Pontszov = ' ';
                 Quit = True;
                }
    }
    DrawPoints(10);
    WriteXY(1,182,#0,'Enter Name');
    WriteXY(0,182,#4,'Enter Name');
    WriteXY(121,182,#0,PontSzov);
    WriteXY(120,182,#1,PontSzov);
    Str(Pontok,Ponts);
    Ponts = '    You have reached '+Spaced(Ponts)+' !!';
    WriteXY(1,170,#0,Ponts);
    WriteXY(0,170,#3,Ponts);
    SetScr;
   Until Quit;
   Pontszam[9].Pontszam = Pontok;
   Pontszam[9].Nev = Pontszov;
   k = 9; l = 10;
   Repeat
    if Pontok > Pontszam[k-1].Pontszam then
     {
      Pontszam[k] = Pontszam[k-1];
      Pontszam[k-1].Pontszam = Pontok;
      Pontszam[k-1].Nev = Pontszov;
      l = k-1;
     }
    k = k - 1;
   Until (k == 0);
   ClrVscr(#148);
   DrawPoints(l);
   Anim(1);
   SetScr;
  }

  Repeat Until KeyPressed;
  While KeyPressed do ch = ReadKey;
  REPEAT
   SetCounter;
   Splash = Splash -2;
   Screen(Splash);
   WaitFor(Speed);
  UNTIL Splash <= 1;

  RemoveStars(Stars);
  KillAllthings(Enemies,Less);
  SaveScore;
 }

FUNCTION MENU : Integer;
 VAR MenuQuit : Boolean;

 public void DrawMenu;
  VAR MP2 : Integer;
      DStr : String;
  {
   CASE Diff OF
    -1 : DStr = 'Easy';
    0 : DStr = 'Medium';
    1 : DStr = 'Hard';
   }
   ClrVscr(#1);
   WriteXY(116,5,#5,'KlonGun');
   WriteXY(115,6,#5,'KlonGun');
   WriteXY(114,5,#5,'KlonGun');
   WriteXY(115,4,#5,'KlonGun');
   WriteXY(115,5,#148,'KlonGun');
   For MP2 = 0 to MaxMenu do
    {
     WriteXY(70,71+20*MP2,#0,MenuSzov[MP2]);
     WriteXY(71,71+20*MP2,#0,MenuSzov[MP2]);
     WriteXY(70,70+20*MP2,#6,MenuSzov[MP2]);
     if MP2 == 1 then
      {
       WriteXY(200,71+20*MP2,#0,DStr);
       WriteXY(201,71+20*MP2,#0,DStr);
       WriteXY(200,70+20*MP2,#4,DStr);
      }
    }
   PutPic(5,60+20*MenuPont,50,30,Ships[1]);
  }

 {
  if Changed then
   {
    ClrVscr(#0);
    SetScr;
    Splash = 63;
    Screen(Splash);
    MenuQuit = False;
    DrawMenu;
    Anim(0);
   }
  REPEAT
   DrawMenu;
   SetScr;
   CASE ReadKey OF
    #0 : CASE ReadKey OF
          #72 : if MenuPont > 0 then MenuPont = MenuPont -1;
          #80 : if MenuPont < MaxMenu then MenuPont = MenuPont +1;
         }
    #27 : { MenuQuit = True; MENU = MaxMenu; }
    #13 : { MenuQuit = True; MENU = MenuPont; }
   }
  UNTIL MenuQuit;
  WHILE KeyPressed DO ch = ReadKey;
 }

public void CREDITS;
 VAR pos,p : Integer;
 {
  ClrVscr(#0);
  SetScr;
  Splash = 1;
  Screen(Splash);
  ClrVscr(#148);
  SetScr;
  pos = 0;
  REPEAT
   SetCounter;
   Splash = Splash + 2;
   Screen(Splash);
   WaitFor(Speed);
  UNTIL Splash >= 62;
  Splash = 63;
  Screen(Splash);
  REPEAT
   ClrVscr(#148);
   pos = pos + 1;
   For p = 0 to MaxCredit do
    {
     WriteXY(161-Length(CreditSzov[p])*9 div 2,200+p*20-pos,#0,CreditSzov[p]);
     WriteXY(160-Length(CreditSzov[p])*9 div 2,201+p*20-pos,#0,CreditSzov[p]);
     WriteXY(160-Length(CreditSzov[p])*9 div 2,200+p*20-pos,#2,CreditSzov[p]);
    }
   SetScr;
  UNTIL (pos >= 400+(MaxCredit+1)*20) || KeyPressed;
  WHILE KeyPressed DO ch = ReadKey;
 }

*/
