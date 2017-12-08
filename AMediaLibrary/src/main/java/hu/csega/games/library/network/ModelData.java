package hu.csega.games.library.network;

public class ModelData extends GameObjectData
{
	public ModelData() {
		description = "Game Model Infromation v0.1";
	}

	public GameObjectData player;
	public GameObjectData[] rooms;
	public GameObjectData[] entrances;
	public GameObjectData[] game_elements;
	public GameObjectData[] start_places;

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}
