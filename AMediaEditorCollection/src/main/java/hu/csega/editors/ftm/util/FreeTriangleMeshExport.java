package hu.csega.editors.ftm.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import hu.csega.editors.FreeTriangleMeshToolStarter;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.editors.ftm.model.FreeTriangleMeshVertex;
import hu.csega.games.engine.ftm.GameMesh;
import hu.csega.games.engine.ftm.GameTriangle;
import hu.csega.games.engine.ftm.GameVertex;

public class FreeTriangleMeshExport {

	public static void export(FreeTriangleMeshModel model, String filename) {
		String textureFilename = model.getTextureFilename();

		// FIXME ???
		if(textureFilename == null)
			textureFilename = FreeTriangleMeshToolStarter.DEFAULT_TEXTURE_FILE;

		GameMesh mesh = new GameMesh();
		mesh.setName(filename);
		mesh.setTexture(textureFilename);

		List<GameVertex> vertices = mesh.getVertices();
		for(FreeTriangleMeshVertex vertex : model.getVertices()) {
			GameVertex v = new GameVertex();
			v.setPX((float)vertex.getPX());
			v.setPY((float)vertex.getPY());
			v.setPZ((float)vertex.getPZ());
			v.setNX((float)vertex.getNX());
			v.setNY((float)vertex.getNY());
			v.setNZ((float)vertex.getNZ());
			v.setTX((float)vertex.getTX());
			v.setTY((float)vertex.getTY());
			vertices.add(v);
		}

		List<GameTriangle> triangles = mesh.getTriangles();
		for(FreeTriangleMeshTriangle triangle : model.getTriangles()) {
			GameTriangle t = new GameTriangle();
			t.setV1(triangle.getVertex1());
			t.setV2(triangle.getVertex2());
			t.setV3(triangle.getVertex3());
			triangles.add(t);
		}

		try(FileOutputStream stream = new FileOutputStream(filename)) {
			JSONObject json = mesh.toJSONObject();
			String string = json.toString(2);
			byte[] bytes = string.getBytes(UTF_8);
			stream.write(bytes);
		} catch(IOException ex) {
			throw new RuntimeException("Couldn't write file: " + filename, ex);
		} catch(JSONException ex) {
			throw new RuntimeException("Couldn't build JSON!", ex);
		}
	}

	private static final Charset UTF_8 = Charset.forName("UTF-8");
}
