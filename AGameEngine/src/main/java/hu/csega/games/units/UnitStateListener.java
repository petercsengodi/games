package hu.csega.games.units;

public interface UnitStateListener<UNIT, STATE extends Enum<?>> {

	void onState(UNIT unit, STATE state);
	
}
