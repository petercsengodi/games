package hu.csega.games.vbg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.games.vbg.swing.VBGFrame;

public class VirtualBoredGamesTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		VBGFrame frame = new VBGFrame();
		assertNotNull(frame.getCanvas());
	}

}
