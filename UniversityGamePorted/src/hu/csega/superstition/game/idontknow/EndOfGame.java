package hu.csega.superstition.game.idontknow;

public struct EndOfGame
{
	public bool finished;
	public string next_map;

	public EndOfGame(bool finished)
	{
		this.finished = finished;
		this.next_map = null;
	}

	public EndOfGame(bool finished, string next_map)
	{
		this.finished = finished;
		this.next_map = next_map;
	}

}