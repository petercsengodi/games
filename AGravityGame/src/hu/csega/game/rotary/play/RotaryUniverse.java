package hu.csega.game.rotary.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.engine.GameField;
import hu.csega.game.rotary.objects.RotaryMapElement;
import hu.csega.game.rotary.objects.RotaryPlayer;

public class RotaryUniverse extends GameField {

	public static final double BOX = 100;

	public RotaryUniverse() {
		super(-399, 399, -299, 299);
	}

	public void init() {
		player = new RotaryPlayer(this);

		for(double i = -350; i <= 350; i+= BOX) {
			RotaryMapElement me = new RotaryMapElement(this);
			me.movementPosition.x = i;
			me.movementPosition.y = -250;
			mapElements.add(me);
			me = new RotaryMapElement(this);
			me.movementPosition.x = i;
			me.movementPosition.y = 250;
			mapElements.add(me);
		}

		for(double i = -250 + BOX; i <= 250 - BOX; i+= BOX) {
			RotaryMapElement me = new RotaryMapElement(this);
			me.movementPosition.x = -350;
			me.movementPosition.y = i;
			mapElements.add(me);
			me = new RotaryMapElement(this);
			me.movementPosition.x = 350;
			me.movementPosition.y = i;
			mapElements.add(me);
		}
	}



	public RotaryPlayer player;
	public List<RotaryMapElement> mapElements = new ArrayList<>();

}
