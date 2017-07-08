package hu.csega.games.rotary.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.games.engine.intf.GameTimer;
import hu.csega.games.rotary.objects.RotaryMapElement;
import hu.csega.games.rotary.objects.RotaryPlayer;

public class RotaryModel implements GameKeyListener {

	public static final double BOX = 100;

	private RotaryPlayer player;
	private List<RotaryMapElement> mapElements = new ArrayList<>();

	private double verticalForce = 200;
	private double horizontalForce = 200;

	public void init() {
		player = new RotaryPlayer();

		for(double i = -350; i <= 350; i+= BOX) {
			RotaryMapElement me = new RotaryMapElement();
			me.movementPosition.x = i;
			me.movementPosition.y = -250;
			mapElements.add(me);
			me = new RotaryMapElement();
			me.movementPosition.x = i;
			me.movementPosition.y = 250;
			mapElements.add(me);
		}

		for(double i = -250 + BOX; i <= 250 - BOX; i+= BOX) {
			RotaryMapElement me = new RotaryMapElement();
			me.movementPosition.x = -350;
			me.movementPosition.y = i;
			mapElements.add(me);
			me = new RotaryMapElement();
			me.movementPosition.x = 350;
			me.movementPosition.y = i;
			mapElements.add(me);
		}
	}

	@Override
	public void hit(GameEngineFacade facade, char key) {
		if(key == 'w' && !player.turning) {
			player.turning = true;
			player.gravitationalPullToReach = player.gravitationalPull + Math.PI / 2.0;
		}
		if(key == 'q' && !player.turning) {
			player.turning = true;
			player.gravitationalPullToReach = player.gravitationalPull - Math.PI / 2.0;
		}
	}

	public void call(GameEngineFacade facade) {
		GameControl control = facade.control();
		if(control == null)
			return;

		GameTimer timer = facade.timer();
		long nanoTimeNow = timer.getNanoTimeNow();
		long nanoTimeLastTime = timer.getNanoTimeOnLastCall();

		double delta = (nanoTimeNow - nanoTimeLastTime) / 1_000_000_000.0;

		if(control.isUpOn() && !control.isDownOn())
			player.movementAcceleration.y = -verticalForce;
		else if(control.isDownOn() && !control.isUpOn())
			player.movementAcceleration.y = verticalForce;
		else
			player.movementAcceleration.y = 0;

		if(control.isLeftOn() && !control.isRightOn())
			player.movementAcceleration.x = -horizontalForce;
		else if(control.isRightOn() && !control.isLeftOn())
			player.movementAcceleration.x = horizontalForce;
		else
			player.movementAcceleration.x = 0;

		player.adjustGravitationalPull(delta);
		player.move(delta);
		player.checkConstraints();
	}

	public RotaryPlayer getPlayer() {
		return player;
	}

	public List<RotaryMapElement> getMapElements() {
		return mapElements;
	}
}
