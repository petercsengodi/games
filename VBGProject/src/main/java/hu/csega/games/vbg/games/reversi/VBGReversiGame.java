package hu.csega.games.vbg.games.reversi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.csega.games.vbg.VirtualBoredGames;
import hu.csega.games.vbg.swing.VBGAbstractGame;

public class VBGReversiGame implements VBGAbstractGame {

	private boolean needsRepaint = false;
	private List<VBGReversiAnimation> animations = new ArrayList<>();
	private VBGReversiState state = null;

	private int fieldBorder = 2;

	private int fieldWidth = 0;
	private int fieldHeight = 0;
	private int puppetWidth = 0;
	private int puppetHeight = 0;

	private int defaultNumberOfFields = 20;

	public VBGReversiGame() {
	}

	@Override
	public boolean needsRepaint() {
		return needsRepaint || animations.size() > 0;
	}

	@Override
	public void paint(BufferedImage buffer) {

		if(state == null) {
			state = new VBGReversiState(defaultNumberOfFields);
		}

		fieldWidth = buffer.getWidth() / state.numberOfFields;
		puppetWidth = fieldWidth - 2 * fieldBorder;
		fieldHeight = buffer.getHeight() / state.numberOfFields;
		puppetHeight = fieldHeight - 2 * fieldBorder;

		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.setColor(Color.black);

		for(int x = 0; x <= state.numberOfFields; x++) {
			g.drawLine(x * fieldWidth, 0, x * fieldWidth, fieldHeight * state.numberOfFields);
		}

		for(int y = 0; y <= state.numberOfFields; y++) {
			g.drawLine(0, y * fieldHeight, fieldWidth * state.numberOfFields, y * fieldHeight);
		}

		for(int x = 0; x < state.numberOfFields; x++) {
			for(int y = 0; y < state.numberOfFields; y++) {
				int field = state.fields[x][y];
				if(field > 0) {
					Color c = (field == 1 ? Color.blue : Color.red);
					g.setColor(c);
					g.fillRect(x * fieldWidth + fieldBorder, y * fieldHeight + fieldBorder, puppetWidth, puppetHeight);
				}
			}
		}

		if(animations.size() > 0) {
			Iterator<VBGReversiAnimation> it = animations.iterator();
			VBGReversiAnimation animation = it.next();

			long now = System.currentTimeMillis();
			if(animation.start >= 0) {

				animation.delta = (now - animation.start) * animation.speed;
				int width = (int)(Math.min(1.0, animation.delta) * puppetWidth);

				if(animation.toColor != null) {
					g.setColor(animation.toColor);
					g.fillRect(animation.x * fieldWidth + fieldBorder, animation.y * fieldHeight + fieldBorder, width, puppetHeight);
				}

				if(animation.fromColor != null) {
					g.setColor(animation.fromColor);
					g.fillRect(animation.x * fieldWidth + fieldBorder + width, animation.y * fieldHeight + fieldBorder, puppetWidth - width, puppetHeight);
				}

				if(animation.delta >= 1) {
					state.fields[animation.x][animation.y] = animation.result;
					it.remove();
				}

			} else {
				animation.start = now;
			}

		}

		needsRepaint = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(animations.size() > 0) {
			return; // no input until animation is done
		}

		if(fieldWidth == 0 || fieldHeight == 0) {
			return;
		}

		int x = e.getX() / fieldWidth;
		int y = e.getY() / fieldHeight;

		if(x < 0 || y < 0 || x >= state.numberOfFields || y >= state.numberOfFields) {
			return;
		}

		animations.add(new VBGReversiAnimation(x, y, null, Color.blue, 1));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(animations.size() > 0) {
			return; // no input until animation is done
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public String compactIntoState() {
		makeSureAnimationsAreDone();
		return VirtualBoredGames.fromSerializableToString(state);
	}

	@Override
	public void loadState(String stateRepresentation) {
		state = (VBGReversiState) VirtualBoredGames.fromStringToSerializable(stateRepresentation);
		if(state == null) {
			state = new VBGReversiState(defaultNumberOfFields);
		}
	}

	private void makeSureAnimationsAreDone() {
		for(VBGReversiAnimation animation : animations) {
			state.fields[animation.x][animation.y] = animation.result;
		}

		animations.clear();
	}

}
