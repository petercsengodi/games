package hu.csega.cube.editor.model;

import java.util.ArrayList;
import java.util.List;

public class CubeSheet {

	public void copyValuesInto(CubeSheet sheetTo) {
		sheetTo.cubePieces.clear();
		sheetTo.cubePieces.addAll(cubePieces);
	}

	public List<CubePiece> cubePieces = new ArrayList<>();

}
