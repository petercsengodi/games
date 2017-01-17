package hu.csega.superstition.servertester;

[Serializable]
		class TestMapObject : GameObjectData
		{
			private Vector3[] mem;

			public TestMapObject()
			{
				description = "Map";
				mem = new Vector3[500];
			}
		}