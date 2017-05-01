package hu.csega.games.vbg.main;

import hu.csega.games.vbg.games.test.VBGTestGame;
import hu.csega.games.vbg.swing.VBGAbstractGame;

public enum VBGAllGames {

	SELECTOR(new VBGSelector()),
	TEST_GAME(new VBGTestGame());

	private VBGAbstractGame game;

	private VBGAllGames(VBGAbstractGame game) {
		this.game = game;
	}

	public VBGAbstractGame getGame() {
		return game;
	}

}
