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
import hu.csega.klongun.screen.TVscr;
import hu.csega.klongun.swing.KlonGunCanvas;
import hu.csega.klongun.swing.KlonGunControl;
import hu.csega.klongun.swing.KlonGunFrame;
import hu.csega.klongun.swing.KlonGunKeyBuffer;

public class KlonGun {

	private static final String HISCORES = "Hiscores";
	// Array sizes
	public static final int MaxShips = 1;
	public static final int MaxFires = 3;
	public static final int MaxStars = 20;
	public static final int MaxEnemy1 = 10;
	public static final int MaxEnemy2 = MaxEnemy1+3;
	public static final int MaxLesers = 12;
	public static final int MaxDeaths = 4;
	public static final int MaxItem = 7;

	// Object speeds
	public static final int Speed1 = 1;
	public static final int Speed2 = 2;
	public static final int Speed3 = 3;
	public static final int Speed_M = 3;

	// overall game speed?
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
	public static final int[] Abra = new int[] {0,1,2,3,4,5,6,7,8,9,5,4,4,5};

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


    // super boss does this
    public List<Enemy> addEnemies = new ArrayList<>();


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

    public int areaScroll; // = areaScroll
    public int bossDel;

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
    public String scoreText;
    public int scoreText1; // PontSzov
    public int scoreText2; // Ponts
    public int splash;

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

