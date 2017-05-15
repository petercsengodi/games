package hu.csega.games.vbg.swing;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public interface VBGAbstractGame extends MouseListener, MouseMotionListener, KeyListener {

	boolean needsRepaint();

	void paint(BufferedImage buffer);

	String compactIntoState();

	void loadState(String stateRepresentation);

}
