package hu.csega.editors.common.lens;

public class EditorLensTranslateImpl implements EditorLens {

	private EditorPoint translation = new EditorPoint(0.0, 0.0, 0.0, 0.0);

	public EditorPoint getTranslation() {
		return translation;
	}

	public void setTranslation(EditorPoint translation) {
		this.translation = translation;
	}

	@Override
	public void fromModelToScreen(EditorPoint original) {
		original.setX(original.getX() + translation.getX());
		original.setY(original.getY() + translation.getY());
		original.setZ(original.getZ() + translation.getZ());
		original.setW(original.getW() + translation.getW());
	}

	@Override
	public void fromScreenToModel(EditorPoint original) {
		original.setX(original.getX() - translation.getX());
		original.setY(original.getY() - translation.getY());
		original.setZ(original.getZ() - translation.getZ());
		original.setW(original.getW() - translation.getW());
	}
}
