package hu.csega.game.boulderdash;

import hu.csega.toolshed.framework.ITool;
import hu.csega.units.DefaultImplementation;
import hu.csega.units.Unit;

@Unit
@DefaultImplementation(BoulderDashToolImpl.class)
public interface BoulderDashTool extends ITool {

}
