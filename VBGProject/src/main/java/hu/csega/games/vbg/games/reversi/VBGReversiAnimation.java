package hu.csega.games.vbg.games.reversi;

import java.awt.Color;

public class VBGReversiAnimation {

	public VBGReversiAnimation(int x, int y, Color from, Color to, int result) {
		this.x = x;
		this.y = y;
		this.fromColor = from;
		this.toColor = to;
		this.start = -1;
		this.delta = 0;
		this.speed = 0.002;
		this.result = result;
	}

	int x;
	int y;
	Color fromColor;
	Color toColor;
	long start;
	double delta;
	double speed;
	int result;

}
