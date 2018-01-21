package hu.csega.games.library.xml.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.games.library.mwc.v1.MWCSimpleMesh;
import hu.csega.games.library.mwc.v1.MWCSphere;
import hu.csega.games.library.mwc.v1.MWCVertex;

public class XmlWMCHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		List<MWCVertex> vertices = new ArrayList<>();
		vertices.add(new MWCVertex(0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f));
		vertices.add(new MWCVertex(0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f));
		vertices.add(new MWCVertex(1f, 0f, 0f, 0f, 1f, 0f, 1f, 0f));

		List<Integer> indices = new ArrayList<>();
		indices.add(0);
		indices.add(1);
		indices.add(2);

		List<MWCSphere> spheres = new ArrayList<>();
		spheres.add(new MWCSphere(1f, 2f, 3f, 4f));

		MWCSimpleMesh mesh = new MWCSimpleMesh();
		mesh.setName("test mesh");
		mesh.setTexture("texture.jpg");
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		mesh.setSpheres(spheres);

		XmlFormat format = new XmlMWCFormat();
		XmlConverter converter = new XmlConverter(format);
		converter.write(System.out, mesh);
	}

}
