package hu.csega.games.library.swing.transformations;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.editors.swing.transformations.StretchTransformation;
import hu.csega.games.engine.g2d.GameHitBox;
import hu.csega.games.engine.g2d.GamePoint;

public class StretchTransformationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		StretchTransformation st = new StretchTransformation();
		GameHitBox textureCoordinates = new GameHitBox(0.0, 0.0, 1.0, 1.0);
		GameHitBox smallCenteredBox = new GameHitBox(-100, -50, 100, 50);
		st.setBoundaryDifferences(textureCoordinates, smallCenteredBox);
		assertTrue(nearTo(st.fromModelToCanvas(new GamePoint(0, 0)), -100, -50));
		assertTrue(nearTo(st.fromModelToCanvas(new GamePoint(0.5, 0.5)), 0, 0));
		assertTrue(nearTo(st.fromModelToCanvas(new GamePoint(1, 1)), 100, 50));

		assertTrue(nearTo(st.fromCanvasToModel(new GamePoint(-100, -50)), 0, 0));
		assertTrue(nearTo(st.fromCanvasToModel(new GamePoint(0, 0)), 0.5, 0.5));
		assertTrue(nearTo(st.fromCanvasToModel(new GamePoint(100, 50)), 1, 1));
	}

	private boolean nearTo(GamePoint target, double x, double y) {
		return Math.abs(target.x - x) < EPSILON && Math.abs(target.y - y) < EPSILON;
	}

	private static final double EPSILON = 0.00001;
}
