package hu.csega.games.library.swing.transformations;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.editors.swing.transformations.TranslationTransformation;
import hu.csega.games.engine.g2d.GamePoint;

public class TranslationTransformationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		TranslationTransformation tt = new TranslationTransformation();
		tt.setTranslation(10, 5);
		assertTrue(nearTo(tt.fromModelToCanvas(new GamePoint(1, 1)), 11, 6));
		assertTrue(nearTo(tt.fromModelToCanvas(new GamePoint(-100, -50)), -90, -45));
		assertTrue(nearTo(tt.fromCanvasToModel(new GamePoint(2, 2)), -8, -3));
		assertTrue(nearTo(tt.fromCanvasToModel(new GamePoint(100, 50)), 90, 45));
	}

	private boolean nearTo(GamePoint target, double x, double y) {
		return Math.abs(target.x - x) < EPSILON && Math.abs(target.y - y) < EPSILON;
	}

	private static final double EPSILON = 0.00001;
}
