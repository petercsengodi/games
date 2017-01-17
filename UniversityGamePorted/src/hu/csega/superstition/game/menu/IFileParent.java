package hu.csega.superstition.game.menu;

interface IFileParent : IMenu
{
	IMenu DoChildrenItem(string filename);
}