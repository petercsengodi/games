package hu.csega.superstition.game.menu;

interface IFileParent extends IMenu
{
	IMenu DoChildrenItem(string filename);
}