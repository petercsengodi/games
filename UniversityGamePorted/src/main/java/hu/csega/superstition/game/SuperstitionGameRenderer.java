package hu.csega.superstition.game;

import java.util.Calendar;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.intf.GameGraphics;

public class SuperstitionGameRenderer {

	private static final float CLOCK_BOX_SIZE = 20f;
	private static final float CLOCK_BASE_X = 0f;
	private static final float CLOCK_BASE_Y = 100f;
	private static final float CLOCK_BASE_Z = -1000f;

	public void renderGame(GameEngineFacade facade, SuperstitionSerializableModel universe, SuperstitionGameElements elements) {
		GameGraphics g = facade.graphics();

		SuperstitionPlayer player = universe.player;

		GameObjectLocation cameraLocation = new GameObjectLocation();
		cameraLocation.position.x = (float)player.x;
		cameraLocation.position.y = (float)player.y;
		cameraLocation.position.z = (float)player.z;
		cameraLocation.rotation.x = (float)(player.movingRotation + player.sightHorizontalRotation);
		cameraLocation.rotation.y = (float)(player.sightVerticalRotation);
		g.placeCamera(cameraLocation);

		g.drawModel(elements.groundHandler, universe.groundLocation);
		g.drawModel(elements.testFTMModel, universe.testFTMLocation);
		g.drawModel(elements.figureFTMModel, universe.figureFTMLocation);
		g.drawModel(elements.faceFTMModel, universe.faceFTMLocation);

		g.drawModel(elements.boxModel, universe.boxLocation1);
		g.drawModel(elements.boxModel, universe.boxLocation2);
		g.drawModel(elements.boxModel, universe.boxLocation3);
		g.drawModel(elements.boxModel, universe.boxLocation4);

		drawClock(elements, g);
	}

	private void drawClock(SuperstitionGameElements elements, GameGraphics g) {
		GameObjectLocation clockLocation = new GameObjectLocation();
		clockLocation.position.set(CLOCK_BASE_X, CLOCK_BASE_Y, CLOCK_BASE_Z);
		clockLocation.position.set(CLOCK_BASE_X, CLOCK_BASE_Y, CLOCK_BASE_Z);
		g.drawModel(elements.clock_semi, clockLocation);

		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		String minutes = CLOCK_NUMBERS[min];
		float offset = 25f;
		for(int i = 0; i < minutes.length(); i++) {
			char c = minutes.charAt(i);
			float s = sizeOf(c);
			offset += s / 2f + 10f;
			draw(offset, c, g, clockLocation, elements);
			offset += s / 2f + 10f;
		}

		String hours = CLOCK_NUMBERS[hour];
		offset = -25f;
		for(int i = hours.length() - 1; i >= 0; i--) {
			char c = hours.charAt(i);
			float s = sizeOf(c);
			offset -= s / 2f + 10f;
			draw(offset, c, g, clockLocation, elements);
			offset -= s / 2f + 10f;
		}
	}

	private static float sizeOf(char c) {
		if(c == '-')
			return 80f;
		if(c == 'I')
			return 20f;
		if(c == 'V')
			return 120f;
		if(c == 'L')
			return 80f;
		if(c == 'X')
			return 120f;
		return -20f;
	}

	private static void draw(float x, char c, GameGraphics g, GameObjectLocation loc, SuperstitionGameElements elements) {
		loc.position.set(CLOCK_BASE_X + x, CLOCK_BASE_Y, CLOCK_BASE_Z);
		if(c == '-') {
			g.drawModel(elements.clock_hy, loc);
		}
		if(c == 'I') {
			g.drawModel(elements.clock_i, loc);
		}
		if(c == 'V') {
			g.drawModel(elements.clock_v, loc);
		}
		if(c == 'L') {
			g.drawModel(elements.clock_l, loc);
		}
		if(c == 'X') {
			g.drawModel(elements.clock_x, loc);
		}
	}

	@SuppressWarnings("unused")
	private static void box(float x, float y, GameGraphics g, GameObjectLocation loc, GameObjectHandler box) {
		loc.position.set(CLOCK_BASE_X + x*CLOCK_BOX_SIZE, CLOCK_BASE_Y + y * CLOCK_BOX_SIZE, CLOCK_BASE_Z);
		g.drawModel(box, loc);
	}

	private static final String[] CLOCK_NUMBERS = new String[] {
			"-",
			"I",
			"II",
			"III",
			"IV",
			"V",
			"VI",
			"VII",
			"VIII",
			"IX",
			"X",
			"XI",
			"XII",
			"XIII",
			"XIV",
			"XV",
			"XVI",
			"XVII",
			"XVIII",
			"XIX",
			"XX",
			"XXI",
			"XXII",
			"XXIII",
			"XXIV",
			"XXV",
			"XXVI",
			"XXVII",
			"XXVIII",
			"XXIX",
			"XXX",
			"XXXI",
			"XXXII",
			"XXXIII",
			"XXXIV",
			"XXXV",
			"XXXVI",
			"XXXVII",
			"XXXVIII",
			"XXXIX",
			"XL",
			"XLI",
			"XLII",
			"XLIII",
			"XLIV",
			"XLV",
			"XLVI",
			"XLVII",
			"XLVIII",
			"XLIX",
			"L",
			"LI",
			"LII",
			"LIII",
			"LIV",
			"LV",
			"LVI",
			"LVII",
			"LVIII",
			"LIX"
	};
}
