package hu.csega.games.library.swing.transformations;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.editors.swing.transformations.CenterTransformation;
import hu.csega.games.engine.g2d.GamePoint;

public class CenterTransformationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CenterTransformation ct = new CenterTransformation();
		ct.setCanvasSize(200.0, 100.0);
		assertTrue(nearTo(ct.fromModelToCanvas(new GamePoint(0, 0)), 100, 50));
		assertTrue(nearTo(ct.fromModelToCanvas(new GamePoint(-100, -50)), 0, 0));
		assertTrue(nearTo(ct.fromCanvasToModel(new GamePoint(0, 0)), -100, -50));
		assertTrue(nearTo(ct.fromCanvasToModel(new GamePoint(100, 50)), 0, 0));
	}

	private boolean nearTo(GamePoint target, double x, double y) {
		return Math.abs(target.x - x) < EPSILON && Math.abs(target.y - y) < EPSILON;
	}

	private static final double EPSILON = 0.00001;
}
