package hu.csega.superstition.unported.game.maze;

import java.util.ArrayList;

import hu.csega.superstition.unported.storygenerator.Room;
import hu.csega.superstition.unported.storygenerator.TwoWayLinkedGraph;

interface IGeneratedMaze {
	TwoWayLinkedGraph generate();

	void addStartRoom(Room room);

	ArrayList getStartPlaces();
}