package hu.csega.alpoc;

import hu.csega.alpoc.frame.AlpocFrame;
import hu.csega.dyn.Agent;

@Agent
public class Alpoc {
	public static final String VERSION = "v1.0.0000";


	public static void main(String[] args) throws Exception {
		AlpocFrame.start();
	}

}
