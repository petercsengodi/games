package hu.csega.superstition.game.menu;

interface IMenu : IDisposable
{
	MenuElement[] getMenuElements();
	IMenu getParent();
	void RenderElements();
	IMenu DoEscape();
	void setLastIndex(int idx);
	int getLastIndex();
}