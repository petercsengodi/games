package hu.csega.superstition;

[Serializable]
class ModelData : GameObjectData
{
	public ModelData()
	{
		description = "Game Model Infromation v0.1";
	}

	public GameObjectData player;
	public GameObjectData[] rooms;
	public GameObjectData[] entrances;
	public GameObjectData[] game_elements;
	public GameObjectData[] start_places;
}
}