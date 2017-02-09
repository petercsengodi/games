package hu.csega.klongun;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import hu.csega.klongun.model.Enemy;
import hu.csega.klongun.model.Player;
import hu.csega.klongun.model.Bullet;
import hu.csega.klongun.model.Score;
import hu.csega.klongun.model.Star;
import hu.csega.klongun.screen.Picture;
import hu.csega.klongun.screen.VirtualScreen;
import hu.csega.klongun.sprites.Rotation;
import hu.csega.klongun.sprites.RotationMapLibrary;
import hu.csega.klongun.sprites.SpriteLibrary;
import hu.csega.klongun.swing.KlonGunCanvas;
import hu.csega.klongun.swing.KlonGunControl;
import hu.csega.klongun.swing.KlonGunFrame;
import hu.csega.klongun.swing.KlonGunKeyBuffer;

public class KlonGun extends SpriteEngine {

	private static final String NAME_OF_THE_GAME = "KlonGun";

	private static final String HISCORES = "High Scores";

	// Array sizes
	public static final int MaxStars = 20;
	public static final int MaxEnemy2 = SpriteLibrary.MaxEnemy1+3;

	// Object speeds
	public static final int Speed1 = 1;
	public static final int Speed2 = 2;
	public static final int Speed3 = 3;
	public static final int Speed_M = 3;

	//	// overall game speed?
	//	public static final int Speed = 1;

	public static final int[] Speed_F = new int[] {7,4,2}; // ship turbins
	public static final int[] Speed_E = new int[] {3,1,5,0,7,3,3,0,15,0,7,3,2,0}; // enemies
	public static final int[] Life_E = new int[] {40,64,15,1250,50,10,15,80,20,5000,20,15,250,15}; // enemy HPs
	public static final int[] Dmg_L = new int[] {5,7,10,10,13,20,2,7,20,25,40,70}; // bullet power
	public static final int[] Late_L = new int[] {2,2,2,3,4,5,3,3,3,5,7,8}; // delay between shootings
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
	public List<Bullet> bullets = new ArrayList<>();


	// super boss does this
	public List<Enemy> addEnemies = new ArrayList<>();

	public int i,j,k,l,m,n,Xv,Yv;

	public SpriteLibrary spriteLibrary;
	public Rotation rotation = new Rotation();

	public int currentLevel;
	public int areaScroll;
	public int bossDel;

	public Player player = new Player();

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

	public static final int sinus[] = new int[126];

	public static final Random RND = new Random(System.currentTimeMillis());

	public static KlonGunFrame frame;
	public static KlonGunCanvas canvas;
	public static KlonGunControl control;
	public static KlonGunKeyBuffer keyBuffer;

	public long timeSpentRunning;

	public static void main(String[] args) {
		for (int i = 0; i < 126; i++)
			sinus[i] = (int) Math.round(Math.sin(i / 20) * 100);

		//////////////////////
		KlonGun kg = new KlonGun();
		frame = new KlonGunFrame(kg);
		control = frame.getControl();
		keyBuffer = control.keyBuffer;
		canvas = frame.getCanvas();

		try {
			kg.run();
		} catch (InterruptedException e) {
			// game ended with interruption
		}
	}

	public String spaced(String xx) {
		while(xx.length() < 5)
			xx = ' ' + xx;

		return xx;
	}


	public void drawPoints(int no) {
		backBuffer.clear(149);
		writeXY(121,0,0,HISCORES);
		writeXY(120,0,4,HISCORES);

		for(int i = 0; i < 10; i++) {
			/*
            With PontSzam[i] do
             if (Pontszam <> 0 )
              if (no <> i )
               {
                Str(Pontszam,Ponts);
                writeXY(21,20+i*15,0,Nev);
                writeXY(221,20+i*15,0,Spaced(Ponts));
                writeXY(20,20+i*15,2,Nev);
                writeXY(220,20+i*15,2,Spaced(Ponts));
               } else {
                Str(Pontszam,Ponts);
                writeXY(21,20+i*15,0,Nev);
                writeXY(221,20+i*15,0,Spaced(Ponts));
                writeXY(20,20+i*15,1,Nev);
                writeXY(220,20+i*15,1,Spaced(Ponts));
               }
			 */


		}
	} // end drawPoints

	public void Anim3() {
		VirtualScreen back = new VirtualScreen();
		int pos = 0;

		backBuffer.copyTo(back);

		do {
			backBuffer.clear(0);
			pos++;

			for(int i = 0; i < VirtualScreen.HEIGHT; i++) {
				for(int j = 0; j < VirtualScreen.WIDTH; j++) {
					k = (i - 100) * pos + 100;
					l = (j - 160) * pos + 160;
					if ((k >= 0) && (l >= 0) && (k < 200) && (l < 320))
						backBuffer.set(l, k, back.get(j, i));
				}
			}

			canvas.repaint();
		} while(pos <= 80);
	}

	public void Anim(int type) {
		VirtualScreen back = new VirtualScreen();
		int pos = 320, i = 0;

		backBuffer.copyTo(back);
		if (type == 0)
			backBuffer.clear(0);
		else
			backBuffer.clear(148);

		do {
			pos -= 4;
			for(i = 0; i < 200; i++) {
				if(i % 2 == 0) {
					System.arraycopy(back.getContent(), i * VirtualScreen.WIDTH + pos,
							backBuffer.getContent(), i * VirtualScreen.WIDTH, 320-pos);
				} else {
					System.arraycopy(back.getContent(), i * VirtualScreen.WIDTH,
							backBuffer.getContent(), i * VirtualScreen.WIDTH + pos, 320-pos);
				}
			}

			canvas.repaint();
		} while(pos != 0);
	}

