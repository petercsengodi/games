package hu.csega.games.library.swing.transformations;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.editors.swing.transformations.ScalingTransformation;
import hu.csega.games.engine.g2d.GamePoint;

public class ScalingTransformationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ScalingTransformation st = new ScalingTransformation();
		st.scale(2.0);
		assertTrue(nearTo(st.fromModelToCanvas(new GamePoint(1, 1)), 2, 2));
		assertTrue(nearTo(st.fromModelToCanvas(new GamePoint(-100, -50)), -200, -100));
		assertTrue(nearTo(st.fromCanvasToModel(new GamePoint(2, 2)), 1, 1));
		assertTrue(nearTo(st.fromCanvasToModel(new GamePoint(100, 50)), 50, 25));
	}

	private boolean nearTo(GamePoint target, double x, double y) {
		return Math.abs(target.x - x) < EPSILON && Math.abs(target.y - y) < EPSILON;
	}

	private static final double EPSILON = 0.00001;
}
