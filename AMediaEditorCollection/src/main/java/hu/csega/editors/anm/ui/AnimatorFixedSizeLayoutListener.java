package hu.csega.editors.anm.ui;

import java.awt.Component;

public abstract class AnimatorFixedSizeLayoutListener implements AnimatorLayoutChangeListener {

	private int offsetX;
	private int offsetY;

	public AnimatorFixedSizeLayoutListener(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	@Override
	public final void arrange(Component component, int width, int height) {
		resize(component, offsetX, offsetY, width, height);
	}

	protected abstract void resize(Component component, int offsetX, int offsetY, int width, int height);

}
