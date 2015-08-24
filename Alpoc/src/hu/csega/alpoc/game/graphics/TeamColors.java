package hu.csega.alpoc.game.graphics;

import java.awt.Color;

public class TeamColors {

	private static Color[] colors = {Color.red, Color.green, Color.blue, Color.magenta};

	public static Color get(int team) {
		if(team < 0 || team >= colors.length) {
			throw new IllegalArgumentException("team="+team);
		}
		
		return colors[team];
	}
}
