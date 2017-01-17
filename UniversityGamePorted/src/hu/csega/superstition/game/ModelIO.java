package hu.csega.superstition.game;

public class ModelIO
{
	public static void SaveModelToFile(Model model, string filename)
	{
		BinaryFormatter formatter = new BinaryFormatter();
		FileStream stream = File.Create(filename);
		GameObjectData data = model.GetDataModel();
		formatter.Serialize(stream, data);
		stream.Close();
	}

	public static void LoadModelFromFile(Model model, string filename)
	{
		BinaryFormatter formatter = new BinaryFormatter();
		FileStream stream = File.Open(filename, FileMode.Open);
		GameObjectData data = formatter.Deserialize(stream) as GameObjectData;
		model.SetDataModel(data);
		stream.Close();
	}

	public static GameObjectData ExtractModelData(byte[] buffer)
	{
		BinaryFormatter formatter = new BinaryFormatter();
		MemoryStream stream = new MemoryStream(buffer);
		GameObjectData ret = formatter.Deserialize(stream) as GameObjectData;
		stream.Close();
		return ret;
	}

}