	public int f1(int x, double y) {
		return (int)Math.round(y * sinus[Math.round(x + 503) % 126]);
	}

	public int f2(int x, double z) {
		return (int)Math.round(z * sinus[Math.round((x+m)*2 + 503) % 126]);
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

		VirtualScreen back = new VirtualScreen();


		int[] FGGVX = new int[320];
		int[] FGGVY = new int[200];

		backBuffer.copyTo(back);

		do {
			if (i % Speed == 0) {
				y = i / 380.0;
				z = i / 1500.0;

				for (n = 0; n < 320; n++)
					FGGVX[n] = n - f2(n - 160, z);
				for (n = 0; n < 200; n++)
					FGGVY[n] = n - f1(n - 100, y) - o;

				backBuffer.clear(0);

				if (FGGVY[k] >= 0 && FGGVY[k] < 200 && FGGVX[n] >= 0 && FGGVX[n] < 320)
					backBuffer.set(n, k, back.get(FGGVX[n], FGGVY[k]));

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

	public void paletteBrightness(int no) {
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 3; j++) {
				int v = (int) Math.round(originalPalette.get(i, j) * no / 63.0);
				palette.set(i, j, v);
			}
		}
	} // end screen


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
					backBuffer.set(s.x, s.y, 148);
				} else {
					backBuffer.set(s.x, s.y, 7);
				}
			}
		}
	} // end if doStars

	public void removeStars() {
		stars.clear();
	}

	public void initBullet(int x1, int y1, int sp0, int yp0, int fj0, int side0) {
		Bullet bullet = new Bullet();
		bullets.add(bullet);

		if (fj0 == -1) {
			bullet.x = 320;
			bullet.y = RND.nextInt(200);
			bullet.kind = RND.nextInt(2) * 5; // 0 || 5
			bullet.xSpeed = -(Speed_L[bullet.kind] + RND.nextInt(Speed_L[bullet.kind] + 1));
			bullet.ySpeed = (RND.nextInt(3) - 1) * (RND.nextInt(Speed_L[bullet.kind] + 1) / 2);
			bullet.damage = Dmg_L[bullet.kind];
			bullet.side = side0;
		} else {
			// load data accoring parameters
			bullet.x = x1;
			bullet.y = y1 + 2;
			bullet.xSpeed = sp0;
			bullet.kind = fj0;
			bullet.ySpeed = yp0;
			bullet.damage = Dmg_L[bullet.kind];
			bullet.side = side0;
		}
	} // end if initBullet

	public void moveBulletsAccordingSpeed() {
		boolean disappeared = false;

		Iterator<Bullet> it = bullets.iterator();
		while (it.hasNext()) {
			Bullet bullet = it.next();
			disappeared = false;

			if(bullet.hit) {
				if(bullet.dyingTime > 0) {
					bullet.dyingTime--;
				} else {
					it.remove();
				}

				continue;
			}

			bullet.x += bullet.xSpeed;
			bullet.y += bullet.ySpeed;

			if (bullet.y < -31 || bullet.y > 231)
				disappeared = true;

			if (bullet.x < -31 || bullet.x > 320)
				disappeared = true;

			if (disappeared) {
				it.remove();
			}
		}
	} // end if moveBullets

	public void nextLevel() {
		currentLevel++;
		areaScroll = 0;

		if (cheat) {
			// if cheat exists, player gets all possible weapon of previous area
			switch (currentLevel) {
			case 1:
				player.fire = 1;
				player.weapons[1] = 3;
				break;

			case 2:
				player.weapons[1] = 4;
				player.weapons[3] = 1;
				player.weapons[4] = 1;
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
			if(Fj0 == 3 || Fj0 == 9 || Fj0 == SpriteLibrary.MaxEnemy1+2)
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
			initBullet(enemy.x + 3, enemy.y + 4, -8, 0, 1, 0);
			initBullet(enemy.x, enemy.y + 11, -8, 0, 6, 0);
			initBullet(enemy.x + 3, enemy.y + 18, -8, 0, 1, 0);
			return;
		}

		if (areaScroll2 == 120) {
			enemy.ySpeed = 5;
			return;
		}

		if (between(areaScroll2, 121, 131) || between(areaScroll2, 151, 161) || between(areaScroll2, 181, 191)) {
			if (areaScroll % 3 == 0) {
				initBullet(enemy.x + 3, enemy.y + 4, -12, 0, 1, 0);
				initBullet(enemy.x + 3, enemy.y + 18, -12, 0, 1, 0);
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
				initBullet(enemy.x, enemy.y + 11, -8, 0, 6, 0);
			}
			return;
		}

		if (areaScroll2 == 310) {
			enemy.ySpeed = 1;
			enemy.xSpeed = 1;
			return;
		}

		if (areaScroll2 == 320) {
			initBullet(enemy.x + 16, enemy.y + 16, 6, 0, 11, 0);
			initBullet(enemy.x + 16, enemy.y + 16, -6, 0, 11, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 0, 6, 11, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 0, -6, 11, 0);
			return;
		}
		if (areaScroll2 == 330) {
			initBullet(enemy.x + 16, enemy.y + 16, -6, -6, 11, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 6, -6, 11, 0);
			initBullet(enemy.x + 16, enemy.y + 16, -6, 6, 11, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 6, 6, 11, 0);
			return;
		}

		if (areaScroll2 == 340) {
			enemy.ySpeed = 2 + RND.nextInt(1);
			enemy.xSpeed = 2 + RND.nextInt(1);
			return;
		}

		if (areaScroll2 % 10 == 0 && areaScroll2 >= 350 && areaScroll2 <= 480) {
			initBullet(enemy.x + 16, enemy.y + 16, 6, 0, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, -6, 0, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 0, 6, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 0, -6, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, -4, -4, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 4, -4, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, -4, 4, 7, 0);
			initBullet(enemy.x + 16, enemy.y + 16, 4, 4, 7, 0);
			return;
		}

		if (areaScroll2 % 10 == 0 && areaScroll2 >= 510 && areaScroll2 <= 590) {
			initBullet(enemy.x + 3, enemy.y + 4, -8, 0, 1, 0);
			initBullet(enemy.x, enemy.y + 11, -8, 0, 6, 0);
			initBullet(enemy.x + 3, enemy.y + 18, -8, 0, 1, 0);
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
				initBullet(enemy.x + 3, enemy.y + 4, -16, 0, 11, 0);
				initBullet(enemy.x, enemy.y + 11, -16, 0, 11, 0);
				initBullet(enemy.x + 3, enemy.y + 18, -16, 0, 11, 0);
				initBullet(enemy.x + 3, enemy.y + 4, 16, 0, 11, 0);
				initBullet(enemy.x, enemy.y + 11, 16, 0, 11, 0);
				initBullet(enemy.x + 3, enemy.y + 18, 16, 0, 11, 0);
				die = true;
				bosses.remove(enemy);
				nextLevel();
			}
			break;

		case 4:
			if (areaScroll % 15 == 0) {
				initBullet(enemy.x + 16, enemy.y + 16, 6, 0, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, -6, 0, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 0, 6, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 0, -6, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, -4, -4, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 4, -4, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, -4, 4, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 4, 4, 8, 0);
			}
			break;

		case 5:
			if (areaScroll % 15 == 0) {
				initBullet(enemy.x + 6, enemy.y + 16, -6, 0, 7, 0);
			}
			break;

		case 7:
			if ((areaScroll + enemy.x - 15) % 30 == 0)
				initBullet(enemy.x + 6, enemy.y + 16, -10, 0, 11, 0);

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
				initBullet(enemy.x + 6, enemy.y + 10, -10, 0, 11, 0);
				initBullet(enemy.x + 6, enemy.y + 22, -10, 0, 11, 0);
			}
			if (areaScroll % 87 == 0) {
				initEnemy(enemy.x + 16, enemy.y + 20, 2, -4, SpriteLibrary.MaxEnemy1 + 3, -1);
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

		case SpriteLibrary.MaxEnemy1:
			if ((enemy.y < (((areaScroll + enemy.x) / 20) % 4) * 25))
				enemy.ySpeed = -2;
			if ((enemy.y > 125 + (((areaScroll + enemy.x) / 20) % 4) * 25))
				enemy.ySpeed = 2;
			if (areaScroll % 20 == 0)
				initBullet(enemy.x + 6, enemy.y + 16, -6, 0, 8, 0);
			break;

		case SpriteLibrary.MaxEnemy1 + 1:
			if (areaScroll % 15 == 0)
				initBullet(enemy.x + 16, enemy.y + 16, -Speed_L[4], 0, 4, 0);
		break;

		case SpriteLibrary.MaxEnemy1 + 2:
			if (areaScroll % 25 == 0) {
				initBullet(enemy.x + 16, enemy.y + 16, 6, 0, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, -6, 0, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 0, 6, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 0, -6, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, -4, -4, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 4, -4, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, -4, 4, 8, 0);
				initBullet(enemy.x + 16, enemy.y + 16, 4, 4, 8, 0);
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

		case SpriteLibrary.MaxEnemy1 + 3:
			if ((areaScroll % 15) == 0)
				initBullet(enemy.x + 6, enemy.y + 16, -10, 0, 5, 0);
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
		bullets.clear();
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
						backBuffer.set(Xv, Yv, c);
				}
			}
		}
	} // end putPic

	public void putPic2(int X, int Y, Picture pic, double alfa) {
		rotation.setPicture(pic);
		rotation.setRotation(alfa);

		int Xv, Yv, c;

		for (int y = -RotationMapLibrary.ROTATION_MAP_SIZE; y <= RotationMapLibrary.ROTATION_MAP_SIZE; y++) {
			for (int x = -RotationMapLibrary.ROTATION_MAP_SIZE; x <= RotationMapLibrary.ROTATION_MAP_SIZE; x++) {
				c = rotation.get(x, y);
				if (c < 255) {
					Xv = X + x;
					Yv = Y + y;
					if (Xv >= 0 && Yv >= 0 && Xv < 320 && Yv < 200)
						backBuffer.set(Xv, Yv, c);
				}
			}
		}
	} // end putPic2

	public void run() throws InterruptedException {
		init();

		spriteLibrary = SpriteLibrary.createAndLoadLibrary();

		diff = -1;
		quitAll = false;
		changed = true;

		long startRound;

		do {

			switch (menuItem) {
			case 0:
				do {
					startRound = System.currentTimeMillis();
					splash -= 2;
					paletteBrightness(splash);
					waitInGraphicThread(startRound);
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
					startRound = System.currentTimeMillis();
					splash = splash - 2;
					paletteBrightness(splash);
					waitInGraphicThread(startRound);
				} while (splash > 1);

				credits();

				do {
					startRound = System.currentTimeMillis();
					splash = splash - 2;
					paletteBrightness(splash);
					waitInGraphicThread(startRound);

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
					startRound = System.currentTimeMillis();
					splash = splash - 2;
					paletteBrightness(splash);
					waitInGraphicThread(startRound);
				} while (splash > 1);

				backBuffer.clear(0);
				canvas.repaint();
				splash = 63;
				paletteBrightness(splash);
				backBuffer.clear(148);
				drawPoints(l);
				Anim(0);
				canvas.repaint();

				ch = keyBuffer.readKey();

				do {
					startRound = System.currentTimeMillis();
					splash -= 2;
					paletteBrightness(splash);
					waitInGraphicThread(startRound);
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

		System.out.println("Thanks for checking out!");

	} // end of run()

	public void putEnemies() {
		boolean die;

		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy enemy = it.next();
			die = false;

			if (enemy.life > 0) {
				Picture enemyShipPicture = spriteLibrary.enemies[Abra[enemy.kind]];
				putPic(enemy.x, enemy.y, 32, 32, enemyShipPicture);

				if (!cheat && player.shipSpriteIndex > -1 && player.protection <= 0) {
					Picture playerShipPicture = spriteLibrary.ships[player.shipSpriteIndex];
					for (int i = 0; i < 32; i++) {
						for (int j = 0; j < 32; j++) {
							if (enemyShipPicture.get(j, i) < 255) {
								Xv = enemy.x + j - player.x;
								Yv = enemy.y + i - player.y;
								if ((Xv >= 0) && (Xv < 50) && (Yv >= 0) && (Yv < 30) && (playerShipPicture.get(Xv, Yv) < 255) && (player.healthPoints > 0)) {
									// I guess if enemy collides with player, player dies
									player.healthPoints = 0;
									player.dyingAnimation = 50;
									player.protection = 200;
								}
							}
						} // end for j
					} // end for i
				} // end if !cheat
			} else {
				String PontSzov;
				if (enemy.time > 0) {
					putPic(enemy.x + 6 - (200 - enemy.time * 4), enemy.y + 6 - (50 - enemy.time), 16, 16, spriteLibrary.wrecks[0]);
					putPic(enemy.x + 16 + (200 - enemy.time * 4), enemy.y + 6 - (50 - enemy.time), 16, 16, spriteLibrary.wrecks[1]);
					putPic(enemy.x + 6 - (200 - enemy.time * 4), enemy.y + 16 + (50 - enemy.time), 16, 16, spriteLibrary.wrecks[2]);
					putPic(enemy.x + 16 + (200 - enemy.time * 4), enemy.y + 16 + (50 - enemy.time), 16, 16, spriteLibrary.wrecks[3]);

					if (!cheated) {
						PontSzov = String.valueOf((2 + diff) * Life_E[enemy.kind] / 10);
						PontSzov = PontSzov + 'p';
					} else {
						PontSzov = "0p";
					}

					writeXY(enemy.x + 16 - (PontSzov.length() * 13) / 2, enemy.y + 8 - (300 - enemy.time * 6), 5, PontSzov);

					if ((enemy.time > 30) && (enemy.item > -1))
						putPic(enemy.x + 8, enemy.y + 8, 16, 16, spriteLibrary.items[enemy.item]);
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

	public void drawBulletsAndCalulateCollision() {
		int X1 = 0;
		int Y1 = 0;
		Picture bulletPicture;
		float tmp1;
		int tmp2;

		Iterator<Bullet> itLess = bullets.iterator();
		bulletIteration: while (itLess.hasNext()) {
			Bullet bullet = itLess.next();

			bulletPicture = spriteLibrary.bullets[bullet.kind];
			if(bullet.hit) {
				if(bullet.dyingTime > 0) {
					tmp1 = (Bullet.MAX_DYING_TIME - bullet.dyingTime) * 5f;
					tmp2 = bulletPicture.getMiddleColor();
					backBuffer.set(bullet.x - (int)(tmp1 * bullet.a1), bullet.y - (int)(tmp1 * bullet.a5), tmp2);
					backBuffer.set(bullet.x + (int)(tmp1 * bullet.a2), bullet.y - (int)(tmp1 * bullet.a6), tmp2);
					backBuffer.set(bullet.x - (int)(tmp1 * bullet.a3), bullet.y + (int)(tmp1 * bullet.a7), tmp2);
					backBuffer.set(bullet.x + (int)(tmp1 * bullet.a4), bullet.y + (int)(tmp1 * bullet.a8), tmp2);
				}

				continue;
			} // end of bullet already hit

			putPic(bullet.x, bullet.y, 16, 5, bulletPicture);
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 16; j++) {

					if ((spriteLibrary.bullets[bullet.kind].get(j, i) < 255) && !bullet.hit) {
						Xv = bullet.x + j;
						Yv = bullet.y + i;

						if ((Xv >= 0) && (Xv < 320) && (Yv >= 0) && (Yv < 200)) {
							if (bullet.side > 0) {

								// player bullet

								Iterator<Enemy> itEnemy = enemies.iterator();
								while (itEnemy.hasNext()) {
									Enemy EnemyPos = itEnemy.next();

									if (EnemyPos.life > 0) {
										X1 = Xv - EnemyPos.x;
										Y1 = Yv - EnemyPos.y;

										if ((X1 >= 0) && (X1 < 32) && (Y1 >= 0) && (Y1 < 32) && spriteLibrary.enemies[Abra[EnemyPos.kind]].get(X1, Y1) < 255) {
											EnemyPos.life = EnemyPos.life - (bullet.damage - diff * bullet.damage / 2);
											if (EnemyPos.life <= 0) {
												EnemyPos.time = 50;

												if (!cheated)
													scores += (2 + diff) * Life_E[EnemyPos.kind] / 10;

												if (bosses.contains(EnemyPos))
													bosses.remove(EnemyPos);

												if (EnemyPos.item > -1)
													switch (EnemyPos.item) {
													case 0:
														if (player.healthPoints > 0)
															player.healthPoints = player.healthPoints + 20;
														if (player.healthPoints > 100)
															player.healthPoints = 100;
														break;

													case 1:
														if (player.weapons[1] < 5)
															player.weapons[1] = player.weapons[1] + 1;
														break;

													case 2:
													case 3:
													case 4:
														if (player.weapons[EnemyPos.item] < 3)
															player.weapons[EnemyPos.item]++;
														break;

													case 5:
														if (player.fire > 1)
															player.fire = 1;
														break;

													case 6:
														if (player.fire > 0)
															player.fire = 0;
														break;
													} // end of switch item
											}

											bullet.setHit(Xv, Yv, Bullet.MAX_DYING_TIME);
											break; // iteration of enemies
										}
									}
								} // end iteration of enemies
							} else if ((player.healthPoints > 0) && (!cheat && player.shipSpriteIndex > -1 && player.protection <= 0)) {

								// enemy bullet

								X1 = Xv - player.x;
								Y1 = Yv - player.y;
								if ((X1 >= 0) && (X1 < 50) && (Y1 >= 0) && (Y1 < 30) && (spriteLibrary.ships[player.shipSpriteIndex].get(X1, Y1) < 255)) {
									player.healthPoints = player.healthPoints - (bullet.damage + diff * bullet.damage / 2);

									if (player.healthPoints <= 0) {
										player.dyingAnimation = 50;
										player.healthPoints = 0;
										player.protection = 200;
									}

									bullet.setHit(Xv, Yv, Bullet.MAX_DYING_TIME);
								}

							} // end if enemy or player bullet

						}

					} // end huge what-if

					if(bullet.hit)
						continue bulletIteration;

				} // end for j
			} // end for i
		}
	}

	public void Palya() {

		if (areaScroll > 100) {
			switch (currentLevel) {
			case 0:
				if (areaScroll < 500 && (areaScroll % 25) == 0)
					initEnemy(320, 70 + ((areaScroll / 25) % 2) * 60, Speed_E[6], ((areaScroll / 25) % 2) * 2 - 1, 6, -1);
				if (areaScroll == 500)
					initEnemy(320, RND.nextInt(20) + 90, 4, 0, 6, 0);
				if ((areaScroll > 500) && (areaScroll < 600) && (areaScroll % 20 == 0))
					initEnemy(250, -10, 1, -2, SpriteLibrary.MaxEnemy1 , -1);
				if ((areaScroll > 600) && (areaScroll < 700) && (areaScroll % 20 == 0))
					initEnemy(250, 210, 1, 2, SpriteLibrary.MaxEnemy1 , -1);
				if (areaScroll == 700)
					initEnemy(250, 210, 1, 2, 6, 1);
				if (areaScroll == 800)
					initEnemy(320, RND.nextInt(20) + 90, 2, 0, 6, 0);
				if (areaScroll == 990)
					initEnemy(320, RND.nextInt(20) + 90, 2, 0, 6, 5);
				if ((areaScroll > 750) && (areaScroll < 1040) && (areaScroll % 25 == 0))
					initEnemy(320, 60 + ((areaScroll / 20) % 4) * 25, 1, 2, SpriteLibrary.MaxEnemy1, -1);
				if (areaScroll == 1100) {
					initEnemy(320, 100, 3, 3, SpriteLibrary.MaxEnemy1 + 2, 1);
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
					initBullet(0, 0, 0, 0, -1, 0);
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

	public void resetStatus() {
		scores = 0;
		player.fire = 2;
		player.shipSpriteIndex = 0;
		player.healthPoints = 100;
		player.y = 75;
		player.x = -40;
		player.numberOfLives = 3;
		player.cantBeHurt = false;
		player.cantBeHurtTime = 0;
		player.shootingDelay = 0;
		player.weapons[0] = 1;
		player.weapons[1] = 1;
		player.weapons[2] = 0;
		player.weapons[3] = 0;
		player.weapons[4] = 0;
		currentLevel = 0;
		areaScroll = 0;

		bosses.clear();
		enemies.clear();
		bullets.clear();

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

	public void game() throws InterruptedException {
		resetStatus();
		addStars();

		long startRound;

		do {
			startRound = System.currentTimeMillis();

			if (splash < 63) {
				splash = splash + 2;
				paletteBrightness(splash);
			}

			Palya();
			doEnemy1();
			enemies.addAll(addEnemies);
			addEnemies.clear();
			moveBulletsAccordingSpeed();

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
					player.weapons[0] = 1;
					break;

				case '2':
					if ((player.weapons[2] > 0))
						player.weapons[0] = 2;
					break;

				case '3':
					if ((player.weapons[3] > 0))
						player.weapons[0] = 3;
					break;

				case '4':
					if ((player.weapons[4] > 0))
						player.weapons[0] = 4;
					break;

				case 'd':
				case 'D':
					player.defenseFire = !player.defenseFire;
					break;

				case 'c':
				case 'C':
					cheat = !cheat;
					cheated = true;
					break;
				}
			}

			if (player.healthPoints > 0) {
				if (player.shootingDelay > 0)
					player.shootingDelay = player.shootingDelay - 1;
				else if (control.isControlOn() && (player.shootingDelay == 0)) {
					switch (player.weapons[0]) {
					case 1:
						switch (player.weapons[1]) {
						case 1:
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 0, 1);
							break;
						case 2:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 0, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 1, 1);
							}
							break;

						case 3:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 2, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -2, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 0, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 1, 1);
							}
							break;

						case 4:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 3, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -3, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 2, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -2, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 1, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 2, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -2, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 2, 1);
							}
							break;

						case 5:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 3, 0, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 2, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -2, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -3, 0, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 2, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -2, 1, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 1, 2, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], -1, 2, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[0], 0, 2, 1);
							}
							break;
						}
						break;

					case 2:
						switch (player.weapons[2]) {
						case 1:
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[3], 0, 3, 1);
							break;
						case 2:
							initBullet(player.x + ShipX[player.shipSpriteIndex] -3, player.y + ShipY[player.shipSpriteIndex] -3, Speed_L[4], 0, 3, 1);
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[4], 0, 4, 1);
							initBullet(player.x + ShipX[player.shipSpriteIndex] -3, player.y + ShipY[player.shipSpriteIndex] +3, Speed_L[4], 0, 3, 1);
							break;
						case 3:
							initBullet(player.x + ShipX[player.shipSpriteIndex] -3, player.y + ShipY[player.shipSpriteIndex] -5, Speed_L[4], 0, 4, 1);
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[5], 0, 5, 1);
							initBullet(player.x + ShipX[player.shipSpriteIndex] -3, player.y + ShipY[player.shipSpriteIndex] +5, Speed_L[4], 0, 4, 1);
							break;
						}
						break;

					case 3:
						switch (player.weapons[3]) {
						case 1:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -3, 4, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 3, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 2, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, 1, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, 0, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, -1, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -2, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -3, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -3, -4, 6, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 6, 0, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 0, 6, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -6, 0, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 0, -6, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 4, 4, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 4, -4, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 4, 6, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -4, 6, 1);
							}
							break;

						case 2:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -3, 4, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 3, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 2, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, 1, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, 0, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, -1, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -2, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -3, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -3, -4, 7, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 6, 0, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 0, 6, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -6, 0, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 0, -6, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 4, 4, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 4, -4, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 4, 7, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -4, 7, 1);
							}
							break;

						case 3:
							if(player.defenseFire) {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -3, 4, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 3, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 2, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, 1, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, 0, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -5, -1, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -2, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -3, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -3, -4, 8, 1);
							} else {
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 6, 0, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 0, 6, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -6, 0, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 0, -6, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 4, 4, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], 4, -4, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, 4, 8, 1);
								initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], -4, -4, 8, 1);
							}
							break;
						}
						break;

					case 4:
						switch (player.weapons[4]) {
						case 1:
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[9], 0, 9, 1);
							break;
						case 2:
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[10], 0, 10, 1);
							break;
						case 3:
							initBullet(player.x + ShipX[player.shipSpriteIndex], player.y + ShipY[player.shipSpriteIndex], Speed_L[11], 0, 11, 1);
							break;
						}
						break;

					}
					player.shootingDelay = Late_L[(player.weapons[0]-1) * 3]; //  + leser[leser[0]]
				}
				if (control.isUpOn())
					player.y = player.y - Speed_F[player.fire];
				if (control.isDownOn())
					player.y = player.y + Speed_F[player.fire];
				if (player.cantBeHurt || (player.x >= 5)) {
					if (control.isLeftOn())
						player.x = player.x - Speed_F[player.fire];
					if (control.isRightOn())
						player.x = player.x + Speed_F[player.fire];
				}
				if (player.x < 5) {
					if (player.cantBeHurt)
						player.x = 5;
					else
						player.x = player.x + Speed_M;
				}
				if (!player.cantBeHurt) {
					player.cantBeHurtTime = player.cantBeHurtTime + 1;
					if (player.cantBeHurtTime >= 50)
						player.cantBeHurt = true;
				}
			}

			if (player.x > 274)
				player.x = 274;
			if (player.y < -10)
				player.y = -10;
			if (player.y > 180)
				player.y = 180;

			areaScroll++;

			backBuffer.clear(0);
			doStars();
			putEnemies();

			if(player.protection > 0)
				player.protection--;

			if (player.healthPoints > 0) {
				putPic(player.x, player.y, 50, 30, spriteLibrary.ships[player.shipSpriteIndex]);
				putPic(player.x - 17, player.y + 9, 17, 14, spriteLibrary.fire[player.fire][areaScroll % 3]);
			} else if (player.shipSpriteIndex > -1) {
				putPic(player.x + 6 - (200 - player.dyingAnimation * 4), player.y + 6 - (50 - player.dyingAnimation), 16, 16, spriteLibrary.wrecks[0]);
				putPic(player.x + 16 + (200 - player.dyingAnimation * 4), player.y + 6 - (50 - player.dyingAnimation), 16, 16, spriteLibrary.wrecks[1]);
				putPic(player.x + 6 - (200 - player.dyingAnimation * 4), player.y + 16 + (50 - player.dyingAnimation), 16, 16, spriteLibrary.wrecks[2]);
				putPic(player.x + 16 + (200 - player.dyingAnimation * 4), player.y + 16 + (50 - player.dyingAnimation), 16, 16, spriteLibrary.wrecks[3]);


				int tmp1 = (50 - player.dyingAnimation) * 3;
				int tmp2 = 3; // this is a bright pixel, I guess
				backBuffer.set(player.x + 20 - tmp1, player.y + 10 - tmp1, tmp2);
				backBuffer.set(player.x + 20 + tmp1, player.y + 10 + tmp1, tmp2);

				tmp2 = 148; // this is a bright pixel, I guess
				backBuffer.set(player.x + 20 - tmp1, player.y + 10 - tmp1, tmp2);
				backBuffer.set(player.x + 20 + tmp1, player.y + 10 + tmp1, tmp2);

				int tmp3 = (100 - player.dyingAnimation * 2) * 3;
				tmp2 = 7; // this is a bright pixel, I guess
				backBuffer.set(player.x + 20 - tmp3, player.y + 10 - tmp1, tmp2);
				backBuffer.set(player.x + 20 - tmp3, player.y + 10 + tmp1, tmp2);
				backBuffer.set(player.x + 20 + tmp3, player.y + 10 - tmp1, tmp2);
				backBuffer.set(player.x + 20 + tmp3, player.y + 10 + tmp1, tmp2);

				if (player.dyingAnimation > 0)
					player.dyingAnimation--;
				else if (player.numberOfLives > 0) {
					player.numberOfLives = player.numberOfLives - 1;
					player.healthPoints = 100;
					player.x = -40;
					player.y = 75;
					player.cantBeHurt = false;
					player.cantBeHurtTime = 0;
				} else {
					player.shipSpriteIndex = -1;
					player.numberOfLives = -1;
				}
			}

			drawBulletsAndCalulateCollision();

			putPic(0, 190, 120, 10, spriteLibrary.statusBar);
			if (player.numberOfLives > -1)
				putPic(120, 190, 10, 10, spriteLibrary.statusIcons[player.numberOfLives][2]);

			for (i = 1; i <= (int) Math.round((player.healthPoints / 100.0) * 115); i++) {
				for (j = 3; j <= 6; j++) {
					backBuffer.set(2 + i, 190 + j, 2);
				}

			}

			for (m = 0; m < 4; m++) {
				if (player.weapons[m+1] == 0)
					putPic(270 + m * 10, 190, 10, 10, spriteLibrary.statusIcons[m][0]);
				else if ((player.weapons[m+1] > 0) && (player.weapons[0] != m + 1))
					putPic(270 + m * 10, 190, 10, 10, spriteLibrary.statusIcons[m][1]);
				else if ((player.weapons[m+1] > 0) && (player.weapons[0] == m + 1))
					putPic(270 + m * 10, 190, 10, 10, spriteLibrary.statusIcons[m][2]);
			} // end for m

			if (!bosses.isEmpty()) {
				for (Enemy boss : bosses) {

					putPic(0, 0, 120, 10, spriteLibrary.statusBar);
					writeXY(125, 0, 4, "BOSS");
					for (i = 1; i <= (int) Math.round(((double)boss.life / Life_E[boss.kind]) * 115.0); i++) {
						for (j = 3; j <= 6; j++) {
							backBuffer.set(2 + i, j, 4);
						}
					}
				}
			} // end if bosses not empty

			if (player.numberOfLives < 0)
				writeXY(127, 117, 5, "GAME OVER");
			if (cheat)
				writeXY(0, 0, 7, "cheat on");

			if (currentLevel == 3) {
				writeXY(127, 117, 5, "YOU WON !");
			} else if (areaScroll > 25 && areaScroll < 200) {
				if (areaScroll >= 25 && areaScroll <= 124) {
					String levelText = "LEVEL " + (currentLevel + 1);
					writeXY(137, (int) Math.round(90 - ((125 - areaScroll)) * (1 - sqr(Math.sin((areaScroll - 25) / 10.0)))), 5, levelText);
				} else if (areaScroll >= 125 && areaScroll <= 149) {
					String levelText = "LEVEL " + (currentLevel + 1);
					writeXY(137, 90, 5, levelText);
				} else if (areaScroll >= 150 && areaScroll <= 199) {
					putPic(154 - (areaScroll - 150) * 4, 84 - areaScroll + 150, 16, 16, spriteLibrary.wrecks[0]);
					putPic(166 + (areaScroll - 150) * 4, 84 - areaScroll + 150, 16, 16, spriteLibrary.wrecks[1]);
					putPic(154 - (areaScroll - 150) * 4, 96 + areaScroll - 150, 16, 16, spriteLibrary.wrecks[2]);
					putPic(166 + (areaScroll - 150) * 4, 96 + areaScroll - 150, 16, 16, spriteLibrary.wrecks[3]);
				}

			} // else if currentArea / areaScroll

			scoreText = "Score: " + spaced(String.valueOf(scores));
			writeXY(165, 0, 2, scoreText);

			canvas.repaint();
			waitInGraphicThread(startRound);
		} while (!quit);

		anim2();
		splash = 63;
		paletteBrightness(splash);
		drawPoints(10);

		/*
		 * if (Pontok > Pontszam[9].Pontszam ) { Str(Pontok,Ponts); Ponts =
		 * "    You have reached "+Spaced(Ponts)+" !!";
		 * writeXY(1,170,0,Ponts); writeXY(0,170,3,Ponts);
		 * writeXY(1,182,0,"Enter Name"); writeXY(0,182,4,"Enter Name");
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
		 * DrawPoints(10); writeXY(1,182,0,"Enter Name");
		 * writeXY(0,182,4,"Enter Name"); writeXY(121,182,0,PontSzov);
		 * writeXY(120,182,1,PontSzov);
		 *
		 * String endScoreText =
		 * "    You have reached "+spaced(String.valueOf(scores))+" !!";
		 * writeXY(1,170,0,endScoreText); writeXY(0,170,3,endScoreText);
		 * gun.setScr(); } while(!Quit); Pontszam[9].Pontszam = Pontok;
		 * Pontszam[9].Nev = Pontszov; k = 9; l = 10; do { if (Pontok >
		 * Pontszam[k-1].Pontszam) { Pontszam[k] = Pontszam[k-1];
		 * Pontszam[k-1].Pontszam = Pontok; Pontszam[k-1].Nev = Pontszov; l =
		 * k-1; } k--; } while(k != 0);
		 *
		 * backBuffer.clear(148); drawPoints(l); Anim(1); canvas.repaint(); }
		 *
		 */

		ch = keyBuffer.readKey();

		do {
			startRound = System.currentTimeMillis();
			splash = splash - 2;
			paletteBrightness(splash);
			waitInGraphicThread(startRound);
		} while (splash > 1);

		removeStars();
		killAllThings();
		saveScore();
	} // end public void game

	public void waitInGraphicThread(long startOfRound) throws InterruptedException {
		timeSpentRunning = System.currentTimeMillis() - startOfRound;
		Thread.sleep(Math.max(10l, 30l - timeSpentRunning));
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

		backBuffer.clear(1);
		writeXY(116, 5, 5, NAME_OF_THE_GAME);
		writeXY(115, 6, 5, NAME_OF_THE_GAME);
		writeXY(114, 5, 5, NAME_OF_THE_GAME);
		writeXY(115, 4, 5, NAME_OF_THE_GAME);
		writeXY(115, 5, 148, NAME_OF_THE_GAME);

		for (MP2 = 0; MP2 <= MaxMenu; MP2++) {
			writeXY(70, 71 + 20 * MP2, 0, MenuSzov[MP2]);
			writeXY(71, 71 + 20 * MP2, 0, MenuSzov[MP2]);
			writeXY(70, 70 + 20 * MP2, 6, MenuSzov[MP2]);
			if (MP2 == 1) {
				writeXY(200, 71 + 20 * MP2, 0, DStr);
				writeXY(201, 71 + 20 * MP2, 0, DStr);
				writeXY(200, 70 + 20 * MP2, 4, DStr);
			}
		}
		putPic(5, 60 + 20 * menuItem, 50, 30, spriteLibrary.ships[1]);
	}

	public void menu() {
		boolean menuQuit = false;

		if (changed) {
			backBuffer.clear(0);
			canvas.repaint();
			splash = 63;
			paletteBrightness(splash);
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

	public void credits() throws InterruptedException {
		int pos = 0;
		int p = 0;

		backBuffer.clear(0);
		canvas.repaint();

		splash = 1;
		paletteBrightness(splash);
		backBuffer.clear(148);

		canvas.repaint();

		pos = 0;

		long startRound;

		do {
			startRound = System.currentTimeMillis();
			splash += 2;
			paletteBrightness(splash);
			waitInGraphicThread(startRound);
		} while (splash >= 62);
		splash = 63;

		paletteBrightness(splash);

		do {
			backBuffer.clear(148);
			pos = pos + 1;
			for (p = 0; p <= MaxCredit; p++) {
				writeXY(161 - CreditSzov[p].length() * 9 / 2, 200 + p * 20 - pos, 0, CreditSzov[p]);
				writeXY(160 - CreditSzov[p].length() * 9 / 2, 201 + p * 20 - pos, 0, CreditSzov[p]);
				writeXY(160 - CreditSzov[p].length() * 9 / 2, 200 + p * 20 - pos, 2, CreditSzov[p]);
			}

			canvas.repaint();
		} while (pos < 400 + (MaxCredit + 1) * 20 && !frame.getControl().isEscapeOn());

	}

} // end of KLONGUN
