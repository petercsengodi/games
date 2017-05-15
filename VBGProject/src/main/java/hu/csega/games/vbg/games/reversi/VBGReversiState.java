package hu.csega.games.vbg.games.reversi;

import java.io.Serializable;

public class VBGReversiState implements Serializable {

	public int numberOfFields;
	public int[][] fields;

	public VBGReversiState(int size) {
		this.numberOfFields = size;
		this.fields = new int[size][size];
	}

	private static final long serialVersionUID = 1L;
}
