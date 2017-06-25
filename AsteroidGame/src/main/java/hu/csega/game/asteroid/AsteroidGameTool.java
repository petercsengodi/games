package hu.csega.game.asteroid;

import hu.csega.toolshed.framework.ITool;
import hu.csega.units.DefaultImplementation;
import hu.csega.units.Unit;

@Unit
@DefaultImplementation(AsteroidGameToolImpl.class)
public interface AsteroidGameTool extends ITool {

}
