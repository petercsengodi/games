package hu.csega.superstition.game.maze;

interface IGeneratedMaze {
	TwoWayLinkedGraph generate();

	void addStartRoom(Room room);

	ArrayList getStartPlaces();
}