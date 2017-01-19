package hu.csega.superstition.game.idontknow;

public class EndOfGame {

	public boolean finished;
	public String next_map;

	public EndOfGame(boolean finished)
	{
		this.finished = finished;
		this.next_map = null;
	}

	public EndOfGame(boolean finished, String next_map)
	{
		this.finished = finished;
		this.next_map = next_map;
	}

}