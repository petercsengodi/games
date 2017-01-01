package hu.csega.game.rotary.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.engine.GameField;
import hu.csega.game.rotary.objects.RotaryMapElement;
import hu.csega.game.rotary.objects.RotaryPlayer;

public class RotaryUniverse extends GameField {

	public RotaryUniverse() {
		super(-399, 399, -299, 299);
	}

	public void init() {
		player = new RotaryPlayer(this);
	}

	public RotaryPlayer player;
	public List<RotaryMapElement> mapElements = new ArrayList<>();

}
