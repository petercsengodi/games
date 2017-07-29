package hu.csega.game.play;

import hu.csega.toolshed.framework.ITool;
import hu.csega.units.DefaultImplementation;
import hu.csega.units.Unit;

@Unit
@DefaultImplementation(ComeOutAndPlayToolImpl.class)
public interface ComeOutAndPlayTool extends ITool {

}
