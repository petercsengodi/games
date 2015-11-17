package hu.csega.alpoc.game.graphics;

import hu.csega.alpoc.frame.AlpocFrame;
import hu.csega.alpoc.game.processes.EngineProcess;

public class RenderingProcess extends EngineProcess {

	@Override
	protected void processIt() {
		AlpocFrame.redrawWorld();
	}
	
}
