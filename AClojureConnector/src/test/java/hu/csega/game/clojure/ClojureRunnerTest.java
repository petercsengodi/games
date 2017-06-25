package hu.csega.game.clojure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clojure.lang.Compiler;
import clojure.lang.RT;

public class ClojureRunnerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public static void main(String[] args) throws Exception {
		ClojureRunnerTest tester = new ClojureRunnerTest();
		tester.test();
	}

	@Test
	public void test() throws Exception {
		// Load the Clojure script -- as a side effect this initializes the runtime.
		RT.init();
		Object ret = Compiler.loadFile("res/test.clj");
		System.out.println("Returned by Clojure program: " + ret);
		if(ret != null) {
			System.out.println("Class: " + ret.getClass().getName());
		}
	}

}
