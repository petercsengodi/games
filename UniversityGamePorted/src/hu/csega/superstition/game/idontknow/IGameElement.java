package hu.csega.superstition.game.idontknow;

interface IGameElement : IClipping, IPeriod, IGameObject
{
	void SetModel(Model model);
}