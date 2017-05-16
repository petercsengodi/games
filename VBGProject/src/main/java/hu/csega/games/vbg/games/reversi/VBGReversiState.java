package hu.csega.games.vbg.games.reversi;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VBGReversiState implements Serializable {

	public int numberOfFields;
	public int[][] fields;
	public int numberOfFreeFields;

	public List<VBGReversiPlayer> players = new ArrayList<>();
	public int currentPlayer = 0;

	public VBGReversiState(int size) {
		this.numberOfFields = size;
		this.fields = new int[size][size];
		this.numberOfFreeFields = size*size;

		VBGReversiPlayer player = new VBGReversiPlayer();
		player.index = 1;
		player.color = Color.blue;
		player.title = "Játékos";
		player.computer = false;
		this.players.add(player);

		VBGReversiPlayer computer = new VBGReversiPlayer();
		computer.index = 2;
		computer.color = Color.red;
		computer.title = "Számítógép";
		computer.computer = true;
		this.players.add(computer);
	}

	public VBGReversiPlayer getCurrentPlayer() {
		return players.get(currentPlayer);
	}

	public void switchToNextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}

	public boolean isComputerTurn() {
		return players.get(currentPlayer).computer;
	}

	public boolean putOneAndCheckIfThereIsFreeLeft() {
		return (numberOfFreeFields--) >= 0;
	}

	public boolean isFreeLeft() {
		return numberOfFreeFields >= 0;
	}
	private static final long serialVersionUID = 1L;
}
