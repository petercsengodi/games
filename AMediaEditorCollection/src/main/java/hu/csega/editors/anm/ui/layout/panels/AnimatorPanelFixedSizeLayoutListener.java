package hu.csega.editors.anm.ui.layout.panels;

import java.awt.Component;

public abstract class AnimatorPanelFixedSizeLayoutListener implements AnimatorPanelLayoutChangeListener {

	private int offsetX;
	private int offsetY;

	public AnimatorPanelFixedSizeLayoutListener(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	@Override
	public final void arrange(Component component, int width, int height) {
		resize(component, offsetX, offsetY, width, height);
	}

	protected abstract void resize(Component component, int offsetX, int offsetY, int width, int height);

}
