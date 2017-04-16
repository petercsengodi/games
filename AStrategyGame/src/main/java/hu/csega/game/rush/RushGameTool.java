package hu.csega.game.rush;

import hu.csega.toolshed.framework.ITool;
import hu.csega.units.DefaultImplementation;
import hu.csega.units.Unit;

@Unit
@DefaultImplementation(RushGameToolImpl.class)
public interface RushGameTool extends ITool {

}
