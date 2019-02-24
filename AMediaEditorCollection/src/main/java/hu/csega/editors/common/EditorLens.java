package hu.csega.editors.common;

public interface EditorLens {

	void fromModelToScreen(EditorPoint original);

	void fromScreenToModel(EditorPoint original);

}
