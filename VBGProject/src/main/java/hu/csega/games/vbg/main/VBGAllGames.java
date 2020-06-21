package hu.csega.games.vbg.main;

import hu.csega.games.vbg.swing.VBGAbstractGame;
import hu.csega.games.vbg.games.greetings.VBGGreetingsGame;

public enum VBGAllGames {

	SELECTOR(new VBGSelector()),
	GREETINGS(new VBGGreetingsGame());

	private VBGAbstractGame game;

	private VBGAllGames(VBGAbstractGame game) {
		this.game = game;
	}

	public VBGAbstractGame getGame() {
		return game;
	}

}
