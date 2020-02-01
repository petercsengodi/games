package hu.csega.games.units;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUnitWithState<UNIT, STATE extends Enum<?>> extends AbstractUnit {

	public void registerStateListener(UnitStateListener<UNIT, STATE> stateListener) {
		if(listeners == null)
			listeners = new ArrayList<>();
		listeners.add(stateListener);
	}
	
	protected AbstractUnitWithState() {
	}
	
	@SuppressWarnings("unchecked")
	protected void naturalStoppingPoint(STATE state) {
		if(listeners != null) {
			for(UnitStateListener<UNIT, STATE> listener : listeners)
				listener.onState((UNIT)this, state);
		}
	}
	
	private List<UnitStateListener<UNIT, STATE>> listeners;
}
