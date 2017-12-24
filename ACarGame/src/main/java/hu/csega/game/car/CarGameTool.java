package hu.csega.game.car;

import hu.csega.toolshed.framework.ITool;
import hu.csega.units.DefaultImplementation;
import hu.csega.units.Unit;

@Unit
@DefaultImplementation(CarGameToolImpl.class)
public interface CarGameTool extends ITool {

}
