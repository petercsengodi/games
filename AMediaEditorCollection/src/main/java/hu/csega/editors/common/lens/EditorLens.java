package hu.csega.editors.common.lens;

public interface EditorLens {

	void fromModelToScreen(EditorPoint original);

	void fromScreenToModel(EditorPoint original);

}
