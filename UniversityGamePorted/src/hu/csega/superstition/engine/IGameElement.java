package hu.csega.superstition.engine;

interface IGameElement extends IClipping, IPeriod, IGameObject
{
	void SetModel(Model model);
}