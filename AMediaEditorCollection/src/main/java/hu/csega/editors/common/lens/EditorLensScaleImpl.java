package hu.csega.editors.common.lens;

public class EditorLensScaleImpl implements EditorLens {

	private EditorPoint scaling = new EditorPoint(1.0, 1.0, 1.0, 1.0);

	public EditorPoint getScaling() {
		return scaling;
	}

	public void setScaling(EditorPoint scaling) {
		this.scaling = scaling;
	}

	@Override
	public void fromModelToScreen(EditorPoint original) {
		original.setX(original.getX() * scaling.getX());
		original.setY(original.getY() * scaling.getY());
		original.setZ(original.getZ() * scaling.getZ());
		original.setW(original.getW() * scaling.getW());
	}

	@Override
	public void fromScreenToModel(EditorPoint original) {
		original.setX(original.getX() / scaling.getX());
		original.setY(original.getY() / scaling.getY());
		original.setZ(original.getZ() / scaling.getZ());
		original.setW(original.getW() / scaling.getW());
	}

}
