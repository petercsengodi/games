package hu.csega.superstition.game;

import java.util.Calendar;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.intf.GameGraphics;

public class SuperstitionGameRenderer {

	private static final float CLOCK_BOX_SIZE = 20f;
	private static final float CLOCK_BASE_X = 0f;
	private static final float CLOCK_BASE_Y = 0f;
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
		clockLocation.position.set(CLOCK_BASE_X, CLOCK_BASE_Y + 2 * CLOCK_BOX_SIZE, CLOCK_BASE_Z);
		g.drawModel(elements.boxModel2, clockLocation);
		clockLocation.position.set(CLOCK_BASE_X, CLOCK_BASE_Y + 4 * CLOCK_BOX_SIZE, CLOCK_BASE_Z);
		g.drawModel(elements.boxModel2, clockLocation);

		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR);

		String minutes = CLOCK_NUMBERS[min];
		float offset = 2;
		for(int i = 0; i < minutes.length(); i++) {
			char c = minutes.charAt(i);
			draw(offset, c, g, clockLocation, elements.boxModel1);
			offset += sizeOf(c) + 1;
		}

		String hours = CLOCK_NUMBERS[hour];
		offset = 0;
		for(int i = hours.length() - 1; i >= 0; i--) {
			char c = hours.charAt(i);
			offset -= sizeOf(c) + 1;
			draw(offset, c, g, clockLocation, elements.boxModel1);
		}
	}

	private static float sizeOf(char c) {
		if(c == '-')
			return 3;
		if(c == 'I')
			return 1;
		if(c == 'V')
			return 5;
		if(c == 'L')
			return 4;
		if(c == 'X')
			return 4;
		return -1;
	}

	private static void draw(float x, char c, GameGraphics g, GameObjectLocation loc, GameObjectHandler box) {
		if(c == '-') {
			box(x + 0, 3, g, loc, box);
			box(x + 1, 3, g, loc, box);
			box(x + 2, 3, g, loc, box);
		}
		if(c == 'I') {
			box(x + 0, 0, g, loc, box);
			box(x + 0, 1, g, loc, box);
			box(x + 0, 2, g, loc, box);
			box(x + 0, 3, g, loc, box);
			box(x + 0, 4, g, loc, box);
			box(x + 0, 5, g, loc, box);
			box(x + 0, 6, g, loc, box);
		}
		if(c == 'V') {
			box(x + 0, 6, g, loc, box);
			box(x + 4, 6, g, loc, box);
			box(x + 0, 5, g, loc, box);
			box(x + 4, 5, g, loc, box);
			box(x + 0.5f, 4, g, loc, box);
			box(x + 3.5f, 4, g, loc, box);
			box(x + 0.5f, 3, g, loc, box);
			box(x + 3.5f, 3, g, loc, box);
			box(x + 1f, 2, g, loc, box);
			box(x + 3f, 2, g, loc, box);
			box(x + 1.5f, 1, g, loc, box);
			box(x + 2.5f, 1, g, loc, box);
			box(x + 2f, 0, g, loc, box);
		}
		if(c == 'L') {
			box(x + 0, 0, g, loc, box);
			box(x + 1, 0, g, loc, box);
			box(x + 2, 0, g, loc, box);
			box(x + 3, 0, g, loc, box);
			box(x + 0, 1, g, loc, box);
			box(x + 0, 2, g, loc, box);
			box(x + 0, 3, g, loc, box);
			box(x + 0, 4, g, loc, box);
			box(x + 0, 5, g, loc, box);
			box(x + 0, 6, g, loc, box);
		}
		if(c == 'X') {
			box(x + 0, 0, g, loc, box);
			box(x + 0, 6, g, loc, box);
			box(x + 0.5f, 1, g, loc, box);
			box(x + 0.5f, 5, g, loc, box);
			box(x + 1f, 2, g, loc, box);
			box(x + 1f, 4, g, loc, box);
			box(x + 1.5f, 3, g, loc, box);
			box(x + 2f, 2, g, loc, box);
			box(x + 2f, 4, g, loc, box);
			box(x + 2.5f, 1, g, loc, box);
			box(x + 2.5f, 5, g, loc, box);
			box(x + 3f, 0, g, loc, box);
			box(x + 3f, 6, g, loc, box);
		}
	}

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