    public static KlonGunFrame frame;
    public static KlonGunCanvas canvas;
    public static KlonGunControl control;
    public static KlonGunKeyBuffer keyBuffer;

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 126; i++)
			sinus[i] = (int) Math.round(Math.sin(i / 20) * 100);

		//////////////////////
		KlonGun kg = new KlonGun();
		frame = new KlonGunFrame(kg);
		control = frame.getControl();
		keyBuffer = control.keyBuffer;
		canvas = frame.getCanvas();
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
             if (Pontszam <> 0 )
              if (no <> i )
               {
                Str(Pontszam,Ponts);
                gun.writeXY(21,20+i*15,0,Nev);
                gun.writeXY(221,20+i*15,0,Spaced(Ponts));
                gun.writeXY(20,20+i*15,2,Nev);
                gun.writeXY(220,20+i*15,2,Spaced(Ponts));
               } else {
                Str(Pontszam,Ponts);
                gun.writeXY(21,20+i*15,0,Nev);
                gun.writeXY(221,20+i*15,0,Spaced(Ponts));
                gun.writeXY(20,20+i*15,1,Nev);
                gun.writeXY(220,20+i*15,1,Spaced(Ponts));
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
    		          gun.vscr.set(l, k, back.get(j, i));
    			}
    		}

    		canvas.repaint();
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

    		canvas.repaint();
    	} while(pos != 0);
    }

    public int f1(int x, double y) {
    	return (int)Math.round(y * sinus[(int)(Math.round(x + 503) % 126)]);
    }

    public int f2(int x, double z) {
    	return (int)Math.round(z * sinus[(int)(Math.round((x+m)*2 + 503) % 126)]);
    }

    public double sqr(double x) {
    	return x*x;
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

				if (FGGVY[k] >= 0 && FGGVY[k] < 200 && FGGVX[n] >= 0 && FGGVX[n] < 320)
					gun.vscr.set(n, k, back.get(FGGVX[n], FGGVY[k]));

				canvas.repaint();
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
			ships[i] = FileUtil.loadPic("ship" + (i+1), 50, 30);
		for(i = 0; i < MaxItem; i++)
			items[i] = FileUtil.loadPic("i_" + (i+1), 16, 16);
		for(i = 0; i < MaxDeaths; i++)
			deaths[i] = FileUtil.loadPic("ruin" + (i+1), 16, 16);
		for(i = 0; i < MaxLesers; i++)
			lesers[i] = FileUtil.loadPic("l" + (i+1), 16, 5);
		for(i = 0; i < MaxEnemy1; i++)
			enemyShips[i] = FileUtil.loadPic("a" + (i+1), 32, 32);
		status = FileUtil.loadPic("status1", 120, 10);

		for(i = 0; i < 4; i++) {
			for(j = 0; j < 4; j++) {
				String picName = String.valueOf(i+1) + j;
				stat2[i][j] = FileUtil.loadPic(picName, 10, 10);
			}
		}

		for(i = 0; i < MaxFires; i++) {
			for(j = 0; j < 3; j++) {
				String picName = "s_fire" + String.valueOf(i+1) + (j+1);
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

		if (fj0 == -1) {
			less.x = 320;
			less.y = RND.nextInt(200);
			less.kind = RND.nextInt(2) * 5; // 0 || 5
			less.xSpeed = -(Speed_L[less.kind] + RND.nextInt(Speed_L[less.kind] + 1));
			less.ySpeed = (RND.nextInt(3) - 1) * (RND.nextInt(Speed_L[less.kind] + 1) / 2);
			less.damage = Dmg_L[less.kind];
			less.side = side0;
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

			less.x += less.xSpeed;
			less.y += less.ySpeed;

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
				pFire = 1;
				leser[1] = 3;
				break;

			case 2:
				leser[1] = 4;
				leser[3] = 1;
				leser[4] = 1;
				break;
			}
		}
	} // end of nextLevel

	public void initEnemy(int X0, int Y0, int Sp0, int Yp0, int Fj0, int It) {
		Enemy enemy = new Enemy();
		addEnemies.add(enemy);

		if(Fj0 == -1) {
			// random generated meteor
			enemy.x = 320;
			enemy.y = RND.nextInt(150);
			enemy.kind = Fj0 = RND.nextInt(3);
			enemy.xSpeed = Speed_E[Fj0] + RND.nextInt(Speed_E[Fj0] + 1);
			enemy.ySpeed = (RND.nextInt(3) - 1) * (RND.nextInt(Speed_E[Fj0] + 1) / 2);
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
			if(Fj0 == 3 || Fj0 == 9 || Fj0 == MaxEnemy1+2)
				bosses.add(enemy);

		}
	} // end of initEnemy

	private boolean between(int v, int lower, int upper) {
		return (v >= lower && v <= upper);
	}

	private void switchAreaScroll2(Enemy enemy) {
		int areaScroll2 = (areaScroll - 2000) % 600;

		if (areaScroll2 < 1) {
			enemy.ySpeed = 0;
			enemy.xSpeed = 3;
			return;
		}

		if (areaScroll2 % 10 == 0 && areaScroll2 >= 10 && areaScroll2 <= 100) {
			initLeser(enemy.x + 3, enemy.y + 4, -8, 0, 1, 0);
			initLeser(enemy.x, enemy.y + 11, -8, 0, 6, 0);
			initLeser(enemy.x + 3, enemy.y + 18, -8, 0, 1, 0);
			return;
		}

		if (areaScroll2 == 120) {
			enemy.ySpeed = 5;
			return;
		}

		if (between(areaScroll2, 121, 131) || between(areaScroll2, 151, 161) || between(areaScroll2, 181, 191)) {
			if (areaScroll % 3 == 0) {
				initLeser(enemy.x + 3, enemy.y + 4, -12, 0, 1, 0);
				initLeser(enemy.x + 3, enemy.y + 18, -12, 0, 1, 0);
			}
			return;
		}

		if (areaScroll2 == 200) {
			enemy.ySpeed = 2;
			enemy.xSpeed = 5;
			return;
		}

		if (between(areaScroll2, 201, 309)) {
			if (areaScroll % 8 == 0) {
				initLeser(enemy.x, enemy.y + 11, -8, 0, 6, 0);
			}
			return;
		}

		if (areaScroll2 == 310) {
			enemy.ySpeed = 1;
			enemy.xSpeed = 1;
			return;
		}

		if (areaScroll2 == 320) {
			initLeser(enemy.x + 16, enemy.y + 16, 6, 0, 11, 0);
			initLeser(enemy.x + 16, enemy.y + 16, -6, 0, 11, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 0, 6, 11, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 0, -6, 11, 0);
			return;
		}
		if (areaScroll2 == 330) {
			initLeser(enemy.x + 16, enemy.y + 16, -6, -6, 11, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 6, -6, 11, 0);
			initLeser(enemy.x + 16, enemy.y + 16, -6, 6, 11, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 6, 6, 11, 0);
			return;
		}

		if (areaScroll2 == 340) {
			enemy.ySpeed = 2 + RND.nextInt(1);
			enemy.xSpeed = 2 + RND.nextInt(1);
			return;
		}

		if (areaScroll2 % 10 == 0 && areaScroll2 >= 350 && areaScroll2 <= 480) {
			initLeser(enemy.x + 16, enemy.y + 16, 6, 0, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, -6, 0, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 0, 6, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 0, -6, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, -4, -4, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 4, -4, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, -4, 4, 7, 0);
			initLeser(enemy.x + 16, enemy.y + 16, 4, 4, 7, 0);
			return;
		}

		if (areaScroll2 % 10 == 0 && areaScroll2 >= 510 && areaScroll2 <= 590) {
			initLeser(enemy.x + 3, enemy.y + 4, -8, 0, 1, 0);
			initLeser(enemy.x, enemy.y + 11, -8, 0, 6, 0);
			initLeser(enemy.x + 3, enemy.y + 18, -8, 0, 1, 0);
			return;
		}
	} // end areaScroll2

	private boolean switchEnemyKind(Enemy enemy) {
		boolean die = false;
		switch (enemy.kind) {

		case 3:
			if (((enemy.x < 0) && (enemy.xSpeed > 0)) ||
					((enemy.x > 294) && (areaScroll > 2100) && (enemy.xSpeed < 0)))
				enemy.xSpeed = -enemy.xSpeed;
			if (((enemy.y < 0) && (enemy.ySpeed > 0)) ||
					((enemy.y > 167) && (enemy.ySpeed < 0)))
				enemy.ySpeed = -enemy.ySpeed;

			switchAreaScroll2(enemy);

			if ((enemy.life < 150) && (enemy.y >= 20) && (enemy.y < 180)) {
				initLeser(enemy.x + 3, enemy.y + 4, -16, 0, 11, 0);
				initLeser(enemy.x, enemy.y + 11, -16, 0, 11, 0);
				initLeser(enemy.x + 3, enemy.y + 18, -16, 0, 11, 0);
				initLeser(enemy.x + 3, enemy.y + 4, 16, 0, 11, 0);
				initLeser(enemy.x, enemy.y + 11, 16, 0, 11, 0);
				initLeser(enemy.x + 3, enemy.y + 18, 16, 0, 11, 0);
				die = true;
				bosses.remove(enemy);
				nextLevel();
			}
			break;

		case 4:
			if (areaScroll % 15 == 0) {
				initLeser(enemy.x + 16, enemy.y + 16, 6, 0, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, -6, 0, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 0, 6, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 0, -6, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, -4, -4, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 4, -4, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, -4, 4, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 4, 4, 8, 0);
			}
			break;

		case 5:
			if (areaScroll % 15 == 0) {
				initLeser(enemy.x + 6, enemy.y + 16, -6, 0, 7, 0);
			}
			break;

		case 7:
			if ((areaScroll + enemy.x - 15) % 30 == 0)
				initLeser(enemy.x + 6, enemy.y + 16, -10, 0, 11, 0);

			if ((enemy.y > 180) && (enemy.ySpeed < 0))
				enemy.ySpeed = -enemy.ySpeed;
			if ((enemy.y < 0) && (enemy.ySpeed > 0))
				enemy.ySpeed = -enemy.ySpeed;
			if (enemy.x < 100)
				enemy.xSpeed = 10;
			break;

		case 8:
			if (enemy.x == 200) {
				enemy.ySpeed = RND.nextInt(2) * 2 - 1;
			} else if ((enemy.x == 110) || (enemy.x == 40)) {
				enemy.ySpeed = enemy.ySpeed * 2;
				enemy.xSpeed = enemy.xSpeed - 4;
			}
			break;

		case 9:
			if (areaScroll % 10 == 0) {
				initLeser(enemy.x + 6, enemy.y + 10, -10, 0, 11, 0);
				initLeser(enemy.x + 6, enemy.y + 22, -10, 0, 11, 0);
			}
			if (areaScroll % 87 == 0) {
				initEnemy(enemy.x + 16, enemy.y + 20, 2, -4, MaxEnemy1 + 3, -1);
			}
			if (areaScroll % 125 == 0) {
				initEnemy(enemy.x + 16, enemy.y + 20, 15, 0, 8, -1);
			}
			if (areaScroll % 210 == 0) {
				initEnemy(enemy.x + 16, enemy.y + 20, 2, -4, 7, RND.nextInt(6));
			}
			if ((enemy.y > 180) && (enemy.ySpeed < 0))
				enemy.ySpeed = -enemy.ySpeed;
			if ((enemy.y < 0) && (enemy.ySpeed > 0))
				enemy.ySpeed = -enemy.ySpeed;
			if (areaScroll % 75 == 0)
				enemy.ySpeed = enemy.ySpeed * 4;
			if (areaScroll % 75 == 32)
				enemy.ySpeed = enemy.ySpeed / 4;
			if (enemy.x < 200)
				enemy.xSpeed = -10;
			if (enemy.x > 320)
				enemy.xSpeed = 3;
			break;

		case MaxEnemy1:
			if ((enemy.y < (((areaScroll + enemy.x) / 20) % 4) * 25))
				enemy.ySpeed = -2;
			if ((enemy.y > 125 + (((areaScroll + enemy.x) / 20) % 4) * 25))
				enemy.ySpeed = 2;
			if (areaScroll % 20 == 0)
				initLeser(enemy.x + 6, enemy.y + 16, -6, 0, 8, 0);
			break;

		case MaxEnemy1 + 1:
			if (areaScroll % 15 == 0)
				initLeser(enemy.x + 16, enemy.y + 16, -Speed_L[4], 0, 4, 0);
			break;

		case MaxEnemy1 + 2:
			if (areaScroll % 25 == 0) {
				initLeser(enemy.x + 16, enemy.y + 16, 6, 0, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, -6, 0, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 0, 6, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 0, -6, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, -4, -4, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 4, -4, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, -4, 4, 8, 0);
				initLeser(enemy.x + 16, enemy.y + 16, 4, 4, 8, 0);
			}
			if ((enemy.y < 0) && (enemy.ySpeed > 0))
				enemy.ySpeed = -enemy.ySpeed;
			if ((enemy.y > 190) && (enemy.ySpeed < 0))
				enemy.ySpeed = -enemy.ySpeed;
			if ((enemy.x < 0) && (enemy.xSpeed > 0))
				enemy.xSpeed = -enemy.xSpeed;
			if ((enemy.x > 310) && (enemy.xSpeed < 0))
				enemy.xSpeed = -enemy.xSpeed;

			switch (areaScroll % 200) {
			case 30:
				enemy.ySpeed = enemy.ySpeed * 2;
				break;
			case 100:
				enemy.ySpeed = enemy.ySpeed / 2;
				break;
			case 70:
				enemy.xSpeed = enemy.xSpeed * 3;
				break;
			case 170:
				enemy.xSpeed = enemy.xSpeed / 3;
				break;
			}
			break;

		case MaxEnemy1 + 3:
			if ((areaScroll % 15) == 0)
				initLeser(enemy.x + 6, enemy.y + 16, -10, 0, 5, 0);
			if ((enemy.y > 190) && (enemy.ySpeed < 0))
				enemy.ySpeed = -enemy.ySpeed;
			if ((enemy.y < 0) && (enemy.ySpeed > 0))
				enemy.ySpeed = -enemy.ySpeed;
			if (enemy.x < 100)
				enemy.xSpeed = 15;
			break;

		} // end switch kind

		return die;
	} // end switchEnemyKind

	public void doEnemy1() {
		boolean die = false;

		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy enemy = it.next();
			die = false;
			enemy.x -= enemy.xSpeed;
			enemy.y -= enemy.ySpeed;

			if (enemy.y < -31 || enemy.y > 231)
				die = true;

			if (enemy.x < -31)
				die = true;

			if (enemy.late > 0) {
				enemy.late--;
			} else {

				if (enemy.life > 0 && enemy.kind >= 0) {
					die = switchEnemyKind(enemy);
				}

			}

			if (die) {
				it.remove();
			}
		}

	} // end of doEnemy1

	public void killAllThings() {
		enemies.clear();
		lesses.clear();
	}

	public void putPic(int X1, int Y1, int X2, int Y2, Picture pic) {
		int Xv, Yv, c;

		for (int y = 0; y < Y2; y++) {
			for (int x = 0; x < X2; x++) {
				c = pic.get(x, y);
				if (c < 255) {
					Xv = X1 + x;
					Yv = Y1 + y;
					if (Xv >= 0 && Yv >= 0 && Xv < 320 && Yv < 200)
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
			switch (menuItem) {
			case 0:
				do {
					gun.setCounter();
					splash -= 2;
					screen(splash);
					WaitFor(Speed);
				} while (splash > 1);
				game();
				changed = true;
				break;

			case 1:
				if (diff == 0)
					diff = 1;
				else if (diff == 1)
					diff = -1;
				else
					diff = 0;
				changed = false;
				break;

			case 2:
				do {
					gun.setCounter();
					splash = splash - 2;
					screen(splash);
					WaitFor(Speed);
				} while (splash > 1);

				credits();

				do {
					gun.setCounter();
					splash = splash - 2;
					screen(splash);
					WaitFor(Speed);

				} while (splash > 1);

				changed = true;
				break;

			case 3:

				/*
				 * Assign(fP,'hiscore.dat');
				 *
				 * ReSet(fP); if (IOResult <> 0 ) { For i = 0 to 9 do
				 * Pontszam[i].Pontszam = 0; ReWrite(fP); For i = 0 to 9 do
				 * Write(fP,Pontszam[i]); Close(fP); ReSet(fP); }
				 *
				 * For i = 0 to 9 do Read(fP,Pontszam[i]); Close(fP);
				 */

				do {
					gun.setCounter();
					splash = splash - 2;
					screen(splash);
					WaitFor(Speed);
				} while (splash > 1);

				gun.clrVscr(0);
				canvas.repaint();
				splash = 63;
				screen(splash);
				gun.clrVscr(148);
				drawPoints(l);
				Anim(0);
				canvas.repaint();

				ch = keyBuffer.readKey();

				do {
					gun.setCounter();
					splash -= 2;
					screen(splash);
					WaitFor(Speed);
				} while (splash > 1);

				changed = true;

				break;

			case 4:
				for (int i = 0; i < 10; i++) {
					scoreRecords[i].points = 0;
					scoreRecords[i].name = "";
				}

				changed = true;
				saveScore();
				break;

			case MaxMenu:
				quitAll = true;
				break;

			} // end switch

		} while (!quitAll);

		Anim3();
		gun.finish();

		System.out.println("Thanks for checking out!");

	} // end of run()

	public void putEnemies() {
		boolean die;

		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy enemy = it.next();
			die = false;

			if (enemy.life > 0) {
				Picture enemyShipPicture = enemyShips[Abra[enemy.kind]];
				putPic(enemy.x, enemy.y, 32, 32, enemyShipPicture);

				Picture playerShipPicture = ships[pShip];
				if (!cheat) {
					for (int i = 0; i < 32; i++) {
						for (int j = 0; j < 32; j++) {
							if (enemyShipPicture.get(j, i) < 255) {
								Xv = enemy.x + j - pX;
								Yv = enemy.y + i - pY;
								if ((Xv >= 0) && (Xv < 50) && (Yv >= 0) && (Yv < 30) && (playerShipPicture.get(Xv, Yv) < 255) && (pLife > 0)) {
									// I guess if enemy collides with player, player dies
									pLife = 0;
									pTime = 50;
								}
							}
						} // end for j
					} // end for i
				} // end if !cheat
			} else {
				String PontSzov;
				if (enemy.time > 0) {
					putPic(enemy.x + 6 - (200 - enemy.time * 4), enemy.y + 6 - (50 - enemy.time), 16, 16, deaths[0]);
					putPic(enemy.x + 16 + (200 - enemy.time * 4), enemy.y + 6 - (50 - enemy.time), 16, 16, deaths[1]);
					putPic(enemy.x + 6 - (200 - enemy.time * 4), enemy.y + 16 + (50 - enemy.time), 16, 16, deaths[2]);
					putPic(enemy.x + 16 + (200 - enemy.time * 4), enemy.y + 16 + (50 - enemy.time), 16, 16, deaths[3]);

					if (!cheated) {
						PontSzov = String.valueOf((2 + diff) * Life_E[enemy.kind] / 10);
						PontSzov = PontSzov + 'p';
					} else {
						PontSzov = "0p";
					}

					gun.writeXY(enemy.x + 16 - (PontSzov.length() * 13) / 2, enemy.y + 8 - (300 - enemy.time * 6), 5, PontSzov);

					if ((enemy.time > 30) && (enemy.item > -1))
						putPic(enemy.x + 8, enemy.y + 8, 16, 16, items[enemy.item]);
					enemy.time = enemy.time - 1;
				} else {
					die = true;
				}
			}
			if (die) {
				it.remove();
			}
		} // end while it has next
	} // end of putEnemies

	public void putLesers() {
		int X1 = 0;
		int Y1 = 0;
		boolean Talalat;

		Iterator<Less> itLess = lesses.iterator();
		while (itLess.hasNext()) {
			Less LessPos = itLess.next();
			Talalat = false;
			putPic(LessPos.x, LessPos.y, 16, 5, lesers[LessPos.kind]);

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 16; j++) {

					if ((lesers[LessPos.kind].get(j, i) < 255) && (!Talalat)) {
						Xv = LessPos.x + j;
						Yv = LessPos.y + i;

						if ((Xv >= 0) && (Xv < 320) && (Yv >= 0) && (Yv < 200)) {
							if (LessPos.side > 0) {

								// player bullet

								Iterator<Enemy> itEnemy = enemies.iterator();
								while (itEnemy.hasNext()) {
									Enemy EnemyPos = itEnemy.next();

									if (EnemyPos.life > 0) {
										X1 = Xv - EnemyPos.x;
										Y1 = Yv - EnemyPos.y;
										if ((X1 >= 0) && (X1 < 32) && (Y1 >= 0) && (Y1 < 32) && enemyShips[Abra[EnemyPos.kind]].get(X1, Y1) < 255) {
											Talalat = true;

											EnemyPos.life = EnemyPos.life - (LessPos.damage - diff * LessPos.damage / 2);
											if (EnemyPos.life <= 0) {
												EnemyPos.time = 50;

												if (!cheated)
													scores += (2 + diff) * Life_E[EnemyPos.kind] / 10;

												if (bosses.contains(EnemyPos))
													bosses.remove(EnemyPos);

												if (EnemyPos.item > -1)
													switch (EnemyPos.item) {
													case 0:
														if (pLife > 0)
															pLife = pLife + 20;
														if (pLife > 100)
															pLife = 100;
														break;

													case 1:
														if (leser[1] < 5)
															leser[1] = leser[1] + 1;
														break;

													case 2:
													case 3:
													case 4:
														if (leser[EnemyPos.item] < 3)
															leser[EnemyPos.item]++;
														break;

													case 5:
														if (pFire > 1)
															pFire = 1;
														break;

													case 6:
														if (pFire > 0)
															pFire = 0;
														break;
													} // end of switch item
											}
										}
									}
								} // end while EnemyPos
							} else if ((pLife > 0) && (!cheat)) {

								// enemy bullet

								Xv = Xv - pX;
								Yv = Yv - pY;
								if ((Xv >= 0) && (Xv < 50) && (Yv >= 0) && (Yv < 30) && (ships[pShip].get(Xv, Yv) < 255)) {
									pLife = pLife - (LessPos.damage + diff * LessPos.damage / 2);
									Talalat = true;

									if (pLife <= 0)
										pTime = 50;
									if (pLife < 0)
										pLife = 0;
								}

							} // end if enemy or player bullet

						}

					} // end huge what-if

				} // end for j
			} // end for i

			if (Talalat) {
				itLess.remove();
			}
		}
	}

	public void Palya() {

		if (areaScroll > 100) {
			switch (currentArea) {
			case 0:
				if (areaScroll < 500 && (areaScroll % 25) == 0)
					initEnemy(320, 70 + ((areaScroll / 25) % 2) * 60, Speed_E[6], ((areaScroll / 25) % 2) * 2 - 1, 6, -1);
				if (areaScroll == 500)
					initEnemy(320, RND.nextInt(20) + 90, 4, 0, 6, 0);
				if ((areaScroll > 500) && (areaScroll < 600) && (areaScroll % 20 == 0))
					initEnemy(250, -10, 1, -2, MaxEnemy1 , -1);
				if ((areaScroll > 600) && (areaScroll < 700) && (areaScroll % 20 == 0))
					initEnemy(250, 210, 1, 2, MaxEnemy1 , -1);
				if (areaScroll == 700)
					initEnemy(250, 210, 1, 2, 6, 1);
				if (areaScroll == 800)
					initEnemy(320, RND.nextInt(20) + 90, 2, 0, 6, 0);
				if (areaScroll == 990)
					initEnemy(320, RND.nextInt(20) + 90, 2, 0, 6, 5);
				if ((areaScroll > 750) && (areaScroll < 1040) && (areaScroll % 25 == 0))
					initEnemy(320, 60 + ((areaScroll / 20) % 4) * 25, 1, 2, MaxEnemy1, -1);
				if (areaScroll == 1100) {
					initEnemy(320, 100, 3, 3, MaxEnemy1 + 2, 1);
					bossDel = 200;
					initEnemy(320, RND.nextInt(20) + 90, 2, 0, 6, 0);
				}
				if (areaScroll > 2200)
					areaScroll = areaScroll - bossDel;
				if ((areaScroll > 1100) && (bosses.isEmpty()))
					nextLevel();
				break;

			case 1:
				if ((areaScroll < 2000) && ((areaScroll % 19) == 0)) {
					initEnemy(0, 0, 0, 0, -1, -1);
				}
				if ((areaScroll < 2000) && ((areaScroll % 32) == 0)) {
					initLeser(0, 0, 0, 0, -1, 0);
				}
				if ((areaScroll < 2000) && (areaScroll % 115 == 0))

					switch ((areaScroll / 115)) {
					case 2:
						initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 0);
						break;
					case 4:
						initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 5);
						break;
					case 6:
						initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 2);
						break;

					}
				if (areaScroll == 1000)
					initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 0);
				if (areaScroll == 1850)
					initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 1);
				if (areaScroll == 1250)
					initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 4);
				if (areaScroll == 1700)
					initEnemy(320, RND.nextInt(160) + 20, 4, 0, 6, 0);
				if (areaScroll == 2000) {
					initEnemy(320, 88, 3, 0, 3, -1);
					bossDel = 3600;
				}
				if (areaScroll > 10000)
					areaScroll = areaScroll - bossDel;
				break;

			case 2:
				if ((areaScroll < 500) && (areaScroll % 20 == 0)) {
					initEnemy(320, 88, Speed_E[5], (Speed_E[5] / 3) * (((areaScroll / 20) % 2) * 2 - 1), 4, -1);
				}
				if (areaScroll == 180)
					initEnemy(320, RND.nextInt(20) + 90, 4, 0, 6, 0);
				if (areaScroll == 360)
					initEnemy(320, RND.nextInt(20) + 90, 4, 0, 6, 0);
				if ((areaScroll > 530) && (areaScroll < 1000) && (areaScroll % 3 == 0))
					initEnemy(320, RND.nextInt(190), 15, 0, 8, -1);
				if (areaScroll == 620)
					initEnemy(320, 68 + RND.nextInt(40), 3, 0, 6, 0);
				if (areaScroll == 800)
					initEnemy(320, 68 + RND.nextInt(40), 3, 0, 6, 2);
				if (areaScroll == 950)
					initEnemy(320, 68 + RND.nextInt(40), 3, 0, 6, 6);
				if ((areaScroll > 1000) && (areaScroll < 1300) && (areaScroll % 25 == 0)) {
					initEnemy(310, 199, 2, 5, 7, -1);
					initEnemy(270, -20, 2, -5, 7, -1);
				}
				if (areaScroll == 1300) {
					initEnemy(310, 199, 2, 5, 7, 3);
					initEnemy(270, -20, 2, -5, 7, 2);
				}
				if ((areaScroll > 1000) && (areaScroll < 1300) && (areaScroll % 85 == 40))
					initEnemy(320, RND.nextInt(180), 4, 0, 6, 0);
				if (areaScroll == 1400) {
					initEnemy(320, 0, 1, 0, 6, 0);
					initEnemy(320, 40, 1, 0, 6, 3);
					initEnemy(320, 120, 1, 0, 6, 2);
					initEnemy(320, 160, 1, 0, 6, 0);
					initEnemy(320, 88, 1, 2, 9, 0);
					bossDel = 4200;
				}
				if (areaScroll > 10000)
					areaScroll = areaScroll - bossDel;
				if ((areaScroll > 1400) && (bosses.isEmpty()))
					nextLevel();
				break;
			} // end switch
		}
	} // end method

	public void temp() {
		scores = 0;
		pFire = 2;
		pShip = 0;
		pLife = 100;
		pY = 75;
		pX = -40;
		sumLife = 3;
		pLogged = false;
		pLogTime = 0;
		pL = 0;
		leser[0] = 1;
		leser[1] = 1;
		leser[2] = 0;
		leser[3] = 0;
		leser[4] = 0;
		currentArea = 0;
		areaScroll = 0;

		bosses.clear();
		enemies.clear();
		lesses.clear();

		cheat = false;

		scoreText = "1p";
		splash = 1;

		/*
		 * Assign(fP,'hiscore.dat');
		 *
		 * ReSet(fP); if (IOResult <> 0 ) { for(i = 0; i < 10; i++)
		 * Pontszam[i].Pontszam = 0; ReWrite(fP); for(i = 0; i < 10; i++)
		 * Write(fP,Pontszam[i]); Close(fP); ReSet(fP); }
		 *
		 * For i = 0 to 9 do Read(fP,Pontszam[i]); Close(fP);
		 */

		quit = false;
		cheated = false;
	}

	public void saveScore() {
		/*
		 * Assign(fP,'hiscore.dat'); ReWrite(fP); For i = 0 to 9 do
		 * Write(fP,Pontszam[i]); Close(fP);
		 */
	}

	public void game() {
		temp();
		addStars();

		do {
			if (splash < 63) {
				splash = splash + 2;
				screen(splash);
			}

			gun.setCounter();
			Palya();
			doEnemy1();
			enemies.addAll(addEnemies);
			addEnemies.clear();
			doLeser();

			if (keyBuffer.isKeyPressed()) {
				ch1 = keyBuffer.readKey();
				switch (ch1) {
				case 0:
					ch2 = keyBuffer.readKey();
					break;

				case 27:
					quit = true;
					break;

				case '1':
					leser[0] = 1;
					break;

				case '2':
					if ((leser[2] > 0))
						leser[0] = 2;
					break;

				case '3':
					if ((leser[3] > 0))
						leser[0] = 3;
					break;

				case '4':
					if ((leser[4] > 0))
						leser[0] = 4;
					break;

				case 'c':
				case 'C':
					cheat = !cheat;
					cheated = true;
					break;
				}
			}

			if (pLife > 0) {
				if (pL > 0)
					pL = pL - 1;
				else if (control.isControlOn() && (pL == 0)) {
					switch (leser[0]) {
					case 1:
						switch (leser[1]) {
						case 1:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 0, 0, 1);
							break;
						case 2:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 1, 0, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], -1, 0, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 0, 0, 1);
							break;

						case 3:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 1, 1, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], -1, 1, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 0, 1, 1);
							break;

						case 4:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 3, 0, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], -3, 0, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 1, 1, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], -1, 1, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 0, 2, 1);
							break;

						case 5:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 3, 1, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], -3, 1, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 1, 2, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], -1, 2, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[0], 0, 2, 1);
							break;
						}
						break;

					case 2:
						switch (leser[2]) {
						case 1:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[3], 0, 3, 1);
							break;
						case 2:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[4], 0, 4, 1);
							break;
						case 3:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[5], 0, 5, 1);
							break;
						}
						break;

					case 3:
						switch (leser[3]) {
						case 1:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 6, 0, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 0, 6, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -6, 0, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 0, -6, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 4, 4, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 4, -4, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -4, 4, 6, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -4, -4, 6, 1);
							break;

						case 2:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 6, 0, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 0, 6, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -6, 0, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 0, -6, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 4, 4, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 4, -4, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -4, 4, 7, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -4, -4, 7, 1);
							break;

						case 3:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 6, 0, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 0, 6, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -6, 0, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 0, -6, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 4, 4, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], 4, -4, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -4, 4, 8, 1);
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], -4, -4, 8, 1);
							break;
						}
						break;

					case 4:
						switch (leser[4]) {
						case 1:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[9], 0, 9, 1);
							break;
						case 2:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[10], 0, 10, 1);
							break;
						case 3:
							initLeser(pX + ShipX[pShip], pY + ShipY[pShip], Speed_L[11], 0, 11, 1);
							break;
						}
						break;

					}
					pL = Late_L[(leser[0]-1) * 3]; //  + leser[leser[0]]
				}
				if (control.isUpOn())
					pY = pY - Speed_F[pFire];
				if (control.isDownOn())
					pY = pY + Speed_F[pFire];
				if (pLogged || (pX >= 5)) {
					if (control.isLeftOn())
						pX = pX - Speed_F[pFire];
					if (control.isRightOn())
						pX = pX + Speed_F[pFire];
				}
				if (pX < 5) {
					if (pLogged)
						pX = 5;
					else
						pX = pX + Speed_M;
				}
				if (!pLogged) {
					pLogTime = pLogTime + 1;
					if (pLogTime >= 50)
						pLogged = true;
				}
			}

			if (pX > 274)
				pX = 274;
			if (pY < -10)
				pY = -10;
			if (pY > 180)
				pY = 180;

			areaScroll++;

			gun.clrVscr(0);
			doStars();
			putLesers();
			putEnemies();

			if (pLife > 0) {
				putPic(pX, pY, 50, 30, ships[pShip]);
				putPic(pX - 17, pY + 9, 17, 14, fires[pFire][areaScroll % 3]);
			} else if (pShip > 0) {
				putPic(pX + 6 - (200 - pTime * 4), pY + 6 - (50 - pTime), 16, 16, deaths[0]);
				putPic(pX + 16 + (200 - pTime * 4), pY + 6 - (50 - pTime), 16, 16, deaths[1]);
				putPic(pX + 6 - (200 - pTime * 4), pY + 16 + (50 - pTime), 16, 16, deaths[2]);
				putPic(pX + 16 + (200 - pTime * 4), pY + 16 + (50 - pTime), 16, 16, deaths[3]);
				if (pTime > 0)
					pTime = pTime - 1;
				else if (sumLife > 1) {
					sumLife = sumLife - 1;
					pLife = 100;
					pX = -40;
					pY = 75;
					pLogged = false;
					pLogTime = 0;
				} else {
					pShip = 0;
					sumLife = 0;
				}
			}

			putPic(0, 190, 120, 10, status);
			if (sumLife > 0)
				putPic(120, 190, 10, 10, stat2[sumLife][2]);

			for (i = 1; i <= (int) Math.round((pLife / 100.0) * 115); i++) {
				for (j = 3; j <= 6; j++) {
					gun.vscr.set(2 + i, 190 + j, 2);
				}

			}

			for (m = 0; m < 4; m++) {
				if (leser[m+1] == 0)
					putPic(270 + m * 10, 190, 10, 10, stat2[m][0]);
				else if ((leser[m+1] > 0) && (leser[0] != m + 1))
					putPic(270 + m * 10, 190, 10, 10, stat2[m][1]);
				else if ((leser[m+1] > 0) && (leser[0] == m + 1))
					putPic(270 + m * 10, 190, 10, 10, stat2[m][2]);
			} // end for m

			if (!bosses.isEmpty()) {
				for (Enemy boss : bosses) {

					putPic(0, 0, 120, 10, status);
					gun.writeXY(125, 0, 4, "BOSS");
					for (i = 1; i <= (int) Math.round(((double)boss.life / Life_E[boss.kind]) * 115.0); i++) {
						for (j = 3; j <= 6; j++) {
							gun.vscr.set(2 + i, j, 4);
						}
					}
				}
			} // end if bosses not empry

			if (sumLife <= 0)
				gun.writeXY(127, 117, 5, "GAME OVER");
			if (cheat)
				gun.writeXY(0, 0, 7, "cheat on");

			if (currentArea == 3) {
				gun.writeXY(127, 117, 5, "YOU WON !");
			} else if (areaScroll > 25 && areaScroll < 200) {
				if (areaScroll >= 25 && areaScroll <= 124) {
					s[0] = String.valueOf(currentArea + 1);
					gun.writeXY(137, (int) Math.round(90 - ((125 - areaScroll)) * (1 - sqr(Math.sin((areaScroll - 25) / 10)))), 5, "LEVEL " + s);
				} else if (areaScroll >= 125 && areaScroll <= 149) {
					gun.writeXY(137, 90, 5, "LEVEL " + s);
				} else if (areaScroll >= 150 && areaScroll <= 199) {
					putPic(154 - (areaScroll - 150) * 4, 84 - areaScroll + 150, 16, 16, deaths[0]);
					putPic(166 + (areaScroll - 150) * 4, 84 - areaScroll + 150, 16, 16, deaths[1]);
					putPic(154 - (areaScroll - 150) * 4, 96 + areaScroll - 150, 16, 16, deaths[2]);
					putPic(166 + (areaScroll - 150) * 4, 96 + areaScroll - 150, 16, 16, deaths[3]);
				}

			} // else if currentArea / areaScroll

			scoreText = "Pontszam " + spaced(scoreText);
			gun.writeXY(165, 0, 2, scoreText);

			canvas.repaint();
			WaitFor(Speed);
		} while (!quit);

		anim2();
		splash = 63;
		screen(splash);
		drawPoints(10);

		/*
		 * if (Pontok > Pontszam[9].Pontszam ) { Str(Pontok,Ponts); Ponts =
		 * "    You have reached "+Spaced(Ponts)+" !!";
		 * gun.writeXY(1,170,0,Ponts); gun.writeXY(0,170,3,Ponts);
		 * gun.writeXY(1,182,0,"Enter Name"); gun.writeXY(0,182,4,"Enter Name");
		 * }
		 */

		Anim(0);
		canvas.repaint();

		/*
		 * if( Pontok > Pontszam[i].Pontszam) { PontSzov = ""; k = 0; Quit =
		 * false; do { zh = keyBuffer.readKey();
		 *
		 * if(zh == 0) { ch = keyBuffer.readKey();
		 *
		 * } else if(zh == 8) { if (Pontszov != null && PontSzov.length() > 0) {
		 * PontSzov[0] --; } } else if(zh >= 'a' && zh <= 'z' || zh >= 'A' && zh
		 * <= 'Z' || zh == ' ') { PontSzov = PontSzov + zh; } else if(zh == 27
		 * || zh == 13) { if ((PontSzov == null || PontSzov.length == 0)
		 * Pontszov = " "; quit = true; }
		 *
		 * DrawPoints(10); gun.writeXY(1,182,0,"Enter Name");
		 * gun.writeXY(0,182,4,"Enter Name"); gun.writeXY(121,182,0,PontSzov);
		 * gun.writeXY(120,182,1,PontSzov);
		 *
		 * String endScoreText =
		 * "    You have reached "+spaced(String.valueOf(scores))+" !!";
		 * gun.writeXY(1,170,0,endScoreText); gun.writeXY(0,170,3,endScoreText);
		 * gun.setScr(); } while(!Quit); Pontszam[9].Pontszam = Pontok;
		 * Pontszam[9].Nev = Pontszov; k = 9; l = 10; do { if (Pontok >
		 * Pontszam[k-1].Pontszam) { Pontszam[k] = Pontszam[k-1];
		 * Pontszam[k-1].Pontszam = Pontok; Pontszam[k-1].Nev = Pontszov; l =
		 * k-1; } k--; } while(k != 0);
		 *
		 * gun.clrVscr(148); drawPoints(l); Anim(1); canvas.repaint(); }
		 *
		 */

		ch = keyBuffer.readKey();

		do {
			gun.setCounter();
			splash = splash - 2;
			screen(splash);
			WaitFor(Speed);
		} while (splash > 1);

		removeStars();
		killAllThings();
		saveScore();
	} // end public void game

	public void WaitFor(long time) {
		try {
			Thread.sleep(/* time */ 30);
		} catch (InterruptedException ex) {
			//
		}
	}

	public void DrawMenu() {
		int MP2;
		String DStr = "";
		switch (diff) {
		case -1:
			DStr = "Easy";
			break;
		case 0:
			DStr = "Medium";
			break;
		case 1:
			DStr = "Hard";
			break;
		}

		gun.clrVscr(1);
		gun.writeXY(116, 5, 5, "KlonGun");
		gun.writeXY(115, 6, 5, "KlonGun");
		gun.writeXY(114, 5, 5, "KlonGun");
		gun.writeXY(115, 4, 5, "KlonGun");
		gun.writeXY(115, 5, 148, "KlonGun");

		for (MP2 = 0; MP2 <= MaxMenu; MP2++) {
			gun.writeXY(70, 71 + 20 * MP2, 0, MenuSzov[MP2]);
			gun.writeXY(71, 71 + 20 * MP2, 0, MenuSzov[MP2]);
			gun.writeXY(70, 70 + 20 * MP2, 6, MenuSzov[MP2]);
			if (MP2 == 1) {
				gun.writeXY(200, 71 + 20 * MP2, 0, DStr);
				gun.writeXY(201, 71 + 20 * MP2, 0, DStr);
				gun.writeXY(200, 70 + 20 * MP2, 4, DStr);
			}
		}
		putPic(5, 60 + 20 * menuItem, 50, 30, ships[1]);
	}

	public void menu() {
		boolean menuQuit = false;

		if (changed) {
			gun.clrVscr(0);
			canvas.repaint();
			splash = 63;
			screen(splash);
			menuQuit = false;
			DrawMenu();
			Anim(0);
		}

		do {
			DrawMenu();
			canvas.repaint();
			switch (keyBuffer.readKey()) {
			case 0:
				switch (keyBuffer.readKey()) {
				case 72:
					if (menuItem > 0)
						menuItem = menuItem - 1;
					break;
				case 80:
					if (menuItem < MaxMenu)
						menuItem = menuItem + 1;
					break;
				}
				break;
			case 27: {
				menuQuit = true;
				menuItem = MaxMenu;
			}
				break;
			case 13: {
				menuQuit = true;
				menuItem = menuItem;
			}
				break;
			}
		} while (!menuQuit);

	}

	public void credits() {
		int pos = 0;
		int p = 0;

		gun.clrVscr(0);
		canvas.repaint();

		splash = 1;
		screen(splash);
		gun.clrVscr(148);

		canvas.repaint();

		pos = 0;

		do {
			gun.setCounter();
			splash += 2;
			screen(splash);
			WaitFor(Speed);
		} while (splash >= 62);
		splash = 63;

		screen(splash);

		do {
			gun.clrVscr(148);
			pos = pos + 1;
			for (p = 0; p <= MaxCredit; p++) {
				gun.writeXY(161 - CreditSzov[p].length() * 9 / 2, 200 + p * 20 - pos, 0, CreditSzov[p]);
				gun.writeXY(160 - CreditSzov[p].length() * 9 / 2, 201 + p * 20 - pos, 0, CreditSzov[p]);
				gun.writeXY(160 - CreditSzov[p].length() * 9 / 2, 200 + p * 20 - pos, 2, CreditSzov[p]);
			}

			canvas.repaint();
		} while (pos < 400 + (MaxCredit + 1) * 20 && !frame.getControl().isEscapeOn());

	}

} // end of KLONGUN
