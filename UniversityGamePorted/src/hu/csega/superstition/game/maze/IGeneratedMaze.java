package hu.csega.superstition.game.maze;

interface IGeneratedMaze
{
	TwoWayLinkedGraph Generate();
//	object GetSerialData();
//	void UploadSerialData(object obj);

	void addStartRoom(Room room);
	ArrayList getStartPlaces();
}