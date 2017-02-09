package hu.csega.klongun.sprites;

import hu.csega.klongun.imported.FileUtil;
import hu.csega.klongun.screen.Picture;

public class SpriteLibrary {

	public static final int MaxShips = 1;
	public static final int MaxFires = 3;
	public static final int MaxEnemy1 = 10;
	public static final int MaxLesers = 12;
	public static final int MaxDeaths = 4;
	public static final int MaxItem = 7;

	public Picture[] ships = new Picture[MaxShips]; // 30 x 40
	public Picture[][] fire = new Picture[MaxFires][3]; // 14 x 17
	public Picture[] enemies = new Picture[MaxEnemy1]; // 32 x 32
	public Picture[] bullets = new Picture[MaxLesers]; // 5 x 16
	public Picture[] wrecks = new Picture[MaxDeaths]; // 16 x 16
	public Picture[] items = new Picture[MaxItem]; // 16 x 16
	public Picture[][] statusIcons = new Picture[4][4]; // 10 x 10
	public Picture statusBar;

	public static SpriteLibrary createAndLoadLibrary() {
		SpriteLibrary sl = new SpriteLibrary();
		sl.load();
		return sl;
	}

	private void load() {
		int i = 0;
		int j = 0;

		// Every pictures start after 4 bytes

		for(i = 0; i < MaxShips; i++)
			ships[i] = FileUtil.loadPic("ship" + (i+1), 50, 30);
		for(i = 0; i < MaxItem; i++)
			items[i] = FileUtil.loadPic("i_" + (i+1), 16, 16);
		for(i = 0; i < MaxDeaths; i++)
			wrecks[i] = FileUtil.loadPic("ruin" + (i+1), 16, 16);
		for(i = 0; i < MaxLesers; i++)
			bullets[i] = FileUtil.loadPic("l" + (i+1), 16, 5);
		for(i = 0; i < MaxEnemy1; i++)
			enemies[i] = FileUtil.loadPic("a" + (i+1), 32, 32);
		statusBar = FileUtil.loadPic("status1", 120, 10);

		for(i = 0; i < 4; i++) {
			for(j = 0; j < 4; j++) {
				String picName = String.valueOf(i+1) + j;
				statusIcons[i][j] = FileUtil.loadPic(picName, 10, 10);
			}
		}

		for(i = 0; i < MaxFires; i++) {
			for(j = 0; j < 3; j++) {
				String picName = "s_fire" + String.valueOf(i+1) + (j+1);
				fire[i][j] = FileUtil.loadPic(picName, 17, 14);
			}
		}

	} // end of load

	private SpriteLibrary() {
	}
}
