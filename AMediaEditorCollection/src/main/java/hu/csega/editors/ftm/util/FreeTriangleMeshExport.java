package hu.csega.editors.ftm.util;

import java.io.IOException;

import hu.csega.editors.FreeTriangleMeshToolStarter;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.editors.ftm.model.FreeTriangleMeshVertex;
import hu.csega.games.library.model.STextureRef;
import hu.csega.games.library.model.mesh.v1.SMesh;
import hu.csega.games.library.model.mesh.v1.SShape;
import hu.csega.games.library.model.mesh.v1.STriangle;
import hu.csega.games.library.model.mesh.v1.SVertex;
import hu.csega.games.library.xml.v1.XmlReflectionException;
import hu.csega.games.library.xml.v1.XmlWriter;

public class FreeTriangleMeshExport {

	public static void export(FreeTriangleMeshModel model, String filename) {
		String textureFilename = model.getTextureFilename();
		
		// FIXME ???
		if(textureFilename == null)
			textureFilename = FreeTriangleMeshToolStarter.DEFAULT_TEXTURE_FILE;
		
		STextureRef textureRef = new STextureRef(textureFilename);

		SMesh meshToExport = new SMesh();
		meshToExport.setName(filename);

		SShape shape = new SShape();
		shape.setTexture(textureRef);

		SVertex[] array = new SVertex[model.getVertices().size()];
		int pointer = 0;

		for(FreeTriangleMeshVertex vertex : model.getVertices()) {
			SVertex sv = new SVertex();
			sv.setPosition((float)vertex.getPX(), (float)vertex.getPY(), (float)vertex.getPZ());
			sv.setTextureCoordinates((float)vertex.getTX(), (float)vertex.getTY());

			// TODO: lots of other properties are not set

			array[pointer++] = sv;
		}

		for(FreeTriangleMeshTriangle triangle : model.getTriangles()) {
			STriangle st = new STriangle();
			st.setCount(3);
			st.getVertices().add(array[triangle.getVertex1()]);
			st.getVertices().add(array[triangle.getVertex2()]);
			st.getVertices().add(array[triangle.getVertex3()]);

			// TODO: lots of other properties are not set

			shape.getTriangles().add(st);
		}

		meshToExport.getFigures().add(shape);

		try(XmlWriter writer = new XmlWriter(filename)) {
			writer.write(meshToExport);
		} catch(XmlReflectionException | IOException ex) {
			throw new RuntimeException("Could not save XML", ex);
		}

	}

}
